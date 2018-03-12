package net.datascientists.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.dao.base.BaseDao;
import net.datascientists.entity.JMXLog;

@Repository("JMXDao")
public class JMXDao implements BaseDao<JMXLog>
{
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public JMXLog save(JMXLog entity)
    {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public void deleteSoft(JMXLog entity)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteHard(JMXLog entity)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<JMXLog> find(String searchName, Object searchVal)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<JMXLog> list()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<JMXLog> listDeleted()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
