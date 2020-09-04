package com.doctor.visit.service.common;

import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusUserMapper;
import com.doctor.visit.repository.JhiUserMapper;
import org.springframework.stereotype.Service;

/**
 * 公共方法
 */
@Service
public class CommonService {
    private final JhiUserMapper jhiUserMapper;
    private final BusUserMapper busUserMapper;

    public CommonService(JhiUserMapper jhiUserMapper, BusUserMapper busUserMapper) {
        this.jhiUserMapper = jhiUserMapper;
        this.busUserMapper = busUserMapper;
    }

    /**
     * 获取后台用户信息
     *
     * @param username
     * @return
     */
    public JhiUser getJhiUser(String username) {
        JhiUser record = new JhiUser();
        record.setLogin(username);
        return jhiUserMapper.selectOne(record);
    }

    /**
     * 获取前端用户信息
     *
     * @param openid
     * @return
     */
    public BusUser getBusUser(String openid) {
        BusUser record = new BusUser();
        record.setWechatOpenid(openid);
        return busUserMapper.selectOne(record);
    }


    /**
     * 获取前端用户信息
     *
     * @param id
     * @return
     */
    public BusUser getBusUser(Long id) {
        return busUserMapper.selectByPrimaryKey(id);
    }
}
