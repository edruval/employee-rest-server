package com.edruval.employeemanagement.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    
    Optional<T> get(long id);
    
    List<T> getAll();
    
    long add(T t);
    
    void update(T t);
    
    void delete(long l);
    
}