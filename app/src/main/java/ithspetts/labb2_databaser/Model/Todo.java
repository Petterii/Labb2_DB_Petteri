package ithspetts.labb2_databaser.Model;

/**
 * Created by mrx on 2018-01-30.
 */

public class Todo {

    private int todolistId;
    private String todolistTitle;
    private String todolistContent;
    private String todolistDate;
    private int todolistCategoryId;
    private int categoryId;
    private String categoryName;
    private int todolistPrio;


    public Todo() {
    }

    public Todo(int todolistId,int todolistPrio, String todolistTitle, String todolistContent, String todolistDate, int todolistCategoryId, int todoCategoryId, int categoryId, String categoryName) {

        this.todolistId = todolistId;
        this.todolistTitle = todolistTitle;
        this.todolistContent = todolistContent;
        this.todolistDate = todolistDate;
        this.todolistCategoryId = todolistCategoryId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.todolistPrio = todolistPrio;
    }

    public int getTodolistPrio() {
        return todolistPrio;
    }
    public void setTodolistPrio(int todolistPrio) {
        this.todolistPrio = todolistPrio;
    }
    public int getTodolistId() {
        return todolistId;
    }
    public void setTodolistId(int todolistId) {
        this.todolistId = todolistId;
    }
    public String getTodolistTitle() {
        return todolistTitle;
    }
    public void setTodolistTitle(String todolistTitle) {
        this.todolistTitle = todolistTitle;
    }
    public String getTodolistContent() {
        return todolistContent;
    }
    public void setTodolistContent(String todolistContent) { this.todolistContent = todolistContent; }
    public String getTodolistDate() {
        return todolistDate;
    }
    public void setTodolistDate(String todolistDate) {
        this.todolistDate = todolistDate;
    }
    public int getTodolistCategoryId() {
        return todolistCategoryId;
    }
    public void setTodolistCategoryId(int todolistCategoryId) { this.todolistCategoryId = todolistCategoryId; }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
