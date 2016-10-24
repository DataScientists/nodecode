package net.datascientists.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.security.dao.UserProfileDao;
import net.datascientists.security.model.UserProfile;

@Transactional
@Service
public class UserProfileServiceImpl implements UserProfileService{
    
   @Autowired
   private UserProfileDao dao;
    
   public List<UserProfile> findAll() {
       return dao.findAll();
   }

   public UserProfile findByType(String type){
       return dao.findByType(type);
   }

   public UserProfile findById(int id) {
       return dao.findById(id);
   }
}
