package com.jorgepinedo.faivpizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.jorgepinedo.faivpizza.Adapters.ListMenuAdapterPayment;
import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.Models.Orders;
import com.jorgepinedo.faivpizza.Models.Review;
import com.jorgepinedo.faivpizza.Tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class LockOrderActivity extends AppCompatActivity implements ListMenuAdapterPayment.EventCustomer{

    PatternLockView mPatternLockView;
    String pattern_string="";

    RecyclerView recycler_review;
    ListMenuAdapterPayment listMenuAdapter;
    List<Review> listReviewMain,listReviewTotal,listReviewDrink;
    App app_db;
    String from="",total,subtotal="",servicio="";
    TextView tv_total,tv_service,tv_subtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_order);

        Bundle extras = getIntent().getExtras();

        app_db = Utils.newInstanceDB(this);

        tv_total = findViewById(R.id.tv_total);
        tv_service = findViewById(R.id.tv_service);
        tv_subtotal = findViewById(R.id.tv_subtotal);


        int[] status={4};

        Orders orders = app_db.ordersDAO().getLastOrder();

        listReviewMain = app_db.ordersDetailDAO().getReviewNotIn(7,status,orders.getId());

        listReviewDrink = app_db.ordersDetailDAO().getReviewIn(7,status,orders.getId());

        listReviewTotal = new ArrayList<>();

        listReviewTotal.addAll(Utils.joinAditionals(listReviewMain,app_db));
        listReviewTotal.addAll(listReviewDrink);

        recycler_review = findViewById(R.id.recycler_review);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_review.setLayoutManager(linearLayoutManager);

        listMenuAdapter = new ListMenuAdapterPayment(listReviewTotal,R.layout.card_product_payment,this,this, app_db);
        recycler_review.setAdapter(listMenuAdapter);

        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

        if(extras!=null){
            from = extras.getString("from");
            servicio = extras.getString("service");
            subtotal = extras.getString("subtotal");
            total = String.valueOf(Float.parseFloat(extras.getString("total")) + Float.parseFloat(servicio));
            tv_total.setText("$"+Utils.numberFormat(Float.parseFloat(total)));
            tv_service.setText("$"+Utils.numberFormat(Float.parseFloat(servicio)));
            tv_subtotal.setText("$"+Utils.numberFormat(Float.parseFloat(extras.getString("total"))));
        }

        Log.d("JORKE",from);
    }

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d("JORKE", "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d("JORKE", "Pattern progress: " + PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            pattern_string = PatternLockUtils.patternToString(mPatternLockView, pattern);

            if(from.equals("payment")){
                if(Utils.getItem(LockOrderActivity.this,"SECRET_TABLET").equals(PatternLockUtils.patternToString(mPatternLockView, pattern))){
                    startActivity(new Intent(LockOrderActivity.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LockOrderActivity.this,"Patron incorrecto!",Toast.LENGTH_SHORT).show();
                }
            }else{
                if(Utils.getItem(LockOrderActivity.this,"SECRET_ADMIN").equals(PatternLockUtils.patternToString(mPatternLockView, pattern))){
                    startActivity(new Intent(LockOrderActivity.this,AdminActivity.class));
                    finish();
                }else{
                    Toast.makeText(LockOrderActivity.this,"Patron incorrecto!",Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        public void onCleared() {
            Log.d("JORKE", "Pattern has been cleared");
        }
    };

    @Override
    public void onClickUpdateTotal() {

    }
}
