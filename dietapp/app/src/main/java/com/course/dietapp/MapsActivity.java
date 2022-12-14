package com.course.dietapp;

import androidx.fragment.app.FragmentActivity;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.course.dietapp.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Double lat;
    Double lng;
    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button btn = findViewById(R.id.button1);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("position",position);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        LatLng seoul = new LatLng(37.55827, 126.998425);
        MarkerOptions mOptions = new MarkerOptions();
        mOptions.position(seoul);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15));
        // ??? ?????? ????????? ?????? //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                lat = point.latitude;
                lng = point.longitude;
                position = getCurrentAddress(point);

                if (position == "") {
                    return;
                }
                if (position != null) {
                    mMap.clear();
                }

                // LatLng: ?????? ?????? ?????? ?????????
                mOptions.position(point);
                mOptions.title("?????? ??????");
                mOptions.snippet(position);
                mMap.addMarker(mOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 20));
            }
        });
    }

    public String getCurrentAddress(LatLng latlng) {
        //????????????... GPS??? ????????? ??????
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latlng.latitude, latlng.longitude,1);
        }catch (IOException ioException) {
            //???????????? ??????
            Toast.makeText(this, "???????????? ????????? ????????????", Toast.LENGTH_LONG).show();
            //return "???????????? ????????? ????????????";
            return "";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "????????? GPS ??????", Toast.LENGTH_LONG).show();
//            return "????????? GPS ??????";
            return "";
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "?????? ?????????", Toast.LENGTH_LONG).show();
//            return "?????? ?????????";
            return "";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }
    }
}
