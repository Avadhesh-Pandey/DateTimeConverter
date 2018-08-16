package com.dateutils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.codesasthra.support.DateTimeConverter;


public class ConverterActivity extends Activity {

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
