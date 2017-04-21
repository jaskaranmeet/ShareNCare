package com.example.hp.sharencare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hp.sharencare.R;

public class ChooseActivity extends AppCompatActivity {

    private Button planbtn,bookbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        planbtn= (Button) findViewById(R.id.planbtn);
        bookbtn= (Button) findViewById(R.id.bookbtn);



        planbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
}
