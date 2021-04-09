package com.example.sgp.Seller_Section;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class
Sell_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Seller_Dashboard.class));
    }

    private EditText edt_cropName, edt_preferredArea, edt_quantity, edt_price;
    private final String[] w_value = {"0.250 kg", "0.50 kg", "1.000 kg", "2.000 kg", "3.000 kg", "4.000 kg", "5.000 kg", "10.000 kg"};
    private String SpinnerText = "", S_edt_nameValue = "", S_edt_cropName = "", S_edt_preferredArea = "", S_edt_quantity = "", S_edt_price = "";

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button choosebutton;
    private ImageView mImageView;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ProgressBar mProgressBar;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        final Spinner spinner = findViewById(R.id.spinner_weight);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, w_value);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        Button btn_proceedToSell = findViewById(R.id.btn_proceedSell);
        edt_cropName = findViewById(R.id.edt_cropName);
        edt_preferredArea = findViewById(R.id.edt_PreferredArea);
        edt_quantity = findViewById(R.id.edt_quantityValue);
        edt_price = findViewById(R.id.edt_price);

        choosebutton = findViewById(R.id.chooseFileButton);
        mImageView = findViewById(R.id.Upload_imageview);
        mProgressBar = findViewById(R.id.progress_bar);

        //mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        //mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        getName(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        if (getIntent().getBooleanExtra("ifcondition", false)) {

            edt_cropName.append(getIntent().getStringExtra("CropValue"));
            edt_preferredArea.append(getIntent().getStringExtra("PreferredArea"));
            SpinnerText = (getIntent().getStringExtra("WeightPerQuantity"));
            edt_quantity.append(getIntent().getStringExtra("Quantity"));
            edt_price.append(getIntent().getStringExtra("PricePerQuantity"));
            Log.d("Tag", SpinnerText + ":" + edt_cropName.getText().toString() + ":" +
                    edt_preferredArea.getText().toString() + ":" +
                    edt_quantity.getText().toString() + ":" +
                    edt_price.getText().toString());


            for (int i = 0; i < w_value.length; i++) {
                Log.d("Tag", SpinnerText + " " + w_value[i].substring(0, 4));

                if (SpinnerText.compareTo(w_value[i].substring(0, 4)) == 0) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }

        choosebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btn_proceedToSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSetText();
                Intent intent = new Intent(Sell_Activity.this, Sell_Confirm.class);
                intent.putExtra("NameValue", S_edt_nameValue);
                intent.putExtra("CropValue", S_edt_cropName);
                intent.putExtra("PreferredArea", S_edt_preferredArea);
                intent.putExtra("WeightPerQuantity", SpinnerText);
                intent.putExtra("Quantity", S_edt_quantity);
                intent.putExtra("PricePerQuantity", S_edt_price);
                startActivity(intent);

                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Sell_Activity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    //uploadFile();
                }

            }
        });

        spinner.setOnItemSelectedListener(this);
    }

    private void getName(String phno) {

        FirebaseFirestore.getInstance().document("DATA/" + phno)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        S_edt_nameValue = value.get("name").toString();
                        Log.d("Tag", S_edt_nameValue);


                    }
                });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    private void edtSetText() {
        S_edt_cropName = edt_cropName.getText().toString();
        S_edt_preferredArea = edt_preferredArea.getText().toString();
        S_edt_quantity = edt_quantity.getText().toString();
        S_edt_price = edt_price.getText().toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SpinnerText = w_value[i].substring(0, 5);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    private String getFileExtension(Uri uri) {
//
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }
//    private void uploadFile() {
//        if (mImageUri != null) {
//            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
//                    + "." + getFileExtension(mImageUri));
//            mUploadTask = fileReference.putFile(mImageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mProgressBar.setProgress(0);
//                                }
//                            }, 500);
//                            Toast.makeText(Sell_Activity.this, "Upload successful", Toast.LENGTH_LONG).show();
//                            Upload upload = new Upload(edt_cropName.getText().toString().trim(),
//                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
//                            String uploadId = mDatabaseRef.push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(upload);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Sell_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            mProgressBar.setProgress((int) progress);
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
//        }
//    }
}