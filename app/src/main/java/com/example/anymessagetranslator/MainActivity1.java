package com.example.anymessagetranslator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity1 extends AppCompatActivity {

    private Button button;


    EditText mResultEt;
    ImageView mPreviewIv;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String CameraPermission[];
    String storagePermission[];

    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        mResultEt = findViewById(R.id.resultEt);
        mPreviewIv = findViewById(R.id.ImageView_image);


        CameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }
    public void openActivity2()
    {
        Intent intent=new Intent(this,MainActivity2.class);
        //extra
        intent.putExtra("Answer",mResultEt.getText().toString());
        //
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.addImage) {

            showImageImporDialog();

        }

        if (id == R.id.setting) {

            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }


    private void showImageImporDialog() {

        String[] items = {" Camera", "Gallery"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                if (which == 0) {

                    //camera
                    if (!checkCameraPermission()) {

                        // if permission not allowed
                        requestCameraPermission();
                    } else {

                        pickCamera();

                    }

                }

                if (which == 1) {

                    //gallery
                    if (!checkStoragePermission()) {

                        // if permission not allowed
                        requestStoragePermission();
                    } else {

                        pickGallery();

                    }
                }


            }
        });
        dialog.create().show();


    }

    private void pickGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);

    }

    private void pickCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {

        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {

        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;


    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, CameraPermission, CAMERA_REQUEST_CODE);


    }

    private boolean checkCameraPermission() {

        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CAMERA_REQUEST_CODE:

                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {

                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                    }
                }

                break;

            case STORAGE_REQUEST_CODE:

                if (grantResults.length > 0) {


                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {

                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                    }
                }

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_PICK_GALLERY_CODE) {

                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {

                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                Uri resulturi = result.getUri();

                mPreviewIv.setImageURI(resulturi);

                BitmapDrawable bitmapDrawable = (BitmapDrawable)mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if(!recognizer.isOperational()){

                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();


                }

                else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items  =  recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i< items.size();i++)
                    {
                        TextBlock myItems = items.valueAt(i);
                        sb.append(myItems.getValue());
                        sb.append("\n");
                    }

                    mResultEt.setText(sb.toString());

                }
            }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }

        }

    }
}

/*package com.example.anymessagetranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity1 extends AppCompatActivity {

    EditText mResultEt;
    ImageView mPreviewIv;

    Button button;


    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String[] CameraPermission;
    String[] storagePermission;

    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

       mResultEt = findViewById(R.id.resultEt);
        mPreviewIv = findViewById(R.id.ImageView_image);

        CameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openActivity2()
    {
        Intent intent=new Intent(this,MainActivity1.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.addImage) {

            showImageImporDialog();

        }

        if (id == R.id.setting) {

            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }


    private void showImageImporDialog() {

        String[] items = {" Camera", "Gallery"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                if (which == 0) {

                    //camera
                    if (!checkCameraPermission()) {

                        // if permission not allowed
                        requestCameraPermission();
                    } else {

                        pickCamera();

                    }

                }

                if (which == 1) {

                    //gallery
                    if (!checkStoragePermission()) {

                        // if permission not allowed
                        requestStoragePermission();
                    } else {

                        pickGallery();

                    }
                }


            }
        });
        dialog.create().show();


    }

    private void pickGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);

    }

    private void pickCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {

        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {

        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);


    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, CameraPermission, CAMERA_REQUEST_CODE);


    }

    private boolean checkCameraPermission() {

        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CAMERA_REQUEST_CODE:

                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted) {
                        pickCamera();
                    } else {

                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                    }
                }

                break;

            case STORAGE_REQUEST_CODE:

                if (grantResults.length > 0) {


                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {

                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                    }
                }

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_PICK_GALLERY_CODE) {

                assert data != null;
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {

                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                assert result != null;
                Uri resulturi = result.getUri();

                mPreviewIv.setImageURI(resulturi);

                BitmapDrawable bitmapDrawable = (BitmapDrawable)mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if(!recognizer.isOperational()){

                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();


                }

                else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock>  items  =  recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i< items.size();i++)
                    {
                        TextBlock myItems = items.valueAt(i);
                        sb.append(myItems.getValue());
                        sb.append("\n");
                    }

                    mResultEt.setText(sb.toString());

                }
            }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }

        }

    }


}*/