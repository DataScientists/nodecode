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

@Repository("AuditDao")
public class AuditDao implements BaseDao<AuditLog> {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public AuditLog save(AuditLog auditLog) {
		sessionFactory.getCurrentSession().saveOrUpdate(auditLog);
		return auditLog;
	}
    @Override
    public void deleteSoft(AuditLog auditLog){
        auditLog.setDeleted(1);
        save(auditLog);
    }
    @Override
    public void deleteHard(AuditLog auditLog){
        sessionFactory.getCurrentSession().delete(auditLog);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<AuditLog> find(String searchName, Object searchVal)
    {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(AuditLog.class);
        crit.add(Restrictions.eq(searchName, searchVal));
        return crit.list();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<AuditLog> list(){
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(AuditLog.class);
        crit.add(Restrictions.eq("deleted", 0));
        return crit.list();
    }
    @Override
    public List<AuditLog> listDeleted(){
        // TODO Auto-generated method stub
        return null;
    }

}
