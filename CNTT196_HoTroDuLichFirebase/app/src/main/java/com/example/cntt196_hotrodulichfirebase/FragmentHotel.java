package com.example.cntt196_hotrodulichfirebase;

import android.animation.Animator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cntt196_hotrodulichfirebase.FirebaseService.StorageService;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterHotel;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterTravel;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_tinh_ver1;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_tinh_ver2;
import com.example.cntt196_hotrodulichfirebase.models.DanhGia;
import com.example.cntt196_hotrodulichfirebase.models.Hotel;
import com.example.cntt196_hotrodulichfirebase.models.LuotThich;
import com.example.cntt196_hotrodulichfirebase.models.NguoiDang;
import com.example.cntt196_hotrodulichfirebase.models.Phong;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHotel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHotel extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View mView;
    //Adapter
    private AdapterHotel adapterHotel;
    private Adapter_listview_tinh_ver1 adapter_listview_tinh_ver1;
    private Adapter_listview_tinh_ver2 adapter_listview_tinh_ver2;
    //DuLieu
    private Context context;
    private LinearLayout linearLayout_fragmentHotel, linearLayout_listTinh_fragmentHotel;
    private ListView listView;


    private RecyclerView lvTinh_fragmentHotel,lvTinh_ver2_fragmentHotel;

    private boolean Flag;

    private ArrayList<Hotel> arrayListHotel;
    private ArrayList<String> arrayListTinh;
    public FragmentHotel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHotel.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHotel newInstance(String param1, String param2) {
        FragmentHotel fragment = new FragmentHotel();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        arrayListHotel=new ArrayList<>();
        arrayListTinh=new ArrayList<>();
        arrayListTinh.add("Tất cả");

        mView = inflater.inflate(R.layout.fragment_hotel, container, false);
        context=requireContext();
        addControls(mView);
//        String rootFileImgIntro= "SlideIntro/Hotel/intro-hotel1.png";
//        StorageService.LoadImageUri(rootFileImgIntro, imageIntro_fragmentHotel,context,1580,720);

        adapterHotel=new AdapterHotel(arrayListHotel,getContext());
        adapter_listview_tinh_ver1=new Adapter_listview_tinh_ver1(arrayListTinh,getContext());
        adapter_listview_tinh_ver2=new Adapter_listview_tinh_ver2(arrayListTinh, getContext());
        listView.setAdapter(adapterHotel);

        lvTinh_ver2_fragmentHotel.setAdapter(adapter_listview_tinh_ver2);
        lvTinh_ver2_fragmentHotel.setLayoutManager(new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL, false));

        lvTinh_fragmentHotel.setAdapter(adapter_listview_tinh_ver1);
        lvTinh_fragmentHotel.setLayoutManager(new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL, false));

        LoadListHotel();

        Animation animationIn= AnimationUtils.loadAnimation(context,R.anim.list_view_in);
        Animation animationOut= AnimationUtils.loadAnimation(context,R.anim.list_view_out);
        //lvTinh_fragmentHotel.startAnimation(animationIn);
        SetStateSrollListView(animationIn, animationOut);

        return mView;
    }


    private void SetStateSrollListView(Animation animationIn, Animation animationOut)
    {
        final int[] previousVisibleItem = {0};
        final int[] previousVisibleItemLast = {0};
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > previousVisibleItem[0]) {
                    if(previousVisibleItem[0]<previousVisibleItemLast[0])
                    {
                        linearLayout_listTinh_fragmentHotel.startAnimation(animationIn);

                    }
                    lvTinh_ver2_fragmentHotel.setVisibility(View.VISIBLE);
                    linearLayout_listTinh_fragmentHotel.setVisibility(View.GONE);
                }
                else if(firstVisibleItem == previousVisibleItem[0])
                {}
                else if (firstVisibleItem < previousVisibleItem[0]) {
                    // Người dùng đang cuộn lên
                    if(previousVisibleItem[0]>previousVisibleItemLast[0])
                    {
                        linearLayout_listTinh_fragmentHotel.startAnimation(animationOut);

                    }
                    lvTinh_ver2_fragmentHotel.setVisibility(View.GONE);
                    linearLayout_listTinh_fragmentHotel.setVisibility(View.VISIBLE);

                }
                previousVisibleItemLast[0]=previousVisibleItem[0];
                previousVisibleItem[0] = firstVisibleItem;
            }
        });
    }
    private void addControls(View view) {
        listView= view.findViewById(R.id.listViewHotel);
        linearLayout_fragmentHotel=view.findViewById(R.id.linearLayout_fragmentHotel);

        lvTinh_fragmentHotel = view.findViewById(R.id.lvTinh_fragmentHotel);
        lvTinh_ver2_fragmentHotel = view.findViewById(R.id.lvTinh_ver2_fragmentHotel);
        linearLayout_listTinh_fragmentHotel=view.findViewById(R.id.linearLayout_listTinh_fragmentHotel);
        lvTinh_ver2_fragmentHotel.setVisibility(View.GONE);
    }
    private void LoadListHotel()
    {
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Hotel").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots!=null)
                        {

                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                            {
                                Hotel hotel=new Hotel();
                                hotel.setID_Document(document.getId());
                                Map<String,Object> subDocument=(Map<String,Object>) document.get("NguoiDang");
                                Log.d("HotelNguoiDang"," => " +  subDocument);
                                if(subDocument!=null)
                                {
                                    NguoiDang nguoiDang=new NguoiDang();
                                    nguoiDang.setMaNguoiDang((String)subDocument.get("MaNguoiDang"));
                                    nguoiDang.setTenNguoiDang((String)subDocument.get("TenNguoiDang"));
                                    nguoiDang.setAnhDaiDien((String)subDocument.get("AnhDaiDien"));
                                    hotel.setNguoiDang(nguoiDang);
                                }
                                hotel.setTenKhachSan(document.getString("TenKhachSan"));
                                hotel.setMoTa(document.getString("MoTa"));
                                hotel.setDanhGias(null);
                                hotel.setDiaChi(document.getString("DiaChi"));
                                hotel.setTrangThai(document.getBoolean("TrangThai"));
                                hotel.setHangSao((long)document.get("HangSao"));

                                hotel.setHinhAnhs((ArrayList<String>) document.get("HinhAnh"));

                                ArrayList<Phong> dsPhong=new ArrayList<>();
                                ArrayList<Map<String,Object>> subArrayDocument= (ArrayList<Map<String, Object>>) document.get("Phong");
                                if(subArrayDocument!=null)
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
                                hotel.setPhongs(dsPhong);


                                ArrayList<Map<String,Object>> subArrayDocumentDanhGia= (ArrayList<Map<String, Object>>) document.get("DanhGia");
                                if(subArrayDocumentDanhGia!=null)
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


                                ArrayList<Map<String,Object>> subArrayDocumentLuotThich= (ArrayList<Map<String, Object>>) document.get("LuotThich");
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

                                Timestamp timestamp=document.getTimestamp("NgayDang");

                                hotel.setNgayDang(timestamp.toDate().toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDateTime());

                                arrayListHotel.add(hotel);
                                String[] DiaChiSplit=hotel.getDiaChi().split(",");
                                if(!TinhIsInstance(DiaChiSplit[DiaChiSplit.length-1]))
                                { arrayListTinh.add(DiaChiSplit[DiaChiSplit.length-1]);}


                                adapterHotel.notifyDataSetChanged();
                                adapter_listview_tinh_ver1.notifyDataSetChanged();
                                adapter_listview_tinh_ver2.notifyDataSetChanged();
                            }
                        }

                    }
                });

    }
    private boolean TinhIsInstance(String tenTinh)
    {
        for(String tinh: arrayListTinh)
        {
            if(tinh.equals(tenTinh))
            {
                return true;
            }
        }
        return false;
    }

}