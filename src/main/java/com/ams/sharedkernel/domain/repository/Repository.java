package com.ams.sharedkernel.domain.repository;

import java.io.Serializable;
import java.util.List;

public interface Repository<T, ID extends Serializable> {
    public ID createOrUpdate(T entity);

    public void delete(ID entityId);

    public List<T> findAll();

    public T findById(ID entityId);

    public Page<T> findNextPageData(Page<T> page);
}
