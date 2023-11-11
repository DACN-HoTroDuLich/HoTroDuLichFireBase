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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.cntt196_hotrodulichfirebase.ActivityDetailHotel;
import com.example.cntt196_hotrodulichfirebase.ActivityDetailTravel;
import com.example.cntt196_hotrodulichfirebase.R;
import com.example.cntt196_hotrodulichfirebase.models.Hotel;
import com.example.cntt196_hotrodulichfirebase.models.NguoiDang;
import com.example.cntt196_hotrodulichfirebase.models.Phong;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;

public class AdapterHotel extends BaseAdapter {
    private ArrayList<Hotel> arrayListHotel;
    private QuerySnapshot queryDocumentSnapshots;
    private Context context;
    private AdapterView.OnItemClickListener mListener;

    public AdapterHotel(QuerySnapshot queryDocumentSnapshots, Context context) {
        this.queryDocumentSnapshots = queryDocumentSnapshots;
        this.context = context;
        this.arrayListHotel = new ArrayList<>();


        if (this.queryDocumentSnapshots != null)
        {
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
            {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                Hotel hotel=new Hotel();
                hotel.setID_Document(document.getId());
                Map<String,Object> subDocument=(Map<String,Object>) document.get("NguoiDang");
                Log.d("HotelNguoiDang"," => " +  subDocument);
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


                    hotel.setNguoiDang(nguoiDang);
                }
                hotel.setTenKhachSan(document.getString("TenKhachSan"));
                hotel.setMoTa(document.getString("MoTa"));
                hotel.setDanhGias(null);
                hotel.setDiaChi(document.getString("DiaChi"));
                hotel.setTrangThai(document.getBoolean("TrangThai"));
                hotel.setHangSao((long)document.get("HangSao"));

                ArrayList<String> dsHinh=new ArrayList<>();
                for (String strHinhAnh:(ArrayList<String>) document.get("HinhAnh"))
                {
                    String rootFile= "Hotel/"+ document.getId()+"/";
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
                hotel.setHinhAnhs(dsHinh);

                ArrayList<Phong> dsPhong=new ArrayList<>();
                ArrayList<Map<String,Object>> subArrayDocument= (ArrayList<Map<String, Object>>) document.get("Phong");
                if(subArrayDocument!=null)
                {
                    Log.e("subArrayDocument","=>"+subArrayDocument);
                    for (Map<String,Object> objectMap:subArrayDocument)
                    {
                        Phong phong=new Phong();
                        phong.setGiaMax((long) objectMap.get("GiaMax"));
                        phong.setGiaMin((long) objectMap.get("GiaMin"));
                        phong.setSoGiuong((long) objectMap.get("SoGiuong"));
                        phong.setHinhAnh((String) objectMap.get("HinhAnh"));
                        if(phong.getHinhAnh()!=null)
                        {
                            String rootFile= "Hotel/"+ document.getId()+"/";
                            storageRef.child(rootFile + phong.getHinhAnh()).getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            phong.setHinhAnh(uri.toString());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }
                        dsPhong.add(phong);
                    }
                }
                hotel.setPhongs(dsPhong);

                Timestamp timestamp=document.getTimestamp("NgayDang");

                hotel.setNgayDang(timestamp.toDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());

                for(int i=0;i<15;i++)
                {
                    arrayListHotel.add(hotel);
                    arrayListHotel.add(hotel);
                    arrayListHotel.add(hotel);
                }
            }
        }
    }

