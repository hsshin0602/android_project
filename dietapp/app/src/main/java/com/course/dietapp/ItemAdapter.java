package com.course.dietapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<FoodItem> mFoodList;
    private Activity context;
    private RoomDB database;

    public ItemAdapter(Activity context, List<FoodItem> mFoodList){
        this.context = context;
        this.mFoodList=mFoodList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem foodItem = mFoodList.get(position);
        database = RoomDB.getInstance(context);
        //화면에 데이터 담기
        holder.setItem(foodItem);

        //아이템 클릭 이벤트
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FoodItem mainData = mFoodList.get(holder.getAdapterPosition());

                final int sID = mainData.getId();
                String sImage= mainData.getImage();
                String sDate = mainData.getDate();
                String sName = mainData.getName();
                String sKcal = mainData.getKcal();
                String sCount = mainData.getCount();
                String sTime = mainData.getTime();
                String sReview = mainData.getReview();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width, height);
                dialog.show();

               // Button bt_update = dialog.findViewById(R.id.bt_update);
                EditText editMultipleText = dialog.findViewById(R.id.dialog_edit_text);
                //ImageView imageView = dialog.findViewById(R.id.imageView2);
                //Uri uri = Uri.parse(sImage);
                //imageView.setImageURI(uri);
                editMultipleText.append( "name: "+ sName+"\n date: "+ sDate + "\n count: " + sCount + "\n kcal: " + sKcal + "\n time: " + sTime + "\n review: "+ sReview + "\n");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView food;
        TextView cal;
        CardView card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            food = itemView.findViewById(R.id.food);
            cal = itemView.findViewById(R.id.kcal);
            card_view = itemView.findViewById(R.id.container);
        }

        public void setItem(FoodItem item){
            String image = item.getImage();
            Uri uri = Uri.parse(image);
            food.setImageURI(uri);
            cal.setText(item.getKcal()+"kcal");
        }
    }
}
