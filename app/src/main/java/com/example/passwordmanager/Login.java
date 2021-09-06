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

public class Login extends AppCompatActivity {
    EditText email,pass;
    Button loginBTN;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        loginBTN=findViewById(R.id.loginBTN);
        fAuth=FirebaseAuth.getInstance();

        loginBTN.setOnClickListener(new View.OnClickListener() {
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
                //Authentication Of the User

                fAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Successfully Logged In.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        }else {
                            Toast.makeText(Login.this,"Failed to Log In."+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void gotoRegister(View view) {
        startActivity(new Intent(getApplicationContext(),Register.class));
        finish();
    }
}