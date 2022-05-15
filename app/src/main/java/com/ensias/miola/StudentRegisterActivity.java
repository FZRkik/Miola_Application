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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentRegisterActivity extends AppCompatActivity {

    private CircleImageView RprofilImg;
    private TextInputEditText RfullName;
    private TextInputEditText Rcne ;
    private TextInputEditText  RdateNaiss ;
    private TextInputEditText RphoneNbr ;
    private TextInputEditText Remail;
    private TextInputEditText Rpasswd ;
    private Button registerBtn ;
    private ProgressDialog loader ;
    private FirebaseAuth mAuth ;
    private DatabaseReference userDataBaseRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        RprofilImg=findViewById(R.id.profile_img);
        RfullName=findViewById(R.id.full_name);
        Rcne=findViewById(R.id.cne);
        RdateNaiss=findViewById(R.id.date_naissance);
        RphoneNbr=findViewById(R.id.phone_number);
        Remail=findViewById(R.id.email);
        Rpasswd=findViewById(R.id.passwd);
        registerBtn=findViewById(R.id.register_btn);
        loader=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=Remail.getText().toString().trim();
                final String pw=Rpasswd.getText().toString().trim();
                final String fn=RfullName.getText().toString().trim();
                final String phn=RphoneNbr.getText().toString().trim();
                final String cne=Rcne.getText().toString().trim();
                final String dn=RdateNaiss.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Remail.setError("Entrez un email !");
                    return ;
                }

                if (TextUtils.isEmpty(pw)){
                    Rpasswd.setError("Donnez un mot de passe au étudiant !");
                    return ;
                }

                if (TextUtils.isEmpty(fn)){
                    RfullName.setError("Entrez le nom complet de l'étudiant !");
                    return ;
                }

                if (TextUtils.isEmpty(phn)){
                    RphoneNbr.setError("Entrez un numerode téléphone !");
                    return ;
                }

                if (TextUtils.isEmpty(cne)){
                    Rcne.setError("Entrez un cne !");
                    return ;
                }

                if (TextUtils.isEmpty(dn)){
                    RdateNaiss.setError("Entrez une date de naissance !");
                    return ;
                }

                else{

                    loader.setMessage("Ajout de l'étudiant ...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                String error=task.getException().toString();
                                Toast.makeText(StudentRegisterActivity.this,error,Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserId=mAuth.getCurrentUser().getUid();
                                userDataBaseRef= FirebaseDatabase.getInstance().getReference().child("users ").child(currentUserId);
                                HashMap userInfo =new HashMap();
                                userInfo.put("id",currentUserId);
                                userInfo.put("fullname",fn);
                                userInfo.put("cne",cne);
                                userInfo.put("date_naissance",dn);
                                userInfo.put("mail",email);
                                userInfo.put("pw",pw);
                                userInfo.put("phone_nbr",phn);
                                userInfo.put("type","Etudiant");

                                userDataBaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                     if(task.isSuccessful()){
                                         Toast.makeText(StudentRegisterActivity.this,"L'étudiant est ajouté avec succes",Toast.LENGTH_SHORT).show();
                                     }
                                     else {
                                         Toast.makeText(StudentRegisterActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                     }
                                     finish();
                                    }
                                });

                                Intent intent=new Intent(StudentRegisterActivity.this , HelpActivity.class);
                                startActivity(intent);
                                finish();

                                loader.dismiss();


                            }
                        }
                    });

                }

            }
        });



    }
}