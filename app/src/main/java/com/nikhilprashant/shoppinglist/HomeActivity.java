package com.nikhilprashant.shoppinglist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class HomeActivity extends AppCompatActivity {

    private EditText itemHome;
    private Button addHome, showHome;
    private TextView showHomeList;
    private DatabaseReference HomeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        itemHome = findViewById(R.id.itemHome);
        addHome = findViewById(R.id.addHome);
        showHome = findViewById(R.id.showHome);
        showHomeList = findViewById(R.id.showHomeList);
        HomeData = FirebaseDatabase.getInstance().getReference().child("HomeData");
        addHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataActivity();
            }
        });
        showHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDataActivity();
            }
        });
    }
    public void AddDataActivity() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("HomeList", itemHome.getText().toString());

        HomeData.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("homeData", "Complete ");
                itemHome.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("homeData", "Failed "+e.toString());
            }
        });
    }
    public void ShowDataActivity() {
        HomeData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String homeList = "";
                int i = 1;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    homeList = homeList + "  " + i++ + "- " + dataSnapshot.child("HomeList").getValue().toString()+"\n";
                }
                showHomeList.setText(homeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}