package com.ensias.miola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ensias.miola.Adapter.UserAdapter;
import com.ensias.miola.Model.Etudiant;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListCordinnateur extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navig_view ;
    private CircleImageView nav_profile_img;
    private TextView nav_fullname,nav_email, nav_cne,nav_type;

    private DatabaseReference userRef;

    private RecyclerView recyclerView ;
    private ProgressBar progressBar ;

    private List<Etudiant> UserList ;
    private UserAdapter userAdapter ;

    DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Miola Application");



        drawerLayout=findViewById(R.id.drawerLayout);
        navig_view =findViewById(R.id.navig_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ListCordinnateur.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navig_view.setNavigationItemSelectedListener(this);

        progressBar= findViewById(R.id.progress_bar);
        recyclerView= findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        UserList = new ArrayList<>();
        userAdapter = new UserAdapter(ListCordinnateur.this,UserList);

        recyclerView.setAdapter(userAdapter);


        readEtudiants();



        nav_profile_img=navig_view.getHeaderView(0).findViewById(R.id.user_img);
        nav_fullname=navig_view.getHeaderView(0).findViewById(R.id.user_fullname);
        nav_cne=navig_view.getHeaderView(0).findViewById(R.id.user_cne);
        nav_email=navig_view.getHeaderView(0).findViewById(R.id.user_email);
        nav_type=navig_view.getHeaderView(0).findViewById(R.id.user_type);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users ").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());
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

    private void readProfs() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users ");

        Query query = reference.orderByChild("type").equalTo("Prof");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Etudiant et = dataSnapshot.getValue(Etudiant.class);
                    UserList.add(et);
                }
                userAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if(UserList.isEmpty()){
                    Toast.makeText(ListCordinnateur.this,"pas de Professeurs",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addUsers() {

        Intent intent=new Intent(ListCordinnateur.this,AdminMain.class);
        startActivity(intent);

    }

  public void readEtudiants() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users ");

        Query query = reference.orderByChild("type").equalTo("Etudiant");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Etudiant et = dataSnapshot.getValue(Etudiant.class);
                    UserList.add(et);
                }
                userAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if(UserList.isEmpty()){
                    Toast.makeText(ListCordinnateur.this,"pas d' Etudiant",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
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
            Intent intent1=new Intent(ListCordinnateur.this,LoginActivity.class );
            startActivity(intent1);
            finish();
        }

        if (item.getItemId() ==R.id.profil){
            Intent intent = new Intent(ListCordinnateur.this,ProfileActivity.class);
            startActivity(intent);

        }

        if (item.getItemId() ==R.id.about){
            Intent intent = new Intent(ListCordinnateur.this,HelpActivity.class);
            startActivity(intent);

        }

        if (item.getItemId() ==R.id.list_profs){

            readProfs();
        }


        if (item.getItemId() ==R.id.emploie_Temps){

            Intent intent = new Intent(ListCordinnateur.this,EmploiActivity.class);
            intent.putExtra("urlPdf","https://drive.google.com/file/d/1JAc_MaUWfzHUBiBBvZ6pk3RNckPjpMMb/view?usp=sharing");
            startActivity(intent);


        }



        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

