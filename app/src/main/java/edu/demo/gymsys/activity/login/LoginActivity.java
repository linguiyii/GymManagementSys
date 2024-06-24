package edu.demo.gymsys.activity.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.demo.gymsys.R;
import edu.demo.gymsys.activity.HomeActivity;
import edu.demo.gymsys.model.User;
import edu.demo.gymsys.service.UserService;
import edu.demo.gymsys.serviceImpl.UserServiceImpl;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;  // 邮箱输入框
    private EditText passwordEditText;  // 密码输入框
    private CheckBox rememberPasswordCheckbox;  // 记住密码复选框
    private Button loginButton;  // 登录按钮
    private TextView forgotPasswordTextView;  // 忘记密码文本视图
    private TextView registerTextView;  // 注册文本视图

    private SharedPreferences sharedPreferences;  // SharedPreferences对象
    private static final String PREFS_NAME = "MyPrefs";  // SharedPreferences文件名
    private static final String PREF_EMAIL = "email";  // 存储邮箱的键名
    private static final String PREF_PASSWORD = "password";  // 存储密码的键名
    private static final String PREF_REMEMBER_PASSWORD = "remember_password";  // 记住密码的键名

    private UserService userService;  // UserService对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化UI组件
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        rememberPasswordCheckbox = findViewById(R.id.remember_password_checkbox);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordTextView = findViewById(R.id.forgot_password);
        registerTextView = findViewById(R.id.register);

        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // 初始化UserService
        userService = new UserServiceImpl(this);

        // 如果勾选了"记住密码"，则加载保存的数据
        if (sharedPreferences.getBoolean(PREF_REMEMBER_PASSWORD, false)) {
            emailEditText.setText(sharedPreferences.getString(PREF_EMAIL, ""));
            passwordEditText.setText(sharedPreferences.getString(PREF_PASSWORD, ""));
            rememberPasswordCheckbox.setChecked(true);
        }

        // 设置按钮点击监听器
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // 执行登录验证逻辑
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入邮箱和密码", Toast.LENGTH_SHORT).show();
                } else {
                    // 从数据库中查询用户
                    User user = userService.getUserByEmail(email);
                    if (user != null && user.getPassword().equals(password)) {
                        // 登录成功
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                        // 如果勾选了"记住密码"，则保存邮箱和密码
                        if (rememberPasswordCheckbox.isChecked()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(PREF_EMAIL, email);
                            editor.putString(PREF_PASSWORD, password);
                            editor.putBoolean(PREF_REMEMBER_PASSWORD, true);
                            editor.apply();
                        } else {
                            // 如果未勾选"记住密码"，则清除保存的数据
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove(PREF_EMAIL);
                            editor.remove(PREF_PASSWORD);
                            editor.remove(PREF_REMEMBER_PASSWORD);
                            editor.apply();
                        }

                        // 跳转到HomeActivity
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // 结束当前Activity，防止用户返回到登录页
                    } else {
                        // 登录失败
                        Toast.makeText(LoginActivity.this, "邮箱或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 设置忘记密码文本点击监听器
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到找回密码页面(ForgotPasswordActivity)
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        // 设置注册文本点击监听器
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册页面(RegisterActivity)
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
