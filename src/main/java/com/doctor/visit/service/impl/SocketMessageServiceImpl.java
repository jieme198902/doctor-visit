package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusSocketMessage;
import com.doctor.visit.repository.BusSocketMessageMapper;
import com.doctor.visit.service.SocketMessageService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class SocketMessageServiceImpl implements SocketMessageService {

    private final BusSocketMessageMapper busSocketMessageMapper;

    public SocketMessageServiceImpl(BusSocketMessageMapper busSocketMessageMapper){
        this.busSocketMessageMapper = busSocketMessageMapper;
    }
    /**
     * 后台 - 查询聊天记录
     * 排序: url?sort=edit_time,desc&sort=id,asc
     * @param bus
     * @param pageable
     * @return
     */
    @Override
    public ComResponse listSocketMessage(BusSocketMessage bus, Pageable pageable){
        if(pageable.getSort().isSorted()) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), Utils.orderBy(pageable));
        }else{
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        }
        Example example = new Example(BusSocketMessage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel", Constants.EXIST);

        if (StringUtils.isNotBlank(bus.getMessage())) {
            criteria.andLike("message", bus.getMessage() + "%");
        }

        Page<BusSocketMessage> busList = (Page<BusSocketMessage>) busSocketMessageMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }



    /**
     * 根据id删除聊天记录
     *
     * @param ids
     * @return
     */
    @Override
    public ComResponse<StringBuilder> deleteSocketMessage(String ids){
        if (StringUtils.isBlank(ids)) {
            return ComResponse.fail("ids为空");
        }
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusSocketMessage delRecord = new BusSocketMessage();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busSocketMessageMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

}
