package net.datascientists.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.dao.UserDao;
import net.datascientists.entity.User;
import net.datascientists.mapper.UserMapper;
import net.datascientists.vo.UserVO;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
    private UserDao dao;
	@Autowired
	private UserMapper mapper;
     
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Override
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
        return user;
    }
     
    @Override
    public User findById(Long id) {
        return dao.findById(id);
    }
 
    @Override
    public User findByUserName(String userName) {
        return dao.findByUserName(userName);
    }

	@Override
	public List<UserVO> list() {
		return mapper.convertToVOList(dao.list());
	}

    @Override
    public Object update(User entity)
    {
        // TODO Auto-generated method stub
        return null;
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
    public List<? extends Object> listDeleted()
    {
        // TODO Auto-generated method stub
        return null;
    }
	
}
