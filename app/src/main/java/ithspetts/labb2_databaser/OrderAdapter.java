package ithspetts.labb2_databaser;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ithspetts.labb2_databaser.Model.Todo;

/**
 * Created by mrx on 2018-02-04.
 */

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<Todo> todoList;
    private LayoutInflater inflater;

    /**
     * Constructor that sets context, todoList and inflater.
     *
     * @param context
     * @param todoList
     */
    public OrderAdapter(Context context, List<Todo> todoList) {
        if (todoList == null)
            this.todoList = new ArrayList<>();
        else
            this.todoList = todoList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return todoList.get(position).getTodolistId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null)
            view = inflater.inflate(R.layout.cellinfo, parent, false);
        else
            view = convertView;

        int prio = todoList.get(position).getTodolistPrio();
        if(prio != 1)
            view.setBackgroundColor(Color.WHITE);
        else
            view.setBackgroundColor(Color.GREEN);


       // view.setBackgroundColor(Color.BLUE);
        TextView textTodoTitle = view.findViewById(R.id.todoText);
       TextView textTodoKategori = view.findViewById(R.id.kategori);
        textTodoTitle.setText("!!!!" + todoList.get(position).getTodolistTitle() + "!!!!!!");
        textTodoKategori.setText("!!" +todoList.get(position).getCategoryName() +"!!");

        return view;
    }

    /**
     * Inserts a new todoList and notifies the ListView.
     *
     * @param todoList - new todoList
     */
    public void setTodoList(List<Todo> todoList) {
        if (todoList == null)
            this.todoList = new ArrayList<>();
        else
            this.todoList = todoList;
        notifyDataSetChanged();
    }

    /**
     * Removes a specific _todo from todoList and notifies the ListView.
     *
     * @param  - specific _todo to remove.
     */
    public void removeTodo(Todo todo) {
        todoList.remove(todo);
        notifyDataSetChanged();
    }
}
