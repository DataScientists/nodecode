package net.datascientists.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.entity.Role;

@Repository
public class UserRoleDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
    public List<Role> findAll(){
		final Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("name"));
        return (List<Role>)crit.list();
    }
	private Criteria createEntityCriteria() {
		final Session session = sessionFactory.getCurrentSession();
		final Criteria crit = session.createCriteria(Role.class);
		return crit;
	}    
    public Role findById(long id) {
    	final Session session = sessionFactory.getCurrentSession();
        return (Role)session.get(Role.class,id);
    }    
    public Role findByType(String type) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("name", type));
        return (Role) crit.uniqueResult();
    }	
}