    @Override
    public int getCount() {
        return arrayListHotel.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListHotel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class  ViewHolder
    {
        TextView tvCountFavorite_hotel_custom, tvGia_hotel_custom, tvhotelStar_hotel_custom, tvDiaChi_hotel_custom,
                tvMoTa_hotel_custom, tvTieuDe_hotel_custom, tvNgayDang_hotel_custom,tvTenNguoiDung_hotel_custom;
        ImageView imgNguoiDung_hotel_custom, imgHotel_hotel_custom;
        RatingBar ratingBar_hotel_custom;
        ImageButton btnFavorite_hodel_custom;
        Button btnXemChiTiet_hotel_custom;
        LinearLayout linearLayout_hotel_custom;
        //CardView cardView_hotel_custom;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AdapterHotel.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new AdapterHotel.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview_item_hotel_ver1, null);

            viewHolder.tvTenNguoiDung_hotel_custom = convertView.findViewById(R.id.tvTenNguoiDung_hotel_custom);
            viewHolder.tvNgayDang_hotel_custom = convertView.findViewById(R.id.tvNgayDang_hotel_custom);
            viewHolder.tvTieuDe_hotel_custom = convertView.findViewById(R.id.tvTieuDe_hotel_custom);
            viewHolder.tvMoTa_hotel_custom = convertView.findViewById(R.id.tvMoTa_hotel_custom);
            viewHolder.tvDiaChi_hotel_custom = convertView.findViewById(R.id.tvDiaChi_hotel_custom);
            viewHolder.tvGia_hotel_custom = convertView.findViewById(R.id.tvGia_hotel_custom);
            viewHolder.tvCountFavorite_hotel_custom = convertView.findViewById(R.id.tvCountFavorite_hotel_custom);
            viewHolder.imgNguoiDung_hotel_custom = convertView.findViewById(R.id.imgNguoiDung_hotel_custom);
            viewHolder.imgHotel_hotel_custom = convertView.findViewById(R.id.imgHotel_hotel_custom);
            viewHolder.ratingBar_hotel_custom = convertView.findViewById(R.id.ratingBar_hotel_custom);
            viewHolder.btnFavorite_hodel_custom = convertView.findViewById(R.id.btnFavorite_hodel_custom);
            viewHolder.btnXemChiTiet_hotel_custom = convertView.findViewById(R.id.btnXemChiTiet_hotel_custom);
            viewHolder.linearLayout_hotel_custom = convertView.findViewById(R.id.linearLayout_hotel_custom);
            viewHolder.tvhotelStar_hotel_custom = convertView.findViewById(R.id.tvhotelStar_hotel_custom);


            convertView.setTag(viewHolder);

            viewHolder = (AdapterHotel.ViewHolder) convertView.getTag();
            Hotel hotel = (Hotel) getItem(position);
            if (hotel.getNguoiDang() != null) {
                Log.e("Hotel_Avarta", "=>" + hotel.getNguoiDang().getAnhDaiDien());

                Picasso picasso = Picasso.with(context);
                picasso.load(hotel.getNguoiDang().getAnhDaiDien()).resize(90, 90)
                        .placeholder(R.drawable.icon2)
                        .into(viewHolder.imgNguoiDung_hotel_custom);
                picasso.invalidate(hotel.getNguoiDang().getAnhDaiDien());

                viewHolder.tvTenNguoiDung_hotel_custom.setText(hotel.getNguoiDang().getTenNguoiDang());
                Log.e("Hotel_TenNguoiDung", "=>" + hotel.getNguoiDang().getTenNguoiDang());
                viewHolder.tvNgayDang_hotel_custom.setText(DateTimeToString.Format(hotel.getNgayDang()));
                viewHolder.tvTieuDe_hotel_custom.setText(hotel.getTenKhachSan());
                viewHolder.tvMoTa_hotel_custom.setText((hotel.getMoTa().length() > 80) ? ("Mô tả: " + hotel.getMoTa().substring(0, 80)
                        + "...") : ("Mô tả: " + hotel.getMoTa()));


                if (hotel.getLuotThichs() != null) {
                    if (hotel.getLuotThichs().size() > 0) {
                        viewHolder.btnFavorite_hodel_custom.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                        viewHolder.ratingBar_hotel_custom.setRating(5);
                        viewHolder.btnFavorite_hodel_custom.setTag(R.drawable.baseline_volunteer_activism_24_0);
                        viewHolder.tvCountFavorite_hotel_custom.setText(hotel.getLuotThichs().get(0).getTenNguoiDang()
                                + " và " + hotel.getLuotThichs().size() + "+");
                    }
                } else {
                    viewHolder.btnFavorite_hodel_custom.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                    viewHolder.ratingBar_hotel_custom.setRating(0);
                    viewHolder.btnFavorite_hodel_custom.setTag(R.drawable.baseline_volunteer_activism_24_0);
                    viewHolder.tvCountFavorite_hotel_custom.setText("");
                }

                String[] DiaChiSplit=hotel.getDiaChi().split(",");
                viewHolder.tvDiaChi_hotel_custom.setText("Địa chỉ: " + DiaChiSplit[DiaChiSplit.length-2]+", "
                +DiaChiSplit[DiaChiSplit.length-1]);
                if (hotel.getHinhAnhs() != null) {
                    if (hotel.getHinhAnhs().size() > 0) {
                        Log.e("Hotel_AnhBia", "=>" + hotel.getHinhAnhs().get(0));
                        Picasso picassoH1 = Picasso.with(context);
                        picassoH1.load(hotel.getHinhAnhs().get(0)).resize(1280, 750)
                                .placeholder(R.drawable.dulich5)
                                .into(viewHolder.imgHotel_hotel_custom);
                        picassoH1.invalidate(hotel.getHinhAnhs().get(0));
                    }
                }
            }


            viewHolder.btnXemChiTiet_hotel_custom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Hotel hotelBundle=(Hotel) getItem(position);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("Hotel", hotelBundle);
                    Intent intent=new Intent(v.getContext(), ActivityDetailHotel.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });

            Log.e("TagBtnCurrent", "=>" + viewHolder.btnFavorite_hodel_custom.getTag());
            AdapterHotel.ViewHolder finalViewHolder = viewHolder;
            viewHolder.btnFavorite_hodel_custom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ((int) v.getTag() == R.drawable.baseline_volunteer_activism_24) {
                        v.setTag(R.drawable.baseline_volunteer_activism_24_0);
                        finalViewHolder.btnFavorite_hodel_custom.setImageResource((int) finalViewHolder.btnFavorite_hodel_custom.getTag());
                    } else {
                        v.setTag(R.drawable.baseline_volunteer_activism_24);
                        finalViewHolder.btnFavorite_hodel_custom.setImageResource((int) finalViewHolder.btnFavorite_hodel_custom.getTag());
                    }
                    Log.e("TagBtn", "=>" + v.getTag());
                }
            });
            //viewHolder.btnFavorite_custom.setImageResource((int)viewHolder.btnFavorite_custom.getTag());


        } else {
            viewHolder = (AdapterHotel.ViewHolder) convertView.getTag();
            Hotel hotel = (Hotel) getItem(position);
            if (hotel.getNguoiDang() != null) {
                Log.e("Hotel_Avarta", "=>" + hotel.getNguoiDang().getAnhDaiDien());

                Picasso picasso = Picasso.with(context);
                picasso.load(hotel.getNguoiDang().getAnhDaiDien()).resize(90, 90)
                        .placeholder(R.drawable.icon2)
                        .into(viewHolder.imgNguoiDung_hotel_custom);
                picasso.invalidate(hotel.getNguoiDang().getAnhDaiDien());

                viewHolder.tvTenNguoiDung_hotel_custom.setText(hotel.getNguoiDang().getTenNguoiDang());
                Log.e("Hotel_TenNguoiDung", "=>" + hotel.getNguoiDang().getTenNguoiDang());
                viewHolder.tvNgayDang_hotel_custom.setText(DateTimeToString.Format(hotel.getNgayDang()));
                viewHolder.tvTieuDe_hotel_custom.setText(hotel.getTenKhachSan());
                viewHolder.tvMoTa_hotel_custom.setText((hotel.getMoTa().length() > 80) ? ("Mô tả: " + hotel.getMoTa().substring(0, 80)
                        + "...") : ("Mô tả: " + hotel.getMoTa()));
                String[] DiaChiSplit=hotel.getDiaChi().split(",");
                viewHolder.tvDiaChi_hotel_custom.setText("Địa chỉ: " + DiaChiSplit[DiaChiSplit.length-2]+", "
                        +DiaChiSplit[DiaChiSplit.length-1]);

                if (hotel.getLuotThichs() != null) {
                    if (hotel.getLuotThichs().size() > 0) {
                        viewHolder.btnFavorite_hodel_custom.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                        viewHolder.ratingBar_hotel_custom.setRating(5);
                        viewHolder.btnFavorite_hodel_custom.setTag(R.drawable.baseline_volunteer_activism_24_0);
                        viewHolder.tvCountFavorite_hotel_custom.setText(hotel.getLuotThichs().get(0).getTenNguoiDang()
                                + " và " + hotel.getLuotThichs().size() + "+");
                    }
                } else {
                    viewHolder.btnFavorite_hodel_custom.setImageResource(R.drawable.baseline_volunteer_activism_24_0);
                    viewHolder.ratingBar_hotel_custom.setRating(0);
                    viewHolder.btnFavorite_hodel_custom.setTag(R.drawable.baseline_volunteer_activism_24_0);
                    viewHolder.tvCountFavorite_hotel_custom.setText("");
                }

                if (hotel.getHinhAnhs() != null) {
                    if (hotel.getHinhAnhs().size() > 0) {
                        Log.e("Hotel_AnhBia", "=>" + hotel.getHinhAnhs().get(0));
                        Picasso picassoH1 = Picasso.with(context);
                        picassoH1.load(hotel.getHinhAnhs().get(0)).resize(1280, 750)
                                .placeholder(R.drawable.dulich5)
                                .into(viewHolder.imgHotel_hotel_custom);
                        picassoH1.invalidate(hotel.getHinhAnhs().get(0));
                    }
                }

                viewHolder.btnXemChiTiet_hotel_custom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Hotel hotelBundle=(Hotel) getItem(position);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("Hotel", hotelBundle);
                        Intent intent=new Intent(v.getContext(), ActivityDetailHotel.class);
                        intent.putExtras(bundle);
                        v.getContext().startActivity(intent);
                    }
                });

                Log.e("TagBtnCurrent", "=>" + viewHolder.btnFavorite_hodel_custom.getTag());
                AdapterHotel.ViewHolder finalViewHolder = viewHolder;
                viewHolder.btnFavorite_hodel_custom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if ((int) v.getTag() == R.drawable.baseline_volunteer_activism_24_0) {
                            v.setTag(R.drawable.baseline_volunteer_activism_24);
                            finalViewHolder.btnFavorite_hodel_custom.setImageResource((int) finalViewHolder.btnFavorite_hodel_custom.getTag());
                        } else {
                            v.setTag(R.drawable.baseline_volunteer_activism_24_0);
                            finalViewHolder.btnFavorite_hodel_custom.setImageResource((int) finalViewHolder.btnFavorite_hodel_custom.getTag());
                        }
                        Log.e("TagBtn", "=>" + v.getTag());
                    }
                });
                //viewHolder.btnFavorite_custom.setImageResource((int)viewHolder.btnFavorite_custom.getTag());

            }
        }
        return convertView;
    }
}
