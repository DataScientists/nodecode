package net.datascientists.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.datascientists.entity.User;
import net.datascientists.mapper.base.BaseMapper;
import net.datascientists.vo.UserVO;

@Component
public class UserMapper implements BaseMapper<UserVO, User>
{
	@Autowired
    RoleMapper roleMapper;
	
	 @Override
	    public UserVO convertToVO(User entity)
	    {
	        if (entity == null)
	        {
	            return null;
	        }

	        UserVO vo = new UserVO();
	        vo.setEmail(entity.getEmail());
	        vo.setFirstName(entity.getFirstName());
	        vo.setId(entity.getId());
	        vo.setLastName(entity.getLastName());
	        vo.setPassword(entity.getPassword());
	        vo.setUserName(entity.getUserName());
	        vo.setState(entity.getState());
	        vo.setRoles(roleMapper.convertToVOList(entity.getRoles()));
	        return vo;
	    }
	
    @Override
    public List<UserVO> convertToVOList(List<User> entityList)
    {
        if (entityList == null)
        {
            return null;
        }
        List<UserVO> list = new ArrayList<>();
        for (User entity : entityList)
        {
            list.add(convertToVO(entity));
        }
        return list;
    }


   


    @Override
    public User convertToEntity(UserVO userVO)
    {
    	if(userVO == null){
			return null;
		}
		User user = new User();
		user.setId(userVO.getId()); 
		user.setDeleted(userVO.getDeleted());
		user.setLastUpdated(userVO.getLastUpdated());
		user.setUserName(userVO.getUserName());
		user.setFirstName(userVO.getFirstName());
		user.setLastName(userVO.getLastName());
		user.setState(userVO.getState());
		user.setPassword(userVO.getPassword());
		user.setEmail(userVO.getEmail());
		user.setRoles(roleMapper.convertToEntityList(userVO.getRoles()));
        return user;
    }


    @Override
    public List<User> convertToEntityList(List<UserVO> voList)
    {
    	 if (voList == null)
         {
             return null;
         }
         List<User> list = new ArrayList<>();
         for (UserVO entity : voList)
         {
             list.add(convertToEntity(entity));
         }
         return list;
    }

}
