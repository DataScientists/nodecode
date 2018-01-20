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
 
     
    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }
     
    public User findById(int id) {
        return dao.findById(id);
    }
 
    public User findBySso(String sso) {
        return dao.findBySSO(sso);
    }

	@Override
	public List<UserVO> getUserRoles() {
		return mapper.convertToUserVOList(dao.findAll());
	}
	
}
