package labs.nusantara.duniaislam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class IntiAlQuran extends AppCompatActivity {
    ImageView btnBack;
    TextView namaAyat, arabSurah, artiSurah;
    private String id = "";

    // RecylcerView
    RecyclerView recyclerView;

    // Firebase
    DatabaseReference detail_ref;
    ValueEventListener detail_event;

    private DatabaseReference myRef;
    private ArrayList<DataAlQuran> messageList;
    private RecAyatAlQuran recyclerAdapter;
    private Integer nomorku;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inti_alquran);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recylerViewAyat);

        namaAyat = findViewById(R.id.namaAyat);

        id = getIntent().getStringExtra("idData");

        // Set Recycler View
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        // Array list
        messageList = new ArrayList<>();

        // Get Data Method
        GetDataSurahFromFirebase();
        GetDataFromFirebase();
    }

    private void GetDataSurahFromFirebase() {
        detail_ref = FirebaseDatabase.getInstance().getReference().child("labs-quran").child(id);
        detail_event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    final DataAlQuran messageData = new DataAlQuran();

                    if (messageData!= null){
                        messageData.setListnamaSurah(snapshot.child("name").getValue().toString());
                        namaAyat.setText(messageData.getListnamaSurah());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        detail_ref.addListenerForSingleValueEvent(detail_event);
    }

    private void GetDataFromFirebase() {
        Query query = myRef.child("labs-quran").child(id).child("verses");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                findViewById(R.id.loadBar).setVisibility(View.GONE);
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    DataAlQuran messageData = new DataAlQuran();
//                    nomorku = Integer.valueOf(id);
//                    nomorku+=1;

//                    Toast.makeText(IntiAlQuran.this, snapshot1.child("text").getValue().toString(), Toast.LENGTH_SHORT).show();
//                    messageData.setId(snapshot1.getKey());
//                    messageData.setNomor(nomorku.toString());
//                    messageData.setNamaSurah(snapshot1.child("name").getValue().toString());
                    messageData.setArabAyat(snapshot1.child("text").getValue().toString());
                    messageData.setArtiAyat(snapshot1.child("translation_id").getValue().toString());

                    messageList.add(messageData);
                }

                recyclerAdapter = new RecAyatAlQuran(IntiAlQuran.this, messageList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
