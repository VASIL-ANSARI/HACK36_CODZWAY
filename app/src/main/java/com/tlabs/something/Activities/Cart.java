package com.tlabs.something.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.tlabs.something.Adapters.ItemAdapter;
import com.tlabs.something.R;
import com.tlabs.something.Utils.Methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.tlabs.something.Utils.UserDetails.getUid;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        BottomNavigationView bottomNavigation=findViewById(R.id.bottomNavigation);
        Methods.addBottomNavigation(bottomNavigation,R.id.cart,this);


      /*  List<Map<String,Object>> data=new ArrayList<>();
        List<String> comapnies=new ArrayList<>();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); */

       /* AlertDialog progressDialog= Methods.progressDialog(this,"Fetching Orders");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show(); */

       /* DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Cart").document(getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d("********",value.toString());
            }
        }); */

    /*    collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    comapnies.add(document.getId());
                    data.add(document.getData());

                }
                ItemAdapter itemAdapter=new ItemAdapter(this,comapnies,data);
                recyclerView.setAdapter(itemAdapter);
            }
            progressDialog.dismiss();
        }); */




    }
}