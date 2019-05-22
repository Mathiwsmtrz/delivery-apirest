package com.mint.delivery;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mint.delivery.adapters.ProductAdapter;
import com.mint.delivery.dto.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class list_opt_productFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Product> productLists;

    String URL_DATA = "https://apptests-math.herokuapp.com/product/";
    public list_opt_productFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_product, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        productLists = new ArrayList<>();

        cargarProductos();
        return v;
    }

    public void cargarProductos(){
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
                        Product product = new Product(
                                jo.getInt("id"),
                                jo.getString("nombres").toString(),
                                jo.getInt("existencias"),
                                jo.getInt("iva"),
                                jo.getInt("valor")
                        );
                        Log.e("eeee",jo.getString("nombres").toString());
                        productLists.add(product);
                    }


                    adapter = new ProductAdapter(productLists,getActivity());
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
}
