package com.jorgepinedo.faivpizza.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.room.Room;

import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.Models.Review;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    public static final String MY_PREFS_NAME="faivs";
    //public static final String IP = "http://192.168.1.2";
    public static final String IP = "http://192.168.1.6";
    //public static final String IP = "http://192.168.1.3:8000";
    public static SharedPreferences prefs;
    public static SharedPreferences.Editor editor;

    public static App newInstanceDB(Activity activity){
        return Room.databaseBuilder(activity,App.class,"spad-five")
                .allowMainThreadQueries()
                .build();
    }

    public static void setItem(Activity activity,String key,String value){
        editor = activity.getSharedPreferences(Utils.MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static String getItem(Activity activity,String key){
        prefs = activity.getSharedPreferences(Utils.MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(key, "");
    }


    public static List<Review> joinAditionals(List<Review> list,App app_db){
        String products="";
        int pos_id=0;
        float subtototal=0;
        int price=0;

        List<Review> list_products = new ArrayList<>();
        String titles="";

        for (Review row:list){
            Map result = joinTitleProduct(app_db.ordersDetailDAO().getChild(row.getId()));

            pos_id = row.getPos_id();
            subtototal =row.getSubtotal()+ Integer.parseInt(String.valueOf(result.get("subtotal")));
            price = Integer.parseInt(String.valueOf(result.get("price"))) + (int)row.getPrice();
            list_products.add(new Review(row.getId(),"Pizza: " +row.getTitle()+", "+ result.get("titles"),pos_id,price,
                    row.getQuantity(),
                    subtototal,row.getStatus_id()
            ));
        }

        return list_products;
    }

    public static Map joinTitleProduct(List<Review> list){
        Map<String,String> result = new HashMap();
        String products="";
        int subtototal=0,pos_id=0,price=0;

        for (Review row:list){
            products+=(products.isEmpty())?"":",";
            products+=" "+row.getTitle();
            pos_id = row.getPos_id();
            subtototal+=row.getSubtotal();
            price+=row.getPrice();
        }

        result.put("price",price+"");
        result.put("subtotal",subtototal+"");
        result.put("titles",products);
        return result;
        //return new Review(1,"Pizza: " + products,pos_id,0,1,subtototal);
    }

    public static String numberFormat(double number){
        DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
        simbolo.setDecimalSeparator(',');
        simbolo.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("###,###.##",simbolo);

        //DecimalFormat formatter = new DecimalFormat("###,###,##0.00");

        return formatter.format(Double.parseDouble(String.valueOf(number)));
    }

    public static boolean getStateNetworking(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork !=null && activeNetwork.isConnected();
    }

}
