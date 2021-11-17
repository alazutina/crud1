package com.anna.crud.repository;


import java.util.List;

interface GenericRepository<T,ID> {

    void deleteById(ID id);
    T save(T t);
    T update(T t);
    List<T> getAll();
    T getById(ID id);
}
