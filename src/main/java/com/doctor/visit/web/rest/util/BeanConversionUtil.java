package com.doctor.visit.web.rest.util;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.domain.BusFile;
import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.domain.dto.BusArticleDto;
import com.doctor.visit.domain.dto.BusDoctorDto;
import com.doctor.visit.domain.dto.BusHospitalDto;
import com.doctor.visit.repository.BusFileMapper;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * bean对象转换，克隆等
 */
public class BeanConversionUtil {

    /**
     * 医生转化
     * 从 bean 转为 Dto
     * @param bus
     * @return
     */
    public static BusDoctorDto beanToDto(BusDoctor bus, String requestPath, BusFileMapper busFileMapper) {
        BusDoctorDto busDto = new BusDoctorDto();
        BeanUtils.copyProperties(bus,busDto);
        BusFile busFile = new BusFile();
        busFile.setBus("bus_doctor");
        busFile.setFileType(Constants.FILE_TYPE_IMG);
        busFile.setBusId(bus.getId());
        List<BusFile> files = busFileMapper.select(busFile);
        if (null != files && !files.isEmpty()) {
            busDto.setImg(requestPath + files.get(0).getFilePath());
        }
        return busDto;
    }

    /**
     * 医院转化
     * 从 bean 转为 Dto
     * @param bus
     * @return
     */
    public static BusHospitalDto beanToDto(BusHospital bus, String requestPath, BusFileMapper busFileMapper) {
        BusHospitalDto busDto = new BusHospitalDto();
        BeanUtils.copyProperties(bus,busDto);
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
     * @param bus
     * @return
     */
    public static BusArticleDto beanToDto(BusArticle bus,String requestPath) {
        BusArticleDto busDto = new BusArticleDto();
        BeanUtils.copyProperties(bus,busDto);
        busDto.setCollect(bus.getIsDel());
        busDto.setUrl(requestPath+bus.getUrl());
        return busDto;
    }

}
