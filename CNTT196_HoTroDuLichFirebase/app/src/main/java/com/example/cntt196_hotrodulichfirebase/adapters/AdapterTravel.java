package com.example.cntt196_hotrodulichfirebase.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cntt196_hotrodulichfirebase.ActivityDetailTravel;
import com.example.cntt196_hotrodulichfirebase.R;
import com.example.cntt196_hotrodulichfirebase.models.HinhAnh;
import com.example.cntt196_hotrodulichfirebase.models.NguoiDang;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;

public class AdapterTravel extends BaseAdapter {
    private ArrayList<Travel> arrayListTravel;
    private QuerySnapshot queryDocumentSnapshots;
    private Context context;
    private AdapterView.OnItemClickListener mListener;



    public AdapterTravel(QuerySnapshot queryDocumentSnapshots, Context context) {
        this.queryDocumentSnapshots = queryDocumentSnapshots;
        this.context = context;
        this.arrayListTravel = new ArrayList<>();


        if (this.queryDocumentSnapshots != null)
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

                    //StorageReference pathReference = storageRef.child("avarta/"+(String)subDocument.get("AnhDaiDien"));
                    storageRef.child("avarta/"+(String)subDocument.get("AnhDaiDien")).getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    nguoiDang.setAnhDaiDien(uri.toString());
                                    Toast.makeText(context, "=>"+uri, Toast.LENGTH_SHORT).show();
                                    Log.e("URL_avarta","=>"+uri);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                    travel.setNguoiDang(nguoiDang);
                }
                travel.setTieuDe(document.getString("TieuDe"));
                travel.setMoTa(document.getString("MoTa"));
                travel.setDanhGias(null);
                travel.setDiaChi(document.getString("DiaChi"));
                travel.setGiaMax(document.getDouble("GiaMax"));
                travel.setGiaMin(document.getDouble("GiaMin"));
                //travel.setHinhAnhs((ArrayList<String>) document.get("HinhAnh"));

