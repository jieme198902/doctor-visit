package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusFile;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface BusFileMapper extends CommMapper<BusFile> {

    /**
     * 根据数据库名称和表明查询是否存在
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    int selectBusExist(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

    /**
     *
     * @param tableName
     * @param idField
     * @param busLongId
     * @param busStringId
     * @return
     */
    int selectBusByIdExist(@Param("tableName") String tableName, @Param("idField")String idField, @Param("busLongId") Long busLongId);

}
