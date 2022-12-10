package com.course.dietapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {
    ImageView imageView;
    Uri uri;
    public Uri image_uri;
<<<<<<< HEAD:dietapp/app/src/main/java/com/course/dietapp/InputActivity.java
    Button bt1;
    RoomDB database;
=======
>>>>>>> parent of a768cd2 (moso2/  myproject_ upload):moso2/app/src/main/java/com/course/moso2/InputActivity.java

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        imageView = findViewById(R.id.imageView);
        Button image_choice = findViewById(R.id.image_choice);
<<<<<<< HEAD:dietapp/app/src/main/java/com/course/dietapp/InputActivity.java
        Button Mapbtn1 = findViewById(R.id.MapBtn1);
        database = RoomDB.getInstance(this);

=======
>>>>>>> parent of a768cd2 (moso2/  myproject_ upload):moso2/app/src/main/java/com/course/moso2/InputActivity.java
        image_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Content_Main","onClick:-----------------");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                activityResultLauncher.launch(intent);

            }
        });
<<<<<<< HEAD:dietapp/app/src/main/java/com/course/dietapp/InputActivity.java

        Mapbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //화면전환
                Intent intent = new Intent(InputActivity.this, MapsActivity.class);
                startActivity(intent);
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
=======
>>>>>>> parent of a768cd2 (moso2/  myproject_ upload):moso2/app/src/main/java/com/course/moso2/InputActivity.java
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();
                        Uri uri = intent.getData();
                        image_uri = uri;
                        imageView.setImageURI(uri);
                    }
                }

            }
    );

    public void addInput(View view) {

        FoodItem data = new FoodItem();
        data.setImage(image_uri.toString());
        data.setDate(((EditText) findViewById(R.id.editText6)).getText().toString());
        data.setName(((EditText) findViewById(R.id.editText1)).getText().toString());
        data.setKcal(((EditText) findViewById(R.id.editText3)).getText().toString());
        data.setCount(((EditText) findViewById(R.id.editText2)).getText().toString());
        data.setTime(((EditText) findViewById(R.id.editText4)).getText().toString());
        data.setReview(((EditText) findViewById(R.id.editText5)).getText().toString());
        //위도 경도 추가
        database.mainDao().insert(data);

//        ContentValues addValues = new ContentValues();
//        addValues.put(MyContentProvider.IMAGE, image_uri.toString());
//        addValues.put(MyContentProvider.NAME, ((EditText) findViewById(R.id.editText1)).getText().toString());
//        addValues.put(MyContentProvider.DATE, ((EditText) findViewById(R.id.editText6)).getText().toString());
//        addValues.put(MyContentProvider.KCAL, ((EditText) findViewById(R.id.editText3)).getText().toString());
//        addValues.put(MyContentProvider.COUNT, ((EditText) findViewById(R.id.editText2)).getText().toString());
//        addValues.put(MyContentProvider.TIME, ((EditText) findViewById(R.id.editText4)).getText().toString());
//        addValues.put(MyContentProvider.REVIEW, ((EditText) findViewById(R.id.editText5)).getText().toString());
//        getContentResolver().insert(MyContentProvider.CONTENT_URI, addValues);
        Toast.makeText(getBaseContext(), "Record Added", Toast.LENGTH_LONG).show();
    } // put(항목,값)

}