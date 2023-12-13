package com.example.cntt196_hotrodulichfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cntt196_hotrodulichfirebase.FirebaseService.StorageService;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_images_ver1;
import com.example.cntt196_hotrodulichfirebase.adapters.DateTimeToString;
import com.example.cntt196_hotrodulichfirebase.models.BaiVietAdmin;
import com.example.cntt196_hotrodulichfirebase.models.DanhGia;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityDetailBaiVietAdminTravel extends AppCompatActivity {

    private ImageButton imgButtonBack_AdminPostDetail;
    private ImageView imgAvatarUser_AdminPostDetail, imgMain_AdminPostDetail;
    private TextView tvUserName_AdminPostDetail, tvDateTime_AdminPostDetail, tvTitle_AdminPostDetail, tvContent_AdminPostDetail,
            tvAddress_AdminPostDetail;
    private RecyclerView rclSubImg_AdminPostDetail;
    Button btnAccept, btnReject;
    FirebaseFirestore fStore;
    private BaiVietAdmin baiVietAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bai_viet_admin_travel);
        addControls();
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            baiVietAdmin=(BaiVietAdmin) bundle.getSerializable("PostAdmin");
            SetValueToControl();
        }
        else
        {
            finish();
        }

        imgButtonBack_AdminPostDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void SetValueToControl()
    {
        fStore.collection("Travel").document(baiVietAdmin.getId_Document()).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot != null){
                                    Map<String, Object> data = documentSnapshot.getData();
                                    Map<String, Object> nguoiDang = (Map<String, Object>) data.get("NguoiDang");
                                    List<String> lstImage = (List<String>) data.get("HinhAnh");

                                    String filePath="avarta/" + nguoiDang.get("AnhDaiDien");
                                    StorageService.LoadImageUri_Avarta(filePath,imgAvatarUser_AdminPostDetail,getApplicationContext());

                                    tvUserName_AdminPostDetail.setText(nguoiDang.get("TenNguoiDang").toString());

                                    Timestamp timestamp = (Timestamp) data.get("NgayDang");
                                    LocalDateTime localDateTime = timestamp.toDate().toInstant()
                                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
                                    tvDateTime_AdminPostDetail.setText(formatDateTime(localDateTime));

                                    tvTitle_AdminPostDetail.setText(data.get("TieuDe").toString());

                                    String rootFile= "Travel/"+ baiVietAdmin.getId_Document()+"/"+lstImage.get(0);
                                    StorageService.LoadImageUri(rootFile,imgMain_AdminPostDetail,getApplicationContext(),1280,750);

                                    Adapter_listview_images_ver1 adapter_listview_images_ver1 = new
                                            Adapter_listview_images_ver1(getAllImageTravelInDoc(lstImage),getApplicationContext(),baiVietAdmin.getId_Document(),true);
                                    rclSubImg_AdminPostDetail.setAdapter(adapter_listview_images_ver1);
                                    rclSubImg_AdminPostDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()
                                            , LinearLayoutManager.HORIZONTAL, false));

                                    tvContent_AdminPostDetail.setText(data.get("MoTa").toString());

                                    tvAddress_AdminPostDetail.setText(data.get("DiaChi").toString());
                                }
                            }
                        });
    }
    private ArrayList<String> getAllImageTravelInDoc(List<String> lstImage){
        ArrayList<String> lstResult = new ArrayList<>();
        for(String imgName:lstImage){
            lstResult.add(imgName);
        }
        return lstResult;
    }
    public String formatDateTime(LocalDateTime localDateTime){
        String dateTime = localDateTime.toString();
        String date = dateTime.substring(0, 10);
        String time = dateTime.substring(11, 16);
        String result = date + " " + time;
        return result;
    }
    private void addControls()
    {
        imgButtonBack_AdminPostDetail=findViewById(R.id.imgButtonBack_AdminPostDetail);
        imgAvatarUser_AdminPostDetail= findViewById(R.id.imgAvatarUser_AdminPostDetail);
        tvUserName_AdminPostDetail= findViewById(R.id.tvUserName_AdminPostDetail);
        tvDateTime_AdminPostDetail= findViewById(R.id.tvDateTime_AdminPostDetail);
        tvTitle_AdminPostDetail= findViewById(R.id.tvTitle_AdminPostDetail);
        imgMain_AdminPostDetail= findViewById(R.id.imgMain_AdminPostDetail);
        rclSubImg_AdminPostDetail= findViewById(R.id.rclSubImg_AdminPostDetail);
        tvContent_AdminPostDetail= findViewById(R.id.tvContent_AdminPostDetail);
        tvAddress_AdminPostDetail= findViewById(R.id.tvAddress_AdminPostDetail);
        btnAccept= findViewById(R.id.btnAccept);
        btnReject= findViewById(R.id.btnReject);
        fStore = FirebaseFirestore.getInstance();
    }
}