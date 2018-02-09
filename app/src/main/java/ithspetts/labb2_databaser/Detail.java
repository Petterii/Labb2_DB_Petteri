package ithspetts.labb2_databaser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import ithspetts.labb2_databaser.Database.DBHelper;
import ithspetts.labb2_databaser.Model.Todo;

public class Detail extends AppCompatActivity {

    TextView contentText;
    TextView titleText;
Switch prioSwitch;
    Todo todo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleText = findViewById(R.id.titleText);
        contentText = findViewById(R.id.contentText);
        prioSwitch = findViewById(R.id.prioSwitch);

        Intent intent = getIntent();

        long todoId = intent.getLongExtra("TODOID", -1);

        DBHelper dbHelper = new DBHelper(this);
        todo = dbHelper.getTodoById((int)todoId);

        titleText.setText(todo.getTodolistTitle());
        contentText.setText(todo.getTodolistContent());

    }

    public void pressedNoteDone(View view){
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.deleteRow(todo);
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);
    }

    public void pressedSaveChanges(View view){
        int prio;
        if(prioSwitch.isChecked())
            prio = 1;
        else prio = 0;

        todo.setTodolistPrio(prio);
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.updatePrio(todo);

        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);


    }


}