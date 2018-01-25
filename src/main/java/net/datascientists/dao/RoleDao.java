package net.datascientists.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.dao.base.BaseDao;
import net.datascientists.entity.Role;

@Repository("RoleDao")
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
	
	@SuppressWarnings("unchecked")
    @Override
    public List<Role> find(String searchName, Object searchVal)
    {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(Role.class);
        crit.add(Restrictions.eq(searchName, searchVal));
        return crit.list();
    }

    @Override
    public Role save(Role entity)
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
    public List<Role> listDeleted()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
