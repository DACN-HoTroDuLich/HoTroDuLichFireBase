package com.example.cntt196_hotrodulichfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cntt196_hotrodulichfirebase.adapters.DateTimeToString;
import com.example.cntt196_hotrodulichfirebase.models.User_;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ActivityLogin extends AppCompatActivity {

    private TextInputLayout editTextTextEmailAddress,editTextTextPassword;
    private Button btnLogin, btnRegister;
    private TextView tvQuenMatKhau;
    private ImageButton btnShowHiden_Register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();
        mAuth = FirebaseAuth.getInstance();


        editTextTextEmailAddress.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strEmail= String.valueOf(editTextTextEmailAddress.getEditText().getText());
                if(!DateTimeToString.isEmailValid(strEmail))
                {
                    editTextTextEmailAddress.setError("Email không đúng định dạng");
                }
                else
                {
                    editTextTextEmailAddress.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextTextPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int passwordSize=editTextTextPassword.getEditText().getText().length();
                if(passwordSize<6)
                {
                    editTextTextPassword.setError("Mật khẩu có ít nhất 6 ký tự");
                }
                else
                {
                    editTextTextPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnShowHiden_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTextPassword.getEditText().getInputType()== InputType.TYPE_TEXT_VARIATION_PASSWORD)
                {
                    editTextTextPassword.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                    editTextTextPassword.getEditText().setTransformationMethod(SingleLineTransformationMethod.getInstance());
                    btnShowHiden_Register.setImageResource(R.drawable.baseline_remove_red_eye_24);
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editTextTextPassword.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            editTextTextPassword.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
                            btnShowHiden_Register.setImageResource(R.drawable.baseline_visibility_off_24);
                        }
                    },5000);
                }
                else
                {
                    editTextTextPassword.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editTextTextPassword.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnShowHiden_Register.setImageResource(R.drawable.baseline_visibility_off_24);
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityLogin.this, ActivityGetStared.class);
                ActivityLogin.this.startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextTextEmailAddress.getEditText().getText().toString().equals(""))
                {
                    editTextTextEmailAddress.setError("Email không được để trống");
//                    Toast.makeText(ActivityLogin.this,
//                            "Email hoặc mật khẩu không được để trống",
//                            Toast.LENGTH_LONG).show();

                }
                else if( editTextTextPassword.getEditText().getText().toString().equals(""))
                {
                    editTextTextPassword.setError("Mật khẩu không được để trống");
                }
                else
                {
                    User_ user_=new User_();
                    user_.setIdentifier(editTextTextEmailAddress.getEditText().getText().toString());
                    user_.setPassword(editTextTextPassword.getEditText().getText().toString());

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
                                        editTextTextPassword.setError("Mật khẩu không đúng");
                                        Toast.makeText(ActivityLogin.this,
                                                "Đăng nhập thất bại",
                                                Toast.LENGTH_SHORT).show();
//                                        editTextTextPassword.getEditText().setText("");
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
        btnRegister=findViewById(R.id.btnDangKy);
        tvQuenMatKhau=findViewById(R.id.tvQuenMatKhau);
        btnShowHiden_Register=findViewById(R.id.btnShowHiden_Register);
    }
}