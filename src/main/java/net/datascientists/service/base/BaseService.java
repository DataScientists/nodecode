package net.datascientists.service.base;

import java.util.List;

public interface BaseService<T> {

    public T save(T vo);
    public void deleteSoft(T vo);
    public void deleteHard(T vo);
    public List<T> find(String searchName, Object searchVal);
    public List<T> list();
    public List<T> listDeleted();
	
}
