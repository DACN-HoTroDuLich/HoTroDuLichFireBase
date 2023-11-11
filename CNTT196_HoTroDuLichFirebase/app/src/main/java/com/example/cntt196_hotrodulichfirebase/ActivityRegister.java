package com.example.cntt196_hotrodulichfirebase;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityRegister extends AppCompatActivity {

    private EditText edtHoVaTen,edtEmail,edtPassword;
    private RadioGroup radioGroup;
    private RadioButton radioNam, radioNu;
    private Button btnDangKy;
    private TextView tvDagnNhap, tvNgaySinh;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener dateOf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Init();
        KhoiTaoEdtNgaySinh();
        tvNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ActivityRegister.this, dateOf,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tvDagnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityRegister.this, ActivityLogin.class);
                ActivityRegister.this.startActivity(intent);
            }
        });
    }
    private void Init()
    {
        edtEmail=findViewById(R.id.edtEmail);
        edtHoVaTen=findViewById(R.id.edtHoVaTen);
        edtPassword=findViewById(R.id.edtPassword);
        tvNgaySinh=findViewById(R.id.tvNgaySinh);
        btnDangKy=findViewById(R.id.btnDangKy);
        tvDagnNhap=findViewById(R.id.tvDangNhap);
    }
    private void KhoiTaoEdtNgaySinh()
    {
        DateFormat fmtDate=DateFormat.getDateInstance();
        calendar=Calendar.getInstance();

        dateOf=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateDateLabel(calendar);

            }
        };

    }
    public void updateDateLabel(Calendar calendar)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        tvNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
    }
}