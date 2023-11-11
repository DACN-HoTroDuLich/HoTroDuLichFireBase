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

import com.example.cntt196_hotrodulichfirebase.adapters.AdapterTravel;
import com.example.cntt196_hotrodulichfirebase.adapters.AdapterTravelHome;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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



    private QuerySnapshot queryDocumentSnapshots;
    private ArrayList<DocumentSnapshot> documentSnapshots;

    private Context context;
    private AdapterTravelHome adapterTravelHome;
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
        LoadListTravel();
        return mView;
    }

    private void LoadListTravel() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String[] slideIntros= {"dulich1.jpg","dulich2.jpg","dulich3.jpg","dulich4.jpg","dulich5.jpg"};
        ArrayList<String> slideIntrosLink=new ArrayList<>();
        for(int i=0;i<slideIntros.length;i++)
        {
            storageRef.child("SlideIntro/"+slideIntros[i]).getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            slideIntrosLink.add(uri.toString());
                            Log.e("URL_Slide","=>"+uri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        if(slideIntrosLink.size()>0)
        {
            Picasso picassoSlideIntro=Picasso.with(context);
            picassoSlideIntro.load(slideIntrosLink.get(0)).resize(1280,720)
                    .placeholder(R.drawable.default_image_empty)
                    .into(img1_Flipper_frgHome);
            picassoSlideIntro.invalidate(slideIntrosLink.get(0));

            picassoSlideIntro.load(slideIntrosLink.get(1)).resize(1280,720)
                    .placeholder(R.drawable.default_image_empty)
                    .into(img2_Flipper_frgHome);
            picassoSlideIntro.invalidate(slideIntrosLink.get(1));

            picassoSlideIntro.load(slideIntrosLink.get(2)).resize(1280,720)
                    .placeholder(R.drawable.default_image_empty)
                    .into(img3_Flipper_frgHome);
            picassoSlideIntro.invalidate(slideIntrosLink.get(2));

            picassoSlideIntro.load(slideIntrosLink.get(3)).resize(1280,720)
                    .placeholder(R.drawable.default_image_empty)
                    .into(img4_Flipper_frgHome);
            picassoSlideIntro.invalidate(slideIntrosLink.get(3));

            picassoSlideIntro.load(slideIntrosLink.get(4)).resize(1280,720)
                    .placeholder(R.drawable.default_image_empty)
                    .into(img5_Flipper_frgHome);
            picassoSlideIntro.invalidate(slideIntrosLink.get(4));

        }
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


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Travel").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null) {
                            adapterTravelHome = new AdapterTravelHome(queryDocumentSnapshots, context);
                            recyclerview_travel_frgHome.setAdapter(adapterTravelHome);
                            recyclerview_travel_frgHome.setLayoutManager(new LinearLayoutManager(context
                                    , LinearLayoutManager.HORIZONTAL, false));

                            recyclerview_hotel_frgHome.setAdapter(adapterTravelHome);
                            recyclerview_hotel_frgHome.setLayoutManager(new LinearLayoutManager(context
                                    , LinearLayoutManager.HORIZONTAL, false));
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