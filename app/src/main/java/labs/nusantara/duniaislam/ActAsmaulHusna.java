package labs.nusantara.duniaislam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActAsmaulHusna extends AppCompatActivity implements RecAsmaulHusna.OnItemClickListener {
    // Btn Back
    ImageView btnBack;

    // RecylcerView
    RecyclerView recyclerView;

    // Firebase
    private DatabaseReference myRef;
    DatabaseReference detail_ref;
    ValueEventListener detail_event;


    private ArrayList<DataAsmaulHusna> messageList;
    private RecAsmaulHusna recyclerAdapter;
    private Context mContext;
    private Integer nomorku;
    private EditText cari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_asmaulhusna);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recylerView);

        // Pencarian
        cari = findViewById(R.id.cariAsmaul);
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
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 3);
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
        myRef = FirebaseDatabase.getInstance().getReference().child("labs-asmaulhusna");
        Query query = myRef;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();

                findViewById(R.id.loadBar).setVisibility(View.GONE);
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    DataAsmaulHusna messageData = new DataAsmaulHusna();
                    nomorku = Integer.valueOf(snapshot1.getKey());
                    nomorku+=1;

                    messageData.setId(snapshot1.getKey());
                    messageData.setNomor(nomorku.toString());
                    messageData.setLatin(snapshot1.child("latin").getValue().toString());
                    messageData.setArabic(snapshot1.child("arabic").getValue().toString());

                    messageList.add(messageData);
                }

                recyclerAdapter = new RecAsmaulHusna(ActAsmaulHusna.this, messageList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();

                recyclerAdapter.setOnItemClickListener(ActAsmaulHusna.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {
        DataAsmaulHusna data = messageList.get(position);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ActAsmaulHusna.this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.activity_bottomsheet, (LinearLayout) findViewById(R.id.bottomSheetContainer)
        );
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        cari.setText("");
        cari.clearFocus();

        TextView btmArab = bottomSheetView.findViewById(R.id.bottomTxtArab);
        TextView btmLatin = bottomSheetView.findViewById(R.id.bottomTxtLatin);
        TextView btmArti = bottomSheetView.findViewById(R.id.bottomTxtArti);

        detail_ref = FirebaseDatabase.getInstance().getReference().child("labs-asmaulhusna").child(data.id);
        detail_event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    final DataAsmaulHusna messageData = new DataAsmaulHusna();

                    if (messageData!= null){
                        messageData.setArabic(snapshot.child("arabic").getValue().toString());
                        messageData.setLatin(snapshot.child("latin").getValue().toString());
                        messageData.setTranslation_id(snapshot.child("translation_id").getValue().toString());

                        btmArab.setText(messageData.getArabic());
                        btmLatin.setText(messageData.getLatin());
                        btmArti.setText(messageData.getTranslation_id());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        detail_ref.addListenerForSingleValueEvent(detail_event);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
