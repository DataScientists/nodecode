package net.datascientists.dao.base;

import java.util.List;

public interface BaseDao<T>
{

    public Object save(T entity);
    public void deleteSoft(T entity);
    public void deleteHard(T entity);
    public Object findById(Long id);
    public List<? extends Object> list();
    public List<? extends Object> listDeleted();

}
