package com.example.taskmanag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class AddEvent extends AppCompatActivity {

    private EditText mTitle,mDesc,mdate;
    private Button mSaveBtn,mShowBtn;
    private FirebaseFirestore db;
    private String uTitle,uDesc,uDate,uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        mTitle=findViewById(R.id.edit_title);
        mDesc=findViewById(R.id.edit_desc);
        mdate=findViewById(R.id.edit_date);
        mSaveBtn=findViewById(R.id.save_btn);
        mShowBtn=findViewById(R.id.show_btn);
        db=FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mSaveBtn.setText("Update");
            uTitle=bundle.getString("uTitle");
            uDesc=bundle.getString("uDesc");
            uDate=bundle.getString("uDate");
            uId=bundle.getString("uId");
            mTitle.setText(uTitle);
            mDesc.setText(uDesc);
            mdate.setText(uDate);

        }else {

            mSaveBtn.setText("Save");
        }

        mShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddEvent.this,EventActivity.class));
            }
        });



        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=mTitle.getText().toString();
                String desc=mDesc.getText().toString();
                String date=mdate.getText().toString();


                Bundle bundle1=getIntent().getExtras();
                if (bundle != null){
                    String id = uId.toString();
                    updateToFireStore(id,title,desc,date);
                }else {
                    String id= UUID.randomUUID().toString();
                    saveToFireStore(id,title,desc,date);
                }


            }
        });
    }
    private void updateToFireStore(String id,String title, String desc,String date)
    {
        db.collection("Event").document(id).update("title",title,"Desc",desc,"date",date)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddEvent.this,"Data Updated",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddEvent.this,"Error :"+ task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddEvent.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void saveToFireStore(String id,String title, String desc,String date){
        if(!title.isEmpty() && !desc.isEmpty() && !date.isEmpty()){
            HashMap<String , Object> map=new HashMap<>();
            map.put("id",id);
            map.put("title",title);
            map.put("desc",desc);
            map.put("date",date);

            db.collection("Event").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddEvent.this,"Data Saved",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddEvent.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });

        }else
            Toast.makeText(this,"Empty Fields not Allowed",Toast.LENGTH_SHORT).show();
    }
}