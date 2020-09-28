package com.doctor.visit.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.WkFxtInsulinFee;
import com.doctor.visit.domain.excel.WkFxtInsulinExcel;
import com.doctor.visit.repository.WkFxtInsulinFeeMapper;
import com.doctor.visit.service.FrontService;
import com.doctor.visit.service.common.MailService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.google.common.collect.Maps;
import liquibase.pro.packaged.W;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FrontServiceImpl implements FrontService {
    private final Logger log = LoggerFactory.getLogger(FrontService.class);

    private final WkFxtInsulinFeeMapper wkFxtInsulinFeeMapper;

    @Value("${custom.rootPath}")
    private String rootPath;

    private final MailService mailService;

    public FrontServiceImpl(WkFxtInsulinFeeMapper wkFxtInsulinFeeMapper, MailService mailService) {
        this.wkFxtInsulinFeeMapper = wkFxtInsulinFeeMapper;
        this.mailService = mailService;
    }

    /**
     * 插入或者更新个人费用
     *
     * @param bus
     * @param request
     * @return
     */
    @Override
    public ComResponse<WkFxtInsulinFee> insertOrUpdateInsulinFee(WkFxtInsulinFee bus, HttpServletRequest request) {
        //根据开始时间、结束时间获取时间小时数
        Long ss = bus.getEndTime().getTime() - bus.getStartTime().getTime();
        double hours = ss / (60.0 * 60.0 * 1000.0);
        bus.setTotalTime(BigDecimal.valueOf(hours).setScale(2, BigDecimal.ROUND_HALF_UP));
        bus.setTotalFee(BigDecimal.valueOf(hours * 5.0).setScale(2, BigDecimal.ROUND_HALF_UP));
        bus.setMedicalFee(BigDecimal.valueOf(hours * 5.0 / 2.0).setScale(2, BigDecimal.ROUND_HALF_UP));
        bus.setNurseFee(bus.getMedicalFee());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = simpleDateFormat.format(bus.getStartTime());
        bus.setYearmonth(startTime.replace("-", "").substring(0, 6));
        //
        bus.setCreateTime(new Date());
        if (null != bus.getId()) {
            wkFxtInsulinFeeMapper.updateByPrimaryKeySelective(bus);
        } else {
            bus.setId(IDKeyUtil.generateId());
            wkFxtInsulinFeeMapper.insertSelective(bus);
        }
        return ComResponse.ok(bus);
    }

    /**
     * 根据月份获取个人费用
     *
     * @param bus
     * @return
     */
    @Override
    public ComResponse<List<WkFxtInsulinFee>> selectInsulinFeeByMonth(WkFxtInsulinFee bus) {
        return ComResponse.ok(wkFxtInsulinFeeMapper.select(bus));
    }

    /**
     * 生成表格
     *
     * @param bus
     * @param email
     * @return
     */
    @Override
    public ComResponse<WkFxtInsulinFee> generateExcel(WkFxtInsulinFee bus, String email) throws Exception {
        //查询这个月的总数
        List<WkFxtInsulinFee> fxtInsulinFees = wkFxtInsulinFeeMapper.select(bus);
        if (null == fxtInsulinFees || fxtInsulinFees.isEmpty()) {
            return ComResponse.fail("当月没有数据");
        }
        Map<Integer, List<WkFxtInsulinExcel>> mapData = Maps.newHashMap();
        log.info(Utils.toJson(bus) + "共取出" + fxtInsulinFees.size());
        //处理总费用
        List<WkFxtInsulinExcel> allData = Lists.newArrayList();
        WkFxtInsulinExcel allTotalBean = new WkFxtInsulinExcel();
        allTotalBean.setBedNo("总计");
        List<Integer> keys = Lists.newArrayList();

        for (WkFxtInsulinFee it : fxtInsulinFees) {
            WkFxtInsulinExcel copyIt = new WkFxtInsulinExcel();
            BeanUtils.copyProperties(it, copyIt);
            //处理个性化数据
            // (`id`, `bed_no`, `name`, `hospital_no`, `group_no`, `start_time`, `end_time`, `total_time`, `total_fee`, `medical_fee`, `nurse_fee`, `create_time`, `yearmonth`)
            // VALUES
            // (470047848579022848, 13, '张月兰', 560839, 1, '2020-09-27 04:09:00', '2020-09-30 04:09:00', 72.0, 360.0, 180.0, 180.0, '2020-09-27 04:09:10', '202009');
            copyIt.setBedNo(it.getBedNo() + "床");
            //医疗分组
            copyIt.setGroupNo(Utils.getGroupNoName(it.getGroupNo()));
            //床号
            copyIt.setHospitalNo(String.valueOf(it.getHospitalNo()));

            allData.add(copyIt);
            allTotalBean.setMonthTotalTime(allTotalBean.getMonthTotalTime().add(it.getTotalTime()));
            allTotalBean.setMonthTotalFee(allTotalBean.getMonthTotalFee().add(it.getTotalFee()));
            allTotalBean.setMonthTotalMedicalFee(allTotalBean.getMonthTotalMedicalFee().add(it.getMedicalFee()));
            allTotalBean.setMonthTotalNurseFee(allTotalBean.getMonthTotalNurseFee().add(it.getNurseFee()));

            allTotalBean.setTotalTime(allTotalBean.getMonthTotalTime());
            allTotalBean.setTotalFee(allTotalBean.getMonthTotalFee());
            allTotalBean.setMedicalFee(allTotalBean.getMonthTotalMedicalFee());
            allTotalBean.setNurseFee(allTotalBean.getMonthTotalNurseFee());


            List<WkFxtInsulinExcel> feeList = mapData.get(it.getGroupNo());
            if (null == feeList) {
                feeList = Lists.newArrayList();
                feeList.add(copyIt);
                mapData.put(it.getGroupNo(), feeList);
                keys.add(it.getGroupNo());
            } else {
                feeList.add(copyIt);
            }
        }
        //按照组 分组 分Sheet
        keys.sort(Comparator.comparingInt(o -> o));
        String year = bus.getYearmonth().substring(0, 4);
        String month = bus.getYearmonth().substring(4);
        String fileName = year + "年" + month + "月胰岛素泵.xlsx";
        String fileDir = rootPath + Constants.FXT + File.separator;
        File fileD = new File(fileDir);
        if (!fileD.exists()) {
            fileD.mkdirs();
        }
        fileName = fileDir + fileName;
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(file, WkFxtInsulinExcel.class).build();
            //导出汇总的数据
            WriteSheet writeAllSheet = EasyExcel.writerSheet(0, year + "." + month + "汇总").build();
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            allData.add(allTotalBean);

            excelWriter.write(allData, writeAllSheet);
            log.info(writeAllSheet.getSheetName() + allData.size());
            //导出组的数据
            for (Integer it : keys) {
                List<WkFxtInsulinExcel> perList = mapData.get(it);
                if (null != perList && !perList.isEmpty()) {

                    WkFxtInsulinExcel perTotalBean = new WkFxtInsulinExcel();
                    perTotalBean.setBedNo("总计");
                    perList.forEach(bean -> {
                        perTotalBean.setMonthTotalTime(perTotalBean.getMonthTotalTime().add(bean.getTotalTime()));
                        perTotalBean.setMonthTotalFee(perTotalBean.getMonthTotalFee().add(bean.getTotalFee()));
                        perTotalBean.setMonthTotalMedicalFee(perTotalBean.getMonthTotalMedicalFee().add(bean.getMedicalFee()));
                        perTotalBean.setMonthTotalNurseFee(perTotalBean.getMonthTotalNurseFee().add(bean.getNurseFee()));
                    });
                    perTotalBean.setTotalTime(perTotalBean.getMonthTotalTime());
                    perTotalBean.setTotalFee(perTotalBean.getMonthTotalFee());
                    perTotalBean.setMedicalFee(perTotalBean.getMonthTotalMedicalFee());
                    perTotalBean.setNurseFee(perTotalBean.getMonthTotalNurseFee());

                    perList.add(perTotalBean);
                    String sheetName = year + "." + month + Utils.getGroupNoName(it);
                    // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                    WriteSheet writeSheet = EasyExcel.writerSheet(it, sheetName).build();
                    log.info(writeSheet.getSheetName() + perList.size());
                    // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
                    excelWriter.write(perList, writeSheet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != excelWriter) {
                excelWriter.finish();
            }
        }
        //发送邮件
        mailService.sendEmailText(email, file.getName(), file.getName() + "，请从附件中下载", file);
        file.delete();
        return ComResponse.okMsg("已发送成功，请到邮箱查看");
    }
}
