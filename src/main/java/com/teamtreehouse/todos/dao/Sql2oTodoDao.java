package com.teamtreehouse.todos.dao;

import com.teamtreehouse.todos.exc.DaoException;
import com.teamtreehouse.todos.model.Todo;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oTodoDao implements TodoDao {

    private final Sql2o sql2o;

    public Sql2oTodoDao(Sql2o sql2o) {
        this.sql2o = sql2o;

    }

    @Override
    public void add(Todo todo) throws DaoException {

        String sql = "INSERT INTO todos(name) VALUES (:name)";
        try(Connection con  = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(todo)
                    .executeUpdate()
                    .getKey();
            todo.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem adding todo");
        }

    }

    @Override
    public List<Todo> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM todos")
                    .executeAndFetch(Todo.class);
        }
    }

    @Override
    public Todo findById(int id) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * from todos WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Todo.class);
        }
    }

    @Override
    public void update(Todo todo) {
        try (Connection con = sql2o.open()) {
            con.createQuery("UPDATE todos SET name = :name, completed = :completed WHERE id = :id")
                    .addParameter("id", todo.getId())
                    .addParameter("completed", todo.isCompleted())
                    .addParameter("name", todo.getName())
                    .executeUpdate();
        }
    }

    @Override
    public void delete(Todo todo) {
        try (Connection con = sql2o.open()) {
            con.createQuery("DELETE from todos WHERE id = :id")
                    .addParameter("id", todo.getId())
                    .executeUpdate();
        }
    }

//    @Override
//    public List<Todo> findByTodoId(int todoId) {
//        try (Connection con = sql2o.open()) {
//            return con.createQuery("SELECT * from todos WHERE todo_id = :todoId")
//                    .addColumnMapping("TODO_ID", "todoId")
//                    .addParameter("todoId", todoId)
//                    .executeAndFetch(Todo.class);
//        }
//    }
}
