package com.example.cntt196_hotrodulichfirebase;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.example.cntt196_hotrodulichfirebase.adapters.AdapterBaiVietAdmin;
import com.example.cntt196_hotrodulichfirebase.models.BaiVietAdmin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;

public class ActivityPostTravelAdmin extends AppCompatActivity {
    FirebaseFirestore fStore;
    private ArrayList<BaiVietAdmin> arrLst_BaiVietAdmin;
    private AdapterBaiVietAdmin adapterBaiVietAdmin;
    private Context context;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_travel_admin);
        listView = findViewById(R.id.lvBaiVietAdmin);

        initVariable();
        listView.setAdapter(adapterBaiVietAdmin);
        LoadTravelListData();

    }
    private void initVariable(){
        fStore = FirebaseFirestore.getInstance();
        arrLst_BaiVietAdmin = new ArrayList<>();
        context = getApplicationContext();
        adapterBaiVietAdmin = new AdapterBaiVietAdmin(arrLst_BaiVietAdmin,context);
    }
    private void LoadTravelListData() {
        fStore.collection("Travel").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null) {
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                BaiVietAdmin baiVietAdmin = new BaiVietAdmin();
                                baiVietAdmin.setId_Document(document.getId());
                                Map<String, Object> subDocument = (Map<String, Object>) document.get("NguoiDang");
                                if (subDocument != null) {
                                    baiVietAdmin.setTenNguoiDang((String) subDocument.get("TenNguoiDang"));
                                    baiVietAdmin.setImg((String)subDocument.get("AnhDaiDien"));
                                }
                                baiVietAdmin.setTieuDe(document.getString("TieuDe"));

                                Timestamp timestamp = document.getTimestamp("NgayDang");
                                baiVietAdmin.setNgayGioDang(timestamp.toDate().toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDateTime());

                                arrLst_BaiVietAdmin.add(baiVietAdmin);
                                adapterBaiVietAdmin.notifyDataSetChanged();

                            }
                        }
                    }
                });
    }
}