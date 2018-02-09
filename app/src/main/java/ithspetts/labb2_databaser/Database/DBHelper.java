package ithspetts.labb2_databaser.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ithspetts.labb2_databaser.Model.Todo;
import ithspetts.labb2_databaser.Model.Users;

/**
 * Created by mrx on 2018-01-30.
 */

public class DBHelper extends SQLiteOpenHelper{


    private static final String LOGTAG = "TODO";
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 5;


    // User table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_USER_NAME = "userName";
    public static final String COLUMN_USER_PASSWORD = "userPassword";
    private static final String TABLE_USERS_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_USER_NAME + " TEXT, " +
                    COLUMN_USER_PASSWORD + " TEXT " +
                    ")";

    // Todolist table
    public static final String TABLE_TODOLIST = "todolist";
    public static final String COLUMN_TODOLIST_ID = "todolistId";
    public static final String COLUMN_TODOLIST_TITLE = "todolistTitle";
    public static final String COLUMN_TODOLIST_CONTENT = "todolistContent";
    public static final String COLUMN_TODOLIST_PRIO = "todolistPrio";
    public static final String COLUMN_TODOLIST_CATEGORYID = "todolistCategoryId";

    private static final String TABLE_TODOLIST_CREATE =
            "CREATE TABLE " + TABLE_TODOLIST + " (" +
                    COLUMN_TODOLIST_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_TODOLIST_TITLE + " TEXT, " +
                    COLUMN_TODOLIST_CONTENT + " TEXT, " +
                    COLUMN_TODOLIST_PRIO + " INTEGER, " +
                    COLUMN_TODOLIST_CATEGORYID + " INTEGER" +
                    ")";

    // Category table
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ID = "categoryId";
    public static final String COLUMN_CATEGORY_NAME = "categoryName";
    private static final String TABLE_CATEGORY_CREATE =
            "CREATE TABLE " + TABLE_CATEGORY + " (" +
                    COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_CATEGORY_NAME + " TEXT" +
                    ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOGTAG, "In oncreate");
        db.execSQL(TABLE_TODOLIST_CREATE);
        db.execSQL(TABLE_CATEGORY_CREATE);
        db.execSQL(TABLE_USERS_CREATE);

        Log.i(LOGTAG, "Tables has been created");
        db.execSQL("INSERT INTO users (userName,userPassword) VALUES ('Kalle','1234'), ('Pelle','king123'), ('Anna','king123'), ('Danne','king123'), ('Aron','king123')");

        db.execSQL("INSERT INTO category (categoryName) VALUES ('Arbete'), ('Fritid')");
        db.execSQL("INSERT INTO todolist (todolistTitle, todolistContent, todolistCategoryId) " +
                "VALUES ('Programmera i Java', 'Arbeta med en app i Android Studio', 1), " +
                "('Köpa skivor', 'Åk till ICA', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        Log.i(LOGTAG, "Database has been upgraded from " +
                oldVersion + " to " + newVersion);
        onCreate(db);
    }

    // Get all Todlolist items
    public List<Todo> getAllTodolist(){
        List<Todo> todolistItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(DBHelper.TABLE_TODOLIST,null,null,null,null,null,null);
        Log.i(LOGTAG, "Count: Todolist items: " + c.getCount() + " rows");
        if(c.getCount() > 0){
            while(c.moveToNext()){
                Todo todo = new Todo();
                todo.setTodolistId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_ID)));
                todo.setTodolistTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_TITLE)));
                todo.setTodolistContent(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_CONTENT)));
                todo.setTodolistPrio(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_PRIO)));

                todolistItems.add(todo);

                Log.i("Todolist items: ", todo.getTodolistId() + ", "
                        + todo.getTodolistTitle() + ", " + todo.getTodolistDate());
            }
        }
        return todolistItems;
    }


    public List<Users> getAllUsers() {
        List<Users> users = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS + ";";
        Cursor c = db.rawQuery(query, null);

        Log.i(LOGTAG, "Count: Todolist items/Category " + c.getCount() + " rows");

        while(c.moveToNext()){
            Users user = new Users();
            user.setUserId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_USER_ID)));
            user.setUserName(c.getString(c.getColumnIndex(DBHelper.COLUMN_USER_NAME)));
            user.setUserPassword(c.getString(c.getColumnIndex(DBHelper.COLUMN_USER_PASSWORD)));

            users.add(user);


        }
        return users;
    }

    public int getAmountOfTodos(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT COUNT(todolistId) AS amount FROM todolist";
        Cursor c = db.rawQuery(query, null);
        c.moveToNext();
        int amount = c.getInt(c.getColumnIndex("amount"));


        return amount;
    }

    public void deleteRow(Todo todo){
        SQLiteDatabase db = getWritableDatabase();

        db.delete("todolist", "todolistId" + " = " + todo.getTodolistId(), null);;
    }

    // Get all todolist items for a category
    public List<Todo> getAllTodolistCategory() {
        List<Todo> todolistItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " +
                "category INNER JOIN todolist ON " +
                "category.categoryId = todolist.todolistCategoryId " +
                "ORDER BY todolist.todolistPrio DESC;";

        Cursor c = db.rawQuery(query, null);

        Log.i(LOGTAG, "Count: Todolist items/Category " + c.getCount() + " rows");

        while(c.moveToNext()){
            Todo todo = new Todo();
            todo.setTodolistId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_ID)));
            todo.setTodolistTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_TITLE)));
            todo.setTodolistContent(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_CONTENT)));
            todo.setTodolistPrio(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_PRIO)));

            todo.setCategoryId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_ID)));
            todo.setCategoryName(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));
            todolistItems.add(todo);

            Log.i("items/Category:" , todo.getTodolistId() + ", "
                    + todo.getTodolistTitle() + ", " + todo.getCategoryName());
        }
        return todolistItems;
    }

    public void addNoteToDB(String noteTitle,int prio,int kategori){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("INSERT INTO todolist (todolistTitle,todolistPrio, todolistCategoryId) " +
                "VALUES ('"+noteTitle+ "'," + prio +"," + kategori +");");
    }

    public void updatePrio(Todo todo){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE todolist SET todolistPrio = "+todo.getTodolistPrio()+" WHERE todolistId = "+todo.getTodolistId()+";");
    }

    public Todo getTodoById(int todoId){
        Todo todo = new Todo();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " +
                "category INNER JOIN todolist ON " +
                "category.categoryId = todolist.todolistCategoryId " +
                "WHERE todolist.todolistId = " + todoId + ";";
        Cursor c = db.rawQuery(query, null);

        c.moveToNext();
            todo.setTodolistId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_ID)));
            todo.setTodolistTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_TITLE)));
            todo.setTodolistContent(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_CONTENT)));
        todo.setTodolistPrio(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_PRIO)));

        todo.setCategoryId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_ID)));
            todo.setCategoryName(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));


            Log.i("items/Category:" , todo.getTodolistId() + ", "
                    + todo.getTodolistTitle() + ", " + todo.getCategoryName());
        return todo;
    }
}
