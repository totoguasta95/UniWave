package com.example.andrea.uniwavenew;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andrea on 09/02/2017.
 */

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

public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

    Context mapsContext;

    public ParserTask(Context context){
        mapsContext = context;
    }

    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);
            DirectionsJSONParser parser = new DirectionsJSONParser();

            // Starts parsing data
            routes = parser.parse(jObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;
        MarkerOptions markerOptions = new MarkerOptions();
        String distance = "";
        String duration = "";
        boolean in;

        if (result.size() < 1) {
            Log.i("Error:", "No points");
            return;
        }

        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                if (j == 0) {    // Get distance from the list
                    distance = (String) point.get("distance");
                    continue;
                } else if (j == 1) { // Get duration from the list
                    duration = (String) point.get("duration");
                    continue;
                }

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(4);
            lineOptions.color(Color.BLUE);

            // Drawing polyline in the Google Map for the i-th route
            MapsActivity.polyline = MapsActivity.mMap.addPolyline(lineOptions);

            in = false;

            for(LatLng e : MapsActivity.polyline.getPoints()){
                for(int c = 0; c < MapsActivity.submergedRoads.size() && !in; c++) {
                    if (Math.sqrt(((e.latitude - MapsActivity.submergedRoads.get(c).getCircle().getCenter().latitude) * (e.latitude - MapsActivity.submergedRoads.get(c).getCircle().getCenter().latitude)) + ((e.longitude - MapsActivity.submergedRoads.get(c).getCircle().getCenter().longitude) * (e.longitude - MapsActivity.submergedRoads.get(c).getCircle().getCenter().longitude))) < Math.sqrt((MapsActivity.submergedRoads.get(c).getCircle().getRadius() * MapsActivity.submergedRoads.get(c).getCircle().getRadius())) / 30.92 * 0.000277778) {
                        in = true;
                    }
                }
            }

            if(in == true){
                MapsActivity.polyline.remove();
            } else{
                i = result.size();
            }
        }

        if(MapsActivity.polyline != null)
            Toast.makeText(mapsContext, "Strada ottimale calcolata", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(mapsContext, "Non esiste un percorso per evitare l'acqua alta", Toast.LENGTH_LONG).show();

    }
}
