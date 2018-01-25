package net.datascientists.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import net.datascientists.entity.User;
import net.datascientists.mapper.base.BaseMapper;
import net.datascientists.vo.UserVO;

@Component
public class UserMapper implements BaseMapper<UserVO, User>
{

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
        //vo.setUserProfiles(convertToUserProfileVO(entity.getUserRoles()));
        return vo;
    }


    @Override
    public User convertToEntity(UserVO vo)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<User> convertToEntityList(List<UserVO> voList)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
