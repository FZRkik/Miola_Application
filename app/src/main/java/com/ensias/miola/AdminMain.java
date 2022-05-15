package com.ensias.miola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMain extends AppCompatActivity  implements  NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navig_view ;
    private CircleImageView nav_profile_img;
    private TextView nav_fullname,nav_email, nav_cne,nav_type;


    private Button AddE_btn, addP_btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Miola Application");



        drawerLayout=findViewById(R.id.drawerLayout);
        navig_view =findViewById(R.id.navig_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AdminMain.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navig_view.setNavigationItemSelectedListener(this);

       // progressBar= findViewById(R.id.progress_bar);

        addP_btn=findViewById(R.id.addP_btn);
        addP_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminMain.this,ProfRegisterActivity.class);
                startActivity(intent);
            }
        });


        AddE_btn=findViewById(R.id.addE_btn);
        AddE_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminMain.this,StudentRegisterActivity.class);
                startActivity(intent);
            }
        });

        nav_profile_img=navig_view.getHeaderView(0).findViewById(R.id.user_img);
        nav_fullname=navig_view.getHeaderView(0).findViewById(R.id.user_fullname);
        nav_cne=navig_view.getHeaderView(0).findViewById(R.id.user_cne);
        nav_email=navig_view.getHeaderView(0).findViewById(R.id.user_email);
        nav_type=navig_view.getHeaderView(0).findViewById(R.id.user_type);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users ").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String n=snapshot.child("fullname").getValue().toString();
                    nav_fullname.setText(n);

                    String e=snapshot.child("mail").getValue().toString();
                    nav_email.setText(e);

                    String c=snapshot.child("cne").getValue().toString();
                    nav_cne.setText(c);
                    String t=snapshot.child("type").getValue().toString();
                    nav_type.setText(t);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() ==R.id.log_out){
            FirebaseAuth.getInstance().signOut();
            Intent intent1=new Intent(AdminMain.this,LoginActivity.class );
            startActivity(intent1);
            finish();
        }

        if (item.getItemId() ==R.id.profil){
            Intent intent = new Intent(AdminMain.this,ProfileActivity.class);
            startActivity(intent);

        }

        if (item.getItemId() ==R.id.list_etudiants){

            Intent intent = new Intent(AdminMain.this, ListCordinnateur.class);
            startActivity(intent);
        }





        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

