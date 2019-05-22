package com.mint.delivery.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mint.delivery.R;
import com.mint.delivery.dto.Product;
import com.mint.delivery.interfaces.productInterface;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private List<Product> productLists;
    private Context context;
    LayoutInflater layoutInflater;

    public ProductAdapter(List<Product> productLists, Context context) {
        this.productLists = productLists;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = layoutInflater.inflate(R.layout.cardproduct, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Product productList = productLists.get(i);
        viewHolder.nombres.setText(productList.getNombres().toString());
        viewHolder.stock.setText(productList.getExistencias().toString());
        viewHolder.valor.setText(productList.getValor().toString());
    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nombres;
        public TextView stock;
        public TextView valor;
        public LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombres = (TextView) itemView.findViewById(R.id.card_product_nombre);
            stock = (TextView) itemView.findViewById(R.id.card_product_stock);
            valor = (TextView) itemView.findViewById(R.id.card_product_valor);
            linear = (LinearLayout) itemView.findViewById(R.id.card_product);

            linear.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index =getAdapterPosition();
            Product product = productLists.get(index);
            ((productInterface) itemView.getContext()).onItemSelected(product);
        }
    }

}