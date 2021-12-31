package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.domain.dto.BusArticleDto;
import com.doctor.visit.repository.*;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.service.ArticleService;
import com.doctor.visit.service.DictService;
import com.doctor.visit.service.common.CommonService;
import com.doctor.visit.service.common.UploadService;
import com.doctor.visit.web.rest.util.BeanConversionUtil;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 文章的信息
 *
 * @author kuanwang
 * @date 2020-06-29
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Value("${custom.rootPath}")
    private String rootPath;
    //已修改
    @Value("${custom.requestPath}")
    private String requestPath;
    //
    private final CommonService commonService;
    private final UploadService uploadService;
    private final DictService dictService;

    //
    private final BusFileMapper busFileMapper;
    private final BusArticleMapper busArticleMapper;
    private final BusArticleClassMapper busArticleClassMapper;
    private final BusRelationUserArticleMapper busRelationUserArticleMapper;
    private final BusRelationUserArticleShareMapper busRelationUserArticleShareMapper;

    public ArticleServiceImpl(BusArticleClassMapper busArticleClassMapper, BusArticleMapper busArticleMapper, CommonService commonService, UploadService uploadService, DictService dictService,BusFileMapper busFileMapper, BusRelationUserArticleMapper busRelationUserArticleMapper, BusRelationUserArticleShareMapper busRelationUserArticleShareMapper) {
        this.busArticleClassMapper = busArticleClassMapper;
        this.busArticleMapper = busArticleMapper;
        this.commonService = commonService;
        this.uploadService = uploadService;
        this.dictService = dictService;
        this.busFileMapper = busFileMapper;
        this.busRelationUserArticleMapper = busRelationUserArticleMapper;
        this.busRelationUserArticleShareMapper = busRelationUserArticleShareMapper;
    }


    /**
     * 后台 - 查询文章分类列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @Override
    public ComResponse listArticleClass(BusArticleClass bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusArticleClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel", Constants.EXIST);
        if (StringUtils.isNotBlank(bus.getName())) {
            criteria.andLike("name", bus.getName() + "%");
        }
        Page<BusArticleClass> busList = (Page<BusArticleClass>) busArticleClassMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 后台 - 新增或者更新文章分类
     *
     * @param bus
     * @return
     */
    @Override
    public ComResponse<BusArticleClass> insertOrUpdateArticleClass(BusArticleClass bus) {
        if (StringUtils.isBlank(bus.getName())) {
            return ComResponse.failBadRequest();
        }
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getId()) {
                busArticleClassMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                bus.setCreateTime(new Date());
                busArticleClassMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(bus);
    }

    /**
     * 根据id删除文章分类
     *
     * @param ids
     * @return
     */
    @Override
    public ComResponse<StringBuilder> deleteArticleClass(String ids) {
        if (StringUtils.isBlank(ids)) {
            return ComResponse.fail("ids参数为空。");
        }
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusArticleClass delRecord = new BusArticleClass();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busArticleClassMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }


    /**
     * 前台 - 获取文章列表
     * 排序: url?sort=edit_time,desc&sort=id,asc
     * @param bus
     * @param pageable
     * @return
     */
    @Override
    public ComResponse<List<BusArticleDto>> listArticle(BusArticle bus, Pageable pageable, HttpServletRequest request, boolean sys) throws Exception {
        if(pageable.getSort().isSorted()){
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(),Utils.orderBy(pageable));
        }else{
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        }
        Page<BusArticle> busList = null;
        if (sys) {
            //后台
            Example example = new Example(BusArticle.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isDel", Constants.EXIST);
            if (StringUtils.isNotBlank(bus.getTitle())) {
                criteria.andLike("title", bus.getTitle() + "%");
            }
            if (null != bus.getClassId()) {
                criteria.andEqualTo("classId", bus.getClassId());
            }
            busList = (Page<BusArticle>) busArticleMapper.selectByExample(example);
        } else {
            //前台
            Long userId = Utils.getUserIdWithoutException(request);
            busList = (Page<BusArticle>) busArticleMapper.selectArticleListWithFav(userId,bus.getClassId(),bus.getTitle());
        }

        ComResponse<List<BusDict>> requestPathCom = dictService.listDistByType("requestPath");
        if(requestPathCom.isSuccess()){
            requestPath = requestPathCom.getData().get(0).getDicValue();
        }

        List<BusArticleDto> busDtoList = Lists.newArrayList();
        busList.getResult().forEach(busArticle -> {
            if(!sys){
                busArticle.setContent(null);
            }
            busDtoList.add(BeanConversionUtil.beanToDto(busArticle, requestPath,busFileMapper));
        });
        return ComResponse.ok(busDtoList, busList.getTotal());
    }

    /**
     * 获取文章详情
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ComResponse<BusArticleDto> oneArticle(BusArticle bus, HttpServletRequest request) throws Exception {
        BusArticle busArticle = busArticleMapper.selectByPrimaryKey(bus.getId());
        if(Constants.EXIST.equals(busArticle.getIsDel())){
            return ComResponse.ok(BeanConversionUtil.beanToDto(busArticle,requestPath,busFileMapper));
        }
        return ComResponse.fail("文章已不存在！");
    }

    /**
     * 前台 - 获取我收藏的文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @Override
    public ComResponse<List<BusArticleDto>> listFavArticle(BusArticle bus, Pageable pageable, HttpServletRequest request) throws Exception {
        //获取用户的id
        bus.setCreateBy(Utils.getUserId(request));
        if (null == bus.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusArticle> busList = (Page<BusArticle>) busArticleMapper.selectFavArticle(bus.getCreateBy());
        List<BusArticleDto> busDtoList = Lists.newArrayList();
        ComResponse<List<BusDict>> requestPathCom = dictService.listDistByType("requestPath");
        if(requestPathCom.isSuccess()){
            requestPath = requestPathCom.getData().get(0).getDicValue();
        }
        busList.getResult().forEach(busArticle -> busDtoList.add(BeanConversionUtil.beanToDto(busArticle, requestPath,busFileMapper)));

        return ComResponse.ok(busDtoList, busList.getTotal());
    }

    /**
     * 新增或者更新文章
     * 静态化文章生成url
     *
     * @param bus
     * @param request 处理封面
     * @return
     */
    @Override
    public ComResponse<BusArticle> insertOrUpdateArticle(BusArticle bus, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getId()) {
                //设置html的静态化，并且维护url Long busId, String bus, String title, String forwardFrom, String content
                bus.setUrl(Utils.writeHtml(new Utils.BusHtml(bus.getId(), "bus_article", bus.getTitle(), bus.getForwardFrom(), bus.getContent()), rootPath));
                busArticleMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                //设置html的静态化，并且维护url Long busId, String bus, String title, String forwardFrom, String content
                bus.setUrl(Utils.writeHtml(new Utils.BusHtml(bus.getId(), "bus_article", bus.getTitle(), bus.getForwardFrom(), bus.getContent()), rootPath));
                busArticleMapper.insertSelective(bus);
            }
            //上传封面图
            BusFile busFile = new BusFile();
            busFile.setBus("bus_article");
            busFile.setBusId(bus.getId());
            busFile.setFileType(Constants.FILE_TYPE_IMG);
            uploadService.uploadFiles(busFile, request);

        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除文章分类
     *
     * @param ids
     * @return
     */
    @Override
    public ComResponse<StringBuilder> deleteArticle(String ids) {
        if (StringUtils.isBlank(ids)) {
            return ComResponse.fail("ids为空");
        }
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusArticle delRecord = new BusArticle();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busArticleMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

    /**
     * 前台 - 新增或者删除用户收藏的文章
     *
     * @param bus
     * @return
     */
    @Override
    public ComResponse insertOrUpdateRelationUserArticle(BusRelationUserArticle bus, HttpServletRequest request) throws Exception {
        //获取用户的id
        if (Utils.isBlank(bus.getArticleId())) {
            return ComResponse.fail("文章id为空");
        }
        //获取用户的id
        Long userId = Utils.getUserId(request);
        BusRelationUserArticle exist = new BusRelationUserArticle();
        exist.setUserId(userId);
        exist.setArticleId(bus.getArticleId());

        BusRelationUserArticle existCount = busRelationUserArticleMapper.selectOne(exist);
        //该用户没有收藏过该文章，或者取消收藏过该文章
        if (null == existCount) {
            //新增一条收藏，不管是del还是exist
            bus.setId(IDKeyUtil.generateId());
            bus.setUserId(userId);
            busRelationUserArticleMapper.insertSelective(bus);
        } else {
            existCount.setIsDel(bus.getIsDel());
            busRelationUserArticleMapper.updateByPrimaryKeySelective(existCount);
        }
        return ComResponse.okMsg("1".equals(bus.getIsDel()) ? "已取消收藏" : "已收藏");
    }

    /**
     * 前台 - 新增或者删除用户分享的文章
     *
     * @param bus
     * @return
     */
    @Override
    public ComResponse insertOrUpdateRelationUserArticleShare(BusRelationUserArticleShare bus, HttpServletRequest request) throws Exception {
        //获取用户的id
        if (Utils.isBlank(bus.getArticleId())) {
            return ComResponse.fail("文章id为空");
        }

        Long userId = Utils.getUserId(request);
        BusRelationUserArticleShare exist = new BusRelationUserArticleShare();
        exist.setUserId(userId);
        exist.setArticleId(bus.getArticleId());

        BusRelationUserArticleShare existCount = busRelationUserArticleShareMapper.selectOne(exist);
        //该用户没有收藏过该文章，或者取消收藏过该文章
        if (null == existCount) {
            //新增一条收藏，不管是del还是exist
            bus.setId(IDKeyUtil.generateId());
            bus.setUserId(userId);
            busRelationUserArticleShareMapper.insertSelective(bus);
        } else {
            existCount.setIsDel(bus.getIsDel());
            busRelationUserArticleShareMapper.updateByPrimaryKeySelective(existCount);
        }
        return ComResponse.okMsg("1".equals(bus.getIsDel()) ? "已删除分享的文章" : "已分享");
    }


    /**
     * 前台 - 获取我分享的文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @Override
    public ComResponse<List<BusArticleDto>> listArticleShare(BusArticle bus, Pageable pageable, HttpServletRequest request) throws Exception {
        //获取用户的id
        Long userId = Utils.getUserId(request);
        bus.setCreateBy(userId);
        if (null == bus.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusArticle> busList = (Page<BusArticle>) busArticleMapper.selectShareArticle(bus.getCreateBy());
        List<BusArticleDto> busDtoList = Lists.newArrayList();
        ComResponse<List<BusDict>> requestPathCom = dictService.listDistByType("requestPath");
        if(requestPathCom.isSuccess()){
            requestPath = requestPathCom.getData().get(0).getDicValue();
        }
        busList.getResult().forEach(busArticle -> busDtoList.add(BeanConversionUtil.beanToDto(busArticle, requestPath,busFileMapper)));
        return ComResponse.ok(busDtoList, busList.getTotal());
    }

}
