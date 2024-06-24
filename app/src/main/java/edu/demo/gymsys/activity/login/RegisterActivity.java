package edu.demo.gymsys.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.demo.gymsys.R;
import edu.demo.gymsys.model.User;
import edu.demo.gymsys.service.UserService;
import edu.demo.gymsys.serviceImpl.UserServiceImpl;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private TextView loginLinkTextView;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 初始化UI组件
        usernameEditText = findViewById(R.id.editTextUsername);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);
        loginLinkTextView = findViewById(R.id.textViewLoginLink);

        // 初始化UserService
        userService = new UserServiceImpl(this);

        // 设置注册按钮点击监听器
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // 逻辑处理
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "请填写所有字段", Toast.LENGTH_SHORT).show();
                } else {
                    // 检查用户是否已经存在
                    User existingUser = userService.getUserByEmail(email);
                    if (existingUser != null) {
                        Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        // 插入新用户
                        long userId = userService.insertUser(email, password);
                        if (userId != -1) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                            // 注册成功后跳转到登录界面(LoginActivity)
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // 结束当前Activity，防止用户返回到注册页
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        // 设置登录链接点击监听器，跳转到登录界面(LoginActivity)
        loginLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // 结束当前Activity，防止用户返回到注册页
            }
        });
    }
}
