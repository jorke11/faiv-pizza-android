package com.jorgepinedo.faivpizza.Contents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jorgepinedo.faivpizza.Adapters.ListMenuDrinkAdapter;
import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.MainActivity;
import com.jorgepinedo.faivpizza.Models.Ingredients;
import com.jorgepinedo.faivpizza.Models.Orders;
import com.jorgepinedo.faivpizza.Models.OrdersDetail;
import com.jorgepinedo.faivpizza.Models.Products;
import com.jorgepinedo.faivpizza.R;
import com.jorgepinedo.faivpizza.Tools.Utils;

import java.util.List;


public class DrinkFragment extends Fragment implements ListMenuDrinkAdapter.eventCustom{


    RecyclerView recycler_products;
    ListMenuDrinkAdapter listMenuAdapter;
    ImageView image_table,image_massa,image_salsa,image_queso,image_topping,image_topping2;

    List<Products> list;
    App app_db;
    Button btn_next;
    String topping_1,topping_2;


    public DrinkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink, container, false);
        app_db = Utils.newInstanceDB(getActivity());
        list = app_db.productsDAO().getAllProductsCategory(new int[]{7});
        recycler_products = view.findViewById(R.id.recycler_products);
        btn_next = view.findViewById(R.id.btn_next);
        image_table = view.findViewById(R.id.image_table);
        image_massa = view.findViewById(R.id.image_massa);
        image_salsa = view.findViewById(R.id.image_salsa);
        image_queso = view.findViewById(R.id.image_queso);
        image_topping = view.findViewById(R.id.image_topping);
        image_topping2 = view.findViewById(R.id.image_topping2);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_products.setLayoutManager(linearLayoutManager);

        listMenuAdapter = new ListMenuDrinkAdapter(list,R.layout.card_product_drink,getActivity(), (ListMenuDrinkAdapter.eventCustom) this,app_db);
        recycler_products.setAdapter(listMenuAdapter);


        List<Ingredients> ingredients= app_db.ordersDetailDAO().getcategoryExists(new int[]{1,2,3});

        int id=0;

        for (Ingredients row:ingredients){
            if(row.getCategory_id() == 1){
                id = getActivity().getResources().getIdentifier(getActivity().getApplicationContext().getPackageName()+":drawable/" + row.getUrl(), null, null);
                image_massa.setImageResource(id);
            }else if(row.getCategory_id() == 2){
                id = getActivity().getResources().getIdentifier(getActivity().getApplicationContext().getPackageName()+":drawable/" + row.getUrl(), null, null);
                image_queso.setImageResource(id);
            }else if(row.getCategory_id() == 3){
                id = getActivity().getResources().getIdentifier(getActivity().getApplicationContext().getPackageName()+":drawable/" + row.getUrl(), null, null);
                image_salsa.setImageResource(id);
            }
        }

        List<Ingredients> toppings = app_db.ordersDetailDAO().getcategoryExists(new int[]{4,5,6});

        if(toppings.size()>0){
            if(toppings.size() == 1){

                id = getActivity().getResources().getIdentifier(getActivity().getApplicationContext().getPackageName()+":drawable/"+toppings.get(0).getUrl(), null, null);
                image_topping.setImageResource(id);
                image_topping2.setImageDrawable(null);
            }

            if(toppings.size() == 2){

                id = getActivity().getResources().getIdentifier(getActivity().getApplicationContext().getPackageName()+":drawable/"+toppings.get(0).getUrl(), null, null);
                image_topping.setImageResource(id);

                int id2 = getActivity().getResources().getIdentifier(getActivity().getApplicationContext().getPackageName()+":drawable/"+toppings.get(1).getUrl(), null, null);
                image_topping2.setImageResource(id2);

                if(toppings.get(0).getPriority() > toppings.get(1).getPriority()){
                    image_topping.setImageResource(id2);
                    image_topping2.setImageResource(id);
                }
            }

        }else{

            image_topping.setImageDrawable(null);
            image_topping2.setImageDrawable(null);
        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).chageFragment(new ReviewFragment());
                ((MainActivity)getActivity()).enableBtnReview();
            }
        });

        ((MainActivity)getActivity()).hideInformation(true);


        return view;
    }

    @Override
    public void onClick(Products products,boolean type_event) {

        OrdersDetail row = app_db.ordersDetailDAO().getOrdersByProductId(products.getId());

        if(type_event) {
            if (row == null) {
                Orders orders=app_db.ordersDAO().getOrderCurrent();
                app_db.ordersDetailDAO().insertAll(new OrdersDetail(orders.getId(), products.getId(), 0));
            } else {
                app_db.ordersDetailDAO().updateQuantity(row.getId(), row.getQuantity() + 1);
            }
        }else{

            int total = row.getQuantity() - 1;
            Log.d("JORKE",total+"");
            if(total == 0){
                app_db.ordersDetailDAO().delete(row);
            }else{
                app_db.ordersDetailDAO().updateQuantity(row.getId(), total);
            }
        }

        listMenuAdapter.notifyDataSetChanged();
        ((MainActivity)getActivity()).loadTotal();

    }
}
