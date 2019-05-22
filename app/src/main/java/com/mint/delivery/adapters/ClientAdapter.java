package com.mint.delivery.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mint.delivery.R;
import com.mint.delivery.dto.Client;
import com.mint.delivery.interfaces.clientInterface;

import java.util.List;

public class ClientAdapter  extends RecyclerView.Adapter<ClientAdapter.ViewHolder>{

    private List<Client> clientLists;
    private Context context;
    LayoutInflater layoutInflater;

    public ClientAdapter(List<Client> productLists, Context context) {
        this.clientLists = productLists;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = layoutInflater.inflate(R.layout.cardclient, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Client clientList = clientLists.get(i);
        viewHolder.nombres.setText(clientList.getNombres());
        viewHolder.cedula.setText(clientList.getCedula());
    }

    @Override
    public int getItemCount() {
        return clientLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nombres;
        public TextView cedula;
        public LinearLayout linear;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nombres = (TextView) itemView.findViewById(R.id.card_client_nombres);
            cedula = (TextView) itemView.findViewById(R.id.card_client_cedula);
            linear = (LinearLayout) itemView.findViewById(R.id.card_client);

            linear.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index =getAdapterPosition();
            Client client = clientLists.get(index);
            ((clientInterface) itemView.getContext()).onItemSelected(client);
        }
    }

}