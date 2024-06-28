package edu.demo.gymsys.activity.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.demo.gymsys.model.User;
import edu.demo.gymsys.service.UserService;
import edu.demo.gymsys.serviceImpl.UserServiceImpl;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<User>> users;
    private UserService userService;

    public HomeViewModel(Context context) {
        users = new MutableLiveData<>();
        userService = new UserServiceImpl(context);
        loadUsers();  // 在构造函数中调用以加载数据
    }

    private void loadUsers() {
        // 从 UserService 获取用户数据，并设置到 LiveData
        ArrayList<User> userList = userService.getUsers();
        users.setValue(userList);
    }

    public LiveData<ArrayList<User>> getUsers() {
        return users;
    }

    public static class HomeViewModelFactory implements ViewModelProvider.Factory {
        private final Context context;

        public HomeViewModelFactory(Context context) {
            this.context = context.getApplicationContext();  // 使用应用程序上下文
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(HomeViewModel.class)) {
                return (T) new HomeViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
