package edu.demo.gymsys.activity.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.demo.gymsys.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText verificationCodeEditText;
    private EditText newPasswordEditText;
    private Button sendVerificationCodeButton;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // 初始化UI组件
        emailEditText = findViewById(R.id.editTextEmail);
        verificationCodeEditText = findViewById(R.id.editTextVerificationCode);
        newPasswordEditText = findViewById(R.id.editTextNewPassword);
        sendVerificationCodeButton = findViewById(R.id.buttonSendVerificationCode);
        resetPasswordButton = findViewById(R.id.buttonResetPassword);

        // 设置发送验证码按钮点击监听器
        sendVerificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPasswordActivity.this, "请输入注册邮箱", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: 实现发送验证码的逻辑
                    // 在实际应用中，应发送验证码到指定邮箱
                    // 这里只作示例，假设发送成功
                    Toast.makeText(ForgotPasswordActivity.this, "验证码已发送到您的邮箱", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 设置重置密码按钮点击监听器
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String verificationCode = verificationCodeEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(verificationCode) || TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(ForgotPasswordActivity.this, "请填写所有字段", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: 实现重置密码的逻辑
                    // 在实际应用中，应验证验证码的正确性，并更新用户密码
                    // 这里只作示例，假设重置成功
                    Toast.makeText(ForgotPasswordActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                    finish(); // 完成后关闭当前Activity
                }
            }
        });
    }
}
