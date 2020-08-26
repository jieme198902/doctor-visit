package com.doctor.visit.service;

import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.domain.SysRole;
import com.doctor.visit.domain.dto.JhiUserDto;
import com.doctor.visit.repository.JhiUserMapper;
import com.doctor.visit.repository.SysRoleMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    private final SysRoleMapper sysRoleMapper;

    public JhiUserService(JhiUserMapper jhiUserMapper, SysRoleMapper sysRoleMapper) {
        this.jhiUserMapper = jhiUserMapper;
        this.sysRoleMapper = sysRoleMapper;
    }

    /**
     * 获取系统用户列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<JhiUserDto>> listJhiUser(JhiUser bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        JhiUser record = new JhiUser();
        if (StringUtils.isNotBlank(bus.getLogin())) {
            record.setLogin(bus.getLogin());
        }
        Page<JhiUser> busList = (Page<JhiUser>) jhiUserMapper.select(record);
        List<JhiUserDto> jhiUserDtos = Lists.newArrayList();
        busList.getResult().forEach(jhiUser -> {
            JhiUserDto jhiUserDto = new JhiUserDto();
            BeanUtils.copyProperties(jhiUser, jhiUserDto);
            List<SysRole> sysRoles = sysRoleMapper.selectUserRole(jhiUserDto.getId());
            jhiUserDto.setSysRoles(sysRoles);
            jhiUserDtos.add(jhiUserDto);
        });
        return ComResponse.ok(jhiUserDtos, busList.getTotal());
    }
}
