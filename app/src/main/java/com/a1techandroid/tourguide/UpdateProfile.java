package com.a1techandroid.tourguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfile extends AppCompatActivity {
    EditText nameEt, emailEt, phoneEt, cityEt;
    Button update;
    CircleImageView circleImageView;
    private Uri mImageUri = null;


    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private FirebaseAuth mAuth;
    private StorageReference mFirebaseStorage;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("UserProfile");
        mFirebaseStorage = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);
        initViews();
        setUpClick();

    }

    public void initViews(){
        nameEt = findViewById(R.id.name);
        emailEt = findViewById(R.id.email);
        phoneEt = findViewById(R.id.phone);
        cityEt = findViewById(R.id.city);
        circleImageView = findViewById(R.id.ProfilePic);

        update=findViewById(R.id.add_user);
    }

    public void setUpClick(){
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1);

            }
        });

//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mProgressDialog.setTitle("Updating Account...");
//                mProgressDialog.setMessage("Please wait...");
//                mProgressDialog.show();
//                final StorageReference imagePath = mFirebaseStorage.child("Profile_Pics")
//                        .child(mImageUri.getLastPathSegment());
//                imagePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                Uri donwloadUrl = uri;
//                                String imageUrl = donwloadUrl.toString();
//                                UserModel officers = new UserModel(nameEt.getText().toString(), emailEt.getText().toString(), phoneEt.getText().toString(), cityEt.getText().toString(), imageUrl);
//                                mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                        .setValue(officers).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(UpdateProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
//                                            finish();
//                                            mProgressDialog.hide();
//                                        } else {
//                                            Toast.makeText(UpdateProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                                            mProgressDialog.hide();
//                                        }
//                                    }
//                                });
//
//
//                            }
//                        });
//                    }
//                });            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri mImageUri = data.getData();
            circleImageView.setImageURI(mImageUri);
            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri = result.getUri();
                circleImageView.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
