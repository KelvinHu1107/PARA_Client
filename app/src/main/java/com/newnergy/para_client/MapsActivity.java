package com.newnergy.para_client;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener,
        LocationListener {

    private GoogleMap mMap;
    private TextView title;
    ImageView back, save;
    private int PROXIMITY_RADIUS = 10000;//range from current location
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleApiClient googleApiClient;

    private List<Marker> MarkerList=new ArrayList<Marker>();
   // Marker melbourne;
    private String mapData;
    private Intent intent;
    private String username;
    private  boolean isDetails;
    private String workType;
    Loading_Dialog myLoading;
    Context context = this;

    @Override
    public void onResume()
    {
        super.onResume();
        RefreshTokenController controller = new RefreshTokenController(){
            @Override
            public void response(boolean result) {

                DataTransmitController c = new DataTransmitController() {
                    @Override
                    public void onResponse(String result) {
                        super.onResponse(result);
                        myLoading.CloseLoadingDialog();

                        String outSide[] = result.trim().split("\"");

                        String info1[] = ValueMessager.currentVersion.trim().split("\\.");
                        String info2[] = outSide[1].trim().split("\\.");

                        if(!info1[0].equals(info2[0])){
                            Intent intent = new Intent(MapsActivity.this, Client_PopUp_Version.class);
                            startActivity(intent);
                        }
                    }
                };
                c.execute("http://para.co.nz/api/version/getversion", "", "GET");
            }
        };
        myLoading.ShowLoadingDialog();
        controller.refreshToken(ValueMessager.email.toString(), ValueMessager.refreshToken);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

//        if (!CheckGooglePlayServices()) {
//            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
//            finish();
//        } else {
//            Log.d("onCreate", "Google Play Services available.");
//        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initialize();
    }

    public void initialize()
    {
        title = (TextView) findViewById(R.id.tree_field_title);
        back = (ImageView) findViewById(R.id.imageView_back);
        save = (ImageView) findViewById(R.id.imageView_ok);

        save.setVisibility(View.INVISIBLE);
        title.setText("Location");

        intent=getIntent();
        mapData=intent.getStringExtra("mapData");
        System.out.println("mapData"+mapData);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();
        isDetails=getIntent().getExtras().getBoolean("state");
        username=getIntent().getExtras().getString("username");
        workType=getIntent().getExtras().getString("providerType");
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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera

        //set current location
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        addAddressMark(mapData);

        //back Action
        back.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v) {

                                        switch(ValueMessager.lastPageMap){
                                            case "FurtherInfo":
                                                Intent mainPage = new Intent(MapsActivity.this, Client_Further_Info.class);
                                                startActivity(mainPage);
                                                break;
                                            case "Dealt":
                                                Intent intent = new Intent(MapsActivity.this, Client_dealt_providerInfo.class);
                                                startActivity(intent);
                                                break;
                                        }


                                    }
                                });


        //Listeners for click the mark show information
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                for (int i=0;i<MarkerList.size();i++) {
                    if (arg0.getTitle().equals(MarkerList.get(i).getTitle())) {// if marker source is clicked
                        //Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_LONG).show();
                        System.out.println("arg0: "+arg0.getTitle()+" "+ arg0.getPosition());
                        System.out.println("MarkerList: "+MarkerList.get(i).getTitle()+" "+ MarkerList.get(i).getPosition());
                        //arg0=MarkerList.get(0);
                        CameraUpdate u= CameraUpdateFactory.newLatLngZoom(arg0.getPosition(),15);
                        mMap.moveCamera(u);
                        arg0.showInfoWindow();

                        return true;
                    }
               }
                return true;
            }


        });


        //override the marker information
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.activity_map_mark_activity, null);

                //TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                TextView tv_name = (TextView) v.findViewById(R.id.tv_name);
                TextView tv_address = (TextView) v.findViewById(R.id.tv_address);

                tv_address.setText("Address: "+ arg0.getTitle());
                tv_name.setText("Name: "+ username);

                return v;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(isDetails==true)
                {
                    Intent intent = new Intent(MapsActivity.this,Client_dealt_providerInfo.class);
                    Bundle dataMap = new Bundle();
                    dataMap.putString("Title", marker.getTitle());
                    intent.putExtras(dataMap);

                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        Toast.makeText(MapsActivity.this, "Your Current Location", Toast.LENGTH_LONG).show();

        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f", latitude, longitude));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }



    //mark the LatLng according the address
    public LatLng getLocationFromAddress(String strAddress) throws IOException
    {

        strAddress=strAddress.replace(" ", "+");
        strAddress=strAddress.replace(",", ",+");


        Geocoder coder = new Geocoder(this);
        List<Address> list=coder.getFromLocationName(strAddress,1);

        if(list.size() >0) {
            Address add = list.get(0);
            String locality = add.getLocality();


            double lat=add.getLatitude();
            double lng =add.getLongitude();

            LatLng ll=new LatLng(lat,lng);
            return  ll;
        }else{
            LatLng ll=new LatLng(-36.848445,174.762188);
            //kelvinkelvin what if no latlng????????

            Toast.makeText(this,"This address is invalid",Toast.LENGTH_LONG).show();

            return ll;
        }

    }
    public void addAddressMark(String strAddress)
    {
        mMap.clear();
        strAddress=strAddress+", NZ";
        //MarkerOptions markerOptions = new MarkerOptions();
       // markerOptions.title(strAddress);
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        try {
            //String str_from="New World Whangaparaoa";??
            //String str_from="4 Langton Road, Stanmore Bay, Whangaparaoa, New Zealand";
            //Stanmore Bay School
            //String str_from="683 Whangaparaoa Road, Whangaparaoa, NZ";
            LatLng ll =getLocationFromAddress(strAddress);

            Marker melbourne = mMap.addMarker(new MarkerOptions()
                    .position(ll)
                    .title(strAddress)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.add_funds))
                    //kelvinkelvin change map icon


            );

//            if(workType.equals(""))
//            melbourne.setIcon();

            MarkerList.add(melbourne);

           // MarkerList.get(0).showInfoWindow();
          // melbourne.showInfoWindow();

            CameraUpdate u= CameraUpdateFactory.newLatLngZoom(ll,15);
            mMap.moveCamera(u);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
//    private boolean CheckGooglePlayServices() {
//        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
//        int result = googleAPI.isGooglePlayServicesAvailable(this);
//        if (result != ConnectionResult.SUCCESS) {
//            if (googleAPI.isUserResolvableError(result)) {
//                googleAPI.getErrorDialog(this, result,
//                        0).show();
//            }
//            return false;
//        }
//        return true;
//    }
}
