package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusDoctor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusDoctorMapper extends CommMapper<BusDoctor> {

    /**
     * 获取用户收藏的医生
     * @param userId
     * @return
     */
    List<BusDoctor> selectFavDoctor(@Param("userId") Long userId);

    /**
     * 微信端 获取医生列表 关注状态
     * @param userId
     * @return
     */
    List<BusDoctor> selectDoctorWithFav(@Param("userId") Long userId,@Param("name")String name,@Param("clincId") Long clincId);

}
