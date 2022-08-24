//package com.example.scholarshiptracker;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//
//public class ScholarshipItemAdapter extends RecyclerView.Adapter<ScholarshipItemAdapter.MyViewHolder> {
//
//    Context context;
//    ArrayList<ScholarshipItem> scholarshipItems;
//
//    public ScholarshipItemAdapter(Context context, ArrayList<ScholarshipItem> scholarshipItems) {
//        this.context = context;
//        this.scholarshipItems = scholarshipItems;
//    }
//
//    @NonNull
//    @NotNull
//    @Override
//    public ScholarshipItemAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//
//        View v = LayoutInflater.from(context).inflate(R.layout.list_of_scholarship, parent, false);
//
//        return new MyViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull @NotNull ScholarshipItemAdapter.MyViewHolder holder, int position) {
//
//        ScholarshipItem scholarshipItem = scholarshipItems.get(position);
//
//        holder.scholarshipName.setText(scholarshipItem.scholarshipName);
//        holder.dueDate.setText(scholarshipItem.dueDate);
//        holder.shortName.setText(scholarshipItem.shortName);
//        Glide.with(context).asBitmap().load(scholarshipItem.get(position).getImageUrl()).into(holder.imageUrl);
////        Glide.with(mContext).asBitmap().load(scholarshipLists.get(position).getImageUrl()).into(holder.scholarship_pic);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return scholarshipItems.size();
//    }
//
//    public static  class MyViewHolder extends RecyclerView.ViewHolder{
//
//        TextView scholarshipName, dueDate, shortName;
//        ImageView imageUrl;
//
//        public MyViewHolder(@NonNull @NotNull View itemView) {
//            super(itemView);
//            scholarshipName = itemView.findViewById(R.id.scholarship_name);
//            dueDate = itemView.findViewById(R.id.scholarship_date);
//            shortName = itemView.findViewById(R.id.scholarship_shortDesc);
//            imageUrl = itemView.findViewById(R.id.scholarshipImage);
//        }
//    }
//}
