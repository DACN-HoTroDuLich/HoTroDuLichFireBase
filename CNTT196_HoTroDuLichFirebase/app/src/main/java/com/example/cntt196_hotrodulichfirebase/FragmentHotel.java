package com.example.cntt196_hotrodulichfirebase;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.cntt196_hotrodulichfirebase.adapters.AdapterHotel;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterTravel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

    private QuerySnapshot queryDocumentSnapshots;
    private ArrayList<DocumentSnapshot> documentSnapshots;
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
        mView = inflater.inflate(R.layout.fragment_hotel, container, false);
        context=requireContext();
        addControls(mView);
        //dsTravel=new ArrayList<Travel>();
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
                            adapterHotel=new AdapterHotel(queryDocumentSnapshots,getContext());
                            listView.setAdapter(adapterHotel);
                        }

                    }
                });

    }
}