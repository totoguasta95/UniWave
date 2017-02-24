package com.example.andrea.uniwavenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Created by Andrea on 02/12/2016.
 */
public class PrevisionGetter {

    private ProgressDialog pDialog;
    private static String url = "http://dati.venezia.it/sites/default/files/dataset/opendata/previsione.json";
    static List<Map<String,String>> heightList01 = new ArrayList<Map<String,String>>();
    static List<Map<String,String>> heightList02 = new ArrayList<Map<String,String>>();
    static List<Map<String,String>> heightList03 = new ArrayList<Map<String,String>>();
    Context previsionContext;

    public PrevisionGetter(Context context){
        previsionContext = context;
        heightList01.clear();
        heightList02.clear();
        heightList03.clear();
        new getPrevision().execute();
    }

    private class getPrevision extends AsyncTask<Void, Void, Void> {

        String Data;
        String Tipo;
        String Valore;
        String TipoValore;
        Date DataFormat;
        Date Data01;
        Date Data02;
        Date Data03;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONArray data = new JSONArray(jsonStr);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dataComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat dataNormal = new SimpleDateFormat("MM-dd");
                    SimpleDateFormat dataReduce = new SimpleDateFormat("HH:mm");
                    Data01 = calendar.getTime();
                    calendar.add(Calendar.DATE, 1);
                    Data02 = calendar.getTime();
                    calendar.add(Calendar.DATE, 1);
                    Data03 = calendar.getTime();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);
                        Data = c.getString("DATA_ESTREMALE");
                        DataFormat = dataComplete.parse(Data);
                        if(DataFormat.getMonth() == Data01.getMonth() && DataFormat.getDay() == Data01.getDay()){
                            Tipo = c.getString("TIPO_ESTREMALE");
                            Valore = c.getString("VALORE");
                            Tipo = Tipo.concat(": ");
                            TipoValore = Tipo.concat(Valore + " cm");
                            Data = dataReduce.format(DataFormat);
                            heightList01.add(new HashMap<String, String>(){
                                {
                                    put("Data",Data);
                                    put("TipoValore",TipoValore);
                                }
                            });
                        }
                        if(DataFormat.getMonth() == Data02.getMonth() && DataFormat.getDay() == Data02.getDay()){
                            Tipo = c.getString("TIPO_ESTREMALE");
                            Valore = c.getString("VALORE");
                            Tipo = Tipo.concat(": ");
                            TipoValore = Tipo.concat(Valore + " cm");
                            Data = dataReduce.format(DataFormat);
                            heightList02.add(new HashMap<String, String>(){
                                {
                                    put("Data",Data);
                                    put("TipoValore",TipoValore);
                                }
                            });
                        }
                        if(DataFormat.getMonth() == Data03.getMonth() && DataFormat.getDay() == Data03.getDay()){
                            Tipo = c.getString("TIPO_ESTREMALE");
                            Valore = c.getString("VALORE");
                            Tipo = Tipo.concat(": ");
                            TipoValore = Tipo.concat(Valore + " cm");
                            Data = dataReduce.format(DataFormat);
                            heightList03.add(new HashMap<String, String>(){
                                {
                                    put("Data",Data);
                                    put("TipoValore",TipoValore);
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            /**
             * Updating parsed JSON data into ListView
             * */
            UniversityGetter uGetter = new UniversityGetter(previsionContext);
        }

    }
}
