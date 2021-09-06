package com.example.passwordmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText fName,email,pass;
    Button signupBTN;
    TextView newAcc;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        fName=findViewById(R.id.fullName);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        signupBTN=findViewById(R.id.signupBTN);
        newAcc=findViewById(R.id.newAcc);

        fAuth=FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String mail=email.getText().toString().trim();
               String password=pass.getText().toString().trim();

               if(TextUtils.isEmpty(mail)){
                    email.setError("Email is required.");
                    return;
               }
               if(TextUtils.isEmpty(password)){
                   pass.setError("Password is Required");
                   return;
               }
               if(password.length()<6){
                   pass.setError("Password should have more than six characters");
                   return;
               }
               fAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }else{
                            Toast.makeText(Register.this,"Failed to Register."+task.getException(),Toast.LENGTH_SHORT).show();

                        }
                   }
               });
            }
        });

    }

    public void gotoLogin(View view) {
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}