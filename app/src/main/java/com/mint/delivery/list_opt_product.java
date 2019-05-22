package com.mint.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mint.delivery.dto.Product;
import com.mint.delivery.interfaces.productInterface;

public class list_opt_product extends AppCompatActivity implements productInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_opt_product);
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
    public void onItemSelected(Product data) {
        Intent intent = new Intent();
        intent.putExtra("id", data.getId().toString());
        intent.putExtra("nombres", data.getNombres());
        intent.putExtra("valor", data.getValor().toString());
        intent.putExtra("iva", data.getIva().toString());
        intent.putExtra("stock", data.getExistencias().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
