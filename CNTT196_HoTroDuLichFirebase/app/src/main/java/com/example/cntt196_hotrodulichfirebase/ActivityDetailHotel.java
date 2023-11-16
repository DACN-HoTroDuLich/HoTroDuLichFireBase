package com.example.cntt196_hotrodulichfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cntt196_hotrodulichfirebase.FirebaseService.StorageService;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterNhanXet;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterPhong;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_images_ver1;
import com.example.cntt196_hotrodulichfirebase.adapters.DateTimeToString;
import com.example.cntt196_hotrodulichfirebase.models.DanhGia;
import com.example.cntt196_hotrodulichfirebase.models.Hotel;
import com.example.cntt196_hotrodulichfirebase.models.NguoiDang;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.example.cntt196_hotrodulichfirebase.models.User_;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityDetailHotel extends AppCompatActivity {

    private TextView tvTenNguoiDung_detail_hotel, tvNgayDang_detail_hotel, tvTieuDe_detail_hotel, tvMoTa_detail_hotel
            , tvDiaChi_detail_hotel, tvCountFavorite_detail_hotel, tvCountDanhGia_detail_hotel;
    private ImageView imgNguoiDung_detail_hotel, imgHinhAnhBaiDang_detail_hotel;
    private RatingBar ratingBar_detail_hotel,AvargarateRatingBar_detail_hotel;
    private ImageButton btnFavorite_detail_hotel, btnBack_hotel;
    private RecyclerView recylistHinhAnh_detail_hotel;
    private RecyclerView lvPhong_detail_hotel, lvHoiDap_detail_hotel,lvDanhGia_detail_hotel;
    private Button btnThemNhanXet_detail_hotel, btnXemTatCa_detail_hotel;
    private Hotel hotel;

    private User_ USER_HOTEL_DETAIL;
    private void Init()
    {
        btnXemTatCa_detail_hotel=findViewById(R.id.btnXemTatCa_detail_hotel);
        btnThemNhanXet_detail_hotel=findViewById(R.id.btnThemNhanXet_detail_hotel);
        tvTieuDe_detail_hotel=findViewById(R.id.tvTieuDe_detail_hotel);
        tvCountDanhGia_detail_hotel=findViewById(R.id.tvCountDanhGia_detail_hotel);

        tvTenNguoiDung_detail_hotel= findViewById(R.id.tvTenNguoiDung_detail_hotel);
        tvNgayDang_detail_hotel= findViewById(R.id.tvNgayDang_detail_hotel);
        tvTieuDe_detail_hotel= findViewById(R.id.tvTieuDe_detail_hotel);
        tvMoTa_detail_hotel= findViewById(R.id.tvMoTa_detail_hotel);
        tvDiaChi_detail_hotel= findViewById(R.id.tvDiaChi_detail_hotel);
        tvCountFavorite_detail_hotel= findViewById(R.id.tvCountFavorite_detail_hotel);
        imgNguoiDung_detail_hotel= findViewById(R.id.imgNguoiDung_detail_hotel);
        imgHinhAnhBaiDang_detail_hotel= findViewById(R.id.imgHinhAnhBaiDang_detail_hotel);
        AvargarateRatingBar_detail_hotel=findViewById(R.id.AvargarateRatingBar_detail_hotel);
        btnFavorite_detail_hotel= findViewById(R.id.btnFavorite_detail_hotel);
        btnBack_hotel=findViewById(R.id.btnBack_detail_hotel);
        recylistHinhAnh_detail_hotel=findViewById(R.id.recylistHinhAnh_detail_hotel);
        lvPhong_detail_hotel=findViewById(R.id.lvPhong_detail_hotel);
        lvDanhGia_detail_hotel=findViewById(R.id.lvDanhGia_detail_hotel);
        lvHoiDap_detail_hotel=findViewById(R.id.lvHoiDap_detail_hotel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hotel);

        Init();
        USER_HOTEL_DETAIL=MainActivity.USER_;
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            hotel=(Hotel) bundle.getSerializable("Hotel");
            SetValueToControl();
        }
        else
        {
            finish();
        }
        btnBack_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnThemNhanXet_detail_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(USER_HOTEL_DETAIL!=null)
                {
                    LoadDialogThemNhanXet();
                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(ActivityDetailHotel.this);
                    builder.setMessage("Cần đăng nhập để kích hoạt tính năng này");
                    builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentLogin=new Intent(ActivityDetailHotel.this,ActivityLogin.class);
                            ActivityDetailHotel.this.startActivity(intentLogin);
                        }
                    });
                }
            }
        });
        btnXemTatCa_detail_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hotel.getDanhGias()!=null)
                {
                    LoadDialogListNhanXet();
                }
                else
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(ActivityDetailHotel.this);
                    builder.setMessage("Hiện chưa có đánh giá về bài viết này");
                    builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    private void LoadDialogListNhanXet()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout_danh_gia_ver1);
        dialog.setCancelable(false);

        ImageButton imgBtn_lose_dialog_layout_danh_gia;
        RecyclerView lvDanhGia_dialog_layout_danh_gia;

        imgBtn_lose_dialog_layout_danh_gia=dialog.findViewById(R.id.imgBtn_lose_dialog_layout_danh_gia);
        lvDanhGia_dialog_layout_danh_gia= dialog.findViewById(R.id.lvDanhGia_dialog_layout_danh_gia);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);

        imgBtn_lose_dialog_layout_danh_gia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog.setCancelable(true);
                dialog.cancel();
            }
        });

        AdapterNhanXet adapterNhanXet=new AdapterNhanXet(ActivityDetailHotel.this,hotel.getDanhGias()
                ,hotel.getID_Document(),2);
        lvDanhGia_dialog_layout_danh_gia.setAdapter(adapterNhanXet);
        lvDanhGia_dialog_layout_danh_gia.setLayoutManager(new LinearLayoutManager(ActivityDetailHotel.this
                , LinearLayoutManager.VERTICAL, false));

    }
    private void LoadDialogThemNhanXet()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout_add_danh_gia_ver1);
        dialog.setCancelable(false);

        ImageView imgNguoiDang_dialog_add_danhgia ;
        Button btnCancel_detail_hotel , btnNhanXet_detail_hotel;
        RatingBar ratingBar_dialog_add_danhgia;
        EditText edtDanhGia_dialog_add_danhgia;
        TextView tvTenNguoiDang_dialog_add_danhgia;
        imgNguoiDang_dialog_add_danhgia = dialog.findViewById(R.id.imgNguoiDang_dialog_add_danhgia);
        btnCancel_detail_hotel = dialog.findViewById(R.id.btnCancel_detail_hotel);
        btnNhanXet_detail_hotel = dialog.findViewById(R.id.btnNhanXet_detail_hotel);
        ratingBar_dialog_add_danhgia = dialog.findViewById(R.id.ratingBar_dialog_add_danhgia);
        edtDanhGia_dialog_add_danhgia = dialog.findViewById(R.id.edtDanhGia_dialog_add_danhgia);
        tvTenNguoiDang_dialog_add_danhgia=dialog.findViewById(R.id.tvTenNguoiDang_dialog_add_danhgia);

        Picasso picassoHotelDetail= Picasso.with(ActivityDetailHotel.this);
        picassoHotelDetail.load(USER_HOTEL_DETAIL.getAvarta()).resize(90, 90)
                .placeholder(R.drawable.icon2)
                .into(imgNguoiDang_dialog_add_danhgia);
        picassoHotelDetail.invalidate(USER_HOTEL_DETAIL.getAvarta());

        tvTenNguoiDang_dialog_add_danhgia.setText(USER_HOTEL_DETAIL.getFullName());
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);

        btnCancel_detail_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //dialog.setCancelable(true);
                dialog.cancel();
            }
        });


    }
    private void SetValueToControl()
    {
        if(hotel.getNguoiDang()!=null)
        {
            String filePath="avarta/" + hotel.getNguoiDang().getAnhDaiDien();
            StorageService.LoadImageUri_Avarta(filePath,imgNguoiDung_detail_hotel,this);

            tvTenNguoiDung_detail_hotel.setText(hotel.getNguoiDang().getTenNguoiDang());
            tvNgayDang_detail_hotel.setText(DateTimeToString.Format(hotel.getNgayDang()));
            tvTieuDe_detail_hotel.setText(hotel.getTenKhachSan());
            tvMoTa_detail_hotel.setText(hotel.getMoTa());
            tvDiaChi_detail_hotel.setText(hotel.getDiaChi());

            if(hotel.getLuotThichs()!=null) {
                btnFavorite_detail_hotel.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                btnFavorite_detail_hotel.setTag(R.drawable.baseline_volunteer_activism_24_0);
                if (hotel.getLuotThichs().size() > 0)
                {
                    for (NguoiDang nguoiDang : hotel.getLuotThichs()) {
                        if (nguoiDang.getMaNguoiDang().equals(MainActivity.USER_.getIdentifier())) {
                            Log.e("UserState", "=>" + nguoiDang.getMaNguoiDang());
                            btnFavorite_detail_hotel.setTag(R.drawable.baseline_volunteer_activism_24);
                            btnFavorite_detail_hotel.setImageResource((int) btnFavorite_detail_hotel.getTag());
                        }
                    }
                    if (hotel.getLuotThichs().size() > 1) {
                        int countLuotThich = hotel.getLuotThichs().size() - 1;
                        tvCountFavorite_detail_hotel.setText(hotel.getLuotThichs().get(0).getTenNguoiDang() +
                                " và +" + countLuotThich);
                    } else {
                        tvCountFavorite_detail_hotel.setText(hotel.getLuotThichs().get(0).getTenNguoiDang());
                    }
                }
            }
            else {
                btnFavorite_detail_hotel.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                btnFavorite_detail_hotel.setTag(R.drawable.baseline_volunteer_activism_24_0);
            }
            if(hotel.getDanhGias()!=null)
            {
                float rate = 0;
                for (DanhGia danhGia : hotel.getDanhGias()) {
                    rate += danhGia.getRate();
                }
                rate = rate / hotel.getDanhGias().size();
                AvargarateRatingBar_detail_hotel.setRating(rate);
                tvCountDanhGia_detail_hotel.setText(rate+"\n"+"Đánh giá "+"("+hotel.getDanhGias().size()+")");
            }

            if(hotel.getHinhAnhs()!=null)
            {
                if(hotel.getHinhAnhs().size()>0)
                {
                    String rootFile= "Hotel/"+ hotel.getID_Document()+"/"+hotel.getHinhAnhs().get(0);
                    StorageService.LoadImageUri(rootFile,imgHinhAnhBaiDang_detail_hotel,this,1280,750);

                    Adapter_listview_images_ver1 adapter_listview_images_ver1=new
                            Adapter_listview_images_ver1(hotel.getHinhAnhs(),ActivityDetailHotel.this, hotel.getID_Document(),false);
                    recylistHinhAnh_detail_hotel.setAdapter(adapter_listview_images_ver1);
                    recylistHinhAnh_detail_hotel.setLayoutManager(new LinearLayoutManager(ActivityDetailHotel.this
                            , LinearLayoutManager.HORIZONTAL, false));
                }
            }
            if(hotel.getPhongs()!=null)
            {
                AdapterPhong adapterPhong=new AdapterPhong(hotel.getPhongs(),ActivityDetailHotel.this, hotel.getID_Document());
                lvPhong_detail_hotel.setAdapter(adapterPhong);
                lvPhong_detail_hotel.setLayoutManager(new LinearLayoutManager(ActivityDetailHotel.this
                        , LinearLayoutManager.HORIZONTAL, false));
            }
            if(hotel.getDanhGias()!=null)
            {
                float rating=0;
                for (DanhGia danhGia:hotel.getDanhGias())
                {
                    rating+=danhGia.getRate();
                }
                rating=rating/hotel.getDanhGias().size();
                AvargarateRatingBar_detail_hotel.setRating(rating);
                AvargarateRatingBar_detail_hotel.setIsIndicator(true);
                ArrayList<DanhGia> dsDanhGia=new ArrayList<>();
                if(hotel.getDanhGias().size()<3)
                {
                    for (DanhGia danhGia:hotel.getDanhGias())
                    {
                        dsDanhGia.add(danhGia);
                    }
                }
                else {
                    dsDanhGia.add(hotel.getDanhGias().get(0));
                    dsDanhGia.add(hotel.getDanhGias().get(1));
                }
                AdapterNhanXet adapterNhanXet=new AdapterNhanXet(ActivityDetailHotel.this
                        ,dsDanhGia,hotel.getID_Document(),1);
                lvDanhGia_detail_hotel.setAdapter(adapterNhanXet);
                lvDanhGia_detail_hotel.setLayoutManager(new LinearLayoutManager(ActivityDetailHotel.this
                        , LinearLayoutManager.VERTICAL, false));
            }

        }

    }
}