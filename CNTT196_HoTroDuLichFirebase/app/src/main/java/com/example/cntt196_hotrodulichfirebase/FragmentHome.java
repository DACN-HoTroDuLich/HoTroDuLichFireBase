package com.example.cntt196_hotrodulichfirebase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cntt196_hotrodulichfirebase.FirebaseService.StorageService;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterTravel;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterTravelHome;
import com.example.cntt196_hotrodulichfirebase.models.NguoiDang;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View mView;
    private ImageButton btn_Previous_frgHome, btn_Next_frgHome;
    private RecyclerView recyclerview_hotel_frgHome, recyclerview_travel_frgHome;
    private ImageView img5_Flipper_frgHome, img4_Flipper_frgHome
    ,img3_Flipper_frgHome, img2_Flipper_frgHome, img1_Flipper_frgHome;
    private ViewFlipper viewFlipper;





    private Context context;
    private AdapterTravelHome adapterTravelHome;
    private ArrayList<Travel> travelArrayList;
    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
        context=requireContext();
        mView=inflater.inflate(R.layout.fragment_home, container, false);
        Init(mView);
        LoadListSlider();
        travelArrayList=new ArrayList<>();
        adapterTravelHome=new AdapterTravelHome(travelArrayList,context);
        recyclerview_travel_frgHome.setAdapter(adapterTravelHome);
        recyclerview_travel_frgHome.setLayoutManager(new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL, false));

        recyclerview_hotel_frgHome.setAdapter(adapterTravelHome);
        recyclerview_hotel_frgHome.setLayoutManager(new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL, false));

        LoadListTravel();
        return mView;
    }

    private void LoadListSlider() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String[] slideIntros= {"dulich1.jpg","dulich2.jpg","dulich3.jpg","dulich4.jpg","dulich5.jpg"};
        ArrayList<String> slideIntrosLink=new ArrayList<>();

        String rootFile1= "SlideIntro/"+ slideIntros[0];
        StorageService.LoadImageUri(rootFile1,img1_Flipper_frgHome,context,1280,570);

        String rootFile2= "SlideIntro/"+ slideIntros[1];
        StorageService.LoadImageUri(rootFile2,img2_Flipper_frgHome,context,1280,570);

        String rootFile3= "SlideIntro/"+ slideIntros[2];
        StorageService.LoadImageUri(rootFile3,img3_Flipper_frgHome,context,1280,570);

        String rootFile4= "SlideIntro/"+ slideIntros[3];
        StorageService.LoadImageUri(rootFile4,img4_Flipper_frgHome,context,1280,570);

        String rootFile5= "SlideIntro/"+ slideIntros[4];
        StorageService.LoadImageUri(rootFile5,img5_Flipper_frgHome,context,1280,570);

        viewFlipper.setAutoStart(true);
        btn_Previous_frgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(context,
                        R.anim.slide_left_to_right);
                viewFlipper.setOutAnimation(context,
                        R.anim.slide_right_to_left);

                // It shows previous item.
                viewFlipper.showPrevious();
            }
        });
        btn_Next_frgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(context,
                        R.anim.slide_right_to_left);
                viewFlipper.setOutAnimation(context,
                        R.anim.slide_left_to_right);
                viewFlipper.showNext();
            }
        });
    }
    private void LoadListTravel()
    {
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Travel").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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

                                Number numMax = (Number) document.get("GiaMax");
                                Number numMin = (Number) document.get("GiaMin");
                                travel.setGiaMax((long) Float.parseFloat(numMax.toString()));
                                travel.setGiaMin((long)Float.parseFloat(numMin.toString()));

                                ArrayList<String> dsHinh=new ArrayList<>();
                                dsHinh= (ArrayList<String>) document.get("HinhAnh");
                                travel.setHinhAnhs(dsHinh);


                                Timestamp timestamp=document.getTimestamp("NgayDang");

                                travel.setNgayDang(timestamp.toDate().toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDateTime());

                                travelArrayList.add(travel);
                                adapterTravelHome.notifyDataSetChanged();
                            }
                        }

                    }
                });
    }
    private void Init(View view)
    {
        btn_Previous_frgHome= view.findViewById(R.id.btn_Previous_frgHome);
        btn_Next_frgHome= view.findViewById(R.id.btn_Next_frgHome);
        viewFlipper=view.findViewById(R.id.viewFlipper);
        recyclerview_hotel_frgHome= view.findViewById(R.id.recyclerview_hotel_frgHome);
        recyclerview_travel_frgHome= view.findViewById(R.id.recyclerview_travel_frgHome);
        img5_Flipper_frgHome= view.findViewById(R.id.img5_Flipper_frgHome);
        img4_Flipper_frgHome= view.findViewById(R.id.img4_Flipper_frgHome);
        img3_Flipper_frgHome= view.findViewById(R.id.img3_Flipper_frgHome);
        img2_Flipper_frgHome= view.findViewById(R.id.img2_Flipper_frgHome);
        img1_Flipper_frgHome= view.findViewById(R.id.img1_Flipper_frgHome);
    }
}