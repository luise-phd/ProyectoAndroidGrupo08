package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class APIRestActivity extends AppCompatActivity {
    private static final String TAG = APIRestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apirest);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL githubEndpoint = new URL("https://www.datos.gov.co/resource/xdk5-pm3f.json");
                    HttpsURLConnection myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
                    if (myConnection.getResponseCode() == 200) {
                        Log.e(TAG, "Conexión exitosa");
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        List<String> listado = readMessagesArray(jsonReader);
                        for (int i=0; i<listado.size(); i++) {
                            String[] municipio = listado.get(i).split("- Municipio: ");
                            if (municipio[1].equals("Bochalema")) {
                                Log.i(TAG, listado.get(i));
                            }
                        }
                        jsonReader.close();
                        myConnection.disconnect();
                    } else {
                        Log.e(TAG, "No se pudo realizar la conexión. Codigo: "+myConnection.getResponseCode());
                    }
                } catch (MalformedURLException e) {
                    Log.e(TAG, ""+e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, ""+e.getMessage());
                }
            }
        });
    }

    public List<String> readMessagesArray(JsonReader reader) throws IOException {
        List<String> messages = new ArrayList<String>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    public String readMessage(JsonReader reader) throws IOException {
        String region = "";
        String c_digo_dane_del_departamento = "";
        String departamento = "";
        String c_digo_dane_del_municipio = "";
        String municipio = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("region")) {
                region = reader.nextString();
            } else if (name.equals("c_digo_dane_del_departamento")) {
                c_digo_dane_del_departamento = reader.nextString();
            } else if (name.equals("departamento")) {
                departamento = reader.nextString();
            } else if (name.equals("c_digo_dane_del_municipio")) {
                c_digo_dane_del_municipio = reader.nextString();
            } else if (name.equals("municipio")) {
                municipio = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new String("Region: " + region + " - " +
                "Cod. Departamento: " + c_digo_dane_del_departamento + " - " +
                "Departamento: " + departamento + " - " +
                "Cod. Municipio: " + c_digo_dane_del_municipio + " - " +
                "Municipio: " + municipio);
    }

    public void OnBackPressed() {
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == android.R.id.home) {
            OnBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}