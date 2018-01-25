package net.datascientists.service.base;

import java.util.List;

public interface BaseService<T> {

    public T save(T entity);
    public void deleteSoft(T entity);
    public void deleteHard(T entity);
    public List<T> find(String searchName, Object searchVal);
    public List<T> list();
    public List<T> listDeleted();
	
}
