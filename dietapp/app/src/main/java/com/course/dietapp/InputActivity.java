package com.course.dietapp;

import static androidx.core.content.FileProvider.getUriForFile;

import static com.course.dietapp.MainActivity.REQUEST_CODE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputActivity extends AppCompatActivity {
    ImageView imageView;
    Uri uri;
    Uri image_uri;
    Button bt1;
    RoomDB database;
    String currentPhotoPath;
    private static final int MY_GALLERY_PERMISSION_CODE = 101;
    String lat;
    String lng;
    TextView restaurant_position;
    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        imageView = findViewById(R.id.imageView);
        Button image_choice = findViewById(R.id.image_choice);
        Button Mapbtn1 = findViewById(R.id.MapBtn1);
        database = RoomDB.getInstance(this);
        restaurant_position = (TextView) findViewById(R.id.textView7);

        image_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission("android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{"android.permission.CAMERA"}, MY_GALLERY_PERMISSION_CODE);
                    } else {
                        Log.d("Content_Main","onClick:-----------------");
                        String filename = "photo";
                        File fileImage = null;
                        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        try{
                            fileImage = File.createTempFile(filename,".jpg", storageDirectory);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        currentPhotoPath = fileImage.getAbsolutePath();
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        activityResultLauncher.launch(intent);

                    }
                }

            }
        });


        Mapbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //화면전환
                Intent intent = new Intent(InputActivity.this, MapsActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });


        bt1=findViewById(R.id.save);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInput(view);
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();
                        Uri uri = intent.getData();

                        InputStream in = null;
                        try {
                            in = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(in);
                            in.close();
                            File file = new File(currentPhotoPath);
                            FileOutputStream output = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,output);
                            imageView.setImageURI(Uri.fromFile(file));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
    );

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_GALLERY_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Content_Main","onClick:-----------------");
                String filename = "photo";
                File fileImage = null;
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try{
                    fileImage = File.createTempFile(filename,".jpg", storageDirectory);
                }catch (IOException e){
                    e.printStackTrace();
                }
                currentPhotoPath = fileImage.getAbsolutePath();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        }
    }

    public void addInput(View view) {

        FoodItem data = new FoodItem();
        data.setImage(currentPhotoPath);
        data.setDate(((EditText) findViewById(R.id.editText6)).getText().toString());
        data.setName(((EditText) findViewById(R.id.editText1)).getText().toString());
        data.setKcal(((EditText) findViewById(R.id.editText3)).getText().toString());
        data.setCount(((EditText) findViewById(R.id.editText2)).getText().toString());
        data.setTime(((EditText) findViewById(R.id.editText4)).getText().toString());
        data.setReview(((EditText) findViewById(R.id.editText5)).getText().toString());
        data.setPosition(position);

        //위도 경도 추가
        database.mainDao().insert(data);
        Toast.makeText(getBaseContext(), "Record Added", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 요청 코드에 따라 어떤 화면인지 구별할 수 있다.
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                position = data.getStringExtra("position");
                if(position != null){
                    restaurant_position.setText(position);
                }
            }
        }
    }
}