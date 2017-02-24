package com.uniwave.andrea.uniwavenew;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.andrea.uniwavenew.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import java.util.ArrayList;

/*Copyright [2017] [The Alliance]

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.*/

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    static GoogleMap mMap;
    ArrayList<LatLng> markerPoints;
    double myLatitude;
    double myLongitude;
    static Polyline polyline;
    static ArrayList<altimetryCircle> submergedRoads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Initializing
        markerPoints = new ArrayList<LatLng>();
        submergedRoads = new ArrayList<altimetryCircle>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Venice and move the camera
        LatLng coordinate = new LatLng(45.436336379, 12.330106794);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 5);
        mMap.animateCamera(yourLocation);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(coordinate)      // Sets the center of the map to Venice
                .zoom(12)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to nord
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        for(int i = 0; i < UniversityGetter.universityList.size(); i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(UniversityGetter.universityList.get(i).latitude, UniversityGetter.universityList.get(i).longitude))
                    .title(UniversityGetter.universityList.get(i).nome)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .snippet(UniversityGetter.universityList.get(i).indirizzo));
        }
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.430312,12.330186))
                .radius(80)))
                ,100));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.430875,12.327572))
                .radius(70)))
                ,125));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.430512,12.322339))
                .radius(50)))
                ,100));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.433096,12.316587))
                .radius(50)))
                ,100));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.432893,12.320923))
                .radius(60)))
                ,100));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.433992,12.329775))
                .radius(30)))
                ,100));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.438494,12.335793))
                .radius(40)))
                ,100));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.445984,12.320086))
                .radius(50)))
                ,100));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.444734,12.323230))
                .radius(50)))
                ,100));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.441859,12.323069))
                .radius(40)))
                ,100));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.442323,12.336694))
                .radius(70)))
                ,100));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.433857,12.340751))
                .radius(80)))
                ,93));
        submergedRoads.add(new altimetryCircle((mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.434128,12.344422))
                .radius(40)))
                ,110));
        for(altimetryCircle e : submergedRoads){
            e.getCircle().setVisible(false);
            if(MainActivity.waterHeight + 2 <= e.getHeight()){
                e.getCircle().remove();
                e.valido = true;
            }
        }
        for(int i = 0; i < submergedRoads.size(); i++){
            if(submergedRoads.get(i).getValido() == true) {
                submergedRoads.remove(i);
                i--;
            }
        }
        // create class object
        GPSTracker gps = new GPSTracker(this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            myLatitude = gps.getLatitude();
            myLongitude = gps.getLongitude();

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        // show position on map
        mMap.setMyLocationEnabled(true);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (polyline != null)
                    polyline.remove();
                // Getting URL to the Google Directions API
                String url = getDirectionsUrl(new LatLng(myLatitude, myLongitude), marker.getPosition());

                DownloadTask downloadTask = new DownloadTask(MapsActivity.this);

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }
        });

    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Navigation mode
        String mode = "mode=walking";

        // Alternatives routes
        String alternatives = "alternatives=true";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + alternatives;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    public class altimetryCircle{
        Circle c;
        int height;
        boolean valido;

        public altimetryCircle(Circle c, int height){
            this.c = c;
            this.height = height;
            valido = false;
        }

        public Circle getCircle(){
            return c;
        }

        public int getHeight(){
            return height;
        }

        public boolean getValido(){
            return valido;
        }
    }

}