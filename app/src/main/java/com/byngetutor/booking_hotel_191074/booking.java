package com.byngetutor.booking_hotel_191074;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class booking extends AppCompatActivity {
    EditText medt_nama, medt_nowa, medt_tipekmr, medt_orang, medt_hari;
    TextView mtv_total;
    Button mbtn_hitung, mbtn_ok;
    private String url = "http://10.0.2.2/hotel/simpan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        this.setTitle("BOOKING");

        medt_nama=findViewById(R.id.edtnama);
        medt_nowa=findViewById(R.id.edtnomor);
        medt_tipekmr=findViewById(R.id.edttipekamar);
        medt_orang=findViewById(R.id.edtjmlorang);
        medt_hari=findViewById(R.id.edtjmlhari);
        mtv_total=findViewById(R.id.tvtotal);
        mbtn_hitung=findViewById(R.id.btnhitung);
        mbtn_ok=findViewById(R.id.btnok);

        mbtn_hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer hari,harga,hasil;
                hari=Integer.parseInt(medt_hari.getText().toString());
                harga=250000;
                hasil=harga*hari;
                mtv_total.setText("RP."+hasil.toString());
            }
        });

        mbtn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }

    void simpan(){
        String nama=medt_nama.getText().toString();
        String no_wa=medt_nowa.getText().toString();
        String tipekamar=medt_tipekmr.getText().toString();
        String jumlah_orang=medt_orang.getText().toString();
        String jumlah_hari=medt_hari.getText().toString();
        String total=mtv_total.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(booking.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(booking.this, "tidak dapat diproses", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("no_wa", no_wa);
                params.put("tipe_kamar", tipekamar);
                params.put("jumlah_orang", jumlah_orang);
                params.put("jumlah_hari", jumlah_hari);
                params.put("total", total);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}