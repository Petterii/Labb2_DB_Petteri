package ithspetts.labb2_databaser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import ithspetts.labb2_databaser.Database.DBHelper;

public class AddNote extends AppCompatActivity {

    TextView titleText;
    Switch prioSwitch;
    Switch kategoriSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        kategoriSwitch = findViewById(R.id.kategoriSwitch);
        prioSwitch = findViewById(R.id.prioSwitch);
        titleText = findViewById(R.id.noteTitle);

    }

    public void addNote(View view){
        int prio;
        if(prioSwitch.isChecked())
            prio = 1;
        else prio = 0;

        int kategori;
        if (kategoriSwitch.isChecked())
            kategori = 2;
        else kategori =1;
        DBHelper db = new DBHelper(this);
        db.addNoteToDB(titleText.getText().toString(),prio,kategori);


        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);
    }

}
