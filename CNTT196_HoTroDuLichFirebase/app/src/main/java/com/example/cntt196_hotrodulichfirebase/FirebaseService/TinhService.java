package com.example.cntt196_hotrodulichfirebase.FirebaseService;

import com.example.cntt196_hotrodulichfirebase.models.TinhModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TinhService {
    public static ArrayList<String> getAllTinh()
    {
        ArrayList<String> dsTinh = new ArrayList<>();
        dsTinh.add("Thành phố Hà Nội");
        dsTinh.add("Thành phố Hồ Chí Minh");
        dsTinh.add("Tỉnh Hà Giang");
        dsTinh.add("Tỉnh Cao Bằng");
        dsTinh.add("Tỉnh Bắc Kạn");
        dsTinh.add("Tỉnh Tuyên Quang");
        dsTinh.add("Tỉnh Lào Cai");
        dsTinh.add("Tỉnh Điện Biên");
        dsTinh.add("Tỉnh Lai Châu");
        dsTinh.add("Tỉnh Sơn La");
        dsTinh.add("Tỉnh Yên Bái");
        dsTinh.add("Tỉnh Hòa Bình");
        dsTinh.add("Tỉnh Thái Nguyên");
        dsTinh.add("Tỉnh Lạng Sơn");
        dsTinh.add("Tỉnh Quảng Ninh");
        dsTinh.add("Tỉnh Bắc Giang");
        dsTinh.add("Tỉnh Phú Thọ");
        dsTinh.add("Tỉnh Vĩnh Phúc");
        dsTinh.add("Tỉnh Bắc Ninh");
        dsTinh.add("Tỉnh Hải Dương");
        dsTinh.add("Tỉnh Hải Phòng");
        dsTinh.add("Tỉnh Hưng Yên");
        dsTinh.add("Tỉnh Thái Bình");
        dsTinh.add("Tỉnh Hà Nam");
        dsTinh.add("Tỉnh Nam Định");
        dsTinh.add("Tỉnh Ninh Bình");
        dsTinh.add("Tỉnh Thanh Hóa");
        dsTinh.add("Tỉnh Nghệ An");
        dsTinh.add("Tỉnh Hà Tĩnh");
        dsTinh.add("Tỉnh Quảng Bình");
        dsTinh.add("Tỉnh Quảng Trị");
        dsTinh.add("Tỉnh Thừa Thiên Huế");
        dsTinh.add("Thành phố Đà Nẵng");
        dsTinh.add("Tỉnh Quảng Nam");
        dsTinh.add("Tỉnh Quảng Ngãi");
        dsTinh.add("Tỉnh Bình Định");
        dsTinh.add("Tỉnh Phú Yên");
        dsTinh.add("Tỉnh Khánh Hòa");
        dsTinh.add("Tỉnh Ninh Thuận");
        dsTinh.add("Tỉnh Bình Thuận");
        dsTinh.add("Tỉnh Kon Tum");
        dsTinh.add("Tỉnh Gia Lai");
        dsTinh.add("Tỉnh Đắk Lắk");
        dsTinh.add("Tỉnh Đắk Nông");
        dsTinh.add("Tỉnh Lâm Đồng");
        dsTinh.add("Tỉnh Bình Phước");
        dsTinh.add("Tỉnh Tây Ninh");
        dsTinh.add("Tỉnh Bình Dương");
        dsTinh.add("Tỉnh Đồng Nai");
        dsTinh.add("Tỉnh Bà Rịa - Vũng Tàu");
        dsTinh.add("Tỉnh Long An");
        dsTinh.add("Tỉnh Tiền Giang");
        dsTinh.add("Tỉnh Bến Tre");
        dsTinh.add("Tỉnh Trà Vinh");
        dsTinh.add("Tỉnh Vĩnh Long");
        dsTinh.add("Tỉnh Đồng Tháp");
        dsTinh.add("Tỉnh An Giang");
        dsTinh.add("Tỉnh Kiên Giang");
        dsTinh.add("Thành phố Cần Thơ");
        dsTinh.add("Tỉnh Hậu Giang");
        dsTinh.add("Tỉnh Sóc Trăng");
        dsTinh.add("Tỉnh Bạc Liêu");
        dsTinh.add("Tỉnh Cà Mau");

        return  dsTinh;
    }
}
