package com.doctor.visit.service;

import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.domain.BusRelationUserDoctor;
import com.doctor.visit.domain.dto.BusDoctorDto;
import com.doctor.visit.domain.dto.BusDoctorHospitalDto;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DoctorService {
    /**
     * 根据名称 查询医生和医院列表
     *
     * @param bus
     * @param pageable
     * @param request
     * @return
     * @throws Exception
     */
    ComResponse<BusDoctorHospitalDto> listDoctorOrHospital(BusHospital bus, Pageable pageable, HttpServletRequest request) throws Exception;

    /**
     * 后台 - 获取医生列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusDoctorDto>> listDoctor(BusDoctor bus, Pageable pageable, HttpServletRequest request, boolean sys) throws Exception;

    /**
     * 新增或者更新医生
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    ComResponse<BusDoctor> insertOrUpdateDoctor(BusDoctor bus, HttpServletRequest request);

    /**
     * 根据id删除医院
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteDoctor(String ids);

    /**
     * 前台 - 获取关注的医生列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusDoctorDto>> listFavDoctor(BusDoctor bus, Pageable pageable, HttpServletRequest request) throws Exception;

    /**
     * 前台 - 删除或者修改用户关注的医生
     *
     * @param bus
     * @return
     */
    ComResponse insertOrUpdateRelationUserDoctor(BusRelationUserDoctor bus, HttpServletRequest request) throws Exception;
}
