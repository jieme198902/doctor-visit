package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.JhiUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JhiUserMapper extends CommMapper<JhiUser> {

    /**
     * 把用户id置为医生id，先把权限给修改了
     * @param userId
     * @param doctorId
     * @return
     */
    int updateUserIdToDoctorIdBefore(@Param("userId")Long userId,@Param("doctorId")Long doctorId);

    /**
     * 把用户id置为医生id
     * @param userId
     * @param doctorId
     * @return
     */
    int updateUserIdToDoctorId(@Param("userId")Long userId,@Param("doctorId")Long doctorId);


}
