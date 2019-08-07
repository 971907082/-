package org.wlxy.example.dao;

import org.apache.ibatis.annotations.*;
import org.wlxy.example.model.User;

import java.util.List;

@Mapper
public interface UserDao {
    //查询
    //    @Select("select * from user")
    List<User> getAllUser(User u);

    //删除
    //    @Delete("delete from user where id = #{id}")
    int removeUserById(int id);

    //增加
    //    @Insert("insert into user (userName,password,roleId)values(#{userName},#{password},#{roleId})")
    int addUser(User user);

    //更新
    //    @Update("update user set userName=#{userName},password=#{password} where id=#{id}")
    int updateUser(User user);

    //根据id查找
//    @Select("select * from user where id =#{id}")
    User getUserById(int id);

    @Select("select * from user where userName =#{userName}")
    User getUserByUserName(String userName);

    @Select("select * from user where email =#{email}")
    User getUserByEmail(String email);
}
