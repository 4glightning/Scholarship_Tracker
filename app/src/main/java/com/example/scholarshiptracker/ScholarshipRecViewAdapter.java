package com.example.scholarshiptracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.scholarshiptracker.ScholarshipActivity.SCHOLARSHIP_ID_KEY;


public class ScholarshipRecViewAdapter extends RecyclerView.Adapter<ScholarshipRecViewAdapter.ViewHolder>{
    private static final String TAG = "ScholarshipRecViewAdapt";

    public ArrayList<ScholarshipList> scholarshipLists = new ArrayList<>();
    private Context mContext;
    private String parentActivity;

    public ScholarshipRecViewAdapter(Context mContext, String parentActivity) {
        this.mContext = mContext;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_scholarship, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        Glide.with(mContext).asBitmap().load(scholarshipLists.get(position).getImageUrl()).into(holder.scholarship_pic);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ScholarshipActivity.class);
                intent.putExtra(SCHOLARSHIP_ID_KEY, scholarshipLists.get(position).getSid());
                mContext.startActivity(intent);
            }
        });

        holder.scholarship_name.setText(scholarshipLists.get(position).getSname());
        holder.scholarship_date.setText(scholarshipLists.get(position).getStartDate());
        holder.scholarship_shortDesc.setText(scholarshipLists.get(position).getSshortDesc());

        if (scholarshipLists.get(position).isExpanded()){
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

            if(parentActivity.equals("fragment_scholarship")){
                holder.btnDelete.setVisibility(View.GONE);
            }else if(parentActivity.equals("fragment_already_applied_scholarship")){
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete " + scholarshipLists.get(position).getSname() + "?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(Utils.getInstance(mContext).removeFromAlreadyApplied(scholarshipLists.get(position))){
                                    Toast.makeText(mContext, "Scholarship Removed", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create().show();
                    }
                });
            }


        }else{
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return scholarshipLists.size();
    }

    public void setScholarshipLists(ArrayList<ScholarshipList> scholarshipLists) {
        this.scholarshipLists = scholarshipLists;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView scholarship_pic;

        private ImageView downArrow, upArrow;
        private RelativeLayout expandedRelLayout;
        private TextView scholarship_name, scholarship_date, scholarship_shortDesc;

        private TextView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.scholarshipCardView);
            scholarship_pic = itemView.findViewById(R.id.scholarshipImage);

            downArrow = itemView.findViewById(R.id.btnDownArrow);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            expandedRelLayout = itemView.findViewById(R.id.expandedRelLayout);
            scholarship_name = itemView.findViewById(R.id.scholarship_name);
            scholarship_date = itemView.findViewById(R.id.scholarship_date);
            scholarship_shortDesc = itemView.findViewById(R.id.scholarship_shortDesc);

            btnDelete = itemView.findViewById(R.id.btnDelete);

            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ScholarshipList scholarship = scholarshipLists.get(getAbsoluteAdapterPosition());
                    scholarship.setExpanded(!scholarship.isExpanded());
                    notifyItemChanged(getAbsoluteAdapterPosition());
                }
            });

            upArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ScholarshipList scholarship = scholarshipLists.get(getAbsoluteAdapterPosition());
                    scholarship.setExpanded(!scholarship.isExpanded());
                    notifyItemChanged(getAbsoluteAdapterPosition());
                }
            });

        }
    }
}
