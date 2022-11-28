package com.course.androidproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    ArrayList<FoodItem> mFoodList=new ArrayList<FoodItem>();

    public ItemAdapter(ArrayList<FoodItem> mFoodList){
        this.mFoodList=mFoodList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        FoodItem foodItem = mFoodList.get(position);

        //화면에 데이터 담기
        holder.setItem(foodItem);

        //아이템 클릭 이벤트
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int mPostion = holder.getAdapterPosition();

                Context context = view.getContext();

                Intent detailActivity = new Intent(context, DetailActivity.class);

                detailActivity.putExtra("id"    ,mFoodList.get(mPostion).getResourceId()); //아이디
                detailActivity.putExtra("name"  ,mFoodList.get(mPostion).getName()); //이름
                ((MainActivity)context).startActivity(detailActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        CardView card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.food);
            name = itemView.findViewById(R.id.name);
            card_view = itemView.findViewById(R.id.container);
        }

        public void setItem(FoodItem item){
            profile.setImageResource(item.getResourceId());
            name.setText(String.valueOf(item.getName()));
        }
    }
}
