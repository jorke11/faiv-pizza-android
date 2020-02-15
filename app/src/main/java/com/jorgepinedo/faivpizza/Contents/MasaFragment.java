package com.jorgepinedo.faivpizza.Contents;


import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.room.Room;

import com.jorgepinedo.faivpizza.Adapters.ListMenuAdapter;
import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.MainActivity;
import com.jorgepinedo.faivpizza.Models.Orders;
import com.jorgepinedo.faivpizza.Models.OrdersDetail;
import com.jorgepinedo.faivpizza.Models.Products;
import com.jorgepinedo.faivpizza.R;
import com.jorgepinedo.faivpizza.Tools.Utils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasaFragment extends Fragment implements ListMenuAdapter.OnDragListener{


    RecyclerView recycler_products;
    ListMenuAdapter listMenuAdapter;
    ImageView image_content;
    ImageView image_table,image_massa;

    List<Products> list;
    App app_db;
    Products current_product;

    public MasaFragment() {
        // Required empty public constructor
    }


    /**
     * Menu Masa
     */

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        app_db = Room.databaseBuilder(getActivity(),App.class,"spad-five")
                .allowMainThreadQueries()
                .build();

        View view = inflater.inflate(R.layout.fragment_masa, container, false);

        list = app_db.productsDAO().getAllProductsCategory(new int[]{1});

        recycler_products = view.findViewById(R.id.recycler_products);
        image_table = view.findViewById(R.id.image_table);
        image_massa = view.findViewById(R.id.image_massa);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_products.setLayoutManager(linearLayoutManager);

        listMenuAdapter = new ListMenuAdapter(list,R.layout.card_product_item,getActivity(), (ListMenuAdapter.OnDragListener) this,app_db);
        recycler_products.setAdapter(listMenuAdapter);

        image_table.setOnDragListener(dragListener);

        String masa = Utils.getItem(getActivity(),"masa");


        if(!masa.isEmpty()){
            int id = getActivity().getResources().getIdentifier(getActivity().getApplicationContext().getPackageName()+":drawable/" + masa, null, null);
            image_massa.setImageResource(id);
        }


        return view;
    }


    View.OnDragListener dragListener=new View.OnDragListener(){

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragAction = event.getAction();

            final View view = (View) event.getLocalState();

            switch (dragAction){
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;

                case DragEvent.ACTION_DRAG_EXITED:

                    break;

                case DragEvent.ACTION_DROP:

                    /*final Fragment fragment =new SalsaFragment();
                    final OrdersDetail row=app_db.ordersDetailDAO().getOrdersByCategoryId(current_product.getCategory_id());

                    if(row != null){
                        if(row.getProduct_id()==current_product.getId()){
                            Toast.makeText(getActivity(),"Ya tienes la "+current_product.getTitle()+" seccionada!",Toast.LENGTH_LONG).show();
                        }else{
                            Products old_product = app_db.productsDAO().getProductById(row.getProduct_id());
                            AlertDialog.Builder alert= new AlertDialog.Builder(getActivity());
                            alert.setTitle("Cambio de producto");
                            alert.setMessage("Seleccionado: ("+current_product.getTitle()+")\nA Reemplazar : ("+old_product.getTitle()+")\n¿Estas seguro de cambiarlo?");
                            alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((MainActivity)getActivity()).chageFragment(fragment);
                                    ((MainActivity)getActivity()).enableBtnsThird();
                                    Utils.setItem(getActivity(),"masa",current_product.getUrl());
                                    app_db.ordersDetailDAO().updateQuantity(row.getId(),current_product.getId());
                                }
                            });
                            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    ((MainActivity)getActivity()).chageFragment(fragment);
                                    ((MainActivity)getActivity()).enableBtnsThird();
                                }
                            });

                            alert.create().show();
                        }
                    }else{
                        ((MainActivity)getActivity()).chageFragment(fragment);
                        ((MainActivity)getActivity()).enableBtnsThird();
                        app_db.ordersDetailDAO().insertAll(new OrdersDetail(1,current_product.getId(),1));
                        Utils.setItem(getActivity(),"masa",current_product.getUrl());
                    }


                    ((MainActivity)getActivity()).loadTotal();*/

                    break;
            }

            return true;
        }
    };


    public void postEventClick(){
        final Fragment fragment =new SalsaFragment();
        final OrdersDetail row = app_db.ordersDetailDAO().getOrdersByCategoryId(current_product.getCategory_id());

        if(row != null){
            if(row.getProduct_id() == current_product.getId()){
                ((MainActivity)getActivity()).chageFragment(fragment);
                ((MainActivity)getActivity()).enableBtnsThird();
            }else{
                Products old_product = app_db.productsDAO().getProductById(row.getProduct_id());

                ((MainActivity)getActivity()).chageFragment(fragment);
                ((MainActivity)getActivity()).enableBtnsThird();
                Utils.setItem(getActivity(),"masa",current_product.getUrl());
                app_db.ordersDetailDAO().updateChangeProduct(row.getId(),current_product.getId());

                /*AlertDialog.Builder alert= new AlertDialog.Builder(getActivity());
                alert.setTitle("Cambio de producto");
                alert.setMessage("Seleccionado: ("+current_product.getTitle()+")\nA Reemplazar : ("+old_product.getTitle()+")\n¿Estas seguro de cambiarlo?");
                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)getActivity()).chageFragment(fragment);
                        ((MainActivity)getActivity()).enableBtnsThird();
                        Utils.setItem(getActivity(),"masa",current_product.getUrl());
                        app_db.ordersDetailDAO().updateChangeProduct(row.getId(),current_product.getId());
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ((MainActivity)getActivity()).chageFragment(fragment);
                        ((MainActivity)getActivity()).enableBtnsThird();
                    }
                });

                alert.create().show();*/
            }
        }else{

            Orders orders=app_db.ordersDAO().getOrderCurrent();
            ((MainActivity)getActivity()).chageFragment(fragment);
            ((MainActivity)getActivity()).enableBtnsThird();
            app_db.ordersDetailDAO().insertAll(new OrdersDetail(orders.getId(),current_product.getId(),0));
            Utils.setItem(getActivity(),"masa",current_product.getUrl());
        }


        ((MainActivity)getActivity()).loadTotal();
    }



    @Override
    public void onDragLongClick(View view,Products products) {
        current_product = products;
        ClipData clipData = ClipData.newPlainText("","");
        View.DragShadowBuilder dragShadowBuilder=new View.DragShadowBuilder(view);
        view.startDrag(clipData,dragShadowBuilder,view,0);
    }

    @Override
    public void onClick(View view, Products products) {
        current_product = products;
        postEventClick();
    }
}
