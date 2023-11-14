package com.example.cntt196_hotrodulichfirebase.FirebaseService;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.cntt196_hotrodulichfirebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class StorageService {
    public static void LoadImageUri(String filePath, ImageView imageView, Context context,int width,int height)
    {
        String uriImage1;
        Uri uri_final;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child(filePath).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso picasso = Picasso.with(context);
                        picasso.load(uri).resize(width, height)
                                .placeholder(R.drawable.default_image_empty)
                                .into(imageView);
                        picasso.invalidate(uri);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LoiLoadImage","=>"+filePath);
                    }
                });
    }
    public static void LoadImageUri_Avarta(String filePath, ImageView imageView, Context context)
    {
        String uriImage1;
        Uri uri_final;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child(filePath).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso picasso = Picasso.with(context);
                        picasso.load(uri).resize(90,90)
                                .placeholder(R.drawable.icon2)
                                .into(imageView);
                        picasso.invalidate(uri);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LoiLoadImage","=>"+filePath);
                    }
                });
    }
}
