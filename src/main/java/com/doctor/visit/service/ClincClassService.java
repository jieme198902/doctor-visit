package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusClincClass;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusClincClassMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 科室的业务层
 *
 * @author kuanwang
 * @date 2020-06-29
 */
@Service
public class ClincClassService {
    private final CommonService commonService;
    //
    private final BusClincClassMapper busClincClassMapper;

    public ClincClassService(CommonService commonService, BusClincClassMapper busClincClassMapper) {
        this.commonService = commonService;
        this.busClincClassMapper = busClincClassMapper;
    }

    /**
     * 获取科室列表
     *
     * @param BusClincClass
     * @param pageable
     * @return
     */
    public ComResponse<List<BusClincClass>> listClincClass(BusClincClass BusClincClass, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        BusClincClass.setIsDel(Constants.EXIST);
        Example example = new Example(BusClincClass.class);
        Example.Criteria criteria = example.createCriteria();

        Page<BusClincClass> busArticleList = (Page<BusClincClass>) busClincClassMapper.selectByExample(example);
        return ComResponse.ok(busArticleList.getResult(), busArticleList.getTotal());
    }

    /**
     * 新增或者更新科室
     * FIXME
     *
     * @param BusClincClass
     * @param request       这里需要处理文件
     * @return
     */
    public ComResponse<BusClincClass> insertOrUpdateClincClass(BusClincClass BusClincClass, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            BusClincClass.setEditTime(new Date());
            BusClincClass.setEditBy(jhiUser.getId());
            BusClincClass.setEditName(jhiUser.getFirstName());
            if (null != BusClincClass.getId()) {
                busClincClassMapper.updateByPrimaryKeySelective(BusClincClass);
            } else {
                BusClincClass.setId(IDKeyUtil.generateId());
                busClincClassMapper.insertSelective(BusClincClass);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(BusClincClass);
    }


    /**
     * 根据id删除科室
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteClincClass(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusClincClass delRecord = new BusClincClass();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busClincClassMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

}
