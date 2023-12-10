package com.example.cntt196_hotrodulichfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.cntt196_hotrodulichfirebase.adapters.AdapterTravel;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_tinh_ver1;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_tinh_ver2;
import com.example.cntt196_hotrodulichfirebase.models.NguoiDang;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
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

public class MainActivityAdmin extends AppCompatActivity {

    private ArrayList<String> arrayListTinh;
    ArrayList<Travel> arrayListTravel;
    private AdapterTravel adapterTravel;
    private Adapter_listview_tinh_ver1 adapter_listview_tinh_ver1;
    private Adapter_listview_tinh_ver2 adapter_listview_tinh_ver2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        arrayListTravel = new ArrayList<Travel>();
        LoadListTravel();
    }
    private void LoadListTravel()
    {
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Travel").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null)
                {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                    {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();

                        Travel travel=new Travel();
                        travel.setID_Document(document.getId());
                        Map<String,Object> subDocument=(Map<String,Object>) document.get("NguoiDang");
                        Log.d("TravelNguoiDang"," => " +  subDocument);
                        if(subDocument!=null)
                        {
                            NguoiDang nguoiDang=new NguoiDang();
                            nguoiDang.setMaNguoiDang((String)subDocument.get("MaNguoiDang"));
                            nguoiDang.setTenNguoiDang((String)subDocument.get("TenNguoiDang"));
                            nguoiDang.setAnhDaiDien((String)subDocument.get("AnhDaiDien"));
                            travel.setNguoiDang(nguoiDang);
                        }
                        travel.setTieuDe(document.getString("TieuDe"));
                        travel.setMoTa(document.getString("MoTa"));
                        travel.setDanhGias(null);
                        travel.setDiaChi(document.getString("DiaChi"));
                        travel.setGiaMax(document.getDouble("GiaMax"));
                        travel.setGiaMin(document.getDouble("GiaMin"));
                        travel.setTrangThai(document.getBoolean("TrangThai"));

                        ArrayList<String> dsHinh=new ArrayList<>();
                        dsHinh= (ArrayList<String>) document.get("HinhAnh");
                        travel.setHinhAnhs(dsHinh);


                        Timestamp timestamp=document.getTimestamp("NgayDang");

                        travel.setNgayDang(timestamp.toDate().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDateTime());

                        for(int i=0;i<15;i++)
                        {
                            arrayListTravel.add(travel);
                            arrayListTravel.add(travel);
                            arrayListTravel.add(travel);
                            arrayListTravel.add(travel);
                            String[] DiaChiSplit=travel.getDiaChi().split(",");
//                            arrayListTinh.add(DiaChiSplit[DiaChiSplit.length-1]);
                        }
//                        adapterTravel.notifyDataSetChanged();
//                        adapter_listview_tinh_ver1.notifyDataSetChanged();
//                        adapter_listview_tinh_ver2.notifyDataSetChanged();

                    }
                }
            }
        });
    }
}