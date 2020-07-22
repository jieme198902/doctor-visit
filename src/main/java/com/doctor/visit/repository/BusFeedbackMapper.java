package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusFeedback;
import org.apache.ibatis.annotations.Param;

public interface BusFeedbackMapper extends CommMapper<BusFeedback> {

    Integer countFeedback(@Param("thisMonth")String thisMonth);
}
