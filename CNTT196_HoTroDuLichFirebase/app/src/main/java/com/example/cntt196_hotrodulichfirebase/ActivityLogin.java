package com.example.cntt196_hotrodulichfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cntt196_hotrodulichfirebase.models.User_;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ActivityLogin extends AppCompatActivity {

    private EditText editTextTextEmailAddress,editTextTextPassword;
    private Button btnLogin;
    private TextView tvDangKy,tvQuenMatKhau;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();
        mAuth = FirebaseAuth.getInstance();


        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityLogin.this, ActivityRegister.class);
                ActivityLogin.this.startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextTextEmailAddress.getText().toString().equals("")||
                        editTextTextPassword.getText().toString().equals(""))
                {
                    Toast.makeText(ActivityLogin.this,
                            "Email hoặc mật khẩu không được để trống",
                            Toast.LENGTH_LONG).show();

                }
                else
                {
                    User_ user_=new User_();
                    user_.setIdentifier(editTextTextEmailAddress.getText().toString());
                    user_.setPassword(editTextTextPassword.getText().toString());

                    mAuth.signInWithEmailAndPassword(user_.getIdentifier(),user_.getPassword())
                            .addOnCompleteListener(ActivityLogin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(ActivityLogin.this,
                                                "Đăng nhập thành công",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(ActivityLogin.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(ActivityLogin.this,
                                                "Đăng nhập thất bại",
                                                Toast.LENGTH_SHORT).show();
                                        editTextTextPassword.setText("");
                                    }
                                }
                            });
                }
            }
        });
    }
    private void Init()
    {
        editTextTextEmailAddress=findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword=findViewById(R.id.editTextTextPassword);
        btnLogin=findViewById(R.id.btnLogin);
        tvDangKy=findViewById(R.id.tvDangKy);
        tvQuenMatKhau=findViewById(R.id.tvQuenMatKhau);
    }
}