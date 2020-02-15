package com.jorgepinedo.faivpizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.jorgepinedo.faivpizza.Tools.Utils;

import java.util.List;

public class LockActivity extends AppCompatActivity {

    PatternLockView mPatternLockView;
    String pattern_string="";

    String from="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        Bundle extras = getIntent().getExtras();


        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

        if(extras!=null){
            from = extras.getString("from");
        }

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
                if(Utils.getItem(LockActivity.this,"SECRET_TABLET").equals(PatternLockUtils.patternToString(mPatternLockView, pattern))){
                    startActivity(new Intent(LockActivity.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LockActivity.this,"Patron incorrecto!",Toast.LENGTH_SHORT).show();
                }
            }else{
                if(Utils.getItem(LockActivity.this,"SECRET_ADMIN").equals(PatternLockUtils.patternToString(mPatternLockView, pattern))){
                    startActivity(new Intent(LockActivity.this,AdminActivity.class));
                    finish();
                }else{
                    Toast.makeText(LockActivity.this,"Patron incorrecto!",Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        public void onCleared() {
            Log.d("JORKE", "Pattern has been cleared");
        }
    };
}
