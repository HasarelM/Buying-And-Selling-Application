package com.dev.hasarelm.buyingselling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.hasarelm.buyingselling.Activity.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView mTvText,mTvCopy;
    private ImageView mImgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mTvText = findViewById(R.id.text_icon_text);
        mImgView = findViewById(R.id.splash_image);
        mTvCopy = findViewById(R.id.text_icon_text_copy);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.my_animation);
        mTvText.startAnimation(animation);
        mImgView.startAnimation(animation);
        mTvCopy.startAnimation(animation);
        final Intent intent = new Intent(this,LoginActivity.class);
        Thread timer = new Thread(){

            public void run(){

                try {
                    sleep(5000);

                }catch (Exception f){

                }finally {
                    startActivity(intent);
                    finish();
                }
            }
        };

        timer.start();
    }
}