package com.adgvit.meme_o_mania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText emailLogin;
    private EditText passwordLogin;

    private Button loginButton;
    private LinearLayout signupLayout;
    private TextView forgotTextView;

    public static FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin=findViewById(R.id.emailForgotEditText);
        passwordLogin=findViewById(R.id.passwordLoginEditText);
        loginButton=findViewById(R.id.loginLoginButton);
        signupLayout=findViewById(R.id.signUpLoginLinearLayout);
        forgotTextView=findViewById(R.id.forgotLoginTextView);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null)
        {
            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(intent);
        }

        clickListeners();

    }

    public void clickListeners()
    {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(checkEmpty()){
                    if(checkMail()){
                        firebaseAuth.signInWithEmailAndPassword(emailLogin.getText().toString().trim(), passwordLogin.getText().toString())
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            Intent intent=new Intent(LoginActivity.this,NavigationActivity.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this, "Log In Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else
                    {
                        emailLogin.setError("Please Enter VIT Email");
                        emailLogin.requestFocus();
                    }
                }
            }
        });

        signupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public Boolean checkMail()
    {
        String tempEmail=emailLogin.getText().toString().trim();
        Pattern emailPattern=Pattern.compile("^[a-z]+.[a-z]*[0-9]?201[0-9]@vitstudent.ac.in$");
        Matcher emailMatcher=emailPattern.matcher(tempEmail);
        if(emailMatcher.matches())
        {
            return true;
        }
        return false;
    }

    public Boolean checkEmpty()
    {
        if(emailLogin.getText().length()==0)
        {
            emailLogin.setError("Please enter your email");
            emailLogin.requestFocus();
            return false;
        }
        else if(passwordLogin.getText().length()==0)
        {
            passwordLogin.setError("Please enter your password");
            passwordLogin.requestFocus();
            return false;
        }
        return true;
    }

}
