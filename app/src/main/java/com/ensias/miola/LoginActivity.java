package com.ensias.miola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText loginEmail;
    private TextInputEditText password;
    private TextView help_button;
    private Button login_button ;
    private ProgressDialog loader ;
    private FirebaseAuth mAuth ;
   private FirebaseAuth.AuthStateListener authStateListener ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       mAuth=FirebaseAuth.getInstance();
      authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=mAuth.getCurrentUser();
                if (user != null){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

        loader=new ProgressDialog(this);
        loginEmail=findViewById(R.id.login);
        password=findViewById(R.id.passwd);
        login_button=findViewById(R.id.login_btn);
        help_button=findViewById(R.id.help_text);
        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final String email=loginEmail.getText().toString().trim();
            final String pw=password.getText().toString().trim();
            if (TextUtils.isEmpty(email)){
                loginEmail.setError("Email ne peut pas etre vide!");
            }
            if(TextUtils.isEmpty(pw)){
                password.setError("le mot de passe ne peut pas etre vide!");
            }
            else{
            loader.setMessage("Authentification en cours ...");
            loader.setCanceledOnTouchOutside(false);
            loader.show();

            mAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Vous avez s'authentifier avec succes !",Toast.LENGTH_SHORT).show();


                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                                .child("users ").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String type = snapshot.child("type").getValue().toString();
                                if(type.equals("Cordinnateur")){

                                    Intent intent=new Intent(LoginActivity.this,AdminMain.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {

                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Votre Compte n'existe pas, Veuillez contacter votre Administration",Toast.LENGTH_SHORT).show();
                    }
                    loader.dismiss();
                }
            });

            }


            }
        });


    }

   @Override
    protected void onRestart() {
        super.onRestart();
        mAuth.addAuthStateListener(authStateListener);
    }

   @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}