package com.jorgepinedo.faivpizza.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.Models.OrdersDetail;
import com.jorgepinedo.faivpizza.Models.Products;
import com.jorgepinedo.faivpizza.R;
import com.jorgepinedo.faivpizza.Tools.Utils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMenuAdapterAdmin extends RecyclerView.Adapter<ListMenuAdapterAdmin.MenuViewHolder> {

    private static final String TAG = "CardAdapterRecicleView";
    private List<Products> listItem,filterList;
    private int resource;
    private Activity activity;
    View view;
    App app_db;

    public ListMenuAdapterAdmin(List<Products> listItem, int resource, Activity activity, App app_db) {
        this.listItem = listItem;
        this.resource = resource;
        this.activity = activity;
        this.filterList = new ArrayList<Products>();
        this.filterList.addAll(this.listItem);
        this.app_db = app_db;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int i) {
        final Products row = filterList.get(i);

        //DecimalFormat formatter = new DecimalFormat("###,###,##0.00");

        String price = Utils.numberFormat(row.getPrice());

        //String title = (row.getTitle().length()>13)?row.getTitle().substring(0,13)+"..":row.getTitle();

        //holder.title.setText(title);
        holder.title.setText(row.getTitle());
        holder.price.setText("$"+price);


        int id = activity.getResources().getIdentifier(activity.getApplicationContext().getPackageName()+":drawable/mini_" + row.getUrl(), null, null);
        holder.imageView.setImageResource(id);

        OrdersDetail detail = app_db.ordersDetailDAO().getOrdersByProductId(row.getId());

        if(detail != null){
            //holder.title.setTypeface(null, Typeface.BOLD);
            id = activity.getResources().getIdentifier(activity.getApplicationContext().getPackageName()+":drawable/checked", null, null);
            holder.imageView.setImageResource(id);
        }

        /*holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onDragListener.onDragLongClick(v,row);
                return true;
            }
        });*/

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{

        private TextView title,price;
        private CardView card_complete;
        CircleImageView imageView;

        public MenuViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.imageView);
            card_complete = itemView.findViewById(R.id.card_complete);

        }
    }

}
