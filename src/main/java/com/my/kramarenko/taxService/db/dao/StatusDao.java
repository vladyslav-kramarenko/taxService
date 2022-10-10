package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.Status;

import java.util.List;

public interface StatusDao {
    List<Status> getAllStatuses() throws DBException;

    Status getStatus(int id) throws DBException;
}
