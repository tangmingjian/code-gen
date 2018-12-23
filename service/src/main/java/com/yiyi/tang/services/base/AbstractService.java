package com.yiyi.tang.services.base;


import com.yiyi.tang.base.Mapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class AbstractService<T, ID> implements Service<T, ID> {

    @Autowired
    protected Mapper<T> mapper;

    private Class<T> modelClass;

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public Boolean save(T model) {
        return mapper.insertSelective(model) > 0;
    }

    public Boolean save(List<T> models) {
        return mapper.insertList(models) > 0;
    }

    public Boolean deleteById(ID id) {
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    public Boolean update(T model) {
        return mapper.updateByPrimaryKeySelective(model) > 0;
    }

    public T findById(ID id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public int count(T param) {
        return mapper.selectCount(param);
    }
}
