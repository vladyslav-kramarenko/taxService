package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.exception.DBException;

import java.util.List;

public interface TypeDAO {
    public List<Type> getAllTypes() throws DBException;
}
