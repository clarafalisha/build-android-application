package com.byngetutor.booking_hotel_191074;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity {
    EditText username, password;
    Button btn_login;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("LOGIN");

        username = findViewById(R.id.edtuser);
        password = findViewById(R.id.edtpass);
        btn_login = findViewById(R.id.btnlogin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("")||password.getText().toString().equals("")){
                    builder = new AlertDialog.Builder(login.this);
                    builder.setTitle("Warning..!");
                    builder.setMessage("Your fill not input..!!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    proses_login backgroundTask = new proses_login(login.this);
                    backgroundTask.execute("Login", username.getText().toString(),password.getText().toString());
                }
            }
        });
    }
}