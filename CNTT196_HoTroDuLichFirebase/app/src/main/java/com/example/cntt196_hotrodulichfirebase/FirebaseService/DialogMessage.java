package com.example.cntt196_hotrodulichfirebase.FirebaseService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogMessage {
    public static void ThongBao(String thongBao, Context context)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(thongBao);
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
