package com.example.cntt196_hotrodulichfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySearch extends AppCompatActivity {

    ImageButton btnSearch, btnBack;
    EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Init();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivitySearch.this, MainActivity.class);
                ActivitySearch.this.startActivity(intent);
            }
        });
    }

    private void Init()
    {
        btnBack=findViewById(R.id.btnBack);
        btnSearch=findViewById(R.id.btnSearch);
        edtSearch=findViewById(R.id.edtSearch);

    }
}