package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.JhiUserMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户
 *
 * @author kuanwang
 */
@Service
public class JhiUserService {

    private final JhiUserMapper jhiUserMapper;

    public JhiUserService(JhiUserMapper jhiUserMapper) {
        this.jhiUserMapper = jhiUserMapper;
    }

    /**
     * 获取系统用户列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<JhiUser>> listJhiUser(JhiUser bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        JhiUser record = new JhiUser();
        if (StringUtils.isNotBlank(bus.getLogin())) {
            record.setLogin(bus.getLogin());
        }
        Page<JhiUser> busList = (Page<JhiUser>) jhiUserMapper.select(record);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }
}
