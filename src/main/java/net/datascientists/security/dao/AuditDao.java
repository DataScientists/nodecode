package net.datascientists.security.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.security.model.AuditLog;

@Repository
public class AuditDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(AuditLog auditLog) {
		final Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(auditLog);
	}
	
	
}
