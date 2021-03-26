package com.jorgepinedo.faivpizza;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jorgepinedo.faivpizza.Database.App;
import com.jorgepinedo.faivpizza.Models.Categories;
import com.jorgepinedo.faivpizza.Models.Products;
import com.jorgepinedo.faivpizza.Tools.Utils;

public class InitSplashActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 3000; // 3 segundos

    App app_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_splash);

        app_db = Utils.newInstanceDB(this);

        initParams();
        saveCategories();
        saveProducts();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(InitSplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            ;
        }, DURACION_SPLASH);
    }

    public void initParams(){

        if(Utils.getItem(InitSplashActivity.this,"PRINTER").equals("")){
            Utils.setItem(InitSplashActivity.this,"PRINTER","true");
        }

        if(Utils.getItem(InitSplashActivity.this,"TABLE").equals("")){
            Utils.setItem(InitSplashActivity.this,"TABLE","1");
        }

        if(Utils.getItem(InitSplashActivity.this,"SECRET_ADMIN").equals("")){
            Utils.setItem(InitSplashActivity.this,"SECRET_ADMIN","03678");
        }

        if(Utils.getItem(InitSplashActivity.this,"SECRET_TABLET").equals("")){
            Utils.setItem(InitSplashActivity.this,"SECRET_TABLET","03678");
        }

        if(Utils.getItem(InitSplashActivity.this,"IP_SERVER").equals("")){
            Utils.setItem(InitSplashActivity.this,"IP_SERVER","http://192.168.1.4");
        }
    }

    public void saveProducts(){
        validateProduct(new Products("Masa Tradicional",500,"masa_tradicional",40,1,1,1,1,22,4,1,109));
        validateProduct(new Products("Masa Gluten free",800,"masa_fit",40,1,2,1,1,18,2,1,88));

        validateProduct(new Products("Queso Mozarella",2800,"queso_mozarella",40,2,3,2,1,0,12,9,133));
        validateProduct(new Products("Queso Almendras",3600,"queso_finesse",40,2,4,2,1,3,7,8,90));

        validateProduct(new Products("Salsa Pomodoro",600,"salsa_pomodoro",30,3,5,2,1,2,1,0,9));
        validateProduct(new Products("Salsa Pesto",600,"salsa_pesto",30,3,6,2,1,1,4,13,132));

        //favorits
        validateProduct(new Products("Prosciutto",4500,"prosciutto",30,4,7,2,1,0,8,2,59));
        validateProduct(new Products("Pollo",1900,"pollo",30,4,8,2,9,0,9,2,59));
        validateProduct(new Products("Champiñon",900,"champinones",20,4,10,2,13,1,1,0,4));
        validateProduct(new Products("Piña",800,"pina",20,4,11,2,3,3,0,0,10));
        validateProduct(new Products("Ciruelas",900,"ciruelas",20,4,15,2,4,2,0,0,9));
        validateProduct(new Products("Tomates secos",3500,"tomate_seco",20,4,16,2,5,8,2,0,45));
        validateProduct(new Products("Queso parmesano",700,"queso_parmessano",10,4,19,2,14,0,4,3,39));


        //protein
        validateProduct(new Products("Pavo",3900,"pavo",30,5,9,2,11,1,5,1,38));
        validateProduct(new Products("Tocineta",1500,"tocineta",20,5,14,2,6,0,7,8,108));
        validateProduct(new Products("Jamon de cerdo",1900,"jamon_cerdo",30,5,17,2,10,1,5,1,36));
        validateProduct(new Products("Pepperoni",2500,"pepperoni",30,5,20,2,7,1,6,12,140));


        //vegetables
        validateProduct(new Products("Rugula",400,"rugula",10,6,13,2,12,0,0,0,3));
        validateProduct(new Products("Aceitunas negras",1000,"aceitunas_negras",20,6,18,2,2,1,0,2,21));
        validateProduct(new Products("Tomate Chonto",600,"tomate_chonto",20,6,12,2,8,1,0,0,4));
        validateProduct(new Products("Cebolla carameliza",800,"cebolla_caramelizada",20,6,21,2,15,7,1,1,38));



        //Bebidas
        validateProduct(new Products("Agua",2000,"agua",10,7,6,1,1,0,0,0,0));
        validateProduct(new Products("Agua con gas",2000,"agua_con_gas",10,7,5,1,1,0,0,0,0));
        validateProduct(new Products("Coca Cola",2000,"coca_cola",10,7,4,1,1,0,0,0,0));
        validateProduct(new Products("Coca Cola Zero",2000,"coca_cola_zero",10,7,3,1,1,0,0,0,0));
        validateProduct(new Products("Coca Cola Light",2000,"coca_light",10,7,2,1,1,0,0,0,0));
        validateProduct(new Products("Sprite",2000,"sprite",10,7,8,1,1,0,0,0,0));
        validateProduct(new Products("Quatro",2000,"quatro",10,7,7,1,1,0,0,0,0));
        validateProduct(new Products("Kola Roman",2000,"kola_roman",10,7,9,1,1,0,0,0,0));

        validateProduct(new Products("Galleta chips de chocolate",1500,"galleta",10,8,10,1,1,0,0,0,0));
    }

    public void validateProduct(Products products){
        Products products1 = app_db.productsDAO().getProductByUrl(products.getUrl());

        if(products1 == null){
            Log.d("JORKE","aca");
            app_db.productsDAO().insert(products);
        }else{
            Log.d("JORKE","else");
            app_db.productsDAO().update(products);
        }
    }

    public void saveCategories(){
        app_db.categoriesDAO().deleteAll();
        app_db.categoriesDAO().insertAll(new Categories(1,"Masa"));
        app_db.categoriesDAO().insertAll(new Categories(2,"Salsa"));
        app_db.categoriesDAO().insertAll(new Categories(3,"Queso"));
        app_db.categoriesDAO().insertAll(new Categories(4,"Topping"));
        app_db.categoriesDAO().insertAll(new Categories(5,"Bedidas"));
    }
}