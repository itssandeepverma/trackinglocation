package com.apkglobal.tracking;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    Button b1,change;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        b1=findViewById(R.id.share);
        change=findViewById(R.id.change);
    }

    @Override
    protected void onStart() {

        super.onStart();
        Shared shared =new Shared(getApplicationContext());
        shared.firsttime();

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
        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double currlat,currlong;
                currlat=location.getLatitude();
                currlong=location.getLongitude();
                Geocoder geocoder=new Geocoder(MapsActivity.this);

                try {
                    List<Address> list=geocoder.getFromLocation(currlat,currlong,1);
                    LatLng sydney = new LatLng(currlat, currlong);

                     address=list.get(0).getAddressLine(0);

                    mMap.addMarker(new MarkerOptions().position(sydney).title(address));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));

                } catch (IOException e) {

                    e.printStackTrace();
                }


                    SharedPreferences sp=getSharedPreferences("spfile",0);
                    final String name=sp.getString("name",null);
                    final String mobile=sp.getString("mobile",null);

                    // for getting time
                    Calendar c = Calendar.getInstance();
                    final SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                    final String datetime = dateformat.format(c.getTime());

                   /*

                    StringRequest stringRequest=new StringRequest(1, url, new com.android.volley.Response.Listener<String>() {
                        @Override ///1 for post method
                        public void onResponse(String response) {

                            Toast.makeText(MapsActivity.this, response, Toast.LENGTH_SHORT).show();
                            //will get response from inser api

                            //to clear the form after data send


                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> map=new HashMap<>();
                            map.put("nameid",name);
                            map.put("mobileid",mobile);
                            map.put("addressid",address);
                            map.put("timeid",datetime);
                            return map;
                        }
                    };

                    RequestQueue requestQueue= Volley.newRequestQueue(MapsActivity.this);
                    requestQueue.add(stringRequest);

               */

                   change.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent i=new Intent(MapsActivity.this,Register.class);
                           startActivity(i);
                           finish();
                       }
                   });
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent =  new Intent(android.content.Intent.ACTION_SEND);

//set type

                        shareIntent.setType("text/plain");

//add what a subject you want

                        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Mylocation");

                        String shareMessage="Name: "+name+"\n"+"Mobile:" + mobile+"\n"+"Address:"+ address+"\n" + "Time:"+datetime;

//message

                        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareMessage);

//start sharing via

                        startActivity(Intent.createChooser(shareIntent,"Sharing via"));
                    }
                });

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });

        // Add a marker in Sydney and move the camera

    }
}
