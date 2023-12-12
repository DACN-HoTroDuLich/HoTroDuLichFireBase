package com.example.cntt196_hotrodulichfirebase;

import androidx.annotation.NonNull;
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

import com.example.cntt196_hotrodulichfirebase.FirebaseService.DialogMessage;
import com.example.cntt196_hotrodulichfirebase.FirebaseService.HotelService;
import com.example.cntt196_hotrodulichfirebase.FirebaseService.StorageService;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterNhanXet;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterPhong;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_hoidap_ver1;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_images_ver1;
import com.example.cntt196_hotrodulichfirebase.adapters.DateTimeToString;
import com.example.cntt196_hotrodulichfirebase.models.DanhGia;
import com.example.cntt196_hotrodulichfirebase.models.HoiDap;
import com.example.cntt196_hotrodulichfirebase.models.Hotel;
import com.example.cntt196_hotrodulichfirebase.models.NguoiDang;
import com.example.cntt196_hotrodulichfirebase.models.Phong;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.example.cntt196_hotrodulichfirebase.models.User_;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityDetailHotel extends AppCompatActivity {

    private TextView tvTenNguoiDung_detail_hotel, tvNgayDang_detail_hotel, tvTieuDe_detail_hotel, tvMoTa_detail_hotel
            , tvDiaChi_detail_hotel, tvCountFavorite_detail_hotel, tvCountDanhGia_detail_hotel;
    private ImageView imgNguoiDung_detail_hotel, imgHinhAnhBaiDang_detail_hotel;
    private RatingBar ratingBar_detail_hotel,AvargarateRatingBar_detail_hotel;
    private ImageButton btnFavorite_detail_hotel, btnBack_hotel, btnShowMap_detail_hotel;
    private RecyclerView recylistHinhAnh_detail_hotel;
    private RecyclerView lvPhong_detail_hotel, lvHoiDap_detail_hotel,lvDanhGia_detail_hotel;
    private EditText edtHoiDap_detail_hotel;
    private Button btnThemNhanXet_detail_hotel, btnXemTatCa_detail_hotel, btnHoiDap_detail_hotel;
    private Hotel hotel;

    private User_ USER_HOTEL_DETAIL;

    private FirebaseFirestore firebaseFirestore;
    private void Init()
    {
        btnShowMap_detail_hotel =findViewById(R.id.btnShowMap_detail_hotel);
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
        edtHoiDap_detail_hotel = findViewById(R.id.edtHoiDap_detail_hotel);
        btnHoiDap_detail_hotel=findViewById(R.id.btnHoiDap_detail_hotel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hotel);

        Init();
        firebaseFirestore= FirebaseFirestore.getInstance();
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
                    AlertDialog dialog = builder.create();
                    dialog.show();
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
        btnShowMap_detail_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleMap = new Bundle();
                bundleMap.putSerializable("DiaChi", hotel.getDiaChi());
                Intent intentMap=new Intent(ActivityDetailHotel.this, MapsActivity.class);
                intentMap.putExtras(bundleMap);
                ActivityDetailHotel.this.startActivity(intentMap);
            }
        });
        btnHoiDap_detail_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtHoiDap_detail_hotel.getText().equals(""))
                {
                    DialogMessage.ThongBao("Hỏi đáp không được để trống",ActivityDetailHotel.this);
                }
                else
                {
                    String IDHotel=hotel.getID_Document();
                    firebaseFirestore.collection("Hotel").document(IDHotel)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.get("HoiDap")==null)
                                    {

                                        String maHoiDap=MainActivity.USER_.getUser_UID() +
                                                hotel.getID_Document() + DateTimeToString.GenarateID();

                                        ArrayList<Map<String,Object>> arrayHoiDap=new ArrayList<>();
                                        Map<String,Object> HoiDap=new HashMap<>();
                                        HoiDap.put("MaHoiDap",maHoiDap);
                                        HoiDap.put("MaNguoiHoi",MainActivity.USER_.getIdentifier());
                                        HoiDap.put("NgayHoi", Timestamp.now());
                                        HoiDap.put("NoiDungHoiDap",edtHoiDap_detail_hotel.getText().toString().trim());
                                        HoiDap.put("TenNguoiHoi", MainActivity.USER_.getFullName());
                                        HoiDap.put("avartaNguoiHoi", MainActivity.USER_.getAvarta());
                                        HoiDap.put("TraLoi",null);
                                        arrayHoiDap.add(HoiDap);

                                        firebaseFirestore.collection("Hotel").document(IDHotel)
                                        .update("HoiDap",arrayHoiDap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                DialogMessage.ThongBao("Thêm hỏi đáp thành công",
                                                      ActivityDetailHotel.this);
                                                AfterInsertNhanXet(IDHotel,firebaseFirestore);
                                                edtHoiDap_detail_hotel.setText("");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                DialogMessage.ThongBao("Thêm hỏi đáp không thành công",
                                                        ActivityDetailHotel.this);
                                            }
                                        });
                                    }
                                    else
                                    {
                                        String maHoiDap=MainActivity.USER_.getUser_UID() +
                                                hotel.getID_Document() + DateTimeToString.GenarateID();

                                        Map<String,Object> HoiDap=new HashMap<>();
                                        HoiDap.put("MaHoiDap",maHoiDap);
                                        HoiDap.put("MaNguoiHoi",MainActivity.USER_.getIdentifier());
                                        HoiDap.put("NgayHoi", Timestamp.now());
                                        HoiDap.put("NoiDungHoiDap",edtHoiDap_detail_hotel.getText().toString().trim());
                                        HoiDap.put("TenNguoiHoi", MainActivity.USER_.getFullName());
                                        HoiDap.put("avartaNguoiHoi", MainActivity.USER_.getAvarta());
                                        HoiDap.put("TraLoi",null);

                                        firebaseFirestore.collection("Hotel").document(IDHotel)
                                        .update("HoiDap", FieldValue.arrayUnion(HoiDap))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    DialogMessage.ThongBao("Thêm hỏi đáp thành công",
                                                            ActivityDetailHotel.this);
                                                    AfterInsertNhanXet(IDHotel,firebaseFirestore);

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    DialogMessage.ThongBao("Thêm hỏi đáp không thành công",
                                                           ActivityDetailHotel.this);
                                                }
                                            });
                                        }
                                    }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("DIAOLOGNhanXet", "onFailure: Loi load document");
                                }
                            });

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
                ,hotel.getID_Document(),2,false);
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

        String filePath="avarta/" + MainActivity.USER_.getAvarta();
        StorageService.LoadImageUri_Avarta(filePath,imgNguoiDang_dialog_add_danhgia,
                ActivityDetailHotel.this);

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

        btnNhanXet_detail_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtDanhGia_dialog_add_danhgia.getText().equals(""))
                {
                    DialogMessage.ThongBao("Nhận xét của bạn đang trống",dialog.getContext());
                }
                else
                {
                    String IDHotel=hotel.getID_Document();
                    firebaseFirestore.collection("Hotel").document(IDHotel)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.get("DanhGia")==null)
                                    {
                                        ArrayList<Map<String,Object>> arrayNhanXet=new ArrayList<>();
                                        Map<String,Object> NhanXet=new HashMap<>();
                                        NhanXet.put("MaNguoiDanhGia",MainActivity.USER_.getIdentifier());
                                        NhanXet.put("NgayDang", Timestamp.now());
                                        NhanXet.put("NoiDungDanhGia",edtDanhGia_dialog_add_danhgia.getText().toString().trim());
                                        long rate= (long) ratingBar_dialog_add_danhgia.getRating();
                                        NhanXet.put("Rate", rate);
                                        NhanXet.put("TenNguoiDanhGia", MainActivity.USER_.getFullName());
                                        NhanXet.put("avartaNguoiDanhGia", MainActivity.USER_.getAvarta());
                                        arrayNhanXet.add(NhanXet);

                                        firebaseFirestore.collection("Hotel").document(IDHotel)
                                                .update("DanhGia",arrayNhanXet)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        DialogMessage.ThongBao("Thêm nhận xét thành công",
                                                                dialog.getContext());
                                                        AfterInsertNhanXet(IDHotel,firebaseFirestore);
                                                        dialog.dismiss();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        DialogMessage.ThongBao("Thêm nhận xét không thành công",
                                                                dialog.getContext());
                                                    }
                                                });
                                    }
                                    else
                                    {
                                        {
                                            ArrayList<Map<String,Object>> arrayNhanXet=new ArrayList<>();
                                            Map<String,Object> NhanXet=new HashMap<>();
                                            NhanXet.put("MaNguoiDanhGia",MainActivity.USER_.getIdentifier());
                                            NhanXet.put("NgayDang", Timestamp.now());
                                            NhanXet.put("NoiDungDanhGia",edtDanhGia_dialog_add_danhgia.getText().toString().trim());
                                            long rate= (long) ratingBar_dialog_add_danhgia.getRating();
                                            NhanXet.put("Rate", rate);
                                            NhanXet.put("TenNguoiDanhGia", MainActivity.USER_.getFullName());
                                            NhanXet.put("avartaNguoiDanhGia", MainActivity.USER_.getAvarta());
                                            arrayNhanXet.add(NhanXet);

                                            firebaseFirestore.collection("Hotel").document(IDHotel)
                                            .update("DanhGia", FieldValue.arrayUnion(NhanXet))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    DialogMessage.ThongBao("Thêm nhận xét thành công",
                                                            dialog.getContext());
                                                    AfterInsertNhanXet(IDHotel,firebaseFirestore);
                                                    dialog.dismiss();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    DialogMessage.ThongBao("Thêm nhận xét không thành công",
                                                            dialog.getContext());
                                                }
                                            });
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("DIAOLOGNhanXet", "onFailure: Loi load document");
                                }
                            });

                }
            }

        });

    }

    private void AfterInsertNhanXet( String IDHotel,FirebaseFirestore firebaseFirestore)
    {
        firebaseFirestore.collection("Hotel").document(IDHotel)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.getId()!=null)
                        {
                            hotel.setID_Document(documentSnapshot.getId());
                            Map<String,Object> subDocument=(Map<String,Object>) documentSnapshot.get("NguoiDang");
                            Log.d("HotelNguoiDang"," => " +  subDocument);
                            if(subDocument!=null)
                            {
                                NguoiDang nguoiDang=new NguoiDang();
                                nguoiDang.setMaNguoiDang((String)subDocument.get("MaNguoiDang"));
                                nguoiDang.setTenNguoiDang((String)subDocument.get("TenNguoiDang"));
                                nguoiDang.setAnhDaiDien((String)subDocument.get("AnhDaiDien"));
                                hotel.setNguoiDang(nguoiDang);
                            }
                            hotel.setTenKhachSan(documentSnapshot.getString("TenKhachSan"));
                            hotel.setMoTa(documentSnapshot.getString("MoTa"));
                            hotel.setDanhGias(null);
                            hotel.setDiaChi(documentSnapshot.getString("DiaChi"));
                            hotel.setTrangThai(documentSnapshot.getBoolean("TrangThai"));
                            hotel.setHangSao((long)documentSnapshot.get("HangSao"));

                            hotel.setHinhAnhs((ArrayList<String>) documentSnapshot.get("HinhAnh"));

                            ArrayList<Phong> dsPhong=new ArrayList<>();
                            ArrayList<Map<String,Object>> subArrayDocument= (ArrayList<Map<String, Object>>) documentSnapshot.get("Phong");
                            if(subArrayDocument!=null)
                            {
                                if(subArrayDocument.size()>0)
                                {
                                    Log.e("subArrayDocument","=>"+subArrayDocument);
                                    for (Map<String,Object> objectMap:subArrayDocument)
                                    {
                                        Phong phong=new Phong();
                                        Number numMax = (Number) objectMap.get("GiaMax");
                                        Number numMin = (Number) objectMap.get("GiaMin");
                                        Number numSoGiuong= (Number)objectMap.get("SoGiuong");
                                        phong.setGiaMax((long) Float.parseFloat(numMax.toString()));
                                        phong.setGiaMin((long) Float.parseFloat(numMin.toString()));
                                        phong.setSoGiuong((long) Float.parseFloat(numSoGiuong.toString()));
                                        phong.setHinhAnh((String) objectMap.get("HinhAnh"));
                                        dsPhong.add(phong);
                                    }
                                }
                            }
                            hotel.setPhongs(dsPhong);


                            ArrayList<Map<String,Object>> subArrayDocumentDanhGia=
                                    (ArrayList<Map<String, Object>>) documentSnapshot.get("DanhGia");
                            if(subArrayDocumentDanhGia!=null)
                            {
                                if(subArrayDocumentDanhGia.size()>0)
                                {
                                    ArrayList<DanhGia> dsDanhGia=new ArrayList<>();
                                    for (Map<String,Object> objectMap:subArrayDocumentDanhGia)
                                    {
                                        DanhGia danhGia=new DanhGia();
                                        danhGia.setMaNguoiDanhGia((String) objectMap.get("MaNguoiDanhGia"));
                                        //lay bien thoi gian kieu timestamp
                                        Timestamp DanhGiatimestamp= (Timestamp) objectMap.get("NgayDang");
                                        //convert sang localdatetime
                                        danhGia.setNgayDang(DanhGiatimestamp.toDate().toInstant()
                                                .atZone(ZoneId.systemDefault()).toLocalDateTime());

                                        danhGia.setTenNguoiDanhGia((String) objectMap.get("TenNguoiDanhGia"));
                                        danhGia.setRate((Long) objectMap.get("Rate"));
                                        Log.e("Rate","=>"+danhGia.getRate());
                                        danhGia.setNoiDung((String) objectMap.get("NoiDungDanhGia"));
                                        danhGia.setImgNguoiDang((String) objectMap.get("avartaNguoiDanhGia"));
                                        dsDanhGia.add(danhGia);
                                    }
                                    hotel.setDanhGias(dsDanhGia);
                                }
                            }

                            ArrayList<Map<String,Object>> subArrayDocumentHoiDap=
                                    (ArrayList<Map<String, Object>>) documentSnapshot.get("HoiDap");
                            if(subArrayDocumentHoiDap!=null)
                            {
                                if(subArrayDocumentHoiDap.size()>0)
                                {
                                    ArrayList<HoiDap> dsHoiDap=new ArrayList<>();
                                    for (Map<String,Object> objectMap:subArrayDocumentHoiDap)
                                    {
                                        HoiDap hoiDap=new HoiDap();
                                        hoiDap.setMaHoiDap((String) objectMap.get("MaHoiDap"));
                                        hoiDap.setMaNguoiHoi((String)objectMap.get("MaNguoiHoi"));
                                        //lay bien thoi gian kieu timestamp
                                        Timestamp DanhGiatimestamp= (Timestamp) objectMap.get("NgayHoi");
                                        //convert sang localdatetime
                                        hoiDap.setNgayHoi(DanhGiatimestamp.toDate().toInstant()
                                                .atZone(ZoneId.systemDefault()).toLocalDateTime());

                                        hoiDap.setTenNguoiHoi((String) objectMap.get("TenNguoiHoi"));
                                        hoiDap.setNoiDungHoiDap((String) objectMap.get("NoiDungHoiDap"));
                                        hoiDap.setImgNguoiHoi((String)objectMap.get("avartaNguoiHoi"));

                                        ArrayList<Map<String,Object>> subArrayDocumentTraLoiHoiDap=
                                                (ArrayList<Map<String, Object>>) objectMap.get("TraLoi");
                                        if(subArrayDocumentTraLoiHoiDap!=null)
                                        {
                                            ArrayList<HoiDap> dsTraLoi=new ArrayList<>();
                                            for (Map<String,Object> objectMapTraLoi : subArrayDocumentTraLoiHoiDap)
                                            {
                                                HoiDap traloi=new HoiDap();
                                                traloi.setMaHoiDap((String) objectMapTraLoi.get("MaCauTraLoi"));
                                                traloi.setMaNguoiHoi((String)objectMapTraLoi.get("MaNguoiTraLoi"));
                                                //lay bien thoi gian kieu timestamp
                                                Timestamp DanhGiatimestampTraLoi= (Timestamp) objectMapTraLoi.get("NgayTraLoi");
                                                //convert sang localdatetime
                                                traloi.setNgayHoi(DanhGiatimestampTraLoi.toDate().toInstant()
                                                        .atZone(ZoneId.systemDefault()).toLocalDateTime());

                                                traloi.setTenNguoiHoi((String) objectMapTraLoi.get("TenNguoiTraLoi"));
                                                traloi.setImgNguoiHoi((String)objectMapTraLoi.get("avartaNguoiHoi"));
                                                traloi.setNoiDungHoiDap((String) objectMapTraLoi.get("NoiDungTraLoi"));
                                                dsTraLoi.add(traloi);
                                            }
                                            hoiDap.setTraLois(dsTraLoi);
                                        }

                                        dsHoiDap.add(hoiDap);
                                    }
                                    hotel.setHoiDaps(dsHoiDap);
                                }
                            }


                            ArrayList<Map<String,Object>> subArrayDocumentLuotThich=
                                    (ArrayList<Map<String, Object>>) documentSnapshot.get("LuotThich");
                            if(subArrayDocumentLuotThich!=null)
                            {
                                ArrayList<NguoiDang> dsLuotThich=new ArrayList<>();
                                for (Map<String,Object> objectMap:subArrayDocumentLuotThich)
                                {
                                    dsLuotThich.add(new NguoiDang(null,(String) objectMap.get("MaNguoiThich")
                                            , (String) objectMap.get("TenNguoiThich")));
                                }
                                hotel.setLuotThichs(dsLuotThich);
                            }

                            Timestamp timestamp=documentSnapshot.getTimestamp("NgayDang");

                            hotel.setNgayDang(timestamp.toDate().toInstant()
                                    .atZone(ZoneId.systemDefault()).toLocalDateTime());
                        }
                        SetValueToControl();
                        //dialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DIAOLOGNhanXet", "onFailure: Loi load document");
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
                rate = Math.round(rate / hotel.getDanhGias().size() * 10) / 10f;
                AvargarateRatingBar_detail_hotel.setRating(rate);
                AvargarateRatingBar_detail_hotel.setIsIndicator(true);
                tvCountDanhGia_detail_hotel.setText(rate+"\n"+"Đánh giá "+"("+hotel.getDanhGias().size()+")");

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
                        ,dsDanhGia,hotel.getID_Document(),1,false);
                lvDanhGia_detail_hotel.setAdapter(adapterNhanXet);
                lvDanhGia_detail_hotel.setLayoutManager(new LinearLayoutManager(ActivityDetailHotel.this
                        , LinearLayoutManager.VERTICAL, false));
            }
            else
            {
                tvCountDanhGia_detail_hotel.setText("0.0\n"+"Đánh giá(0)");
                AvargarateRatingBar_detail_hotel.setIsIndicator(true);
            }
            if(hotel.getHoiDaps()!=null)
            {
                Adapter_listview_hoidap_ver1 adapter_listview_hoidap_ver1=new Adapter_listview_hoidap_ver1(hotel.getHoiDaps(),
                        ActivityDetailHotel.this,hotel.getID_Document(),false);
                adapter_listview_hoidap_ver1.notifyDataSetChanged();
                lvHoiDap_detail_hotel.setAdapter(adapter_listview_hoidap_ver1);
                lvHoiDap_detail_hotel.setLayoutManager(new LinearLayoutManager(ActivityDetailHotel.this,
                        RecyclerView.VERTICAL,false));
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
                AdapterPhong adapterPhong=new AdapterPhong(hotel.getPhongs(),ActivityDetailHotel.this,
                        hotel.getID_Document(),false);
                lvPhong_detail_hotel.setAdapter(adapterPhong);
                lvPhong_detail_hotel.setLayoutManager(new LinearLayoutManager(ActivityDetailHotel.this
                        , LinearLayoutManager.HORIZONTAL, false));
            }

        }

    }
}