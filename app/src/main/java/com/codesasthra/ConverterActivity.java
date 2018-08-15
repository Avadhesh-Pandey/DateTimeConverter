package com.codesasthra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import codesasthra.com.datetimeconverterlibrary.DateTimeConverter;

public class ConverterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        String dtc=new DateTimeConverter.Builder()
                .setDateString("1990-12-28")
                .setOutputFormat("E, MMM dd yyyy")
                .create();

        SOP("DateTimeConverter_dtc=="+dtc);
    }

    void SOP(String msg)
    {
        Log.d("DEBUG",""+msg);
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
