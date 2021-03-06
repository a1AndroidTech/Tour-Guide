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

import com.a1techandroid.tourguide.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
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

        items22.add("User");
        items22.add("Hotel");
        items22.add("Ticket Agency");
        items22.add("Train");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items22);
        spinner1.setAdapter(adapter);
    }

    public void initViews(){
        name = (EditText) findViewById(R.id.input_firstname_signup);
        confirmPassword = (EditText) findViewById(R.id.input_confirm_pass_signup);
        email = (EditText) findViewById(R.id.input_email_signup);
        phone = (EditText) findViewById(R.id.input_lastname_signup);
        password = (EditText) findViewById(R.id.input_pass_signup);
        login = (TextView) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        nameError = (TextInputLayout) findViewById(R.id.nameError);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        phoneError = (TextInputLayout) findViewById(R.id.phoneError);
        passError = (TextInputLayout) findViewById(R.id.passError);
        progressBar= findViewById(R.id.progressBar);
        spinner1=findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);

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
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SetValidation() {
        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            nameError.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else  {
            isNameValid = true;
            nameError.setErrorEnabled(false);
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid phone number.
        if (phone.getText().toString().isEmpty()) {
            phoneError.setError(getResources().getString(R.string.phone_error));
            isPhoneValid = false;
        } else  {
            isPhoneValid = true;
            phoneError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            passError.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
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
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            mProgressDialog.hide();

                        } else {
                            mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        mProgressDialog.hide();
                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                        finish();
                                        mProgressDialog.hide();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                        mProgressDialog.hide();
                                    }
                                }
                            });

                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
         Text = String.valueOf(spinner1.getSelectedItem());

         if (position == 0){
             Text = "User";
         }else if (position == 1){
             Text = "Hotel";

         }else if (position == 2){
             Text = "Ticket Agency";

         }else if (position ==3){
             Text = "Train";

         }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
