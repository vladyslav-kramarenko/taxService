package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.Type;

import java.util.List;

public interface TypeDao {
    List<Type> getAllTypes() throws DBException;

    Type getType(String id) throws DBException;
}
