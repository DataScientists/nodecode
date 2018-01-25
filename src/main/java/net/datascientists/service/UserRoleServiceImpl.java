package net.datascientists.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.dao.IUserRoleDao;
import net.datascientists.entity.Role;

@Transactional
@Service
public class UserRoleServiceImpl implements UserRoleService
{

    @Autowired
    private IUserRoleDao dao;


    @Override
    public List<Role> list()
    {
        return dao.list();
    }


    @Override
    public Role findByType(String type)
    {
        return dao.findByType(type);
    }


    @Override
    public Role findById(Long id)
    {
        return dao.findById(id);
    }


    @Override
    public Object save(Role entity)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Object update(Role entity)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void deleteSoft(Role entity)
    {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void deleteHard(Role entity)
    {
        // TODO Auto-generated method stub
        
    }


    @Override
    public List<? extends Object> listDeleted()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
