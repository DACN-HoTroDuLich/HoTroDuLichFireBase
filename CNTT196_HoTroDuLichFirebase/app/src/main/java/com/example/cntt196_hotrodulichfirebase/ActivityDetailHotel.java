package com.example.cntt196_hotrodulichfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cntt196_hotrodulichfirebase.adapters.AdapterPhong;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_images_ver1;
import com.example.cntt196_hotrodulichfirebase.adapters.DateTimeToString;
import com.example.cntt196_hotrodulichfirebase.models.DanhGia;
import com.example.cntt196_hotrodulichfirebase.models.Hotel;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.squareup.picasso.Picasso;

public class ActivityDetailHotel extends AppCompatActivity {

    private TextView tvTenNguoiDung_detail_hotel, tvNgayDang_detail_hotel, tvTieuDe_detail_hotel, tvMoTa_detail_hotel
            , tvDiaChi_detail_hotel, tvCountFavorite_detail_hotel;
    private ImageView imgNguoiDung_detail_hotel, imgHinhAnhBaiDang_detail_hotel;
    private RatingBar ratingBar_detail_hotel;
    private ImageButton btnFavorite_detail_hotel, btnBack_hotel;
    private RecyclerView recylistHinhAnh_detail_hotel;
    private RecyclerView lvPhong_detail_hotel;

    private Hotel hotel;
    private void Init()
    {
        tvTieuDe_detail_hotel=findViewById(R.id.tvTieuDe_detail_hotel);

        tvTenNguoiDung_detail_hotel= findViewById(R.id.tvTenNguoiDung_detail_hotel);
        tvNgayDang_detail_hotel= findViewById(R.id.tvNgayDang_detail_hotel);
        tvTieuDe_detail_hotel= findViewById(R.id.tvTieuDe_detail_hotel);
        tvMoTa_detail_hotel= findViewById(R.id.tvMoTa_detail_hotel);
        tvDiaChi_detail_hotel= findViewById(R.id.tvDiaChi_detail_hotel);
        tvCountFavorite_detail_hotel= findViewById(R.id.tvCountFavorite_detail_hotel);
        imgNguoiDung_detail_hotel= findViewById(R.id.imgNguoiDung_detail_hotel);
        imgHinhAnhBaiDang_detail_hotel= findViewById(R.id.imgHinhAnhBaiDang_detail_hotel);
        ratingBar_detail_hotel= findViewById(R.id.ratingBar_detail_hotel);
        btnFavorite_detail_hotel= findViewById(R.id.btnFavorite_detail_hotel);
        btnBack_hotel=findViewById(R.id.btnBack_detail_hotel);
        recylistHinhAnh_detail_hotel=findViewById(R.id.recylistHinhAnh_detail_hotel);
        lvPhong_detail_hotel=findViewById(R.id.lvPhong_detail_hotel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hotel);

        Init();

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
    }

    private void SetValueToControl()
    {
        if(hotel.getNguoiDang()!=null)
        {
            Picasso picasso=Picasso.with(this);
            picasso.load(hotel.getNguoiDang().getAnhDaiDien()).resize(90,90)
                    .placeholder(R.drawable.icon2)
                    .into(imgNguoiDung_detail_hotel);
            picasso.invalidate(hotel.getNguoiDang().getAnhDaiDien());

            tvTenNguoiDung_detail_hotel.setText(hotel.getNguoiDang().getTenNguoiDang());
            tvNgayDang_detail_hotel.setText(DateTimeToString.Format(hotel.getNgayDang()));
            tvTieuDe_detail_hotel.setText(hotel.getTenKhachSan());
            tvMoTa_detail_hotel.setText(hotel.getMoTa());
            tvDiaChi_detail_hotel.setText(hotel.getDiaChi());
            if(hotel.getLuotThichs()!=null) {
                if (hotel.getLuotThichs().size() > 0) {
                    btnFavorite_detail_hotel.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                    btnFavorite_detail_hotel.setTag(R.drawable.baseline_volunteer_activism_24_0);
                    if (hotel.getLuotThichs().size() > 1) {
                        tvCountFavorite_detail_hotel.setText(hotel.getLuotThichs().get(0).getTenNguoiDang()
                                + " và " + hotel.getLuotThichs().size() + "+");
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
                ratingBar_detail_hotel.setRating(rate);
            }

            if(hotel.getHinhAnhs()!=null)
            {
                if(hotel.getHinhAnhs().size()>0)
                {
                    Picasso picassoH1=Picasso.with(this);
                    picassoH1.load(hotel.getHinhAnhs().get(0)).resize(1280  ,720)
                            .placeholder(R.drawable.default_image_empty)
                            .into(imgHinhAnhBaiDang_detail_hotel);
                    picassoH1.invalidate(hotel.getHinhAnhs().get(0));


                    Adapter_listview_images_ver1 adapter_listview_images_ver1=new
                            Adapter_listview_images_ver1(hotel.getHinhAnhs(),ActivityDetailHotel.this);
                    recylistHinhAnh_detail_hotel.setAdapter(adapter_listview_images_ver1);
                    recylistHinhAnh_detail_hotel.setLayoutManager(new LinearLayoutManager(ActivityDetailHotel.this
                            , LinearLayoutManager.HORIZONTAL, false));
                }
            }
            if(hotel.getPhongs()!=null)
            {
                AdapterPhong adapterPhong=new AdapterPhong(hotel.getPhongs(),ActivityDetailHotel.this);
                lvPhong_detail_hotel.setAdapter(adapterPhong);
                lvPhong_detail_hotel.setLayoutManager(new LinearLayoutManager(ActivityDetailHotel.this
                        , LinearLayoutManager.HORIZONTAL, false));
            }
        }

    }
}