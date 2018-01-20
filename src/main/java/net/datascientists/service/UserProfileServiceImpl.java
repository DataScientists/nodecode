package net.datascientists.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.dao.UserRoleDao;
import net.datascientists.entity.UserRole;

@Transactional
@Service
public class UserProfileServiceImpl implements UserProfileService{
    
   @Autowired
   private UserRoleDao dao;
    
   public List<UserRole> findAll() {
       return dao.findAll();
   }

   public UserRole findByType(String type){
       return dao.findByType(type);
   }

   public UserRole findById(int id) {
       return dao.findById(id);
   }
}
