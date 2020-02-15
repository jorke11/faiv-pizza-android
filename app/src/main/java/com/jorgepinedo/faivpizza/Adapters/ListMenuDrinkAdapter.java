package com.jorgepinedo.faivpizza.Adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.Models.OrdersDetail;
import com.jorgepinedo.faivpizza.Models.Products;
import com.jorgepinedo.faivpizza.R;
import com.jorgepinedo.faivpizza.Tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class ListMenuDrinkAdapter extends RecyclerView.Adapter<ListMenuDrinkAdapter.MenuViewHolder> {

    private static final String TAG = "CardAdapterRecicleView";
    private List<Products> listItem,filterList;
    private int resource;
    private Activity activity;
    View view;
    App app_db;
    private eventCustom onDragListener;
    public int total=0;

    public ListMenuDrinkAdapter(List<Products> listItem, int resource, Activity activity, eventCustom onDragListener, App app_db) {
        this.listItem = listItem;
        this.resource = resource;
        this.activity = activity;
        this.filterList = new ArrayList<Products>();
        this.filterList.addAll(this.listItem);
        this.onDragListener = onDragListener;
        this.app_db = app_db;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);

        return new MenuViewHolder(view,onDragListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder holder, int i) {
        final Products row = filterList.get(i);

        //String title = (row.getTitle().length()>13)?row.getTitle().substring(0,13)+"..":row.getTitle();
        String price = Utils.numberFormat(row.getPrice());

        //holder.title.setText(title);
        holder.title.setText(row.getTitle());
        holder.price.setText("$"+price);

        int id = activity.getResources().getIdentifier(activity.getApplicationContext().getPackageName()+":drawable/" + row.getUrl(), null, null);
        holder.imageView.setImageResource(id);

        final OrdersDetail detail = app_db.ordersDetailDAO().getOrdersByProductId(row.getId());

        if(detail != null){
            holder.title.setTypeface(null, Typeface.BOLD);
            holder.tv_total_item.setText(detail.getQuantity()+"");
            holder.btn_minus.setVisibility(View.VISIBLE);
        }else{
            holder.tv_total_item.setText("0");
            holder.btn_minus.setVisibility(View.INVISIBLE);
        }

        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDragListener.onClick(row,true);
            }
        });

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDragListener.onClick(row,false);
            }
        });


        //holder.status.setText(row.getStatus());

    }


    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{

        private TextView title,price,tv_total_item;
        private CardView card_complete;
        eventCustom onDragListener;
        ImageView imageView;
        Button btn_plus,btn_minus;


        public MenuViewHolder(View itemView, eventCustom onDragListener) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.imageView);
            card_complete = itemView.findViewById(R.id.card_complete);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            tv_total_item = itemView.findViewById(R.id.tv_total_item);
            this.onDragListener = onDragListener;

        }



    }

    public interface eventCustom{
        void onClick(Products products,boolean type_event);
    }
}
