package com.example.cntt196_hotrodulichfirebase.FirebaseService;

import android.util.Log;

import com.example.cntt196_hotrodulichfirebase.models.HinhAnh;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.ZoneId;
import java.util.ArrayList;

public class TravelService {
    public FirebaseFirestore firebaseFirestore;
    public TravelService()
    {
        firebaseFirestore=FirebaseFirestore.getInstance();
    }
    public void getListTravel(final OnDataLoadedListener listener)
    {

        firebaseFirestore.collection("Travel").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            ArrayList<Travel> dsTravel=new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Travel travel=new Travel();
                                travel.setID_Document(document.getId());
                                travel.setTieuDe(document.getString("TieuDe"));
                                travel.setMoTa(document.getString("MoTa"));
                                travel.setDanhGias(null);
                                travel.setDiaChi(document.getString("DiaChi"));

                                Number numMax = (Number) document.get("GiaMax");
                                Number numMin = (Number) document.get("GiaMin");
                                travel.setGiaMax((long) Float.parseFloat(numMax.toString()));
                                travel.setGiaMin((long)Float.parseFloat(numMin.toString()));

                                travel.setHinhAnhs((ArrayList<String>) document.get("HinhAnh"));

                                Timestamp timestamp=document.getTimestamp("NgayDang");

                                travel.setNgayDang(timestamp.toDate().toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDateTime());
                                dsTravel.add(travel);
                                Log.d("TravelModel", document.getId() + " => " +  dsTravel.size());
                            }
                            listener.onDataLoaded(dsTravel);
                        }
                        else
                        {
                            listener.onDataLoadError(task.getException());
                        }
                    }
                });

        //return dsTravel;
    }
    public interface OnDataLoadedListener {
        void onDataLoaded(ArrayList<Travel> dataList);
        void onDataLoadError(Exception e);
    }
}
