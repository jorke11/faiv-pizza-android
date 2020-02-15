package com.jorgepinedo.faivpizza;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jorgepinedo.faivpizza.Adapters.ListMenuAdapterPayment;
import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.Models.Orders;
import com.jorgepinedo.faivpizza.Models.Review;
import com.jorgepinedo.faivpizza.Tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity  implements ListMenuAdapterPayment.EventCustomer{

    App app_db;
    RecyclerView recycler_review;
    ListMenuAdapterPayment listMenuAdapter;
    Button btn_pay;
    TextView tv_service,tv_total;

    StringRequest stringRequest;
    private String IP ="";
    RequestQueue requestQueue;

    float total = 0,service = 0,subtotal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        IP=Utils.getItem(this,"IP_SERVER");

        app_db = Utils.newInstanceDB(this);

        recycler_review = findViewById(R.id.recycler_review);
        btn_pay = findViewById(R.id.btn_pay);
        tv_service = findViewById(R.id.tv_service);
        tv_total = findViewById(R.id.tv_total);

        total = app_db.ordersDetailDAO().getTotal(new int[]{3});

        String total_formated=Utils.numberFormat(Math.round(total));

        tv_total.setText(total_formated);

        List<Review> listReviewMain,listReviewTotal,listReviewDrink;

        int[] state={3};
        final Orders orders = app_db.ordersDAO().getOrderCurrent();

        listReviewMain = app_db.ordersDetailDAO().getReviewNotIn(7,state,orders.getId());

        listReviewDrink = app_db.ordersDetailDAO().getReviewIn(7,state, orders.getId());

        listReviewTotal = new ArrayList<>();

        listReviewTotal.addAll(Utils.joinAditionals(listReviewMain,app_db));
        listReviewTotal.addAll(listReviewDrink);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_review.setLayoutManager(linearLayoutManager);

        listMenuAdapter = new ListMenuAdapterPayment(listReviewTotal,R.layout.card_product_payment,this, (ListMenuAdapterPayment.EventCustomer) this,app_db);
        recycler_review.setAdapter(listMenuAdapter);

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog;

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(PaymentActivity.this);
                View mView=getLayoutInflater().inflate(R.layout.dialog_add_service,null);

                Button no_accept = mView.findViewById(R.id.btn_noaccept);
                Button accept = mView.findViewById(R.id.btn_accept);

                mBuilder.setView(mView);

                dialog = mBuilder.create();
                dialog.setTitle("");
                dialog.show();

                no_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        orders.setService(0);
                        app_db.ordersDAO().update(orders);
                        tv_service.setText("0");

                        String total_formated = Utils.numberFormat(Math.round(total));
                        tv_total.setText(total_formated);
                        finishOrder(orders,0);

                    }
                });

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        subtotal = total / 1.19f;
                        service  = Math.round(subtotal * 0.10f);
                        int totallocal = Math.round(service + total);
                        orders.setService(total);
                        app_db.ordersDAO().update(orders);

                        String total_service = Utils.numberFormat(Math.round(service));
                        String total_formated = Utils.numberFormat(Math.round(totallocal));

                        tv_service.setText(total_service);
                        tv_total.setText(total_formated);
                        finishOrder(orders,10);
                    }
                });
            }
        });

    }

    private void finishOrder(final Orders orders, final int tip){
        final AlertDialog dialog;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PaymentActivity.this);
        View mView=getLayoutInflater().inflate(R.layout.dialog_close_waiter,null);

        Button accept = mView.findViewById(R.id.btn_accept);

        mBuilder.setView(mView);

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

        dialog = mBuilder.create();
        dialog.getWindow().setLayout(width, height);
        dialog.setTitle("");
        dialog.show();


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishPayment(tip);
            }
        });
    }

    public void redirectLock(){

        Utils.setItem(PaymentActivity.this,"status","finalizado");
        Intent i = new Intent(PaymentActivity.this,LockOrderActivity.class);
        Bundle b = new Bundle();
        b.putString("from","payment");
        b.putString("total",total+"");
        b.putString("subtotal",subtotal+"");
        b.putString("service",service+"");
        i.putExtras(b);
        startActivity(i);
    }

    public void finishPayment(final int tip){

        final Orders orders = app_db.ordersDAO().getOrderCurrent();

        String url=IP+"/api/orders";

        if(Utils.getItem(this,"PRINTER").equals("false")){
            Log.d("JORKE","Without printer");
            orders.setStatus_id(2);
            app_db.ordersDAO().update(orders);
            app_db.ordersDetailDAO().updateFinishOrder(orders.getId());
            cleanImage();

            redirectLock();
        }else {


            stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.d("JORKE-1", response);

                        orders.setStatus_id(2);
                        app_db.ordersDAO().update(orders);
                        app_db.ordersDetailDAO().updateFinishOrder(orders.getId());
                        cleanImage();

                        redirectLock();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("JORKE", error.getMessage() + "");
                            Toast.makeText(PaymentActivity.this, "Problemas con la solicitud", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    //params.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Accept", "application/json");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("order_id", orders.getId() + "");
                    params.put("GratuityPercent", tip + "");
                    params.put("order_pos_id", orders.getOrder_post_id() + "");
                    params.put("service", service + "");
                    params.put("total", (total + service) + "");
                    params.put("subtotal", total + "");
                    /*params.put("detail",getListDetailFormated());*/
                    Log.d("JORKE-SEND-2", params.toString());
                    return params;
                }
            };

            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void cleanImage() {
        Utils.setItem(this,"masa","");
        Utils.setItem(this,"queso","");
        Utils.setItem(this,"salsa","");
        Utils.setItem(this,"topping_1","");
        Utils.setItem(this,"topping_2","");
    }

    @Override
    public void onClickUpdateTotal() {

    }
}
