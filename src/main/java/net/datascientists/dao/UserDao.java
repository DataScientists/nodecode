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
public class UserDao implements IUserDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User save(User user) {
		final Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
		return user;
	}
	
	@Override
	public User findById(Long id) {
		final Session session = sessionFactory.getCurrentSession();
		return (User)session.get(User.class, id);
	}
	
	@Override
	public User findByUserName(String userName) {
		final Session session = sessionFactory.getCurrentSession();
		final Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("userName", userName));
		return (User) crit.uniqueResult();
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> list(){
		final Session session = sessionFactory.getCurrentSession();
		final Criteria crit = session.createCriteria(User.class);
		return crit.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
   	public List<Note> listDeleted() {
    	final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("deleted", 1));
        return crit.list();
    }

    @Override
    public Object update(User entity)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteSoft(User entity)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteHard(User entity)
    {
        // TODO Auto-generated method stub
        
    }
}
