package com.anna.crud.repository;


import java.sql.SQLException;
import java.util.List;

interface GenericRepository<T,ID> {

    void deleteById(ID id) throws SQLException, ClassNotFoundException;
    T save(T t) throws SQLException, ClassNotFoundException;
    T update(T t) throws SQLException, ClassNotFoundException;
    List<T> getAll() throws ClassNotFoundException, SQLException;
    T getById(ID id) throws ClassNotFoundException, SQLException;


//    void deleteById(ID id);
//    T save(T t);
//    T update(T t);
//    List<T> getAll();
//    T getById(ID id);
}
