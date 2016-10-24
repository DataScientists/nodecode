package net.datascientists.mapper;

import java.util.List;
import java.util.Set;

import net.datascientists.security.model.User;
import net.datascientists.security.model.UserProfile;
import net.datascientists.vo.UserProfileVO;
import net.datascientists.vo.UserVO;

public interface UserMapper {

	List<UserVO> convertToUserVOList(List<User> entityList);
	
	UserVO convertToUserVO(User entity);
	
	Set<UserProfileVO> convertToUserProfileVO(Set<UserProfile> entityList);
	
	UserProfileVO convertToUserProfileVO(UserProfile entity);
}
