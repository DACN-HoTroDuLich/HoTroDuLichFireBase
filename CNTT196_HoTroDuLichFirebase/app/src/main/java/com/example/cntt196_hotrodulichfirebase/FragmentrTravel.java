package com.example.cntt196_hotrodulichfirebase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cntt196_hotrodulichfirebase.adapters.AdapterTravel;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_tinh_ver1;
import com.example.cntt196_hotrodulichfirebase.adapters.Adapter_listview_tinh_ver2;
import com.example.cntt196_hotrodulichfirebase.models.HinhAnh;
import com.example.cntt196_hotrodulichfirebase.models.Hotel;
import com.example.cntt196_hotrodulichfirebase.models.NguoiDang;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentrTravel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentrTravel extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private View mView;
    //Adapter
    private AdapterTravel adapterTravel;
    private Adapter_listview_tinh_ver1 adapter_listview_tinh_ver1;
    private Adapter_listview_tinh_ver2 adapter_listview_tinh_ver2;
    //DuLieu
    private Context context;
    private LinearLayout linearLayout_fragmentTravel, linearLayout_listTinh_fragmentTravel;
    private ListView listView;
    private RecyclerView lvTinh_fragmentTravel,lvTinh_ver2_fragmentTravel;


    private boolean Flag;

    private ArrayList<Travel> arrayListTravel;
    private ArrayList<String> arrayListTinh;
    //private ArrayList<Travel> dsTravel;
    //===================


    public FragmentrTravel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentrTravel.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentrTravel newInstance(String param1, String param2) {
        FragmentrTravel fragment = new FragmentrTravel();
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
        arrayListTravel=new ArrayList<>();
        arrayListTinh=new ArrayList<>();
        arrayListTinh.add("Tất cả");
        context=requireContext();
        mView= inflater.inflate(R.layout.fragment_fragmentr_travel, container, false);
        addControls(mView);
        //dsTravel=new ArrayList<Travel>();
        adapterTravel=new AdapterTravel(arrayListTravel,context);

        adapter_listview_tinh_ver1=new Adapter_listview_tinh_ver1(arrayListTinh,getContext());
        adapter_listview_tinh_ver2=new Adapter_listview_tinh_ver2(arrayListTinh, getContext());


        lvTinh_ver2_fragmentTravel.setAdapter(adapter_listview_tinh_ver2);
        lvTinh_ver2_fragmentTravel.setLayoutManager(new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL, false));

        lvTinh_fragmentTravel.setAdapter(adapter_listview_tinh_ver1);
        lvTinh_fragmentTravel.setLayoutManager(new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL, false));

        listView.setAdapter(adapterTravel);
        LoadListTravel();

        Animation animationIn= AnimationUtils.loadAnimation(context,R.anim.list_view_in);
        Animation animationOut= AnimationUtils.loadAnimation(context,R.anim.list_view_out);
        //lvTinh_fragmentTravel.startAnimation(animationIn);
        SetStateSrollListView(animationIn, animationOut);

        return mView;
    }

    private void SetStateSrollListView(Animation animationIn, Animation animationOut)
    {
        final int[] previousVisibleItem = {0};
        final int[] previousVisibleItemLast = {0};
        //final int[] previousVisibleItemLastParent = {0};
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > previousVisibleItemLast[0]) {
                    if(previousVisibleItem[0]<previousVisibleItemLast[0])
                    {
                        linearLayout_listTinh_fragmentTravel.startAnimation(animationIn);

                    }
                    lvTinh_ver2_fragmentTravel.setVisibility(View.VISIBLE);
                    linearLayout_listTinh_fragmentTravel.setVisibility(View.GONE);
                }
                else if (firstVisibleItem < previousVisibleItemLast[0]) {
                    // Người dùng đang cuộn lên
                    if(previousVisibleItem[0]>previousVisibleItemLast[0])
                    {
                        linearLayout_listTinh_fragmentTravel.startAnimation(animationOut);

                    }
                    lvTinh_ver2_fragmentTravel.setVisibility(View.GONE);
                    linearLayout_listTinh_fragmentTravel.setVisibility(View.VISIBLE);

                }
                //previousVisibleItemLastParent[0]=previousVisibleItemLast[0];
                previousVisibleItemLast[0]=previousVisibleItem[0];
                previousVisibleItem[0] = firstVisibleItem;
            }
        });
    }
    private void addControls(View view) {
        listView= view.findViewById(R.id.listViewTravel);
        linearLayout_fragmentTravel=view.findViewById(R.id.linearLayout_fragmentTravel);
        linearLayout_listTinh_fragmentTravel=view.findViewById(R.id.linearLayout_listTinh_fragmentTravel);

        lvTinh_fragmentTravel = view.findViewById(R.id.lvTinh_fragmentTravel);
        lvTinh_ver2_fragmentTravel = view.findViewById(R.id.lvTinh_ver2_fragmentTravel);
        lvTinh_ver2_fragmentTravel.setVisibility(View.GONE);
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
                                Log.d("IdTravel"," => " +  travel.getID_Document());
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

                                arrayListTravel.add(travel);
                                arrayListTravel.add(travel);
                                arrayListTravel.add(travel);
                                String[] DiaChiSplit=travel.getDiaChi().split(",");
                                arrayListTinh.add(DiaChiSplit[DiaChiSplit.length-1]);

                                adapterTravel.notifyDataSetChanged();
                                adapter_listview_tinh_ver1.notifyDataSetChanged();
                                adapter_listview_tinh_ver2.notifyDataSetChanged();
                            }
                        }

                    }
                });
    }
}