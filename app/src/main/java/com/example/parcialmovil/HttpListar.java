package com.example.parcialmovil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpListar extends AsyncTask<Void, Void, String> {

    private Context httpContext;
    ProgressDialog progressDialog;
    public boolean error=true;


    public HttpListar(Context ctx){
        this.httpContext=ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(httpContext, "Canciones", "Cargando canciones...");
    }


    @Override
    protected String doInBackground(Void... params) {
        String uri = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=b284db959637031077380e7e2c6f2775&format=json";
        URL url = null;
        try {

            url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            os.close();

            int responseCode=urlConnection.getResponseCode();
            if(responseCode== HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer sb= new StringBuffer("");
                String linea="";
                while ((linea=in.readLine())!= null){
                    sb.append(linea);
                    break;
                }
                in.close();
                String json = "";
                json = sb.toString();
                JSONObject jo = null;
                jo = new JSONObject(json);

                JSONObject jo_canciones = null;
                jo_canciones = new JSONObject(json);
                jo_canciones = jo.getJSONObject("tracks");

                CancionBD bd = new CancionBD(httpContext, CancionBD.nombreBD, null, 1);
                SQLiteDatabase basededatos = bd.getWritableDatabase();

                basededatos.execSQL("DELETE FROM canciones where album = 'Indefinido'");
                basededatos.close();
                JSONArray array_canciones = jo_canciones.getJSONArray("track");
                for (int i=0; i<array_canciones.length();i++){

                    JSONObject r = array_canciones.getJSONObject(i);

                    Clase_Cancion cancion = new Clase_Cancion();
                    cancion.titulo = r.getString("name");
                    cancion.duracion = r.getString("duration");
                    JSONObject json_artista = r.getJSONObject("artist");
                    cancion.artista = json_artista.getString("name");
                    cancion.album = "Indefinido";
                    new Controller().guardar(httpContext, cancion);

                }
                return "correcto";
            }
            else{
                Toast.makeText(httpContext,"error",Toast.LENGTH_LONG).show();
                return "error";
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return  "";

    }

    @Override
    protected void onPostExecute(String respuesta) {
        super.onPostExecute(respuesta);
        progressDialog.dismiss();
        if (respuesta.equals("correcto")){
            Intent intent = new Intent(httpContext, Listado.class);
            httpContext.startActivity(intent);
        }
        Toast.makeText(httpContext,respuesta , Toast.LENGTH_LONG).show();
    }

}