/**
 * @(#)AbcStorageDbImpl.java, Dec 12, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.storage.impl;

import com.coder4.sbmvt.abc.server.data.Abc;
import com.coder4.sbmvt.abc.server.storage.spi.AbcStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author coder4
 */
@Repository
public class AbcStorageDbImpl implements AbcStorage {

    @Autowired
    private NamedParameterJdbcTemplate writer;

    private static final RowMapper<Abc> ABC_ROW_MAPPER = new BeanPropertyRowMapper<>(Abc.class);

    @Override
    public int saveAbc(Abc abc) {
        // obj -> param
        SqlParameterSource param = new BeanPropertySqlParameterSource(abc);
        // db update
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO `abc`(`id`, `abc`) VALUES(:id, :abc)";
        writer.update(sql, param, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Optional<Abc> getAbc(int id) {
        String sql = "SELECT * FROM `abc` WHERE `id` = :id";

        try {
            return Optional.ofNullable(writer.queryForObject(
                    sql, new MapSqlParameterSource("id", id), ABC_ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }
}