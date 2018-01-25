package net.datascientists.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.dao.base.BaseDao;
import net.datascientists.entity.User;
import net.datascientists.service.base.BaseService;

@Service("UserService")
@Transactional
public class UserService implements BaseService<User>{

    @Autowired
    @Qualifier("UserDao")
    private BaseDao<User> dao;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Override
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
        return user;
    }
     
    @Override
    public List<User> find(String searchName, Object searchVal) {
        return dao.find(searchName,searchVal);
    }

	@Override
	public List<User> list() {
		return dao.list();
	}

    @Override
    public void deleteSoft(User entity)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteHard(User entity)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<User> listDeleted()
    {
        // TODO Auto-generated method stub
        return null;
    }
	
}
