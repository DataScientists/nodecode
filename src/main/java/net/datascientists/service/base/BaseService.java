package net.datascientists.service.base;

import java.util.List;

public interface BaseService<T> {

    public Object save(T entity);
    public void deleteSoft(T entity);
    public void deleteHard(T entity);
    public Object findById(Long id);
    public List<? extends Object> list();
    public List<? extends Object> listDeleted();
	
}
