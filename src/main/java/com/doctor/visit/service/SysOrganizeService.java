package com.doctor.visit.service;

import com.doctor.visit.domain.SysDept;
import com.doctor.visit.domain.SysJob;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SysOrganizeService {
    /**
     * 查询部门列表
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<SysDept>> listDept(SysDept bus, Pageable pageable);



    /**
     * 新增或者修改部门
     * @param bus
     * @param request
     * @return
     */
    ComResponse<SysDept> insertOrUpdateDept(SysDept bus, HttpServletRequest request);

    /**
     * 根据id删除部门
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteDept(String ids);

    /**
     * 查询岗位列表
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<SysJob>> listJob(SysJob bus, Pageable pageable);

    /**
     * 新增或者修改岗位
     * @param bus
     * @param request
     * @return
     */
    ComResponse<SysJob> insertOrUpdateJob(SysJob bus, HttpServletRequest request);

    /**
     * 根据id删除岗位
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteJob(String ids);
}
