package com.doctor.visit.service;

import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.JhiUserMapper;
import org.springframework.stereotype.Service;

/**
 * 公共方法
 */
@Service
public class CommonService {
    private final JhiUserMapper jhiUserMapper;

    public CommonService(JhiUserMapper jhiUserMapper) {
        this.jhiUserMapper = jhiUserMapper;
    }

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    public JhiUser getJhiUser(String username) {
        JhiUser record = new JhiUser();
        record.setLogin(username);
        return jhiUserMapper.selectOne(record);
    }
}
