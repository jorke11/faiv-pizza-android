package com.jorgepinedo.faivpizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.Models.Orders;
import com.jorgepinedo.faivpizza.Tools.Utils;

public class FinishActivity extends AppCompatActivity {

    Button btn_payment,other_pizza;

    App app_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        app_db = Utils.newInstanceDB(this);

        btn_payment = findViewById(R.id.btn_payment);
        other_pizza = findViewById(R.id.other_pizza);

        final Orders orders = app_db.ordersDAO().getOrderCurrent();

        other_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_db.ordersDetailDAO().updateNewRequest();
                Intent i = new Intent(FinishActivity.this,MainActivity.class);
                Bundle b = new Bundle();
                b.putString("status","new");
                i.putExtras(b);
                startActivity(i);
            }
        });

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_db.ordersDetailDAO().updateNewRequest();
                startActivity(new Intent(FinishActivity.this,PaymentActivity.class));
            }
        });

    }
}
