package com.mint.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mint.delivery.dto.Item;
import com.mint.delivery.interfaces.formInvoiceInterface;

public class form_new_invoice extends AppCompatActivity implements formInvoiceInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_new_invoice);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onCloseFormInvoice(String data) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
