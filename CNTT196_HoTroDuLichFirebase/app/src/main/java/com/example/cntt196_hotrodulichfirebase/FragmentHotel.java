package com.example.cntt196_hotrodulichfirebase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cntt196_hotrodulichfirebase.adapters.AdapterHotel;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterTravel;
import com.example.cntt196_hotrodulichfirebase.models.DanhGia;
import com.example.cntt196_hotrodulichfirebase.models.Hotel;
import com.example.cntt196_hotrodulichfirebase.models.NguoiDang;
import com.example.cntt196_hotrodulichfirebase.models.Phong;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    //DuLieu
    private Context context;
    private LinearLayout linearLayout_fragmentHotel;
    private ListView listView;

    private boolean Flag;

    private ArrayList<Hotel> arrayListHotel;
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
        mView = inflater.inflate(R.layout.fragment_hotel, container, false);
        context=requireContext();
        addControls(mView);
        adapterHotel=new AdapterHotel(arrayListHotel,getContext());
        listView.setAdapter(adapterHotel);
        LoadListHotel();
        return mView;
    }
    private void addControls(View view) {
        listView= view.findViewById(R.id.listViewHotel);
        linearLayout_fragmentHotel=view.findViewById(R.id.linearLayout_fragmentHotel);
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
                                        phong.setGiaMax((long) objectMap.get("GiaMax"));
                                        phong.setGiaMin((long) objectMap.get("GiaMin"));
                                        phong.setSoGiuong((long) objectMap.get("SoGiuong"));
                                        phong.setHinhAnh((String) objectMap.get("HinhAnh"));
                                        dsPhong.add(phong);
                                    }
                                }
                                hotel.setPhongs(dsPhong);

                                ArrayList<DanhGia> dsDanhGia=new ArrayList<>();
                                ArrayList<Map<String,Object>> subArrayDocumentDanhGia= (ArrayList<Map<String, Object>>) document.get("DanhGia");
                                if(subArrayDocumentDanhGia!=null)
                                {
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
                                }
                                hotel.setDanhGias(dsDanhGia);

                                Timestamp timestamp=document.getTimestamp("NgayDang");

                                hotel.setNgayDang(timestamp.toDate().toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDateTime());

                                for(int i=0;i<15;i++)
                                {
                                    arrayListHotel.add(hotel);
                                    arrayListHotel.add(hotel);
                                    arrayListHotel.add(hotel);
                                }
                                adapterHotel.notifyDataSetChanged();
                            }
                        }

                    }
                });

    }
}