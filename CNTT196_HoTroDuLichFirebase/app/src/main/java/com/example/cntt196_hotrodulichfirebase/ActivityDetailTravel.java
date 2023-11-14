package com.example.cntt196_hotrodulichfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cntt196_hotrodulichfirebase.FirebaseService.StorageService;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_images_ver1;
import com.example.cntt196_hotrodulichfirebase.adapters.DateTimeToString;
import com.example.cntt196_hotrodulichfirebase.models.DanhGia;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityDetailTravel extends AppCompatActivity {

    //controll
    private TextView tvTenNguoiDung_detail, tvNgayDang_detail, tvTieuDe_detail, tvMoTa_detail
            , tvDiaChi_detail, tvGia_detail, tvCountFavorite_detail;
    private ImageView imgNguoiDung_detail, imgHinhAnhBaiDang_detail;
    private RatingBar ratingBar_detail;
    private ImageButton btnFavorite_detail, btnBack;
    private RecyclerView recylistHinhAnh_detail;


    //value
    private Travel travel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_travel);

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
                ratingBar_detail.setRating(rate);
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
        ratingBar_detail= findViewById(R.id.ratingBar_detail);
        btnFavorite_detail= findViewById(R.id.btnFavorite_detail);
        btnBack=findViewById(R.id.btnBack);
        recylistHinhAnh_detail=findViewById(R.id.recylistHinhAnh_detail);
    }
}