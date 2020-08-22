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

public class ElectronicsActivity extends AppCompatActivity {

    private EditText itemElectronics;
    private Button addElectronics, showElectronics;
    private TextView showElectronicsList;
    private DatabaseReference ElectronicsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics);
        itemElectronics = findViewById(R.id.itemElectronics);
        addElectronics = findViewById(R.id.addElectronics);
        showElectronics = findViewById(R.id.showElectronics);
        showElectronicsList = findViewById(R.id.showElectronicsList);
        ElectronicsData = FirebaseDatabase.getInstance().getReference().child("ElectronicsData");
        addElectronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataActivity();
            }
        });
        showElectronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDataActivity();
            }
        });
    }
    public void AddDataActivity() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ElectronicsList", itemElectronics.getText().toString());

        ElectronicsData.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("electronicsData", "Complete ");
                itemElectronics.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("electronicsData", "Failed "+e.toString());
            }
        });
    }
    public void ShowDataActivity() {
        ElectronicsData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String electronicsList = "";
                int i = 1;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    electronicsList = electronicsList + "  " + i++ + "- " + dataSnapshot.child("ElectronicsList").getValue().toString()+"\n";
                }
                showElectronicsList.setText(electronicsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}