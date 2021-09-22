package labs.nusantara.duniaislam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ActDoaHarian extends AppCompatActivity implements RecDoaHarian.OnItemClickListener {
    // Btn Back
    ImageView btnBack;

    // RecylcerView
    RecyclerView recyclerView;

    // Firebase
    private DatabaseReference myRef;


    private ArrayList<DataDoaHarian> messageList;
    private RecDoaHarian recyclerAdapter;
    private Context mContext;
    private Integer nomorku;
    private EditText cari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_doaharian);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recylerView);

        // Pencarian
        cari = findViewById(R.id.cariDoa);
        cari.setImeOptions(EditorInfo.IME_ACTION_DONE);
        cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (recyclerAdapter != null){
                    recyclerAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (recyclerAdapter != null){
                    recyclerAdapter.getFilter().filter(s);
                }
            }
        });

        // Set Recycler View
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        // Array list
        messageList = new ArrayList<>();

        // Clear Array List
        ClearAll();

        // Get Data Method
        GetDataFromFirebase();
    }

    private void ClearAll() {
        if (messageList != null){
            messageList.clear();

            if (recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        messageList = new ArrayList<>();
    }

    private void GetDataFromFirebase() {
        Query query = myRef.child("labs-doaharian");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();

                findViewById(R.id.loadBar).setVisibility(View.GONE);
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    DataDoaHarian messageData = new DataDoaHarian();
                    nomorku = Integer.valueOf(snapshot1.getKey());
                    nomorku+=1;

                    messageData.setId(snapshot1.getKey());
                    messageData.setNomor(nomorku.toString());
                    messageData.setTitle(snapshot1.child("title").getValue().toString());
                    messageData.setLatin(snapshot1.child("latin").getValue().toString());
                    messageData.setArabic(snapshot1.child("arabic").getValue().toString());
                    messageData.setTranslation(snapshot1.child("translation").getValue().toString());

                    messageList.add(messageData);
                }

                recyclerAdapter = new RecDoaHarian(ActDoaHarian.this, messageList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();

                recyclerAdapter.setOnItemClickListener(ActDoaHarian.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        DataDoaHarian data = messageList.get(position);
        Intent intent = new Intent(ActDoaHarian.this,IntiDoaHarian.class);
        intent.putExtra("idData",data.id);
        startActivity(intent);
        cari.setText("");
        cari.clearFocus();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
