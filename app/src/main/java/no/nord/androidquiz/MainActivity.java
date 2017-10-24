package no.nord.androidquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // For å følge med på livssyklusen til aktiviteten
    private final String TAG = "TAG";

    // Definerer variabler som skal brukes i Aktiviteten
    String[] sporsmalene;
    String[] svarAlternativ;
    ArrayList<Integer> valg = new ArrayList<>();
    int[] fasit;
    // Hvis jeg hadde valgt å beholde Toast ville denne vært satt inn igjen
    //int riktige=0;
    int index=0;
    private TextView sporsmalFelt;
    private RadioGroup alle;
    private RadioButton alt1;
    private RadioButton alt2;
    private RadioButton alt3;
    private RadioButton alt4;
    private Button Neste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Spørsmal TextView variabler hentes fra ressursfila
        sporsmalene = getResources().getStringArray(R.array.sporsmalliste);
        String sporsmalet = sporsmalene[index];
        sporsmalFelt = (TextView) findViewById(R.id.sporsmal);
        sporsmalFelt.setText(sporsmalet);
        fasit = getResources().getIntArray(R.array.fasit);

        //Radiogruppe spesifisering
        alle = (RadioGroup)findViewById(R.id.radioGroupAlternativ);

        // Legger in tekst på Radioknapper første gang
        setSvarAlternativer(index);
        alt1 = (RadioButton) findViewById(R.id.valg1);
        alt1.setText(svarAlternativ[0]);
        alt2 = (RadioButton) findViewById(R.id.valg2);
        alt2.setText(svarAlternativ[1]);
        alt3 = (RadioButton) findViewById(R.id.valg3);
        alt3.setText(svarAlternativ[2]);
        alt4 = (RadioButton) findViewById(R.id.valg4);
        alt4.setText(svarAlternativ[3]);

        // Nesteknapp med metoder
        Neste =(Button) findViewById(R.id.neste);
        Neste.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Sjekker at det er gjort et valg i RadioKnappGruppa
                if (alle.getCheckedRadioButtonId() == -1){
                    Toast toast = Toast.makeText(getApplicationContext(), "Du må gjøre et valg!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else {
                    // Finner indexnummer på teksten for valgte knapp og legger in dette nummer i "valg Array"
                    int valget = alle.indexOfChild(findViewById(alle.getCheckedRadioButtonId()));
                    valg.add(valget);
                    index++;
                    // Sjekker antall spørsmål for max 10 ved index 0-9
                    if (index <= (9)) {
                        String sporsmalet = sporsmalene[index];
                        sporsmalFelt.setText(sporsmalet);
                        setSvarAlternativer(index);
                        alt1 = (RadioButton) findViewById(R.id.valg1);
                        alt1.setText(svarAlternativ[0]);
                        alt2 = (RadioButton) findViewById(R.id.valg2);
                        alt2.setText(svarAlternativ[1]);
                        alt3 = (RadioButton) findViewById(R.id.valg3);
                        alt3.setText(svarAlternativ[2]);
                        alt4 = (RadioButton) findViewById(R.id.valg4);
                        alt4.setText(svarAlternativ[3]);
                        alle.clearCheck();
                    }
                    // Når index er 10 er forrige if ugyldig og else kjøres
                    // Utregningen av resultatet kunne blitt gjort, og presentert i en toast(utkommentert),
                    // men her starter jeg funksjonen SendResultat og avlsutter Aktiviteten.
                    else {
                        /*int teller = 0;
                        for (int i : fasit) {
                            if (i == valg.get(teller)) {
                                riktige++;
                            }
                            teller++;
                        }
                        Toast toast = Toast.makeText(getBaseContext(), "Du har: \n" + valg + "\n" + "av\n" + "10\n" + "riktige!", Toast.LENGTH_LONG);
                        toast.setGravity(0, 0, 0);
                        toast.show();*/
                        sendResultat();
                        finish();
                    }
                }
            }
        });
    }
    // Metode for å hente ut svaralternativer, i forhold til spørsmålet som stilles sitt indexnummer
    public String[] setSvarAlternativer(int verdi){
        switch (verdi) {
            case 0:
                svarAlternativ = getResources().getStringArray(R.array.svar1);
                return svarAlternativ;
            case 1:
                svarAlternativ = getResources().getStringArray(R.array.svar2);
                return svarAlternativ;
            case 2:
                svarAlternativ = getResources().getStringArray(R.array.svar3);
                return svarAlternativ;
            case 3:
                svarAlternativ = getResources().getStringArray(R.array.svar4);
                return svarAlternativ;
            case 4:
                svarAlternativ = getResources().getStringArray(R.array.svar5);
                return svarAlternativ;
            case 5:
                svarAlternativ = getResources().getStringArray(R.array.svar6);
                return svarAlternativ;
            case 6:
                svarAlternativ = getResources().getStringArray(R.array.svar7);
                return svarAlternativ;
            case 7:
                svarAlternativ = getResources().getStringArray(R.array.svar8);
                return svarAlternativ;
            case 8:
                svarAlternativ = getResources().getStringArray(R.array.svar9);
                return svarAlternativ;
            case 9:
                svarAlternativ = getResources().getStringArray(R.array.svar10);
                return svarAlternativ;
        }
        return svarAlternativ;
    }
    // Metode for a sende resultat til Resultat.class
    public void sendResultat() {
        Intent intent = new Intent(this, Resultat.class);
        intent.putExtra("fasit", fasit).putIntegerArrayListExtra("valg", valg);
        startActivity(intent);
    }

    // Metodene under er kun for eget bruk og kunne vært slettet, men jeg foretrekker å ha dem der
    // Jeg tenkte i utgangspunktet å bruke onResume() til å legge inn data første gang
    // og dermed starte på nytt etter å ha sett resultatet, ved å gå tilbake, men jeg ville ha valget
    // mellom å avslutte og prøve på nytt på siste aktivitet. Så da måtte jeg avslutte denne før jeg gikk videre.
    // Om jeg hadde beholdt innleggelsen av data på onResume(),
    // hadde man måttet starte på nytt hver gang man "la ned" appen litt.

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
