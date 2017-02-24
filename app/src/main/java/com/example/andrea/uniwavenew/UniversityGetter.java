package com.example.andrea.uniwavenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Created by Andrea on 19/12/2016.
 */
public class UniversityGetter {

    private ProgressDialog pDialog;
    private static String url = "http://static.unive.it/sitows/didattica/sedi";
    String latitude;
    String longitude;
    String nome;
    String indirizzo;
    Context mapContext;
    static List<University> universityList = new ArrayList<>();

    public UniversityGetter(Context context){
        mapContext = context;
        universityList.clear();
        new getUniversity().execute();
    }

    private class getUniversity extends AsyncTask<Void, Void, Void> {

        String coordinates;
        String[] parts;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            if(jsonStr != null){
                try {
                    JSONArray data = new JSONArray(jsonStr);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);
                        nome = c.getString("NOME");
                        indirizzo = c.getString("INDIRIZZO");
                        indirizzo = indirizzo.replaceAll(", 30121 Venezia", "");
                        indirizzo = indirizzo.replaceAll(", 30122 Venezia","");
                        indirizzo = indirizzo.replaceAll(", 30123 Venezia","");
                        indirizzo = indirizzo.replaceAll(", 30124 Venezia","");
                        indirizzo = indirizzo.replaceAll(", 30125 Venezia","");
                        if(!(nome.equals("Altro")) && !(nome.equals("Treviso - Palazzo San Paolo"))
                           && !(nome.equals("Treviso - Palazzo San Leonardo")) && !(nome.equals("Oriago - Villa Mocenigo"))
                           && !(nome.equals("Campus scientifico via Torino")) && !(nome.equals("Marghera - Vega"))) {
                            coordinates = c.getString("COORDINATE");
                            parts = coordinates.split(",");
                            longitude = parts[0];
                            latitude = parts[1];
                            universityList.add(new University(nome, indirizzo, Double.parseDouble(latitude), Double.parseDouble(longitude)));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (MainActivity.pDialog.isShowing())
                MainActivity.pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
        }
    }

    public class University {
        String nome;
        String indirizzo;
        Double latitude;
        Double longitude;

        public University(String nome, String indirizzo, Double latitude, Double longitude){
            this.nome = nome;
            this.indirizzo = indirizzo;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
