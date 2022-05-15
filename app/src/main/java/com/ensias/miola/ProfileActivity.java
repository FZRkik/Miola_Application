package com.ensias.miola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.TransactionTooLargeException;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView type , name, email, dateNaiss, phoneNbr,cne;
    private CircleImageView student_img;
    private Button backBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

            toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Mon Profile");

        type = findViewById(R.id.type);
        name = findViewById(R.id.name);
        dateNaiss= findViewById(R.id.dateNaissance);
        email=  findViewById(R.id.email);
        phoneNbr=  findViewById(R.id.phone_nbr);
        cne=  findViewById(R.id.cne);
        student_img=findViewById(R.id.Img_Student);
        backBtn=  findViewById(R.id.back_btn);

     DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users ").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String n=snapshot.child("fullname").getValue().toString();
                    name.setText(n);
                    System.out.println(n);

                    String d=snapshot.child("date_naissance").getValue().toString();
                    dateNaiss.setText(d);
                    String e=snapshot.child("mail").getValue().toString();
                    email.setText(e);
                    String ph=snapshot.child("phone_nbr").getValue().toString();
                    phoneNbr.setText(ph);
                    String c=snapshot.child("cne").getValue().toString();
                    cne.setText(c);
                    String t=snapshot.child("type").getValue().toString();
                    type.setText(t);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


}