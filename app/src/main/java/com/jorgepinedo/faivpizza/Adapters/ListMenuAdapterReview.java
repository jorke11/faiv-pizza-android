package com.jorgepinedo.faivpizza.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.Models.Review;
import com.jorgepinedo.faivpizza.R;
import com.jorgepinedo.faivpizza.Tools.Utils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMenuAdapterReview extends RecyclerView.Adapter<ListMenuAdapterReview.MenuViewHolder> {

    private static final String TAG = "CardAdapterRecicleView";
    private List<Review> listItem,filterList;
    private int resource;
    private Activity activity;
    View view;
    App app_db;
    private EventCustomer eventCustomer;

    public ListMenuAdapterReview(List<Review> listItem, int resource, Activity activity, EventCustomer eventCustomer, App app_db) {
        this.listItem = listItem;
        this.resource = resource;
        this.activity = activity;
        this.filterList = new ArrayList<Review>();
        this.filterList.addAll(this.listItem);
        this.eventCustomer = eventCustomer;
        this.app_db = app_db;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);

        return new MenuViewHolder(view,eventCustomer);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder holder, final int i) {
        final Review row = filterList.get(i);

        holder.title.setText(row.getTitle());
        holder.tv_total_item.setText(row.getQuantity()+"");

        final String price = Utils.numberFormat(row.getPrice()* row.getQuantity());

        //String price = Utils.numberFormat(row.getSubtotal());

        holder.tv_price.setText("$"+price);

        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = filterList.get(i).getQuantity()+1;
                filterList.get(i).setQuantity(quantity);
                app_db.ordersDetailDAO().updateQuantity(row.getId(),quantity);
                app_db.ordersDetailDAO().updateChild(row.getId(),quantity);
                notifyDataSetChanged();
                eventCustomer.onClickUpdateTotal();

            }
        });

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = filterList.get(i).getQuantity() - 1 ;

                if(quantity == 0){
                    app_db.ordersDetailDAO().deleteAllChild(row.getId());
                    app_db.ordersDetailDAO().deleteById(row.getId());
                    filterList.remove(row);
                }else{
                    app_db.ordersDetailDAO().updateQuantity(row.getId(),quantity);
                    app_db.ordersDetailDAO().updateChild(row.getId(),quantity);
                    filterList.get(i).setQuantity(quantity);

                }
                notifyDataSetChanged();

                eventCustomer.onClickUpdateTotal();

            }
        });


        if(row.getStatus_id() == 3){
            holder.btn_minus.setVisibility(View.INVISIBLE);
            holder.btn_plus.setVisibility(View.INVISIBLE);
            holder.card_complete.setBackgroundColor(activity.getResources().getColor(R.color.grey));
        }

    }


    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{

        private TextView title,tv_total_item,tv_price;
        Button btn_minus,btn_plus;
        EventCustomer eventCustomer;
        CircleImageView imageView;
        CardView card_complete;

        public MenuViewHolder(View itemView, EventCustomer eventCustomer) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            tv_total_item = itemView.findViewById(R.id.tv_total_item);
            imageView = itemView.findViewById(R.id.imageView);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            tv_price = itemView.findViewById(R.id.tv_price);
            card_complete = itemView.findViewById(R.id.card_complete);
            this.eventCustomer = eventCustomer;

        }
    }

    public interface EventCustomer{
        void onClickUpdateTotal();
    }
}
