package com.byngetutor.booking_hotel_191074;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class proses_login extends AsyncTask<String,Void, String> {
    private String url_proses_login = "http://10.0.2.2/hotel/login.php";

    Context context;
    ProgressDialog progressDialog;
    Activity activity;
    AlertDialog.Builder builder;

    public proses_login(Context context){
        this.context = context;
        activity = (Activity) context;
    }


    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Koneksi ke server..");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params){
        String method = params[0];
        if (method.equals("Login")){
            try {
                URL url = new URL(url_proses_login);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String username, password;
                username = params[1];
                password = params[2];

                String data_login = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(data_login);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }

                connection.disconnect();
                Thread.sleep(1000);
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("Server");
            JSONObject JO = jsonArray.getJSONObject(0);
            String code = JO.getString("code");
            String message = JO.getString("pesan");
            if (code.equals("login_true")){
                builder.setMessage(message);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.dismiss();

                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else if (code.equals("login_false")){
                builder.setMessage(message);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
