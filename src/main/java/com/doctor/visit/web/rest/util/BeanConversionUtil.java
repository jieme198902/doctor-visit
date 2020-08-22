package com.doctor.visit.web.rest.util;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.domain.dto.BusArticleDto;
import com.doctor.visit.domain.dto.BusDoctorDto;
import com.doctor.visit.domain.dto.BusHospitalDto;
import com.doctor.visit.domain.dto.BusOrderInquiryDto;
import com.doctor.visit.repository.BusDoctorMapper;
import com.doctor.visit.repository.BusFileMapper;
import com.doctor.visit.repository.BusGoodsInquiryMapper;
import com.doctor.visit.repository.BusPatientMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * bean对象转换，克隆等
 */
@Component
public class BeanConversionUtil {

    /**
     * 医生转化
     * 从 bean 转为 Dto
     *
     * @param bus
     * @return
     */
    public static BusDoctorDto beanToDto(BusDoctor bus, String requestPath, BusFileMapper busFileMapper, BusGoodsInquiryMapper busGoodsInquiryMapper, boolean sys) {
        BusDoctorDto busDto = new BusDoctorDto();
        BeanUtils.copyProperties(bus, busDto);
        BusFile busFile = new BusFile();
        busFile.setBus("bus_doctor");
        busFile.setFileType(Constants.FILE_TYPE_IMG);
        busFile.setBusId(bus.getId());
        List<BusFile> files = busFileMapper.select(busFile);
        if (null != files && !files.isEmpty()) {
            busDto.setImg(requestPath + files.get(0).getFilePath());
        }
        if (!sys) {
            //前端
            busDto.setAttention(bus.getIsDel());
            //获取该医生的商品
            BusGoodsInquiry listRecord = new BusGoodsInquiry();
            listRecord.setDoctorId(bus.getId());
            listRecord.setIsDel(Constants.EXIST);
            busDto.setGoodsInquiries(busGoodsInquiryMapper.select(listRecord));
        }
        return busDto;
    }

    /**
     * 医院转化
     * 从 bean 转为 Dto
     *
     * @param bus
     * @return
     */
    public static BusHospitalDto beanToDto(BusHospital bus, String requestPath, BusFileMapper busFileMapper) {
        BusHospitalDto busDto = new BusHospitalDto();
        BeanUtils.copyProperties(bus, busDto);
        busDto.setDistance(bus.getIsDel());
        BusFile busFile = new BusFile();
        busFile.setBus("bus_hospital");
        busFile.setFileType(Constants.FILE_TYPE_IMG);
        busFile.setBusId(bus.getId());
        List<BusFile> files = busFileMapper.select(busFile);
        if (null != files && !files.isEmpty()) {
            busDto.setImg(requestPath + files.get(0).getFilePath());
        }
        return busDto;
    }

    /**
     * 文章转换
     * 从 bean 转为 Dto
     *
     * @param bus
     * @return
     */
    public static BusArticleDto beanToDto(BusArticle bus, String requestPath, BusFileMapper busFileMapper) {
        BusArticleDto busDto = new BusArticleDto();
        BeanUtils.copyProperties(bus, busDto);
        busDto.setCollect(bus.getIsDel());
        busDto.setUrl(requestPath + bus.getUrl());
        //获取封面图
        BusFile busFile = new BusFile();
        busFile.setBus("bus_article");
        busFile.setFileType(Constants.FILE_TYPE_IMG);
        busFile.setBusId(bus.getId());
        List<BusFile> files = busFileMapper.select(busFile);
        if (null != files && !files.isEmpty()) {
            busDto.setCoverImg(requestPath + files.get(0).getFilePath());
        }
        return busDto;
    }


    /**
     * 问诊订单转换
     * 从 bean 转为 Dto
     *
     * @param bus
     * @return
     */
    public static BusOrderInquiryDto beanToDto(BusOrderInquiry bus, String requestPath, BusFileMapper busFileMapper, BusPatientMapper busPatientMapper, BusGoodsInquiryMapper busGoodsInquiryMapper,BusDoctorMapper busDoctorMapper) {
        BusOrderInquiryDto busDto = new BusOrderInquiryDto();
        BeanUtils.copyProperties(bus, busDto);
        //获取封面图
        BusFile busFile = new BusFile();
        busFile.setBus("bus_order_inquiry");
        busFile.setFileType(Constants.FILE_TYPE_IMG);
        busFile.setBusId(bus.getId());
        List<BusFile> files = busFileMapper.select(busFile);
        if (null != files && !files.isEmpty()) {
            StringBuilder stringBuffer = new StringBuilder();
            for (BusFile file : files) {
                stringBuffer.append(requestPath + file.getFilePath());
                stringBuffer.append(Constants.COMMA);
            }
            String imgs = stringBuffer.toString();
            if (imgs.endsWith(Constants.COMMA)) {
                imgs = imgs.substring(0, imgs.length() - 1);
            }
            busDto.setImgs(imgs);
        }
        //查询患者的信息
        BusPatient busPatient = busPatientMapper.selectByPrimaryKey(bus.getPatientId());
        busDto.setBusPatient(busPatient);
        //查询医生的信息
        BusDoctor doctor = busDoctorMapper.selectByPrimaryKey(bus.getDoctorId());
        busDto.setBusDoctor(beanToDto(doctor, requestPath, busFileMapper, busGoodsInquiryMapper,false));
        return busDto;
    }

}