                ArrayList<String> dsHinh=new ArrayList<>();
                for (String strHinhAnh:(ArrayList<String>) document.get("HinhAnh"))
                {
                    String rootFile= "Travel/"+ document.getId()+"/";
                    storageRef.child(rootFile + strHinhAnh).getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    dsHinh.add(uri.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
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
                    arrayListTravel.add(travel);
                    arrayListTravel.add(travel);
                }
            }
        }
    }

    @Override
    public int getCount() {
        return arrayListTravel.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListTravel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public  class  ViewHolder
    {
        TextView tvTenNguoiDung, tvNgayDang, tvTieuDe, tvMoTa, tvDiaChi, tvGia, tvCountFavorite;
        ImageView imgNguoiDung_custom, imgHinhAnhBaiDang_custom;
        RatingBar ratingBar_custom;
        ImageButton btnFavorite_custom;
        Button btnXemChiTiet_custom;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.custom_listview_item_ver1,null);

            viewHolder.tvTenNguoiDung=view.findViewById(R.id.tvTenNguoiDung);
            viewHolder.tvNgayDang=view.findViewById(R.id.tvNgayDang);
            viewHolder.tvTieuDe=view.findViewById(R.id.tvTieuDe);
            viewHolder.tvMoTa=view.findViewById(R.id.tvMoTa);
            viewHolder.tvDiaChi=view.findViewById(R.id.tvDiaChi);
            viewHolder.tvGia=view.findViewById(R.id.tvGia);
            viewHolder.tvCountFavorite=view.findViewById(R.id.tvCountFavorite);
            viewHolder.imgNguoiDung_custom=view.findViewById(R.id.imgNguoiDung_custom);
            viewHolder.imgHinhAnhBaiDang_custom=view.findViewById(R.id.imgHinhAnhBaiDang_custom);
            viewHolder.ratingBar_custom=view.findViewById(R.id.ratingBar_custom);
            viewHolder.btnFavorite_custom=view.findViewById(R.id.btnFavorite_custom);
            viewHolder.btnXemChiTiet_custom=view.findViewById(R.id.btnXemChiTiet_custom);

            view.setTag(viewHolder);

            viewHolder=(ViewHolder) view.getTag();
            Travel travel= (Travel) getItem(i);
            if(travel.getNguoiDang()!=null)
            {
                Log.e("Travel_Avarta","=>"+travel.getNguoiDang().getAnhDaiDien());
                Picasso picasso=Picasso.with(context);
                picasso.load(travel.getNguoiDang().getAnhDaiDien()).resize(90,90)
                        .placeholder(R.drawable.icon2)
                        .into(viewHolder.imgNguoiDung_custom);
                picasso.invalidate(travel.getNguoiDang().getAnhDaiDien());

                viewHolder.tvTenNguoiDung.setText(travel.getNguoiDang().getTenNguoiDang());
                Log.e("Travel_TenNguoiDung","=>"+travel.getNguoiDang().getTenNguoiDang());
                viewHolder.tvNgayDang.setText(DateTimeToString.Format(travel.getNgayDang()));
                viewHolder.tvTieuDe.setText(travel.getTieuDe());
                viewHolder.tvMoTa.setText((travel.getMoTa().length()>200)? ("Mô tả: "+travel.getMoTa().substring(0,200)
                        +"..."):("Mô tả: "+travel.getMoTa()));
                viewHolder.tvDiaChi.setText("Địa chỉ: "+travel.getDiaChi());
                if(travel.getGiaMax()==0&&travel.getGiaMin()==0)
                { viewHolder.tvGia.setText("Miễn phí vé tham quan");}
                else
                { viewHolder.tvGia.setText("Giá tham khảo chỉ từ "
                        +String.valueOf( travel.getGiaMin()) +" đến "+String.valueOf(travel.getGiaMax()));}
                if(travel.getLuotThichs()!=null)
                {
                    if(travel.getLuotThichs().size()>0)
                    {
                        viewHolder.btnFavorite_custom.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                        viewHolder.ratingBar_custom.setRating(5);
                        viewHolder.btnFavorite_custom.setTag(R.drawable.baseline_volunteer_activism_24_0);
                        viewHolder.tvCountFavorite.setText(travel.getLuotThichs().get(0).getTenNguoiDang()+" và "+travel.getLuotThichs().size()+"+");
                    }
                }
                else
                {
                    viewHolder.btnFavorite_custom.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                    viewHolder.ratingBar_custom.setRating(0);
                    viewHolder.btnFavorite_custom.setTag(R.drawable.baseline_volunteer_activism_24_0);
                    viewHolder.tvCountFavorite.setText("");}

                if(travel.getHinhAnhs()!=null)
                {
                    if(travel.getHinhAnhs().size()>0)
                    {
                        Log.e("Travel_AnhBia","=>"+travel.getHinhAnhs().get(0));
                        Picasso picassoH1=Picasso.with(context);
                        picassoH1.load(travel.getHinhAnhs().get(0)).resize(1280  ,720)
                                .placeholder(R.drawable.default_image_empty)
                                .into(viewHolder.imgHinhAnhBaiDang_custom);
                        picassoH1.invalidate(travel.getHinhAnhs().get(0));
                    }
                }
            }



            viewHolder.btnXemChiTiet_custom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Travel travelBundle=(Travel) getItem(i);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("Travel", travelBundle);
                    Intent intent=new Intent(v.getContext(), ActivityDetailTravel.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });

            Log.e("TagBtnCurrent","=>"+viewHolder.btnFavorite_custom.getTag());
            ViewHolder finalViewHolder = viewHolder;
            viewHolder.btnFavorite_custom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if((int)v.getTag()==R.drawable.baseline_volunteer_activism_24)
                    {
                        v.setTag(R.drawable.baseline_volunteer_activism_24_0);
                        finalViewHolder.btnFavorite_custom.setImageResource((int) finalViewHolder.btnFavorite_custom.getTag());
                    }
                    else {
                        v.setTag(R.drawable.baseline_volunteer_activism_24);
                        finalViewHolder.btnFavorite_custom.setImageResource((int) finalViewHolder.btnFavorite_custom.getTag());
                    }
                    Log.e("TagBtn","=>"+v.getTag());
                }
            });
            //viewHolder.btnFavorite_custom.setImageResource((int)viewHolder.btnFavorite_custom.getTag());



        }
        else
        {
            viewHolder=(ViewHolder) view.getTag();
            Travel travel= (Travel) getItem(i);
            if(travel.getNguoiDang()!=null)
            {
                Log.e("Travel_Avarta","=>"+travel.getNguoiDang().getAnhDaiDien());
                Picasso picasso=Picasso.with(context);
                picasso.load(travel.getNguoiDang().getAnhDaiDien()).resize(90,90)
                        .placeholder(R.drawable.icon2)
                        .into(viewHolder.imgNguoiDung_custom);
                picasso.invalidate(travel.getNguoiDang().getAnhDaiDien());

                viewHolder.tvTenNguoiDung.setText(travel.getNguoiDang().getTenNguoiDang());
                viewHolder.tvNgayDang.setText(DateTimeToString.Format(travel.getNgayDang()));
                viewHolder.tvTieuDe.setText(travel.getTieuDe());
                viewHolder.tvMoTa.setText((travel.getMoTa().length()>200)? ("Mô tả: "+travel.getMoTa().substring(0,200)
                        +"..."):("Mô tả: "+travel.getMoTa()));
                viewHolder.tvDiaChi.setText("Địa chỉ: "+travel.getDiaChi());
                if(travel.getGiaMax()==0&&travel.getGiaMin()==0)
                { viewHolder.tvGia.setText("Miễn phí vé tham quan");}
                else
                { viewHolder.tvGia.setText("Giá tham khảo chỉ từ "
                        +String.valueOf( travel.getGiaMin()) +" đến "+String.valueOf(travel.getGiaMax()));}
                if(travel.getLuotThichs()!=null)
                {
                    if(travel.getLuotThichs().size()>0)
                    {
                        viewHolder.btnFavorite_custom.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                        viewHolder.ratingBar_custom.setRating(5);
                        viewHolder.btnFavorite_custom.setTag(R.drawable.baseline_volunteer_activism_24_0);
                        viewHolder.tvCountFavorite.setText(travel.getLuotThichs().get(0).getTenNguoiDang()+" và "+travel.getLuotThichs().size()+"+");
                    }
                }
                else
                {
                    viewHolder.btnFavorite_custom.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                    viewHolder.ratingBar_custom.setRating(0);
                    viewHolder.btnFavorite_custom.setTag(R.drawable.baseline_volunteer_activism_24_0);
                    viewHolder.tvCountFavorite.setText("");}

                if(travel.getHinhAnhs()!=null)
                {
                    if(travel.getHinhAnhs().size()>0)
                    {
                        Log.e("Travel_AnhBia","=>"+travel.getHinhAnhs().get(0));
                        Picasso picassoH1=Picasso.with(context);
                        picassoH1.load(travel.getHinhAnhs().get(0)).resize(1280  ,720)
                                .placeholder(R.drawable.default_image_empty)
                                .into(viewHolder.imgHinhAnhBaiDang_custom);
                        picassoH1.invalidate(travel.getHinhAnhs().get(0));
                    }
                }
            }



            viewHolder.btnXemChiTiet_custom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Travel travelBundle=(Travel) getItem(i);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("Travel", travelBundle);
                    Intent intent=new Intent(v.getContext(), ActivityDetailTravel.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });

            Log.e("TagBtnCurrent","=>"+viewHolder.btnFavorite_custom.getTag());
            ViewHolder finalViewHolder = viewHolder;
            viewHolder.btnFavorite_custom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if((int)v.getTag()==R.drawable.baseline_volunteer_activism_24_0)
                    {
                        v.setTag(R.drawable.baseline_volunteer_activism_24);
                        finalViewHolder.btnFavorite_custom.setImageResource((int) finalViewHolder.btnFavorite_custom.getTag());
                    }
                    else {
                        v.setTag(R.drawable.baseline_volunteer_activism_24_0);
                        finalViewHolder.btnFavorite_custom.setImageResource((int) finalViewHolder.btnFavorite_custom.getTag());
                    }
                    Log.e("TagBtn","=>"+v.getTag());
                }
            });
            //viewHolder.btnFavorite_custom.setImageResource((int)viewHolder.btnFavorite_custom.getTag());

        }
        return view;
    }

}
