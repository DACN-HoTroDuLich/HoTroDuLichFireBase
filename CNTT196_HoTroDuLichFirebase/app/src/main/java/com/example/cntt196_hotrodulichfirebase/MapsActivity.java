package com.example.cntt196_hotrodulichfirebase;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cntt196_hotrodulichfirebase.models.Hotel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.cntt196_hotrodulichfirebase.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String DiaChi;

    private EditText edtDiaChi_Maps,edtToaDoCurrent_Maps;
    private ImageButton btnBack_Maps, btnChiDuong_Maps, btnDinhVi_Maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            DiaChi=(String) bundle.getSerializable("DiaChi");
        }
        else
        {
            DiaChi="140 Lê Trọng Tấn, Phường Tây Thạnh, quận Tân Phú, Thành phố Hồ Chí Minh";
        }

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Init();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        edtDiaChi_Maps.setText(DiaChi);
        btnBack_Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Init()
    {
        edtToaDoCurrent_Maps=findViewById(R.id.edtToaDoCurrent_Maps);
        edtToaDoCurrent_Maps.setEnabled(false);
        edtDiaChi_Maps=findViewById(R.id.edtDiaChi_Maps);
        edtDiaChi_Maps.setEnabled(false);
        btnBack_Maps = findViewById(R.id.btnBack_Maps);
        btnChiDuong_Maps=findViewById(R.id.btnChiDuong_Maps);
        btnDinhVi_Maps =findViewById(R.id.btnDinhVi_Maps);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        Geocoder geocoder=new Geocoder(MapsActivity.this, Locale.getDefault());
        try {
            List<Address> addressList= geocoder.getFromLocationName(DiaChi, 1);
            if(addressList!=null)
            {
                if(!addressList.isEmpty())
                {
                    Address resultAddress=addressList.get(0);
                    double latitude=resultAddress.getLatitude();
                    double longitude=resultAddress.getLongitude();
                    LatLng ToaDiemDuLich = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(ToaDiemDuLich).title(resultAddress.getAddressLine(0)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ToaDiemDuLich,15));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Add a marker in Sydney and move the camera


    }
}