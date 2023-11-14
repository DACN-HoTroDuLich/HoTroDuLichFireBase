package com.example.cntt196_hotrodulichfirebase;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cntt196_hotrodulichfirebase.models.User_;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fBtn;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    public static User_ USER_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();

        this.USER_=new User_();
        this.USER_.setActive(true);
        this.USER_.setAuthor(false);
        this.USER_.setDateOfBirth(LocalDate.from(LocalDateTime.now()));
        this.USER_.setFullName("Võ Nguyễn Duy Tân");
        this.USER_.setAvarta("https://firebasestorage.googleapis.com/v0/b/cntt196-hotrodulich.appspot.com/o/avarta%2Flisa.jpg?alt=media&token=041fad2b-a80d-4323-8c39-d912cceac3cf");
        this.USER_.setIdentifier("duytantt9@gmail.com");


        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new FragmentHome()).commit();

        }
        replaceFragment(new FragmentHome());

        navigationView.setCheckedItem(R.id.nav_personal);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.openDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_personal:
                    {
                        Log.d("Item checked","id:"+ item.getItemId());
                        Intent intent=new Intent(MainActivity.this,ActivityLogin.class);
                        MainActivity.this.startActivity(intent);
                        break;
                    }
                    case R.id.top_hotel:
                        break;
                    case  R.id.top_tour:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        setSubMenuSelected();//set click sub menu

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new FragmentHome());
                    break;
                case R.id.travel:
                    replaceFragment(new FragmentrTravel());
                    break;
                case R.id.hotel:
                    replaceFragment(new FragmentHotel());
                    break;
                case R.id.search:
                {
                    Intent intent=new Intent(MainActivity.this,ActivitySearch.class);
                    MainActivity.this.startActivity(intent);
                    break;
                }
            }
            return true;
        });

        fBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });


    }


    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private  void setSubMenuSelected()
    {
        SubMenu subMenu=navigationView.getMenu().findItem(R.id.parentmenu).getSubMenu();
        MenuItem nav_settings=subMenu.findItem(R.id.nav_settings);
        MenuItem nav_share=subMenu.findItem(R.id.nav_share);
        MenuItem nav_about=subMenu.findItem(R.id.nav_about);
        MenuItem nav_login=subMenu.findItem(R.id.nav_login);
        MenuItem nav_logout=subMenu.findItem(R.id.nav_logout);

        nav_settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, "Selected setting", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        nav_share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, "Selected share", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        nav_about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, "Selected about", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        nav_login.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d("Checked item", "ID: "+nav_login.getItemId());
                Intent intent=new Intent(MainActivity.this,ActivityLogin.class);
                MainActivity.this.startActivity(intent);
                return true;
            }
        });
        nav_logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, "Selected logout", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_bottom_sheet);

        LinearLayout linearlayout_DiemDuLich = dialog.findViewById(R.id.linearlayout_DiemDuLich);
        LinearLayout linearlayout_KhachSan = dialog.findViewById(R.id.linearlayout_KhachSan);

        linearlayout_DiemDuLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Them bai dang du lich", Toast.LENGTH_SHORT).show();

            }
        });

        linearlayout_KhachSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Them bài dang khách san",Toast.LENGTH_SHORT).show();

            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }


//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    private void Init()
    {
        fBtn=findViewById(R.id.fbtn);
        drawerLayout=findViewById(R.id.drawer_layout);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        //toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navigation_view);
//
//        SubMenu subMenu=navigationView.getMenu().findItem(R.id.parentmenu).getSubMenu();
//        MenuItem nav_settings=subMenu.findItem(R.id.nav_settings);
//        MenuItem nav_share=subMenu.findItem(R.id.nav_share);
//        MenuItem nav_about=subMenu.findItem(R.id.nav_about);
//        MenuItem nav_login=subMenu.findItem(R.id.nav_login);
//        MenuItem nav_logout=subMenu.findItem(R.id.nav_logout);
//
//        nav_login.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Intent intent=new Intent(MainActivity.this,ActivityLogin.class);
//                MainActivity.this.startActivity(intent);
//                return true;
//            }
//        });

    }


}