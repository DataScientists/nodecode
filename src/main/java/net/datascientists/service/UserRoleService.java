package net.datascientists.service;

import java.util.List;

import net.datascientists.entity.Role;

public interface UserRoleService extends BaseService<Role> {

	List<Role> list();
    
	Role findByType(String type);
     
	Role findById(Long id);
	
}
