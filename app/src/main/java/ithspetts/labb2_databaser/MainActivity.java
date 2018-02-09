package ithspetts.labb2_databaser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import ithspetts.labb2_databaser.Database.DBHelper;
import ithspetts.labb2_databaser.Model.Users;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private ListView listView;
    private OrderAdapter orderAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        Random rand = new Random();

        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        List<Users> users = dbHelper.getAllUsers();



        toolbar.setTitle(users.get(rand.nextInt(4)).getUserName() + " Logged in");
        setSupportActionBar(toolbar);

        int text = dbHelper.getAmountOfTodos();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,"There are "+ text+ " amount of TODO's", duration);
        toast.show();

        orderAdapter = new OrderAdapter(this, dbHelper.getAllTodolistCategory());
        listView.setAdapter(orderAdapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), Detail.class);
                intent.putExtra("TODOID", id);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AddTodo:
                Intent intent = new Intent(this, AddNote.class);
                startActivityForResult(intent, 1);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
