//package com.example.rafif.realyourhelp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class LihatActivity extends AppCompatActivity {
//
//    private FirebaseAuth firebaseAuth;
//    private DatabaseReference databaseSolusi;
//
//    ListView listViewSolusi;
//
//    List<Pasien> solusiList;
////    private String hasilId;
////    List<Hasil> hasilList;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_solusi);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        if (firebaseAuth.getCurrentUser() == null) {
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//
////        databaseHasil = FirebaseDatabase.getInstance().getReference("hasil");
//        databaseSolusi = FirebaseDatabase.getInstance().getReference("hasil");
//
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        listViewSolusi = (ListView) findViewById(R.id.listViewHasil);
////        listViewHasil = (ListView) findViewById(R.id.listViewPasien);
//
//        solusiList = new ArrayList<>();
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        databaseSolusi.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                solusiList.clear();
//
//                for (DataSnapshot pasienSnapshot : dataSnapshot.getChildren()) {
//                    Pasien pasien = pasienSnapshot.getValue(Pasien.class);
//                    solusiList.add(pasien);
//                }
//                InfoList adapter = new InfoList(LihatActivity.this, solusiList);
//                listViewSolusi.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//}
