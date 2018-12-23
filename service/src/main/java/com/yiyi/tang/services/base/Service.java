package com.yiyi.tang.services.base;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

public interface Service<T, ID> {
    Boolean save(T model);

    Boolean save(List<T> models);

    Boolean deleteById(ID id);

    void deleteByIds(String ids);//批量刪除 eg：ids -> “1,2,3,4”

    Boolean update(T model);

    T findById(ID id);

    T findBy(String fieldName, Object value) throws TooManyResultsException;

    List<T> findByIds(String ids);

    List<T> findByCondition(Condition condition);

    List<T> findAll();

    int count(T param);
}
