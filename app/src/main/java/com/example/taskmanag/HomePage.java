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
import java.util.Objects;
import java.util.UUID;

public class HomePage extends AppCompatActivity {
    private EditText mTitle,mDesc,mdead,mdoc,mimg;
    private Button mSaveBtn,mShowBtn;
    private FirebaseFirestore db;
    private String uTitle,uDesc,uDline,uDoc,uImg,uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mTitle=findViewById(R.id.edit_title);
        mDesc=findViewById(R.id.edit_desc);
        mdead=findViewById(R.id.edit_deadline);
        mdoc=findViewById(R.id.edit_doc);
        mimg=findViewById(R.id.edit_img);
        mSaveBtn=findViewById(R.id.save_btn);
        mShowBtn=findViewById(R.id.show_btn);


        db=FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mSaveBtn.setText("Update");
            uTitle=bundle.getString("uTitle");
            uDesc=bundle.getString("uDesc");
            uDline=bundle.getString("uDline");
            uDoc=bundle.getString("uDoc");
            uImg=bundle.getString("uImg");
            uId=bundle.getString("uId");
            mTitle.setText(uTitle);
            mDesc.setText(uDesc);
            mdead.setText(uDline);
            mdoc.setText(uDoc);
            mimg.setText(uImg);

        }else {

            mSaveBtn.setText("Save");
        }

        mShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,ShowActivity.class));
            }
        });



        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=mTitle.getText().toString();
                String desc=mDesc.getText().toString();
                String deadline=mdead.getText().toString();
                String doc=mdoc.getText().toString();
                String img=mimg.getText().toString();


                Bundle bundle1=getIntent().getExtras();
                if (bundle != null){
                    String id = uId.toString();
                    updateToFireStore(id,title,desc,deadline,doc,img);
                }else {
                    String id= UUID.randomUUID().toString();
                    saveToFireStore(id,title,desc,deadline,doc,img);
                }


            }
        });
    }
    private void updateToFireStore(String id,String title, String desc,String deadline,String doc,String img)
    {
        db.collection("Task").document(id).update("title",title,"Desc",desc,"deadline",deadline,"Doc",doc,"img",img)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(HomePage.this,"Data Updated",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(HomePage.this,"Error :"+ task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomePage.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void saveToFireStore(String id,String title, String desc,String deadline,String doc,String img){
        if(!title.isEmpty() && !desc.isEmpty() && !deadline.isEmpty() && !doc.isEmpty() && !img.isEmpty()){
            HashMap<String , Object> map=new HashMap<>();
            map.put("id",id);
            map.put("title",title);
            map.put("desc",desc);
            map.put("deadline",deadline);
            map.put("doc",doc);
            map.put("img",img);
            db.collection("Task").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(HomePage.this,"Data Saved",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HomePage.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });

        }else
            Toast.makeText(this,"Empty Fields not Allowed",Toast.LENGTH_SHORT).show();
    }
}