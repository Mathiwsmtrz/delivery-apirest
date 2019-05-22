package com.mint.delivery.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mint.delivery.R;
import com.mint.delivery.dto.Invoice;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder>{

    private List<Invoice> invoiceLists;
    private Context context;
    LayoutInflater layoutInflater;

    public InvoiceAdapter(List<Invoice> invoiceLists, Context context) {
        this.invoiceLists = invoiceLists;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = layoutInflater.inflate(R.layout.cardinvoice, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Invoice invoiceList = invoiceLists.get(i);
        viewHolder.cliente.setText(invoiceList.getClient().getNombres().toString());
        viewHolder.total.setText(invoiceList.getTotal().toString());
        viewHolder.cod.setText(invoiceList.getId().toString());
        viewHolder.fecha.setText(invoiceList.getFecha().toString());
    }

    @Override
    public int getItemCount() {
        return invoiceLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView cod;
        public TextView cliente;
        public TextView fecha;
        public TextView total;
        public LinearLayout linear;

        public ViewHolder(@NonNull View invoiceView) {
            super(invoiceView);
            cod = (TextView) invoiceView.findViewById(R.id.card_invoice_cod);
            fecha = (TextView) invoiceView.findViewById(R.id.card_invoice_fecha);
            total = (TextView) invoiceView.findViewById(R.id.card_invoice_total);
            cliente = (TextView) invoiceView.findViewById(R.id.card_invoice_client);
            linear = (LinearLayout) invoiceView.findViewById(R.id.card_invoice);

            linear.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}