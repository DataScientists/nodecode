package net.datascientists.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.entity.Roles;

@Repository
public class UserRoleDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
    public List<Roles> findAll(){
		final Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("type"));
        return (List<Roles>)crit.list();
    }

	private Criteria createEntityCriteria() {
		final Session session = sessionFactory.getCurrentSession();
		final Criteria crit = session.createCriteria(Roles.class);
		return crit;
	}
     
    public Roles findById(long id) {
    	final Session session = sessionFactory.getCurrentSession();
        return (Roles)session.get(Roles.class,id);
    }
     
    public Roles findByType(String type) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("type", type));
        return (Roles) crit.uniqueResult();
    }
	
}
