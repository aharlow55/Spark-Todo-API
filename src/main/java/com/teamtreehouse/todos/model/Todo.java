package com.teamtreehouse.todos.model;

import java.util.Objects;

public class Todo {
    private int id;
    private String name;
    private boolean Completed;


    public Todo(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return Completed;
    }

    public void setCompleted(boolean completed) {
        Completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return id == todo.id &&
                Completed == todo.Completed &&
                Objects.equals(name, todo.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, Completed);
    }
}
