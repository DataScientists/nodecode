package net.datascientists.dao;

import net.datascientists.entity.AuditLog;

public interface IAuditDao {

	void save(AuditLog auditLog);

}
