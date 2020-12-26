package com.a1techandroid.tourguide;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;

import com.a1techandroid.tourguide.CustomClasses.Commons;
import com.a1techandroid.tourguide.CustomClasses.CustomDialog;
import com.a1techandroid.tourguide.CustomClasses.Prefrences;
import com.a1techandroid.tourguide.Models.NotificationModel;
import com.a1techandroid.tourguide.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivityNew extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView register, ForgotPassword;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    CustomDialog customDialog;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference mRefe3;
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
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        mRefe3 = database.getReference("Notification");

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
                Intent intent = new Intent(getApplicationContext(), SignupOptionsActivity.class);
                startActivity(intent);
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityNew.this, PWresetActivity.class));
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
                                reference.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".","")).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        UserModel userModel = snapshot.getValue(UserModel.class);
                                        Prefrences.saveUSer(userModel, getApplicationContext());
                                        Intent intent = new Intent(LoginActivityNew.this, MainNewActivity.class);
                                        mRefe3.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                NotificationModel model = snapshot.getValue(NotificationModel.class);
                                                if (model.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                                    if (model.getStatus().equals("Approved")){
                                                        Commons.testMessage(getApplicationContext(), Prefrences.getUser(getApplicationContext()).getName()+" Your "+ model.getType() +"Booking Request Submitted");
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }}
                    }
                });
    }



}
