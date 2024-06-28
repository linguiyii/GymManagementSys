package edu.demo.gymsys.service;

import java.util.ArrayList;
import java.util.List;

import edu.demo.gymsys.dao.UserDao;
import edu.demo.gymsys.model.User;

public interface UserService {

    // 插入用户数据
    long insertUser(String email, String password);

    // 根据邮箱查询用户
    User getUserByEmail(String email);

    //查询用户
    ArrayList<User> getUsers();

    // 更新用户数据
    int updateUser(int id, String email, String password);

    // 删除用户数据
    int deleteUser(int id);
}
