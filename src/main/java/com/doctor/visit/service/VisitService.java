package com.doctor.visit.service;

import com.doctor.visit.domain.SysUser;
import com.doctor.visit.repository.SysUserMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class VisitService {

    private final SysUserMapper sysUserMapper;

    public VisitService(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }


    /**
     * @param request
     * @param pageable
     * @param message
     * @return
     */
    public ComResponse<List<SysUser>> list(HttpServletRequest request, Pageable pageable, String message) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        SysUser record = new SysUser();
        record.setAccount("admin");
        Page<SysUser> users = (Page<SysUser>) sysUserMapper.select(record);

        return ComResponse.OK(users.getResult(), users.getTotal());
    }
}
