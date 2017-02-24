package com.example.andrea.uniwavenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.android.gms.internal.zzip.runOnUiThread;

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
 * Created by Andrea on 22/02/2017.
 */
public class TideGetter {

    Context mainContext;
    private static String url = "http://dati.venezia.it/sites/default/files/dataset/opendata/livello.json";
    String Valore;
    private String TAG = MainActivity.class.getSimpleName();

    public TideGetter(Context context){
        mainContext = context;
        new getData().execute();
    }

    private class getData extends AsyncTask<Void, Void, Void> {

        String Data;
        String Stazione;
        Date DataFormat;
        String jsonStr;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            MainActivity.pDialog = new ProgressDialog(mainContext);
            MainActivity.pDialog.setMessage("Attendi...");
            MainActivity.pDialog.setCancelable(false);
            MainActivity.pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray data = new JSONArray(jsonStr);
                    // looping through All Contacts
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);
                        Stazione = c.getString("stazione");
                        if(Stazione.equals("Punta Salute Canal Grande")) {
                            Data = c.getString("data");
                            Valore = c.getString("valore");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            DataFormat = format.parse(Data);
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            /**
             * Updating parsed JSON data into ListView
             * */
            if(jsonStr != null) {
                SimpleDateFormat simpleHour = new SimpleDateFormat("HH:mm");
                String[] valoreMetri = Valore.split(" ");
                Valore = String.valueOf(Math.round(Float.parseFloat(valoreMetri[0]) * 100));
                MainActivity.waterHeight = Integer.parseInt(Valore);
                MainActivity.hour.setText(simpleHour.format(DataFormat));
                MainActivity.height.setText(Valore.concat(" cm"));
                PrevisionGetter pGetter = new PrevisionGetter(mainContext);
            }
            else{
                if (MainActivity.pDialog.isShowing())
                    MainActivity.pDialog.dismiss();
                Toast.makeText(mainContext, "No connessione internet", Toast.LENGTH_LONG).show();
            }
        }

    }
}
