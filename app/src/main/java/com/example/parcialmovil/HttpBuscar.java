package com.example.parcialmovil;

import android.app.ProgressDialog;
import android.content.Context;
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

public class HttpBuscar extends AsyncTask<Void, Void, String> {

    private Context httpContext;
    ProgressDialog progressDialog;
    public boolean error=true;
    public String cancion_nombre;
    public Clase_Cancion cancion = new Clase_Cancion();


    public HttpBuscar(Context ctx, String cancion){
        this.httpContext=ctx;
        this.cancion_nombre = cancion;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(httpContext, "Lista De Canciones", "buscando");
    }


    @Override
    protected String doInBackground(Void... params) {
        String uri = "http://ws.audioscrobbler.com/2.0/?method=track.search&track="+cancion_nombre+"&api_key=b284db959637031077380e7e2c6f2775&format=json";
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
                jo_canciones = jo.getJSONObject("results");
                JSONObject jo_trackmatches = jo_canciones.getJSONObject("trackmatches");

                JSONArray array_canciones = jo_trackmatches.getJSONArray("track");
                for (int i=0; i<array_canciones.length();i++){

                    JSONObject r = array_canciones.getJSONObject(i);


                    cancion.titulo = r.getString("name");
                    cancion.artista = r.getString("artist");
                    cancion.album = r.getString("album");
                    cancion.duracion = r.getString("duracion");

                }

            }
            else{
                Toast.makeText(httpContext,"error",Toast.LENGTH_LONG).show();

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return  "correcto";

    }

    @Override
    protected void onPostExecute(String mensaje) {
        super.onPostExecute(mensaje);
        progressDialog.dismiss();
        if (mensaje.equals("correcto")){
            AddCancion.my_activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AddCancion.txt_nombre_cancion.setText(cancion.titulo);
                    AddCancion.txt_artista_cancion.setText(cancion.artista);
                   AddCancion.txt_Album.setText(cancion.album);
                    AddCancion.txt_Duracion_Cancion.setText(cancion.duracion);
                }
            });
        }else{
            Toast.makeText(httpContext,mensaje , Toast.LENGTH_LONG).show();
        }

    }

}