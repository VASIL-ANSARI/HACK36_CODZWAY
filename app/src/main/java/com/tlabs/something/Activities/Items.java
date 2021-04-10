package com.tlabs.something.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.tlabs.something.Adapters.ItemAdapter;
import com.tlabs.something.R;
import com.tlabs.something.Utils.Methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Items extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        String brand=getIntent().getStringExtra("brand");
        List<Map<String,Object>> data=new ArrayList<>();
        List<String> comapnies=new ArrayList<>();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

     AlertDialog progressDialog= Methods.progressDialog(this,"Fetching Products");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection(brand);

        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                   comapnies.add(document.getId());
                    data.add(document.getData());

                }
                ItemAdapter itemAdapter=new ItemAdapter(this,comapnies,data);
                recyclerView.setAdapter(itemAdapter);
            }
            progressDialog.dismiss();
        });



    }
}