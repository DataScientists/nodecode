package net.datascientists.service;

import java.util.List;

import net.datascientists.entity.User;
import net.datascientists.vo.UserVO;

public interface UserService extends BaseService<User>{

	User save(User user);
    
    User findById(Long id);
     
    User findByUserName(String sso);
    
    List<UserVO> list();
	
}
