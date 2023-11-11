package com.example.cntt196_hotrodulichfirebase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cntt196_hotrodulichfirebase.adapters.AdapterTravel;
import com.example.cntt196_hotrodulichfirebase.models.HinhAnh;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.ZoneId;
import java.util.ArrayList;

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
    //DuLieu
    private Context context;
    private LinearLayout linearLayout_fragmentTravel;
    private ListView listView;

    private boolean Flag;

    private QuerySnapshot queryDocumentSnapshots;
    private ArrayList<DocumentSnapshot> documentSnapshots;
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
        context=requireContext();
        mView= inflater.inflate(R.layout.fragment_fragmentr_travel, container, false);
        addControls(mView);
        //dsTravel=new ArrayList<Travel>();
        LoadListTravel();





        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragmentr_travel, container, false);
        return mView;
    }
    private void addControls(View view) {
        listView= view.findViewById(R.id.listViewTravel);
        linearLayout_fragmentTravel=view.findViewById(R.id.linearLayout_fragmentTravel);
    }
    private void LoadListTravel()
    {
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Travel").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots!=null)
                        {
                            adapterTravel=new AdapterTravel(queryDocumentSnapshots,getContext());
                            listView.setAdapter(adapterTravel);
                        }

                    }
                });
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(Task<QuerySnapshot> task) {
//                        if(task.isSuccessful())
//                        {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Travel travel=new Travel();
//                                travel.setID_Document(document.getId());
//                                travel.setTieuDe(document.getString("TieuDe"));
//                                travel.setMoTa(document.getString("MoTa"));
//                                travel.setDanhGias(null);
//                                travel.setDiaChi(document.getString("DiaChi"));
//                                travel.setGiaMax(document.getDouble("GiaMax"));
//                                travel.setGiaMin(document.getDouble("GiaMin"));
//                                travel.setHinhAnhs((ArrayList<HinhAnh>) document.get("HinhAnh"));
//
//                                Timestamp timestamp=document.getTimestamp("NgayDang");
//
//                                travel.setNgayDang(timestamp.toDate().toInstant()
//                                        .atZone(ZoneId.systemDefault()).toLocalDateTime());
//                                dsTravel.add(travel);
//                                dsTravel.add(travel);
//
//                                adapterTravel=new AdapterTravel(dsTravel,getContext());
//                                Log.d("TravelModel", document.getId() + " => " +  dsTravel.size());
//                                listView.setAdapter(adapterTravel);
//                            }
//                        }
//                    }
//                });


    }
}