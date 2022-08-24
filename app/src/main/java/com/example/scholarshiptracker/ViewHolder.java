package com.example.scholarshiptracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ViewHolder extends RecyclerView.ViewHolder  {

    TextView category, amount, date, type, comment;
    View mView;

    public ViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAbsoluteAdapterPosition());
            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAbsoluteAdapterPosition());
                return true;
            }
        });

        //initialize views with income_list_item.xml
        category = itemView.findViewById(R.id.category);
        amount = itemView.findViewById(R.id.amount);
        date = itemView.findViewById(R.id.date);
        type = itemView.findViewById(R.id.type);
        comment = itemView.findViewById(R.id.comment);
    }

    private ViewHolder.ClickListener mClickListener;

    //interface for click listener
    public interface  ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
