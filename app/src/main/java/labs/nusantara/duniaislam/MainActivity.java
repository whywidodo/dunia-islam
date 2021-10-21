package labs.nusantara.duniaislam;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import io.github.muddz.styleabletoast.StyleableToast;

public class MainActivity extends AppCompatActivity {
    Dialog myDialog;
    CardView quran, asmaulhusna, doaharian, niatsholat;
    private Toast toast;
    private long lastBackPressTime = 0;

    private StyleableToast customToast, customToastInternet;

    TextView textQuotes;
    // Firebase
    DatabaseReference detail_ref;
    ValueEventListener detail_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(cekInternet(MainActivity.this)) {
            // Isi kode perintah...
        } else {
            customToastInternet = StyleableToast.makeText(MainActivity.this, "Tidak Terhubung ke Internet!", Toast.LENGTH_SHORT, R.style.mytoastNot);
            customToastInternet.show();
        }

        // Pop Up
        myDialog = new Dialog(this);

        quran = findViewById(R.id.cardAlQuran);
        quran.setOnClickListener(view -> loadAlQuran());

        asmaulhusna = findViewById(R.id.cardAsmaulHusna);
        asmaulhusna.setOnClickListener(view -> loadAsmaulHusna());

        niatsholat = findViewById(R.id.cardNiatSholat);
        niatsholat.setOnClickListener(view -> loadNiatSholat());

        doaharian = findViewById(R.id.cardDoaHarian);
        doaharian.setOnClickListener(view -> loadDoaHarian());

        textQuotes = findViewById(R.id.textQuotes);
        // Get Data Method
        GetDataFromFirebase();
    }

    private boolean cekInternet(Context context) {
        Boolean Connected = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) for (int i = 0; i < info.length; i++){
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    Connected = true;
                } else {}
            }
        } else {
            Connected = false;
        }
        return Connected;
    }

    public void ShowPopup (View v){
        TextView txtClose;
        myDialog.setContentView(R.layout.activity_info);
        txtClose = myDialog.findViewById(R.id.txtClose);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }

    private void GetDataFromFirebase() {
        Random rand = new Random();
        int max = 11; // Nilai 0 - 10
        int int_random = rand.nextInt(max);

        detail_ref = FirebaseDatabase.getInstance().getReference().child("labs-quotes").child(String.valueOf(int_random));
        detail_event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    final DataQuotes messageData = new DataQuotes();

                    if (messageData!= null){
                        messageData.setTxt_quotes(snapshot.child("text_quotes").getValue().toString());
                        textQuotes.setText(messageData.getTxt_quotes());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        detail_ref.addListenerForSingleValueEvent(detail_event);
    }


    private void loadAlQuran() {
        Intent changeHalaman = new Intent(this, ActAlQuran.class);
        startActivity(changeHalaman);
    }

    private void loadAsmaulHusna() {
        Intent changeHalaman = new Intent(this, ActAsmaulHusna.class);
        startActivity(changeHalaman);
    }

    private void loadDoaHarian() {
        Intent changeHalaman = new Intent(this, ActDoaHarian.class);
        startActivity(changeHalaman);
    }

    private void loadNiatSholat() {
        Intent changeHalaman = new Intent(this, ActNiatSholat.class);
        startActivity(changeHalaman);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
//            toast = Toast.makeText(this, "Tekan sekali lagi untuk keluar.", 4000);
//            toast.show();
            customToast = StyleableToast.makeText(MainActivity.this, "Tekan sekali lagi untuk keluar.", Toast.LENGTH_LONG, R.style.mytoast);
            customToast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (customToast != null) {
                customToast.cancel();
            }
            super.onBackPressed();
        }
    }

}