package com.example.cntt196_hotrodulichfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cntt196_hotrodulichfirebase.FirebaseService.StorageService;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterNhanXet;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_images_ver1;
import com.example.cntt196_hotrodulichfirebase.adapters.DateTimeToString;
import com.example.cntt196_hotrodulichfirebase.models.DanhGia;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.example.cntt196_hotrodulichfirebase.models.User_;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityDetailTravel extends AppCompatActivity {

    //controll
    private TextView tvTenNguoiDung_detail, tvNgayDang_detail, tvTieuDe_detail, tvMoTa_detail
            , tvDiaChi_detail, tvGia_detail, tvCountFavorite_detail, tvCountDanhGia_detail_travel;
    private ImageView imgNguoiDung_detail, imgHinhAnhBaiDang_detail;
    private RatingBar AvargarateRatingBar_detail_travel;
    private ImageButton btnFavorite_detail, btnBack;
    private RecyclerView recylistHinhAnh_detail;
    private Button btnXemTatCa_detail_travel, btnThemNhanXet_detail_travel;



    //value
    private Travel travel;

    private User_ USER_TRAVEL_DETAIL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_travel);
        USER_TRAVEL_DETAIL=MainActivity.USER_;
        Init();

        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            travel=(Travel) bundle.getSerializable("Travel");
            SetValueToControl();
        }
        else
        {
//            Intent intentRedirect=new Intent(ActivityDetailTravel.this,MainActivity.class);
//            ActivityDetailTravel.this.startActivity(intent);
            finish();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnFavorite_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int)btnFavorite_detail.getTag()==R.drawable.baseline_volunteer_activism_24)
                {
                    btnFavorite_detail.setTag(R.drawable.baseline_volunteer_activism_24_0);
                    btnFavorite_detail.setImageResource((int)btnFavorite_detail.getTag());
                }
                else {
                    btnFavorite_detail.setTag(R.drawable.baseline_volunteer_activism_24);
                    btnFavorite_detail.setImageResource((int) btnFavorite_detail.getTag());
                }
                Log.e("TagBtn","=>"+v.getTag());
            }
        });


        btnThemNhanXet_detail_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(USER_TRAVEL_DETAIL!=null)
                {
                    LoadDialogThemNhanXet();
                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(ActivityDetailTravel.this);
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
                            Intent intentLogin=new Intent(ActivityDetailTravel.this,ActivityLogin.class);
                            ActivityDetailTravel.this.startActivity(intentLogin);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        btnXemTatCa_detail_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(travel.getDanhGias()!=null)
                {
                    LoadDialogListNhanXet();
                }
                else
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(ActivityDetailTravel.this);
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

        AdapterNhanXet adapterNhanXet=new AdapterNhanXet(ActivityDetailTravel.this,travel.getDanhGias()
                ,travel.getID_Document(),2);
        lvDanhGia_dialog_layout_danh_gia.setAdapter(adapterNhanXet);
        lvDanhGia_dialog_layout_danh_gia.setLayoutManager(new LinearLayoutManager(ActivityDetailTravel.this
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

        Picasso picassoHotelDetail= Picasso.with(ActivityDetailTravel.this);
        picassoHotelDetail.load(USER_TRAVEL_DETAIL.getAvarta()).resize(90, 90)
                .placeholder(R.drawable.icon2)
                .into(imgNguoiDang_dialog_add_danhgia);
        picassoHotelDetail.invalidate(USER_TRAVEL_DETAIL.getAvarta());

        tvTenNguoiDang_dialog_add_danhgia.setText(USER_TRAVEL_DETAIL.getFullName());
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
        if(travel.getNguoiDang()!=null)
        {
            String filePath="avarta/" + travel.getNguoiDang().getAnhDaiDien();
            StorageService.LoadImageUri_Avarta(filePath,imgNguoiDung_detail,this);

            tvTenNguoiDung_detail.setText(travel.getNguoiDang().getTenNguoiDang());
            tvNgayDang_detail.setText(DateTimeToString.Format(travel.getNgayDang()));
            tvTieuDe_detail.setText(travel.getTieuDe());
            tvMoTa_detail.setText(travel.getMoTa());
            tvDiaChi_detail.setText(travel.getDiaChi());
            if(travel.getGiaMax()==0&&travel.getGiaMin()==0)
            { tvGia_detail.setText("Miễn phí vé tham quan");}
            else
            { tvGia_detail.setText("Giá tham khảo chỉ từ "
                    +DateTimeToString.FormatVND(travel.getGiaMin()) +" đến "+DateTimeToString.FormatVND(travel.getGiaMax()));}

            if(travel.getLuotThichs()!=null) {
                if (travel.getLuotThichs().size() > 0) {
                    btnFavorite_detail.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                    btnFavorite_detail.setTag(R.drawable.baseline_volunteer_activism_24_0);
                    if (travel.getLuotThichs().size() > 1) {
                        tvCountFavorite_detail.setText(travel.getLuotThichs().get(0).getTenNguoiDang()
                                + " và " + travel.getLuotThichs().size() + "+");
                    } else {
                        tvCountFavorite_detail.setText(travel.getLuotThichs().get(0).getTenNguoiDang());
                    }

                }
            }
            else {
                btnFavorite_detail.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                btnFavorite_detail.setTag(R.drawable.baseline_volunteer_activism_24_0);
            }
            if(travel.getDanhGias()!=null)
            {
                float rate = 0;
                for (DanhGia danhGia : travel.getDanhGias()) {
                    rate += danhGia.getRate();
                }
                rate = rate / travel.getDanhGias().size();
                AvargarateRatingBar_detail_travel.setRating(rate);
            }

            if(travel.getHinhAnhs()!=null)
            {
                if(travel.getHinhAnhs().size()>0)
                {
                    String rootFile= "Travel/"+ travel.getID_Document()+"/"+travel.getHinhAnhs().get(0);
                    StorageService.LoadImageUri(rootFile,imgHinhAnhBaiDang_detail,this,1280,750);

                    Adapter_listview_images_ver1 adapter_listview_images_ver1=new
                            Adapter_listview_images_ver1(travel.getHinhAnhs(),ActivityDetailTravel.this,travel.getID_Document(),true);
                    recylistHinhAnh_detail.setAdapter(adapter_listview_images_ver1);
                    recylistHinhAnh_detail.setLayoutManager(new LinearLayoutManager(ActivityDetailTravel.this
                            , LinearLayoutManager.HORIZONTAL, false));
                }
            }

        }

    }
    private void Init()
    {
        tvTieuDe_detail=findViewById(R.id.tvTieuDe_detail);

        tvTenNguoiDung_detail= findViewById(R.id.tvTenNguoiDung_detail);
        tvNgayDang_detail= findViewById(R.id.tvNgayDang_detail);
        tvTieuDe_detail= findViewById(R.id.tvTieuDe_detail);
        tvMoTa_detail= findViewById(R.id.tvMoTa_detail);
        tvDiaChi_detail= findViewById(R.id.tvDiaChi_detail);
        tvGia_detail= findViewById(R.id.tvGia_detail);
        tvCountFavorite_detail= findViewById(R.id.tvCountFavorite_detail);
        imgNguoiDung_detail= findViewById(R.id.imgNguoiDung_detail);
        imgHinhAnhBaiDang_detail= findViewById(R.id.imgHinhAnhBaiDang_detail);

        btnFavorite_detail= findViewById(R.id.btnFavorite_detail);
        btnBack=findViewById(R.id.btnBack);
        recylistHinhAnh_detail=findViewById(R.id.recylistHinhAnh_detail);

        btnXemTatCa_detail_travel=findViewById(R.id.btnXemTatCa_detail_travel);
        btnThemNhanXet_detail_travel=findViewById(R.id.btnThemNhanXet_detail_travel);
        tvCountDanhGia_detail_travel=findViewById(R.id.tvCountDanhGia_detail_travel);
    }
}