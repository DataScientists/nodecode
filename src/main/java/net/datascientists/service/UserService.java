package net.datascientists.service;

import java.util.List;

import net.datascientists.entity.User;
import net.datascientists.vo.UserVO;

public interface UserService {

	void save(User user);
    
    User findById(int id);
     
    User findBySso(String sso);
    
    List<UserVO> getUserRoles();
	
}
