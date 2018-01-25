package net.datascientists.dao;

import java.util.List;

import net.datascientists.entity.Role;

public interface IUserRoleDao extends BaseDao<Role>
{
    
    public List<Role> list();
    public Role findById(Long id);
    public Role findByType(String type);
    
}
