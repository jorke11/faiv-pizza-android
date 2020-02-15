package com.jorgepinedo.faivpizza;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.jorgepinedo.faivpizza.Contents.ReviewFragment;
import com.jorgepinedo.faivpizza.Contents.ToppingFragment;
import com.jorgepinedo.faivpizza.Contents.QuesoFragment;
import com.jorgepinedo.faivpizza.Contents.OneFragment;
import com.jorgepinedo.faivpizza.Contents.DrinkFragment;
import com.jorgepinedo.faivpizza.Contents.SalsaFragment;
import com.jorgepinedo.faivpizza.Contents.MasaFragment;
import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.Models.Orders;
import com.jorgepinedo.faivpizza.Models.OrdersDetail;
import com.jorgepinedo.faivpizza.Tools.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ToggleButton btn_1,btn_masa,btn_salsa,btn_cheese,btn_toppings,btn_6,btn_menu_review;
    TextView tv_total,tv_subtotal,tv_calorias,tv_grasa,tv_proteinas,tv_carbohidratos;
    App app_db;
    CardView content_pizza,real_content;
    private int mCounter = 0;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle param = getIntent().getExtras();

        app_db = Utils.newInstanceDB(this);

        final ViewGroup rootView = (ViewGroup) findViewById(R.id.main_activity);

        tv_total = findViewById(R.id.tv_total);
        tv_subtotal = findViewById(R.id.tv_subtotal);
        content_pizza = findViewById(R.id.content_pizza);
        real_content = findViewById(R.id.real_content);
        tv_calorias = findViewById(R.id.tv_calorias);
        tv_grasa = findViewById(R.id.tv_grasa);
        tv_proteinas = findViewById(R.id.tv_proteinas);
        tv_carbohidratos = findViewById(R.id.tv_carbohidratos);

        /*final Transition transition = new Slide(Gravity.RIGHT);
        transition.setDuration(400);
        transition.addTarget(R.id.real_content);*/

        content_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Transition transition = new Slide(Gravity.RIGHT);
                transition.setDuration(400);
                transition.addTarget(R.id.real_content);
                TransitionManager.beginDelayedTransition((ViewGroup) rootView , transition);*/
                real_content.setVisibility(View.VISIBLE);
                real_content.bringToFront();
            }
        });

        real_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Transition transition = new Slide(Gravity.RIGHT);
                transition.setDuration(400);
                transition.addTarget(R.id.real_content);
                TransitionManager.beginDelayedTransition((ViewGroup) rootView, transition);*/
                real_content.setVisibility(View.GONE);
                real_content.bringToFront();
            }
        });


        btn_1 = findViewById(R.id.btn_1);
        btn_masa= findViewById(R.id.btn_massa);
        btn_salsa = findViewById(R.id.btn_salsa);
        btn_cheese = findViewById(R.id.btn_cheese);
        btn_toppings = findViewById(R.id.btn_toppings);
        btn_6 = findViewById(R.id.btn_6);
        btn_menu_review = findViewById(R.id.btn_menu_review);

        btn_1.setOnClickListener(this);
        btn_masa.setOnClickListener(this);
        btn_salsa.setOnClickListener(this);
        btn_cheese.setOnClickListener(this);
        btn_toppings.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_menu_review.setOnClickListener(this);

        Fragment fragment = new OneFragment();

        enableBtnsOne();


        Orders order = app_db.ordersDAO().getOrderCurrent();

        if(order==null){
            app_db.ordersDAO().insert(new Orders());
            order = app_db.ordersDAO().getOrderCurrent();
        }

        if(param!=null){
            newRequest();
            fragment = new MasaFragment();
            enableBtnsTwo();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();

        loadTotal();
        initButton();
    }


    private Runnable mResetCounter = new Runnable() {
        @Override
        public void run() {

            mCounter = 0;
            startActivity(new Intent(MainActivity.this,LockActivity.class));
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mCounter == 0)
                    mHandler.postDelayed(mResetCounter, 3000);

                mCounter++;

                if (mCounter == 5){
                    mHandler.removeCallbacks(mResetCounter);
                    mCounter = 0;
                    Toast.makeText(this, "Five taps in three seconds", Toast.LENGTH_SHORT).show();
                }
                return false;
            case MotionEvent.ACTION_UP:
                mCounter=0;
                mHandler.removeCallbacks(mResetCounter);
                return false;
            case MotionEvent.ACTION_CANCEL:
                mCounter=0;
                mHandler.removeCallbacks(mResetCounter);
                return false;

            default :
                return super.onTouchEvent(event);
        }
    }

    public void initButton(){
        btn_masa.setEnabled(false);
        btn_salsa.setEnabled(false);
        btn_cheese.setEnabled(false);
        //btn_toppings.setEnabled(false);
    }

    public void newRequest(){
        app_db.ordersDetailDAO().updateNewRequest();

        Utils.setItem(this,"masa","");
        Utils.setItem(this,"queso","");
        Utils.setItem(this,"salsa","");
        Utils.setItem(this,"topping_1","");
        Utils.setItem(this,"topping_2","");
    }

    public void hideInformation(boolean status){
        if(status){
            real_content.setVisibility(View.GONE);
            content_pizza.setVisibility(View.GONE);
        }else{
            real_content.setVisibility(View.GONE);
            content_pizza.setVisibility(View.VISIBLE);
            content_pizza.bringToFront();

            int grams = app_db.ordersDetailDAO().getGrams();

            tv_calorias.setText("0");
            tv_grasa.setText("0");
            tv_proteinas.setText("0");
            tv_carbohidratos.setText("0");
        }
    }


    @Override
    public void onClick(View v) {

        Fragment fragment = null;
        boolean change = false;

        OrdersDetail ordersDetail=null;

        switch (v.getId()){
            case R.id.btn_1:
                enableBtnsTwo();
                fragment = new OneFragment();
                break;
            case R.id.btn_massa:
                enableBtnsTwo();
                fragment = new MasaFragment();
                break;
            case R.id.btn_salsa:
                enableBtnsThird();
                fragment = new SalsaFragment();
                break;
            case R.id.btn_cheese:

                //ordersDetail =  app_db.ordersDetailDAO().getOrdersByCategoryId(2);

                enableBtnsFour();
                fragment = new QuesoFragment();

                /*if(ordersDetail!=null){
                    enableBtnsFour();
                    fragment = new QuesoFragment();
                    change = true;
                }*/

                break;
            case R.id.btn_toppings:
                enableBtnsFive();
                fragment = new ToppingFragment();
                break;
            case R.id.btn_6:
               enableBtnsSix();
                fragment = new DrinkFragment();
                change = true;
                break;
            case R.id.btn_menu_review:
               enableBtnReview();
                fragment = new ReviewFragment();
                break;
        }

        chageFragment(fragment);
    }

    public void loadTotal(){

        String formattedNumber = Utils.numberFormat(app_db.ordersDetailDAO().getTotal(new int[]{1}));
        tv_subtotal.setText(""+formattedNumber);

        String totalformattedNumber = Utils.numberFormat(app_db.ordersDetailDAO().getTotal(new int[]{1,2,3}));
        tv_total.setText(""+totalformattedNumber );

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTotal();
    }

    public void chageFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    public void enableBtnsOne(){
        btn_1.setChecked(true);
        btn_masa.setChecked(false);
        btn_salsa.setChecked(false);
        btn_cheese.setChecked(false);
        btn_toppings.setChecked(false);
        btn_6.setChecked(false);
        btn_menu_review.setChecked(false);
    }

    //Masas
    public void enableBtnsTwo(){
        btn_masa.setChecked(true);
        btn_salsa.setChecked(false);
        btn_cheese.setChecked(false);
        btn_toppings.setChecked(false);
        btn_6.setChecked(false);
        btn_menu_review.setChecked(false);
    }

    //Sauce
    public void enableBtnsThird(){
        btn_1.setChecked(true);
        btn_masa.setChecked(true);
        btn_masa.setEnabled(true);
        btn_salsa.setChecked(true);
        btn_salsa.setEnabled(true);
        btn_cheese.setChecked(false);
        btn_toppings.setChecked(false);
        btn_6.setChecked(false);
        btn_menu_review.setChecked(false);
    }

    //Cheese
    public void enableBtnsFour(){
        btn_1.setChecked(true);
        btn_masa.setChecked(true);
        btn_salsa.setChecked(true);
        btn_cheese.setEnabled(true);
        btn_cheese.setChecked(true);
        btn_toppings.setChecked(false);
        btn_toppings.setEnabled(true);
        btn_6.setChecked(false);
        btn_menu_review.setChecked(false);
    }

    //Toppigns
    public void enableBtnsFive(){
        btn_1.setChecked(true);
        btn_masa.setChecked(true);
        btn_salsa.setChecked(true);
        btn_cheese.setChecked(true);
        btn_toppings.setChecked(true);
        btn_6.setChecked(false);
        btn_menu_review.setChecked(false);
    }

    public void enableBtnsSix(){
        btn_1.setChecked(true);
        btn_masa.setChecked(true);
        btn_salsa.setChecked(true);
        btn_cheese.setChecked(true);
        btn_toppings.setChecked(true);
        btn_6.setChecked(true);
        btn_menu_review.setChecked(false);
    }

    public void enableBtnReview(){
        btn_masa.setChecked(false);
        btn_salsa.setChecked(false);
        btn_cheese.setChecked(false);
        btn_toppings.setChecked(false);
        btn_6.setChecked(false);
        btn_menu_review.setChecked(true);
    }
}
