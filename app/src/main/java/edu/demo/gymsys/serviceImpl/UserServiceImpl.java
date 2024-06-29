package edu.demo.gymsys.serviceImpl;

import android.content.Context;

import edu.demo.gymsys.dao.UserDao;
import edu.demo.gymsys.model.User;
import edu.demo.gymsys.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(Context context) {
        this.userDao = new UserDao(context);
    }

    @Override
    public long insertUser(String email, String password) {
        // 这里可以添加其他业务逻辑，例如数据验证
        return userDao.insertUser(email, password);
    }

    @Override
    public User getUserByEmail(String email) {
        // 这里可以添加其他业务逻辑
        return userDao.getUserByEmail(email);
    }

    @Override
    public int updateUser(int id, String email, String password) {
        // 这里可以添加其他业务逻辑
        return userDao.updateUser(id, email, password);
    }

    @Override
    public int deleteUser(int id) {
        // 这里可以添加其他业务逻辑
        return userDao.deleteUser(id);
    }
}
