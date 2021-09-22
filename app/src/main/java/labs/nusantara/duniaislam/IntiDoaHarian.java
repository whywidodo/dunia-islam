package labs.nusantara.duniaislam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IntiDoaHarian extends AppCompatActivity {
    ImageView btnBack;
    TextView judul, nama, arab, latin,terjemahan;
    private String id = "";

    // Firebase
    DatabaseReference detail_ref;
    ValueEventListener detail_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inti_doaharian);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        judul = findViewById(R.id.judulDoa);
        nama = findViewById(R.id.namaIntiTxt);
        arab = findViewById(R.id.arabIntiTxt);
        latin = findViewById(R.id.latinIntiTxt);
        terjemahan = findViewById(R.id.terjemahanIntiTxt);

        id = getIntent().getStringExtra("idData");
        detail_ref = FirebaseDatabase.getInstance().getReference().child("labs-doaharian").child(id);
        detail_event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    final DataDoaHarian messageData = new DataDoaHarian();

                    if (messageData!= null){
                        messageData.setTitle(snapshot.child("title").getValue().toString());
                        messageData.setArabic(snapshot.child("arabic").getValue().toString());
                        messageData.setLatin(snapshot.child("latin").getValue().toString());
                        messageData.setTranslation(snapshot.child("translation").getValue().toString());

                        judul.setText(messageData.getTitle());
                        nama.setText(messageData.getTitle());
                        arab.setText(messageData.getArabic());
                        latin.setText(messageData.getLatin());
                        terjemahan.setText(messageData.getTranslation());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        detail_ref.addListenerForSingleValueEvent(detail_event);
    }
}
