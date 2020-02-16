package com.apkglobal.tracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {


    EditText name,mobile;
    Button save;

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        save=findViewById(R.id.register);
        final SharedPreferences.Editor editor;
        sp=getSharedPreferences("spfile",0);
        editor=sp.edit();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("")|| mobile.getText()
                        .toString().equals(""))
                {
                    Toast.makeText(Register.this, "Fill the data", Toast.LENGTH_SHORT).show();
                }

                else
                {
                editor.putString("name",name.getText().toString());
                    editor.putString("mobile",mobile.getText().toString());
                    editor.commit();
                    Intent i=new Intent(Register.this,MapsActivity.class);
                    startActivity(i);
                    Shared shared =new Shared(getApplicationContext());
                    //to change the boolean value as true
                    shared.secondtime();
                    finish();
                }
            }
        });
    }

}
