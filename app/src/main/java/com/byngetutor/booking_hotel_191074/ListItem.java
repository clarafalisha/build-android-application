package com.byngetutor.booking_hotel_191074;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ListItem extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String URL = "http://10.0.2.2/hotel/retrieve.php";
    private String URL_HAPUS = "http://10.0.2.2/hotel/hapus.php";
    private String URL_EDIT = "http://10.0.2.2/hotel/edit.php";
    private String url = "http://10.0.2.2/hotel/simpan.php";

    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemlist = new ArrayList<Data>();
    HotelAdapter adapter;
    LayoutInflater inflater;
    EditText medt_idhotel, medt_nama, medt_nowa, medt_tipekamar, medt_jmlorang, medt_jmlhari, medt_total;
    String t_idhotel, t_nama, t_nowa, t_tipekamar, t_jmlorang, t_jmlhari, t_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        this.setTitle("HISTORY");

        swipe = findViewById(R.id.swipe);
        list = findViewById(R.id.list);

        adapter = new HotelAdapter(ListItem.this, itemlist);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                itemlist.clear();
                adapter.notifyDataSetChanged();
                callVolley();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String id = itemlist.get(i).getId_hotel();
                final CharSequence[] pilihanAksi = {"HAPUS", "UPDATE"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListItem.this);
                dialog.setItems(pilihanAksi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                //hapus
                                hapus(id);
                                break;
                            case 1:
                                //edit
                                edit(id);
                                break;
                        }
                    }
                }).show();
            }
        });

    }

    public void edit(String id_hotel){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String Id_hotel=jObj.getString("id_hotel");
                            String Nama=jObj.getString("nama");
                            String No_wa=jObj.getString("no_wa");
                            String Tipekamar=jObj.getString("tipe_kamar");
                            String Jumlahorang=jObj.getString("jumlah_orang");
                            String Jumlahhari=jObj.getString("jumlah_hari");
                            String Total=jObj.getString("total");

                            dialogForm(Id_hotel, Nama, No_wa, Tipekamar, Jumlahorang, Jumlahhari, Total, "UPDATE");
                            adapter.notifyDataSetChanged();

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListItem.this, "tidak dapat diproses", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_hotel", id_hotel);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    public void dialogForm(String id_hotel, String nama, String no_wa, String tipe_kamar, String jumlah_orang, String jumlah_hari, String total, String button){
        AlertDialog.Builder dialogForm = new AlertDialog.Builder(ListItem.this);
        inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.activity_form_edit, null);
        dialogForm.setView(viewDialog);
        dialogForm.setCancelable(true);
        dialogForm.setTitle("Edit");

        medt_idhotel=viewDialog.findViewById(R.id.edt_id);
        medt_nama=viewDialog.findViewById(R.id.edt_nama);
        medt_nowa=viewDialog.findViewById(R.id.edt_nowa);
        medt_tipekamar=viewDialog.findViewById(R.id.edt_tipekmr);
        medt_jmlorang=viewDialog.findViewById(R.id.edt_jmlorang);
        medt_jmlhari=viewDialog.findViewById(R.id.edt_jmlhari);
        medt_total=viewDialog.findViewById(R.id.edt_total);

        if (id_hotel.isEmpty()){
            medt_idhotel.setText(null);
            medt_nama.setText(null);
            medt_nowa.setText(null);
            medt_tipekamar.setText(null);
            medt_jmlorang.setText(null);
            medt_jmlhari.setText(null);
            medt_total.setText(null);
        }else {
            medt_idhotel.setText(id_hotel);
            medt_nama.setText(nama);
            medt_nowa.setText(no_wa);
            medt_tipekamar.setText(tipe_kamar);
            medt_jmlorang.setText(jumlah_orang);
            medt_jmlhari.setText(jumlah_hari);
            medt_total.setText(total);
        }
        dialogForm.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                t_idhotel=medt_idhotel.getText().toString();
                t_nama=medt_nama.getText().toString();
                t_nowa=medt_nowa.getText().toString();
                t_tipekamar=medt_tipekamar.getText().toString();
                t_jmlorang=medt_jmlorang.getText().toString();
                t_jmlhari=medt_jmlhari.getText().toString();
                t_total=medt_total.getText().toString();

                simpan();
                dialogInterface.dismiss();
            }
        });

        dialogForm.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                medt_idhotel.setText(null);
                medt_nama.setText(null);
                medt_nowa.setText(null);
                medt_tipekamar.setText(null);
                medt_jmlorang.setText(null);
                medt_jmlhari.setText(null);
                medt_total.setText(null);
            }
        });
        dialogForm.show();
    }

    public void simpan(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callVolley();
                        Toast.makeText(ListItem.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListItem.this, "tidak dapat diproses", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                if (t_idhotel.isEmpty()){
                    params.put("nama", t_nama);
                    params.put("no_wa", t_nowa);
                    params.put("tipe_kamar", t_tipekamar);
                    params.put("jumlah_orang", t_jmlorang);
                    params.put("jumlah_hari", t_jmlhari);
                    params.put("total", t_total);
                    return params;
                }else {
                    params.put("id_hotel", t_idhotel);
                    params.put("nama", t_nama);
                    params.put("no_wa", t_nowa);
                    params.put("tipe_kamar", t_tipekamar);
                    params.put("jumlah_orang", t_jmlorang);
                    params.put("jumlah_hari", t_jmlhari);
                    params.put("total", t_total);
                    return params;
                }

            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    public void hapus(String id_hotel){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HAPUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ListItem.this, response, Toast.LENGTH_SHORT).show();
                        callVolley();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListItem.this, "tidak dapat diproses", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_hotel", id_hotel);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    @Override
    public void onRefresh(){
        itemlist.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    private void callVolley(){
        itemlist.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        //membuat request json
        JsonArrayRequest jArr = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i<response.length();i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Data item = new Data();
                        item.setId_hotel(obj.getString("id_hotel"));
                        item.setNama(obj.getString("nama"));
                        item.setNo_wa(obj.getString("no_wa"));
                        item.setTipe_kamar(obj.getString("tipe_kamar"));
                        item.setJumlah_orang(obj.getString("jumlah_orang"));
                        item.setJumlah_hari(obj.getString("jumlah_hari"));
                        item.setTotal(obj.getString("total"));

                        itemlist.add(item);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: "+error.getMessage());
                swipe.setRefreshing(false);

            }
        });
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(jArr);

    }
}