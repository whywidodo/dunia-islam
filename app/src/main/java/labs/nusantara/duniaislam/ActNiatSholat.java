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

public class ActNiatSholat extends AppCompatActivity implements RecNiatSholat.OnItemClickListener {
    // Btn Back
    ImageView btnBack;

    // RecylcerView
    RecyclerView recyclerView;

    // Firebase
    private DatabaseReference myRef;


    private ArrayList<DataNiatSholat> messageList;
    private RecNiatSholat recyclerAdapter;
    private Context mContext;
    private Integer nomorku;
    private EditText cari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_niatsholat);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recylerView);

        // Pencarian
        cari = findViewById(R.id.cariNiat);
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

        // set recycler view
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
        Query query = myRef.child("labs-niatsholat");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();

                findViewById(R.id.loadBar).setVisibility(View.GONE);
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    DataNiatSholat messageData = new DataNiatSholat();
                    nomorku = Integer.valueOf(snapshot1.getKey());
                    nomorku+=1;

                    messageData.setId(snapshot1.getKey());
                    messageData.setNomor(nomorku.toString());
                    messageData.setNama(snapshot1.child("name").getValue().toString());
                    messageData.setLatin(snapshot1.child("latin").getValue().toString());
                    messageData.setArabic(snapshot1.child("arabic").getValue().toString());
                    messageData.setTerjemahan(snapshot1.child("terjemahan").getValue().toString());

                    messageList.add(messageData);
                }

                recyclerAdapter = new RecNiatSholat(ActNiatSholat.this, messageList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();

                recyclerAdapter.setOnItemClickListener(ActNiatSholat.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        DataNiatSholat data = messageList.get(position);
        Intent intent = new Intent(ActNiatSholat.this,IntiNiatSholat.class);
        intent.putExtra("idData",data.id);
        startActivity(intent);
        cari.setText("");
        cari.clearFocus();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
