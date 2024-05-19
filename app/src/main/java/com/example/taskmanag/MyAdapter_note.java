package com.example.taskmanag;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
public class MyAdapter_note extends RecyclerView.Adapter<MyAdapter_note.MyViewHolder>  {
    private NoteActivity activity;
    private List<Note> mlist;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void setFilteredList(List<Note> filteredList){
        this.mlist = filteredList;
        notifyDataSetChanged();


    }

    public MyAdapter_note(NoteActivity activity , List<Note> mlist){
        this.activity = activity;
        this.mlist =mlist;
    }
    public void updateData(int position){
        Note item_note =mlist.get(position);
        Bundle bundle =new Bundle();
        bundle.putString("uId", item_note.getId());
        bundle.putString("uTitle",item_note.getTitle());
        bundle.putString("uDesc",item_note.getDesc());
        bundle.putString("uDate",item_note.getDate());
        bundle.putString("uCategory",item_note.getCategory());

        Intent intent = new Intent(activity,AddNote.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);

    }
    public void DeleteData(int position){
        Note item_note =mlist.get(position);
        db.collection("Note").document(item_note.getId()).delete()
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
    public MyAdapter_note.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_event , parent ,false);
        return new MyAdapter_note.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_note.MyViewHolder holder, int position) {
        holder.title.setText(mlist.get(position).getTitle());
        holder.desc.setText(mlist.get(position).getDesc());
        holder.date.setText(mlist.get(position).getDate());
        holder.category.setText(mlist.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title ,desc ,date,category;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            desc =itemView.findViewById(R.id.desc_text);
            date =itemView.findViewById(R.id.date_text);
            category=itemView.findViewById(R.id.category_text);

        }

    }

}
