package com.a1techandroid.tourguide;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.a1techandroid.tourguide.Adapter.VisitPlacesAdapter;
import com.a1techandroid.tourguide.CustomClasses.Prefrences;
import com.a1techandroid.tourguide.Models.PlaneModel;
import com.a1techandroid.tourguide.Models.UserModel;
import com.a1techandroid.tourguide.Models.VisitPlaces;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfile extends AppCompatActivity {
    EditText nameEt, emailEt, phoneEt, cityEt;
    Button update;
    CircleImageView circleImageView;
    private Uri mImageUri = null;
    UserModel userModel;
    String profileUrl = "";
    ProgressDialog progressDialog;
    boolean isProfileImage = true;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private DatabaseReference mRefe2;

    private FirebaseAuth mAuth;
    private StorageReference mFirebaseStorage;
    ListView listView;
    ImageView add;
    ImageView imageView;
    String placeUrl = "";
    VisitPlacesAdapter visitPlacesAdapter;
    ArrayList<VisitPlaces> listPlaces = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("Users");
        mRefe2 = mDatabase.getReference("Visit_Places");
        mFirebaseStorage = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        initViews();
        setUpClick();
        getprofile();
        getVisitPlaces();


    }

    public void initViews(){
        nameEt = findViewById(R.id.name);
        emailEt = findViewById(R.id.email);
        phoneEt = findViewById(R.id.phone);
        cityEt = findViewById(R.id.city);
        circleImageView = findViewById(R.id.ProfilePic);
        listView= findViewById(R.id.listView);
        add = findViewById(R.id.add);

        update=findViewById(R.id.add_user);
    }

    public void setUpClick(){
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isProfileImage = true;
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1);

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            uploadPlace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (isProfileImage){
                Uri mImageUri = data.getData();
                circleImageView.setImageURI(mImageUri);
                final StorageReference imagePath = mFirebaseStorage.child("profile_pic")
                        .child(mImageUri.getLastPathSegment());
                progressDialog.setMessage("Uploading...");
                progressDialog.show();
                imagePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri donwloadUrl = uri;
                                profileUrl = donwloadUrl.toString();
                                progressDialog.hide();
                                String imageUrl = donwloadUrl.toString();
                                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }else {
                Uri mImageUri = data.getData();
                imageView.setImageURI(mImageUri);
                final StorageReference imagePath = mFirebaseStorage.child("visit_places")
                        .child(mImageUri.getLastPathSegment());
                progressDialog.setMessage("Uploading...");
                progressDialog.show();
                imagePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri donwloadUrl = uri;
                                placeUrl = donwloadUrl.toString();
                                progressDialog.hide();
                                String imageUrl = donwloadUrl.toString();
                                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

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

    public void getprofile() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ""));
        applesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);
                if (model != null){
                    nameEt.setText(model.getName());
                    emailEt.setText(model.getEmail());
                    phoneEt.setText(model.getPhone());

                    if (model.getProfileImage() != "") {
                        Glide.with(getApplicationContext())
                                .load(model.getProfileImage())
                                .centerCrop()
                                .into(circleImageView);
                    }
                }

//                Map<String, Object> postValues = new HashMap<String, Object>();
//                postValues.put("profileStatus", "Approved");
//                model.setProfileStatus("Approved");
//                reference1.child(model.getEmail().replace(".", "")).updateChildren(postValues);
//                Prefrences.saveUSer(model, getActivity());

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void update() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ""));
        applesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);
                Map<String, Object> postValues = new HashMap<String, Object>();
                postValues.put("name", nameEt.getText().toString());
                postValues.put("phone", phoneEt.getText().toString());
                postValues.put("profileImage", profileUrl);

                mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".","")).updateChildren(postValues);
//                Map<String, Object> postValues = new HashMap<String, Object>();
//                postValues.put("profileStatus", "Approved");
//                model.setProfileStatus("Approved");
//                reference1.child(model.getEmail().replace(".", "")).updateChildren(postValues);
//                Prefrences.saveUSer(model, getActivity());

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void uploadPlace(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final Dialog dialog = new Dialog(UpdateProfile.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.visit_place);

        final EditText editText = (EditText) dialog.findViewById(R.id.description);
         imageView = (ImageView) dialog.findViewById(R.id.placeimage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isProfileImage = false;
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1);
            }
        });
//        editText.setText(user.get);
        Button btnSave  = (Button) dialog.findViewById(R.id.add);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (editText.getText().toString() == ""  && placeUrl == ""){
                Toast.makeText(UpdateProfile.this, "Please Attach Image and fill Description", Toast.LENGTH_SHORT).show();
            }else {
                VisitPlaces visitPlaces = new VisitPlaces(editText.getText().toString(), placeUrl);
                mRefe2.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".","")).child(mRefe2.push().getKey()).setValue(visitPlaces);
                dialog.dismiss();
            }
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

    public void getVisitPlaces(){
    mRefe2.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                VisitPlaces planeModel = snapshot1.getValue(VisitPlaces.class);
                listPlaces.add(planeModel);
                visitPlacesAdapter = new VisitPlacesAdapter(getApplicationContext(), listPlaces);
                listView.setAdapter(visitPlacesAdapter);
                visitPlacesAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {


            VisitPlaces planeModel = snapshot.getValue(VisitPlaces.class);

            listPlaces.remove(planeModel);
            visitPlacesAdapter = new VisitPlacesAdapter(getApplicationContext(), listPlaces);
            listView.setAdapter(visitPlacesAdapter);
            visitPlacesAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }


}
