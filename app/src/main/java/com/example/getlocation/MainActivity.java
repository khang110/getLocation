package com.example.getlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

public class MainActivity extends AppCompatActivity {

//    inittialize variable

    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9;
    TextInputEditText tvCity, tvQuanHuyen, tvPhuongXa, tvDiaChiCuThe;
    Button buttonLayViTri;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        btLocation = findViewById(R.id.bt_location);
//        textView3 = findViewById(R.id.text_view3);
//        textView4 = findViewById(R.id.text_view4);
//        textView5 = findViewById(R.id.text_view5);
//        textView6 = findViewById(R.id.text_view6);
//        textView7 = findViewById(R.id.text_view7);
//        textView8 = findViewById(R.id.text_view8);
//        textView9 = findViewById(R.id.text_view9);

        buttonLayViTri = findViewById(R.id.button_layvitri);
        tvCity = findViewById(R.id.textview_city);
        tvQuanHuyen = findViewById(R.id.textview_quanhuyen);
        tvPhuongXa = findViewById(R.id.textview_phuongxa);
        tvDiaChiCuThe = findViewById(R.id.textview_vitricuthe);



//        initialize fusedLocationProviderClient

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        buttonLayViTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                check permission
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44
                    );
                }
            }
        });
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

//                initialize location
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

//                    initialaze address list
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
//                        List<Address> addresses2 = geocoder.getFromLocation(
//                                10.870134158981253, 106.80380339520607, 1
//                        );
//
//
////                        set countryname
//                        textView3.setText(Html.fromHtml(
//                                "<font color='#6200EE'><b>Country Name :</b><br></font>"
//                                        + addresses.get(0).getCountryName()
//                        ));
////                        Set locality
//                        textView4.setText(Html.fromHtml(
//                                "<font color='#6200EE'><b>Thành phố :</b><br></font>"
//                                        + addresses.get(0).getAdminArea()
//                        ));
////                        set address
//                        textView5.setText(Html.fromHtml(
//                                "<font color='#6200EE'><b>Địa chỉ :</b><br></font>"
//                                        + addresses.get(0).getAddressLine(0)
//                        ));
//                        textView6.setText(Html.fromHtml(
//                                "<font color='#6200EE'><b>Tỉnh/thành phố :</b><br></font>"
//                                        + addresses.get(0).getAdminArea()
//                        ));
//                        textView7.setText(Html.fromHtml(
//                                "<font color='#6200EE'><b>Quận/huyện :</b><br></font>"
//                                        + addresses.get(0).getSubAdminArea()
//                        ));
//
//                        textView8.setText(Html.fromHtml(
//                                "<font color='#6200EE'><b>Số nhà :</b><br></font>"
//                                        + addresses.get(0).getFeatureName()
//                        ));

                        String vitri = addresses.get(0).getAddressLine(0);
                        String quocgia = "", thanhpho = "", tendiachi = "", quan = "";
                        quocgia = addresses.get(0).getCountryName();
                        thanhpho = addresses.get(0).getAdminArea();
                        quan = addresses.get(0).getSubAdminArea();
                        tendiachi = addresses.get(0).getFeatureName();
                        String[] diachi = vitri.split(",");
                        tvCity.setText(diachi[diachi.length-2]);
                        tvQuanHuyen.setText(diachi[diachi.length-3]);
                        tvPhuongXa.setText(diachi[diachi.length-4]);
                        tvDiaChiCuThe.setText(tendiachi);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}