package no.nord.androidquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Resultat extends AppCompatActivity {

    private final String TAG = "TAG2";

    // Definerer variabler som skal brukes i Aktiviteten
    private Button avslutt;
    private Button provIgjen;
    private TextView resultat;
    private TextView vurderingen;
    int[] fasit;
    int riktige=0;
    ArrayList<Integer> valg = new ArrayList<>();

    String[] vurderinger;

    // Starter aktiviteten
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_resultat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Legger her in data fra forrige aktivitet til bruk lengre ned
        Intent intent = getIntent();
        fasit = intent.getIntArrayExtra("fasit");
        valg = intent.getIntegerArrayListExtra("valg");

        // Sjekker resultat her på samme måte som i MainActivity.
        // Kunne bare overført resultatet, men det kan ikke brukes alene,
        // hvis man skal presentere fasit med rett/feil svartekst om man ønsker å utvide med en sjekkSvar activity,
        // der man ser fasiten og hvilke man hadde riktig.
        int teller = 0;
        for (int i : fasit) {
            if (i == valg.get(teller)) {
                riktige++;
            }
            teller++;
        }

        // Legger inn resultat i TextViewet resultat
        resultat = (TextView) findViewById(R.id.resultat);
        resultat.setText(riktige+" av "+fasit.length);

        // Legger inn tekstArray fra ressurs fila i "vurderinger" array
        // Klargjør TextViewet "vurderingen" for if-statments
        vurderinger= getResources().getStringArray(R.array.resultater);
        vurderingen = (TextView) findViewById(R.id.vurdering);

        // Sjekker antall riktige mot ønsket vurderingstekst
        if (riktige==10){
            vurderingen.setText(vurderinger[4]);
        }
        else if (riktige>=8 && riktige !=10){
            vurderingen.setText(vurderinger[3]);
        }
        else if (riktige<8 && riktige>=5){
            vurderingen.setText(vurderinger[2]);
        }
        else if (riktige<5 && riktige>=2){
            vurderingen.setText(vurderinger[1]);
        }
        else {
            vurderingen.setText(vurderinger[0]);
        }

        // Starter MainActivity igjen som ble avsluttet før denne aktiviteten startet
        provIgjen = (Button) findViewById(R.id.tilbake);
        provIgjen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNy = new Intent(Resultat.this, MainActivity.class);
                startActivity(startNy);
                finish();
            }
        });

        // Avslutter denne aktiviteten, og siden MainActivity allerede er avsluttet, blir man sendt til HomeScreen.
        avslutt =(Button) findViewById(R.id.avslutt);
        avslutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // Metodene under er kun for eget bruk og kunne vært slettet, men jeg foretrekker å ha dem der
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
