package com.mint.delivery;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mint.delivery.adapters.ItemAdapter;
import com.mint.delivery.dto.Item;
import com.mint.delivery.interfaces.formInvoiceInterface;
import com.mint.delivery.interfaces.itemInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class form_new_invoiceFragment extends Fragment  implements itemInterface {
    Integer id_cliente, id_producto;
    EditText cod_cliente,nombre_cliente, fecha, otros,producto, cantidad, stock,iva,valor;
    Button guardar, cancelar, addProducto;
    Double subtotal_t, iva_t, total_t;
    TextView in_subtotal, in_iva, in_total;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private List<Item> productLists;

    public form_new_invoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_form_new_invoice, container, false);
        productLists = new ArrayList<>();

        subtotal_t = Double.valueOf(0);
        iva_t=Double.valueOf(0);
        total_t=Double.valueOf(0);

        guardar = (Button) v.findViewById(R.id.btn_guardar_invoice);
        cancelar = (Button) v.findViewById(R.id.btn_cancelar_invoice);
        addProducto = (Button) v.findViewById(R.id.btn_add_producto);
        cod_cliente = (EditText) v.findViewById(R.id.inp_cod_cliente);
        nombre_cliente = (EditText) v.findViewById(R.id.inp_nom_cliente);
        fecha = (EditText) v.findViewById(R.id.inp_fecha);
        otros = (EditText) v.findViewById(R.id.inp_otros);
        producto = (EditText) v.findViewById(R.id.inp_cod_producto);
        cantidad = (EditText) v.findViewById(R.id.inp_cantidad);
        stock = (EditText) v.findViewById(R.id.inp_stock);
        valor = (EditText) v.findViewById(R.id.inp_valor);
        iva = (EditText) v.findViewById(R.id.inp_iva);
        in_subtotal = (TextView) v.findViewById(R.id.in_subtotal);
        in_iva  = (TextView) v.findViewById(R.id.in_iva);
        in_total = (TextView) v.findViewById(R.id.in_total);


        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        cod_cliente.setKeyListener(null);
        nombre_cliente.setKeyListener(null);
        producto.setKeyListener(null);
        stock.setKeyListener(null);
        fecha.setKeyListener(null);
        valor.setKeyListener(null);
        iva.setKeyListener(null);

        cod_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), list_opt_client.class);
                startActivityForResult(intent,1);
            }
        });

        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), list_opt_product.class);
                startActivityForResult(intent,2);
            }
        });

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        addProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_producto>0){
                    if(Integer.parseInt(cantidad.getText().toString())>0){
                        Item item = new Item(
                                null,null,
                                id_producto,producto.getText().toString(),
                                Integer.parseInt(stock.getText().toString()),
                                Integer.parseInt(iva.getText().toString()),
                                Integer.parseInt(valor.getText().toString()),
                                Double.valueOf(cantidad.getText().toString())*Integer.parseInt(valor.getText().toString()),
                                Integer.parseInt(cantidad.getText().toString())
                        );
                        productLists.add(item);
                        recalcular();
                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                        producto.setText("");
                        stock.setText("");
                        valor.setText("");
                        iva.setText("");
                        cantidad.setText("0");
                        id_producto = null;
                    }else{
                        Toast.makeText(getContext(), "Debe  agregar por lo menos una cantidad", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Debe ingrsar un producto valido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });

        return v;
    }

    public void recalcular(){
        subtotal_t = Double.valueOf(0);
        iva_t=Double.valueOf(0);
        total_t=Double.valueOf(0);
        for (int i = 0; i < productLists.size(); i++) {
            Double t = Double.valueOf(productLists.get(i).getValor())* Double.valueOf(productLists.get(i).getCantidad());
            Double iv =Double.valueOf(t/(1+(Double.valueOf(productLists.get(i).getIva().toString())/100)));
            total_t += t;
            iva_t += iv;
            subtotal_t += (t-iv);
            productLists.get(i).setTotal(Double.valueOf(t));
        }
        in_subtotal.setText(String.valueOf(roundTwoDecimals(subtotal_t)));
        in_iva.setText(String.valueOf(roundTwoDecimals(iva_t)));
        in_total.setText(String.valueOf(roundTwoDecimals(total_t)));


        adapter = new ItemAdapter(productLists,getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "-"+ (month+1) + "-"+ day;
                fecha.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("0");
        return Double.valueOf(twoDForm.format(d));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            Toast.makeText(getContext(), data.getStringExtra("nombres") + " fue seleccionado", Toast.LENGTH_SHORT).show();
            cod_cliente.setText(data.getStringExtra("cedula"));
            nombre_cliente.setText(data.getStringExtra("nombres"));
            id_cliente = Integer.parseInt(data.getStringExtra("id"));
        } else if ((requestCode == 2) && (resultCode == RESULT_OK)) {
            Toast.makeText(getContext(), data.getStringExtra("nombres") + " fue seleccionado", Toast.LENGTH_SHORT).show();
            producto.setText(data.getStringExtra("nombres"));
            stock.setText(data.getStringExtra("stock"));
            valor.setText(data.getStringExtra("valor"));
            iva.setText(data.getStringExtra("iva"));
            cantidad.setText("0");
            id_producto = Integer.parseInt(data.getStringExtra("id"));
        }
    }


    public void save(View view) {
        String p_client = id_cliente.toString();
        String p_fecha = fecha.getText().toString();
        String p_otros = otros.getText().toString();
        Double p_subtotal = Double.parseDouble(subtotal_t.toString());
        Double p_iva = Double.parseDouble(iva_t.toString());
        Double p_total = Double.parseDouble(total_t.toString());

        if(
                !TextUtils.isEmpty(p_client) &&
                        !TextUtils.isEmpty(p_fecha) &&
                        !TextUtils.isEmpty(p_otros) &&
                        !TextUtils.isEmpty(p_total.toString())
        ){
            sendPost(p_client,p_fecha,p_otros,p_subtotal,p_iva,p_total);
        }else {
            Toast toast = Toast.makeText(getContext(),"Por favor llene toda la informacion necesaria",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void Retornar(){
        ((formInvoiceInterface) getContext()).onCloseFormInvoice(null);
    }
    public void sendPost(final String p_client, final String p_fecha, final String p_otros, final Double p_subtotal, final Double p_iva, final Double p_total){
        final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Guardando informacion...");
        progressDialog.show();
        RequestQueue request = Volley.newRequestQueue(getContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://apptests-math.herokuapp.com/invoice/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String id = jsonObject.getString("id");
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Guardado exitosamente : " + id,Toast.LENGTH_LONG).show();
                    Retornar();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                JSONArray array = new JSONArray();
                for (int i = 0; i < productLists.size(); i++) {
                    try {
                        JSONObject ob = new JSONObject();
                        ob.put("productId",productLists.get(i).getId_producto().toString());
                        ob.put("cantidad",productLists.get(i).getCantidad().toString());
                        ob.put("valor",productLists.get(i).getValor().toString());
                        ob.put("iva",productLists.get(i).getIva().toString());
                        array.put(ob);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                params.put("clientId",p_client);
                params.put("fecha",p_fecha);
                params.put("otros",p_otros);
                params.put("subtotal",String.valueOf((int) roundTwoDecimals(p_subtotal)));
                params.put("iva",String.valueOf((int) roundTwoDecimals(p_iva)));
                params.put("total",String.valueOf((int) roundTwoDecimals(p_total)));
                params.put("products",array.toString());
                return params;
            }
        };

        //Limite de 5 segundos para una respuesta
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        postRequest.setRetryPolicy(policy);

        request.add(postRequest);
    }

    @Override
    public void onItemSelected(Item data) {

    }

    @Override
    public void onItemRemove(Integer data) {
        productLists.remove(productLists.get(data));
    }
}
@SuppressLint("ValidFragment")
class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

}