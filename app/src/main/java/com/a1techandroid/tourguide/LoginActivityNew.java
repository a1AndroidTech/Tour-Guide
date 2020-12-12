package com.a1techandroid.tourguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.a1techandroid.tourguide.CustomClasses.CustomDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivityNew extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView register, ForgotPassword;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    CustomDialog customDialog;
    private FirebaseAuth auth;
    ProgressBar progressBar;
    Button b;
    private ProgressDialog mProgressDialog;
    Spinner spinner1;
    String text, Text;
    ArrayList<String> items22 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        mProgressDialog = new ProgressDialog(LoginActivityNew.this);
        auth = FirebaseAuth.getInstance();
        initViews();
        setUpClicks();

    }

    public void initViews(){
        email = (EditText) findViewById(R.id.input_email_login);
        password = (EditText) findViewById(R.id.input_pass_login);
        login = (Button) findViewById(R.id.ok_login);
        register = (TextView) findViewById(R.id.signup);
        ForgotPassword= findViewById(R.id.forgot);
    }

    public void setUpClicks(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), SignUpNewActivity.class);
                startActivity(intent);
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityNew.this, PWresetActivity.class));
                finish();
            }
        });
    }

    public void SetValidation() {
        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content),
                            "Email is Empty", Snackbar.LENGTH_LONG);
            snackbar.show();            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content),
                            "Enter Valid Email", Snackbar.LENGTH_LONG);
            snackbar.show();
            isEmailValid = false;
        } else  {
            isEmailValid = true;
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content),
                            "Password is Empty", Snackbar.LENGTH_LONG);
            snackbar.show();
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content),
                            "Enter Minimum 6 digits", Snackbar.LENGTH_LONG);
            snackbar.show();
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }

        if (isEmailValid && isPasswordValid) {
            loginUSer(email.getText().toString(), password.getText().toString());
            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    public void loginUSer(String email, String password){
        mProgressDialog.setTitle("Login Account...");
        mProgressDialog.show();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivityNew.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error
                            Toast.makeText(LoginActivityNew.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        } else {
                            mProgressDialog.hide();
                            if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@gmail.com")){
                                Intent intent = new Intent(LoginActivityNew.this, AdminActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Intent intent = new Intent(LoginActivityNew.this, MainNewActivity.class);
                                startActivity(intent);
                                finish();
                            }}
                    }
                });
    }

}
