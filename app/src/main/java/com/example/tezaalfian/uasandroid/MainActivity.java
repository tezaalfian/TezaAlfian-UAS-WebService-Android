package com.example.tezaalfian.uasandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button cekData;
    private EditText inputID;
    private TextView id, nama, daerah, kamar, textsuhu, kelembapan;
    private String keyId;
    private RequestQueue mQue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQue = Volley.newRequestQueue(this);
        cekData = findViewById(R.id.btnData);
        inputID = findViewById(R.id.iputId);
        id = findViewById(R.id.cekId);
        nama = findViewById(R.id.cekNama);
        daerah = findViewById(R.id.cekDaerah);
        kamar = findViewById(R.id.cekKamar);
        textsuhu = findViewById(R.id.cekSuhu);
        kelembapan = findViewById(R.id.cekLembap);

        cekData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyId = inputID.getText().toString();
                uraiJSON();
            }
        });
    }

    private void uraiJSON() {
        String url = "http://papaside.com/data.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String result = null;
                try {
                    JSONObject anggota = response.getJSONObject(Integer.parseInt(keyId)-1);

                    String idAnggota = anggota.getString("Kota");
                    String namaAnggota = anggota.getString("siang");
                    String asalDaerah = anggota.getString("malam");
                    String asalkamar = anggota.getString("dini_hari");
                    String suhu = anggota.getString("suhu");
                    String lembap = anggota.getString("Kelembapan");

                    id.setText(idAnggota);
                    nama.setText(namaAnggota);
                    daerah.setText(asalDaerah);
                    kamar.setText(asalkamar);
                    textsuhu.setText(suhu);
                    kelembapan.setText(lembap);

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        mQue.add(request);
    }
}
