package com.enigma.bookrental.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T, ID> {
    void save(T t);
    List<T> findAll();
    Optional<T> findById(ID id);
    void delete(ID id);
}
