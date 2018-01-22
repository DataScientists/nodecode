package net.datascientists.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.entity.AuditLog;

@Repository
public class AuditDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(AuditLog auditLog) {
		sessionFactory.getCurrentSession().saveOrUpdate(auditLog);
	}	
}
