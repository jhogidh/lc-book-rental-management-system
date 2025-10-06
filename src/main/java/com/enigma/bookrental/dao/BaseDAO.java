package com.enigma.bookrental.dao;

import java.util.List;

public interface BaseDAO<T, ID> {
    void save(T t);
    List<T> findAll();
    T findById(ID id);
    void delete(ID id);
}
