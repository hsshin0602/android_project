package com.course.dietapp;

import static androidx.core.content.FileProvider.getUriForFile;

import static com.course.dietapp.MainActivity.REQUEST_CODE;

import android.app.Activity;
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
        Intent intent=getIntent();
        String date = intent.getStringExtra("date");
        TextView date_bt = (TextView) findViewById(R.id.showDate);
        date_bt.setText(date);

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
                addInput();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                setResult(Activity.RESULT_OK,intent);
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

    public void addInput() {
        int kcal;
        String count=((EditText) findViewById(R.id.editText2)).getText().toString();
        String name=((EditText) findViewById(R.id.editText1)).getText().toString();
        if(name.equals("chicken")){
           kcal=Integer.parseInt(count)*406;
        }
        else if(name.equals("sweet potato")){
            kcal=Integer.parseInt(count)*200;
        }
        else if(name.equals("pizza")){
            kcal=Integer.parseInt(count)*530;
        }
        else if(name.equals("egg")){
            kcal=Integer.parseInt(count)*70;
        }
        else if(name.equals("gogi")){
            kcal=Integer.parseInt(count)*350;
        }
        else{
            kcal=Integer.parseInt(count)*100;
        }

        FoodItem data = new FoodItem();
        data.setImage(currentPhotoPath);
        data.setDate(((TextView) findViewById(R.id.showDate)).getText().toString());
        data.setName(name);
        data.setKcal(Integer.toString(kcal));
        data.setCount(count);
        data.setTime(((EditText) findViewById(R.id.editText4)).getText().toString());
        data.setReview(((EditText) findViewById(R.id.editText5)).getText().toString());
        data.setPosition(position);

        database.mainDao().insert(data);
        Toast.makeText(getBaseContext(), "저장되었습니다", Toast.LENGTH_LONG).show();
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