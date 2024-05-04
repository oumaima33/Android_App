package com.example.taskmanag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
    private ShowActivity activity;
    private List<Model> mlist;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void setFilteredList(List<Model> filteredList){
        this.mlist = filteredList;
        notifyDataSetChanged();


    }

    public MyAdapter(ShowActivity activity , List<Model> mlist){
        this.activity = activity;
        this.mlist =mlist;
    }
    public void updateData(int position){
    Model item =mlist.get(position);
    Bundle bundle =new Bundle();
    bundle.putString("uId", item.getId());
    bundle.putString("uTitle",item.getTitle());
    bundle.putString("uDesc",item.getDesc());
    bundle.putString("uDline",item.getDeadline());
    bundle.putString("uDoc",item.getDoc());
    bundle.putString("uImg",item.getImg());
    Intent intent = new Intent(activity,HomePage.class);
    intent.putExtras(bundle);
    activity.startActivity(intent);

    }
    public void DeleteData(int position){
        Model item =mlist.get(position);
        db.collection("Task").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity,"Data Deleted !!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity,"Error !!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }
    private void notifyRemoved(int position){
        mlist.remove(position);
        notifyItemRemoved(position);
        activity.showData();


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item , parent ,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(mlist.get(position).getTitle());
        holder.desc.setText(mlist.get(position).getDesc());
        holder.deadline.setText(mlist.get(position).getDeadline());
        holder.doc.setText(mlist.get(position).getDoc());
        holder.img.setText(mlist.get(position).getImg());




    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title ,desc ,deadline ,doc,img;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            desc =itemView.findViewById(R.id.desc_text);
            deadline =itemView.findViewById(R.id.dline_text);
            doc =itemView.findViewById(R.id.doc_text);
            img =itemView.findViewById(R.id.img_text);

        }

    }

}
