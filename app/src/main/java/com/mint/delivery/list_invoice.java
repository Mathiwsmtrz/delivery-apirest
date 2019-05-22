package com.mint.delivery;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mint.delivery.adapters.InvoiceAdapter;
import com.mint.delivery.dto.Client;
import com.mint.delivery.dto.Invoice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class list_invoice extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Invoice> invoiceLists;

    String URL_DATA = "https://apptests-math.herokuapp.com/invoice/";
    private FragmentActivity myContext;
    FloatingActionButton btn_new;
    public list_invoice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_invoice, container, false);
        btn_new =  view.findViewById(R.id.btn_new_invoice);
        btn_new.setOnClickListener(this);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerInvoices);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        cargarFacturas();

        myContext=(FragmentActivity) view.getContext();
        return view;

    }


    public void cargarFacturas(){
        invoiceLists = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Cargando informacion");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("record");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        JSONObject clientD = jo.getJSONObject("client");
                        Client client = new Client(
                                clientD.getInt("id"),
                                clientD.getString("cedula").toString(),
                                clientD.getString("nombres").toString()
                        );
                        Invoice invoice = new Invoice(
                                jo.getInt("id"),
                                client,
                                jo.getString("fecha").toString(),
                                jo.getString("otros").toString(),
                                jo.getDouble("subtotal"),
                                jo.getDouble("iva"),
                                jo.getDouble("total")
                        );
                        invoiceLists.add(invoice);
                    }
                    adapter = new InvoiceAdapter(invoiceLists,getActivity());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
//        FragmentManager manager  = myContext.getSupportFragmentManager();
//        manager.beginTransaction().replace(R.id.contenedor_home, new form_invoice()).commit();

        Intent intent = new Intent(getActivity().getApplicationContext(), form_new_invoice.class);
        startActivityForResult(intent,1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            cargarFacturas();
        }
    }

}
