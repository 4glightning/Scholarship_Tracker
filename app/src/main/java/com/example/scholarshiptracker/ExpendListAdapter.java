package com.example.scholarshiptracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExpendListAdapter extends RecyclerView.Adapter<ViewHolder> {

    ExpendList expendList;
    List<ExpendListItem> expendListItems;
    Context context;

    public ExpendListAdapter(ExpendList expendList, List<ExpendListItem> expendListItems) {
        this.expendList = expendList;
        this.expendListItems = expendListItems;
    }

    public void filterList(List<ExpendListItem> expendFilterlist) {
        // below line is to add our filtered
        // list in our course array list.
        expendListItems = expendFilterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expend_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        //handle item click
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when user click item

                //show data in toast on clicking
                String category = expendListItems.get(position).getCategory();
                String date = expendListItems.get(position).getDate();
                String amount = expendListItems.get(position).getAmount();
                Toast.makeText(expendList.getContext(), category + " in " + date + "\nRM " + amount, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                //this will be called when user long click item

                AlertDialog.Builder builder = new AlertDialog.Builder(expendList.getActivity());
                //option to show in dialog
                String[] options = {"Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //update is clicked
                            //get data
                            String id = expendListItems.get(position).getId();
                            String amount = expendListItems.get(position).getAmount();
                            String category = expendListItems.get(position).getCategory();
                            String date = expendListItems.get(position).getDate();
                            String type = expendListItems.get(position).getType();
                            String comment = expendListItems.get(position).getComment();

                            //intent to start activity
                            Intent intent = new Intent(expendList.getActivity(), ExpenditureInputActivity.class);
                            //put data in intent
                            intent.putExtra("pId", id);
                            intent.putExtra("pAmount", amount);
                            intent.putExtra("pCategory", category);
                            intent.putExtra("pDate", date);
                            intent.putExtra("pType", type);
                            intent.putExtra("pComment", comment);
                            //start activity
                            expendList.startActivity(intent);

                        }
                        if(which == 1){
                            //delete is clicked
                            expendList.deleteData(position);
                        }
                    }
                }).create().show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //bind Views / set data
        holder.category.setText(expendListItems.get(position).getCategory());
        holder.amount.setText(expendListItems.get(position).getAmount());
        holder.type.setText(expendListItems.get(position).getType());
        holder.date.setText(expendListItems.get(position).getDate());
        holder.comment.setText(expendListItems.get(position).getComment());

    }

    @Override
    public int getItemCount() {
        return expendListItems.size();
    }
}
