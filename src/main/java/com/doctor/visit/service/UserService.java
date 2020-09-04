package com.doctor.visit.service;

import com.doctor.visit.domain.BusFile;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.SysPermission;
import com.doctor.visit.domain.dto.BusUserDto;
import com.doctor.visit.domain.dto.SysMenuDto;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    /**
     * 登录接口
     *
     * @param jsCode
     * @return
     */
    ComResponse<BusUserDto> authenticate(String jsCode, HttpServletRequest httpServletRequest) throws Exception;

    /**
     * 获取系统用户菜单列表
     *
     * @param bus
     * @return
     */
    ComResponse<List<SysMenuDto>> listSysUserMenu(SysPermission bus);

    /**
     * 获取微信用户列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusUser>> listBusUser(BusUser bus, Pageable pageable);

    /**
     * 拉入黑名单
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> insertOrUpdateUserPullToBlacklist(String ids);

    /**
     * 根据 把用户从黑名单拉出来
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> insertOrUpdateUserPushFromBlacklist(String ids);

    /**
     * 前端 - 上传文件的接口
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusFile> uploadFile(BusFile bus, HttpServletRequest request) throws Exception;
}
