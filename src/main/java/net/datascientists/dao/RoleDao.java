package net.datascientists.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.dao.base.BaseDao;
import net.datascientists.entity.Role;

@Repository
public class RoleDao implements BaseDao<Role>{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@SuppressWarnings("unchecked")
    public List<Role> list(){
	    final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(Role.class);
        crit.addOrder(Order.asc("name"));
        return (List<Role>)crit.list();
    }
	
	@Override
    public Role findById(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Role)session.get(Role.class,id);
    }    

	
	public Role findByType(String type) {
		// TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object save(Role entity)
    {
        // TODO Auto-generated method stub
        return null;
    }

   
    @Override
    public void deleteSoft(Role entity)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteHard(Role entity)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<? extends Object> listDeleted()
    {
        // TODO Auto-generated method stub
        return null;
    }	
}
