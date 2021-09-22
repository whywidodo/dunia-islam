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

public class IntiNiatSholat extends AppCompatActivity {
    ImageView btnBack;
    TextView judul, nama, arab, latin,terjemahan;
    private String id = "";

    // Firebase
    DatabaseReference detail_ref;
    ValueEventListener detail_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inti_niatsholat);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        judul = findViewById(R.id.judulNiat);
        nama = findViewById(R.id.namaIntiTxt);
        arab = findViewById(R.id.arabIntiTxt);
        latin = findViewById(R.id.latinIntiTxt);
        terjemahan = findViewById(R.id.terjemahanIntiTxt);

        id = getIntent().getStringExtra("idData");
//        Toast.makeText(IntiNiatSholat.this, id, Toast.LENGTH_SHORT).show();
        detail_ref = FirebaseDatabase.getInstance().getReference().child("labs-niatsholat").child(id);
        detail_event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    final DataNiatSholat messageData = new DataNiatSholat();
//                    Toast.makeText(IntiNiatSholat.this, nomorku, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(IntiNiatSholat.this, "Berjalan", Toast.LENGTH_SHORT).show();

                    if (messageData!= null){
                        messageData.setNama(snapshot.child("name").getValue().toString());
                        messageData.setArabic(snapshot.child("arabic").getValue().toString());
                        messageData.setLatin(snapshot.child("latin").getValue().toString());
                        messageData.setTerjemahan(snapshot.child("terjemahan").getValue().toString());

                        judul.setText(messageData.getNama());
                        nama.setText(messageData.getNama());
                        arab.setText(messageData.getArabic());
                        latin.setText(messageData.getLatin());
                        terjemahan.setText(messageData.getTerjemahan());

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
