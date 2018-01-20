package net.datascientists.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.entity.UserRole;

@Repository
public class UserRoleDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
    public List<UserRole> findAll(){
		final Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("type"));
        return (List<UserRole>)crit.list();
    }

	private Criteria createEntityCriteria() {
		final Session session = sessionFactory.getCurrentSession();
		final Criteria crit = session.createCriteria(UserRole.class);
		return crit;
	}
     
    public UserRole findById(long id) {
    	final Session session = sessionFactory.getCurrentSession();
        return (UserRole)session.get(UserRole.class,id);
    }
     
    public UserRole findByType(String type) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("type", type));
        return (UserRole) crit.uniqueResult();
    }
	
}
