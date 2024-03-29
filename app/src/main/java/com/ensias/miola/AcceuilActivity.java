package com.ensias.miola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class AcceuilActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView  title , slogan;
    Animation topAnimatiom, bottomAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_acceuil);

        logo=findViewById(R.id.logo);
        title=findViewById(R.id.title);
        slogan=findViewById(R.id.slogan);
        topAnimatiom= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo.setAnimation(topAnimatiom);
        title.setAnimation(bottomAnimation);
        slogan.setAnimation(bottomAnimation);

        int Acceuill_Screen= 4300;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(AcceuilActivity.this,LoginActivity.class);
                startActivity(intent) ;
                finish();
            }
        },Acceuill_Screen);


    }
}