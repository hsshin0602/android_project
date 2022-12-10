//package com.course.dietapp;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class inputDataView extends AppCompatActivity {
//    // image 클릭시 들어가는 곳으로 상세 데이터 보이도록 만들기
//    ImageView imageView;
//    EditText editMultipleText = findViewById(R.id.editText7);
//    String image,name,date,kcal,count,time,review;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_input_data_view);
//        editMultipleText.setText("");
//        imageView = findViewById(R.id.imageView2);
//        //메인엑티비티에서 받아온 데이터
//        Intent intent = getIntent();
//        image = intent.getStringExtra("image");
//        date = intent.getStringExtra("date");
//        name = intent.getStringExtra("name");
//        count = intent.getStringExtra("count");
//        time = intent.getStringExtra("time");
//        review = intent.getStringExtra("review");
//        Uri uri = Uri.parse(image);
//
//        imageView.setImageURI(uri);
//        editMultipleText.append( "\n name: "+ name+"\n date: "+ date + "\n count: " + count
//                + "\n kcal: " + kcal + "\n time: " + time + "\n review: "+ review + "\n");
//
//    }
//}