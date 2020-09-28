package com.doctor.visit.service;

import com.doctor.visit.domain.WkFxtInsulinFee;
import com.doctor.visit.web.rest.util.ComResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface FrontService {

    /**
     * 插入或者更新个人费用
     *
     * @param bus

     * @return
     */
    ComResponse<WkFxtInsulinFee> insertOrUpdateInsulinFee(WkFxtInsulinFee bus, HttpServletRequest request);

    /**
     * 根据月份获取个人费用
     * @param bus
     * @return
     */
    ComResponse<List<WkFxtInsulinFee>> selectInsulinFeeByMonth(WkFxtInsulinFee bus);

    /**
     * 生成表格
     * @param bus
     * @param email
     * @return
     */
    ComResponse<WkFxtInsulinFee> generateExcel(WkFxtInsulinFee bus,String email) throws Exception;

}
