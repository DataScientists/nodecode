package net.datascientists.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.dao.UserRoleDao;
import net.datascientists.entity.Role;

@Transactional
@Service
public class UserRoleServiceImpl implements UserRoleService{
    
   @Autowired
   private UserRoleDao dao;
    
   public List<Role> findAll() {
       return dao.findAll();
   }

   public Role findByType(String type){
       return dao.findByType(type);
   }

   public Role findById(int id) {
       return dao.findById(id);
   }
}
