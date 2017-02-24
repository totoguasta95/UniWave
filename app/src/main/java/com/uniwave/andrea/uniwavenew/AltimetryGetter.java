package com.uniwave.andrea.uniwavenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

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
 * Created by Andrea on 13/02/2017.
 */
public class AltimetryGetter {

    private ProgressDialog pDialog;
    Context mainContext;
    String longitude;
    String latitude;
    String height;

    public AltimetryGetter(Context context){
        mainContext = context;
        new getAltimetry().execute();
    }

    private class getAltimetry extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(mainContext);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String jsonStr;
            try {
                AssetManager assetManager = mainContext.getAssets();
                InputStream input = assetManager.open("PT_QUO.json");
                int size = input.available();
                byte[] buffer = new byte[size];
                input.read(buffer);
                input.close();
                jsonStr = new String(buffer);
                if(jsonStr != null){
                    JSONObject reader = new JSONObject(jsonStr);
                    JSONArray altimetry = reader.getJSONArray("features");
                    for (int i = 0; i < altimetry.length(); i++) {
                        JSONObject c = altimetry.getJSONObject(i);
                        JSONObject c1 = c.optJSONObject("geometry");
                        JSONArray coordinates = c1.optJSONArray("coordinates");
                        JSONObject c2 = c.optJSONObject("properties");
                        longitude = coordinates.getString(0);
                        latitude = coordinates.getString(1);
                        height = c2.getString("pq_quota");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
        }

    }

}
