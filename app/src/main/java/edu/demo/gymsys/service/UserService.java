package edu.demo.gymsys.service;

import edu.demo.gymsys.model.User;

public interface UserService {

    // 插入用户数据
    long insertUser(String email, String password);

    // 根据邮箱查询用户
    User getUserByEmail(String email);

    // 更新用户数据
    int updateUser(int id, String email, String password);

    // 删除用户数据
    int deleteUser(int id);
}
