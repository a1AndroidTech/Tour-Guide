package com.a1techandroid.tourguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.a1techandroid.tourguide.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUpNewActivity extends AppCompatActivity {

    EditText name, email, phone, password, confirmPassword;
    Button register;
    TextView login;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;
    TextInputLayout nameError, emailError, phoneError, passError;
    ProgressBar progressBar;
    private FirebaseAuth auth;
    private ProgressDialog mProgressDialog;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    Spinner spinner1;
    String text, Text;
    ArrayList<String> items22 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_new);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("Users");
        mProgressDialog = new ProgressDialog(this);
        initViews();
        setUpClicks();
    }

    public void initViews(){
        name = (EditText) findViewById(R.id.input_firstname_signup);
        confirmPassword = (EditText) findViewById(R.id.input_confirm_pass_signup);
        email = (EditText) findViewById(R.id.input_email_signup);
        phone = (EditText) findViewById(R.id.input_lastname_signup);
        password = (EditText) findViewById(R.id.input_pass_signup);
        login = (TextView) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
//        nameError = (TextInputLayout) findViewById(R.id.nameError);
//        emailError = (TextInputLayout) findViewById(R.id.emailError);
//        phoneError = (TextInputLayout) findViewById(R.id.phoneError);
//        passError = (TextInputLayout) findViewById(R.id.passError);
//        progressBar= findViewById(R.id.progressBar);

    }

    public void setUpClicks(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivityNew.class);
                startActivity(intent);
            }
        });
    }

    public void SetValidation() {
        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            isNameValid = false;
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content),
                            "Name is Empty", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else  {
            isNameValid = true;
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content),
                            "Email is Empty", Snackbar.LENGTH_LONG);
            snackbar.show();
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content),
                            "Enter Valid Email", Snackbar.LENGTH_LONG);
            snackbar.show();
            isEmailValid = false;
        } else  {
            isEmailValid = true;
        }

        // Check for a valid phone number.
        if (phone.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content),
                            "Phone is Empty", Snackbar.LENGTH_LONG);
            snackbar.show();
            isPhoneValid = false;
        } else  {
            isPhoneValid = true;
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

        if (confirmPassword.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content),
                            "Confirm Password is Empty", Snackbar.LENGTH_LONG);
            snackbar.show();
            isPasswordValid = false;
        } else if (confirmPassword.getText().length() < 6) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content),
                            "Enter Minimum 6 digits", Snackbar.LENGTH_LONG);
            snackbar.show();
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }

        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
            createUserOnServer(email.getText().toString(), password.getText().toString());
            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    public void createUserOnServer(String email, String password){
        com.a1techandroid.tourguide.Models.UserModel userModel = new UserModel("",name.getText().toString(), email, phone.getText().toString(),"","" , Text);
        mProgressDialog.setTitle("Creating Account...");
        mProgressDialog.show();
//        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpNewActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpNewActivity
                                            .this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            mProgressDialog.hide();

                        } else {
                            mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpNewActivity
                                                .this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        mProgressDialog.hide();
                                        startActivity(new Intent(SignUpNewActivity
                                                .this, MainNewActivity.class));
                                        finish();
                                        mProgressDialog.hide();
                                    } else {
                                        Toast.makeText(SignUpNewActivity
                                                .this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                        mProgressDialog.hide();
                                    }
                                }
                            });

                        }
                    }
                });
    }


}


