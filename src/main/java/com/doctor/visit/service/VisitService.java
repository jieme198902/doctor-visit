package com.doctor.visit.service;

import com.doctor.visit.domain.SysUser;
import com.doctor.visit.repository.SysUserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<SysUser>> list(HttpServletRequest request, Pageable pageable, String message) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        SysUser record = new SysUser();
        record.setAccount("admin");
        List<SysUser> users = sysUserMapper.select(record);

        return ResponseEntity.ok(users);
    }
}
