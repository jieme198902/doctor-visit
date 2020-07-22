package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.JhiPersistentAuditEvent;

public interface JhiPersistentAuditEventMapper extends CommMapper<JhiPersistentAuditEvent> {
    /**
     * 最后一次登录成功的状态
     * @param principal
     * @return
     */
    JhiPersistentAuditEvent selectLastLoginInfo(String principal);
}
