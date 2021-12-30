package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusSocketMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface BusSocketMessageMapper extends CommMapper<BusSocketMessage> {
}
