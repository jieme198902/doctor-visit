package com.doctor.visit.service;

import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.domain.dto.JhiUserDto;
import com.doctor.visit.domain.param.UserDoctorParam;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JhiUserService {
    /**
     * 获取系统用户列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<JhiUserDto>> listJhiUser(JhiUser bus, Pageable pageable);

    /**
     * 获取系统用户信息
     *
     * @param bus
     * @return
     */
    ComResponse<JhiUser> oneJhiUser(JhiUser bus);

    /**
     * 把用户id置为医生id
     * @param bus
     * @return
     */
     ComResponse updateUserIdToDoctorId(UserDoctorParam bus);
}
