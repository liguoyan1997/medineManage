package com.it.cn.base;

import java.util.List;

/*
 *  extends ElasticsearchRepository<T,String>
 * */
public interface BaseDao<T> {
    <T> List<T> findList(T entity);

    T get(String id);

    void update(T entity);

    <D extends String> void delete(D id);

    void insert(T entity);
}
