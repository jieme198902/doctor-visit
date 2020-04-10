package com.doctor.visit.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 统一的mapper
 * @param <T>
 */
public interface CommMapper <T> extends Mapper<T>, MySqlMapper<T> {
}
