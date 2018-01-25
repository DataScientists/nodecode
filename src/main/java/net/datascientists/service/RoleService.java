package net.datascientists.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.dao.base.BaseDao;
import net.datascientists.entity.Role;
import net.datascientists.service.base.BaseService;

@Transactional
@Service("RoleService")
public class RoleService implements BaseService<Role> {

    @Autowired
    @Qualifier("RoleDao")
    private BaseDao<Role> dao;
    
    @Override
    public List<Role> list(){
        return (List<Role>) dao.list();
    }

    @Override
    public List<Role> find(String searchName, Object searchVal){
        return dao.find(searchName,searchVal);
    }

    @Override
    public Role save(Role entity){
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteSoft(Role entity){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteHard(Role entity){
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Role> listDeleted(){
        // TODO Auto-generated method stub
        return null;
    }


}
