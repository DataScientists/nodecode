package net.datascientists.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.datascientists.dao.base.BaseDao;
import net.datascientists.entity.User;
import net.datascientists.mapper.UserMapper;
import net.datascientists.service.base.BaseService;
import net.datascientists.vo.UserVO;


@Service("UserService")
@Transactional
public class UserService implements BaseService<UserVO>{

    @Autowired
    @Qualifier("UserDao")
    private BaseDao<User> dao;
	
    @Autowired
	private UserMapper mapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Override
    public UserVO save(UserVO user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userEntity = (User) dao.save(mapper.convertToEntity(user));
        return mapper.convertToVO(userEntity);
    }
     
    @Override
    public List<UserVO> find(String searchName, Object searchVal) {
    	List<User> userList =  dao.find(searchName,searchVal);
    	List<UserVO> userVOList = mapper.convertToVOList(userList);
		
		return userVOList;
    }

	@Override
	public List<UserVO> list() {
		List<UserVO> retValue = new ArrayList<UserVO>();
        List <User> Users = (List<User>) dao.list();
		retValue = mapper.convertToVOList(Users);
		return retValue;
	}

    @Override
    public void deleteSoft(UserVO vo)
    {
    	dao.deleteSoft(mapper.convertToEntity(vo));
        
    }

    @Override
    public void deleteHard(UserVO user)
    {
        dao.deleteHard(mapper.convertToEntity(user));
        
    }

    @Override
    public List<UserVO> listDeleted()
    {
    	List<UserVO> retValue = new ArrayList<UserVO>();
        List <User> Users = (List<User>) dao.listDeleted();
		retValue = mapper.convertToVOList(Users);
		return retValue;
    }
	
}
