package net.datascientists.mapper;

import java.util.List;
import java.util.Set;

import net.datascientists.entity.User;
import net.datascientists.entity.UserRole;
import net.datascientists.vo.UserProfileVO;
import net.datascientists.vo.UserVO;

public interface UserMapper {

	List<UserVO> convertToUserVOList(List<User> entityList);
	
	UserVO convertToUserVO(User entity);
	
	Set<UserProfileVO> convertToUserProfileVO(Set<UserRole> entityList);
	
	UserProfileVO convertToUserProfileVO(UserRole entity);
}
