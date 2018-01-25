package net.datascientists.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.dao.base.BaseDao;
import net.datascientists.entity.AuditLog;
import net.datascientists.entity.Note;
import net.datascientists.entity.User;

@Repository
public class UserDao implements BaseDao<User> {

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
	public User findByUserName(String userName) {
		// TODO Auto-generated method stub
		User retValue = null;
		//ArrayList<User> users = this.list();
		for(User u: this.list()){
			if(u.getUserName().equalsIgnoreCase(userName)){
				retValue = u;
			}
		}
		return retValue;
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
   	public List<User> listDeleted() {
    	final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("deleted", 1));
        return crit.list();
    }
    @Override
    public void deleteSoft(User entity){
        // TODO Auto-generated method stub
        
    }
    @Override
    public void deleteHard(User entity){
        // TODO Auto-generated method stub
        
    }
}
