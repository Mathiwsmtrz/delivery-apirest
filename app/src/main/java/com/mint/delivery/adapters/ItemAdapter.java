package com.mint.delivery.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mint.delivery.R;
import com.mint.delivery.dto.Item;
import com.mint.delivery.interfaces.itemInterface;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private List<Item> itemLists;
    private Context context;
    LayoutInflater layoutInflater;

    public ItemAdapter(List<Item> itemLists, Context context) {
        this.itemLists = itemLists;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = layoutInflater.inflate(R.layout.carditem, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Item itemList = itemLists.get(i);
        viewHolder.nombres.setText(itemList.getNombres().toString());
        viewHolder.cantidad.setText(itemList.getCantidad().toString());
        viewHolder.iva.setText(itemList.getIva().toString());
        viewHolder.total.setText(itemList.getTotal().toString());
        viewHolder.valor.setText(itemList.getValor().toString());
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nombres;
        public TextView cantidad;
        public TextView iva;
        public TextView valor;
        public TextView total;
        public LinearLayout linear;
        public ImageView deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombres = (TextView) itemView.findViewById(R.id.card_item_nombre);
            cantidad = (TextView) itemView.findViewById(R.id.card_item_cantidad);
            valor = (TextView) itemView.findViewById(R.id.card_item_valor);
            total = (TextView) itemView.findViewById(R.id.card_item_total);
            iva = (TextView) itemView.findViewById(R.id.card_item_iva);
            linear = (LinearLayout) itemView.findViewById(R.id.card_item);
            deleteItem = (ImageView) itemView.findViewById(R.id.delete_item);
            deleteItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.card_item) {
                int index = getAdapterPosition();
                Item item = itemLists.get(index);
                //((itemInterface) itemView.getContext()).onItemSelected(item);
            } else {
                int index = getAdapterPosition();
                Item item = itemLists.get(index);
                itemLists.remove(item);
                //((itemInterface) itemView.getContext()).onItemRemove(index);
            }
        }
    }

}