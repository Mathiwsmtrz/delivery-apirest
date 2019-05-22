package com.mint.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mint.delivery.dto.Client;
import com.mint.delivery.interfaces.clientInterface;

public class list_opt_client extends AppCompatActivity implements clientInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_opt_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(Client data) {
        Intent intent = new Intent();
        intent.putExtra("id", data.getId().toString());
        intent.putExtra("cedula", data.getCedula());
        intent.putExtra("nombres", data.getNombres());
        setResult(RESULT_OK, intent);
        finish();
    }
}
