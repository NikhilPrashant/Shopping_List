package com.nikhilprashant.shoppinglist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MobileActivity extends AppCompatActivity {

    private EditText itemMobile;
    private Button addMobile, showMobile;
    private TextView showMobileList;
    private DatabaseReference MobileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        itemMobile = findViewById(R.id.itemMobile);
        addMobile = findViewById(R.id.addMobile);
        showMobile = findViewById(R.id.showMobile);
        showMobileList = findViewById(R.id.showMobileList);
        MobileData = FirebaseDatabase.getInstance().getReference().child("MobileData");
        addMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataActivity();
            }
        });
        showMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDataActivity();
            }
        });
    }
    public void AddDataActivity() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("MobileList", itemMobile.getText().toString());

        MobileData.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("mobileData", "Complete ");
                itemMobile.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("mobileData", "Failed "+e.toString());
            }
        });
    }
    public void ShowDataActivity() {
        MobileData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mobileList = "";
                int i = 1;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    mobileList = mobileList + "  " + i++ + "- " + dataSnapshot.child("MobileList").getValue().toString()+"\n";
                }
                showMobileList.setText(mobileList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}