package net.datascientists.dao;

import net.datascientists.entity.User;

public interface IUserDao extends BaseDao<User>
{
    public User findByUserName(String userName);
}
