package net.datascientists.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.entity.Note;
import net.datascientists.entity.User;

@Repository
public class UserDao{

	@Autowired
	private SessionFactory sessionFactory;

	public void save(User user) {
		final Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
	}
	public User findById(long id) {
		final Session session = sessionFactory.getCurrentSession();
		return (User)session.get(User.class, id);
	}
	public User findByUserName(String userName) {
		final Session session = sessionFactory.getCurrentSession();
		final Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("userName", userName));
		return (User) crit.uniqueResult();
	}	
	@SuppressWarnings("unchecked")
	public List<User> list(){
		final Session session = sessionFactory.getCurrentSession();
		final Criteria crit = session.createCriteria(User.class);
		return crit.list();
	}
	@SuppressWarnings("unchecked")
   	public List<Note> listDeleted() {
    	final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("deleted", 1));
        return crit.list();
    }
}
