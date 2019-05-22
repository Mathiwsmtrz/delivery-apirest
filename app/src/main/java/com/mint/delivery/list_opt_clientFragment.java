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
import com.mint.delivery.adapters.ClientAdapter;
import com.mint.delivery.dto.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class list_opt_clientFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Client> clientLists;

    String URL_DATA = "https://apptests-math.herokuapp.com/client/";
    public list_opt_clientFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_opt_client, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerClients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        clientLists = new ArrayList<>();
        cargarClientes();

        return v;
    }

    public void cargarClientes(){
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
                        Client client = new Client(
                                jo.getInt("id"),
                                jo.getString("cedula").toString(),
                                jo.getString("nombres").toString()
                        );
                        Log.e("eeee",jo.getString("nombres").toString());
                        clientLists.add(client);
                    }


                    adapter = new ClientAdapter(clientLists,getActivity());
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
