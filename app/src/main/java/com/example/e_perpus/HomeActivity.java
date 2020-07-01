package com.example.e_perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    FirebaseFirestore dbInstance = FirebaseFirestore.getInstance();
    ArrayList<Books> listBuku = new ArrayList<>();
    RvAdapter rvAdapter = new RvAdapter(listBuku);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listBuku.clear();
        RecyclerView recyclerView = findViewById(R.id.rv_book);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.btnLogout){
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            fAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return true;
    }

    private void getData() {
        listBuku.clear();
        dbInstance.collection("books").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            List<Books> data= queryDocumentSnapshots.toObjects(Books.class);
                            listBuku.addAll(data);
                            rvAdapter.notifyDataSetChanged();
                            rvAdapter.setOnItemClickCallback(new RvAdapter.OnItemClickCallback() {
                                @Override
                                public void onItemClicked(Books data) {
                                    showDetail(data);
                                }
                            });
                        }else{
                            Log.d("DATA", "Data Kosong");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ERROR",e.getLocalizedMessage());
                    }
                });
    }

    private void showDetail(Books data){
        Intent intent = new Intent(this, PdfViewActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
