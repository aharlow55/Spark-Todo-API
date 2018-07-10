package com.teamtreehouse.todos;

import com.google.gson.Gson;
import com.teamtreehouse.todos.dao.Sql2oTodoDao;
import com.teamtreehouse.todos.dao.TodoDao;
import com.teamtreehouse.todos.model.Todo;
import org.sql2o.Sql2o;

import static spark.Spark.*;


public class App {


    public static void main(String[] args) {
        staticFileLocation("/public");

        get("/blah", (req, res) -> "Hello!");

        Sql2o sql2o = new Sql2o("jdbc:h2:~/todos.db;INIT=RUNSCRIPT from 'classpath:db/init.sql'", "", "");
        TodoDao todoDao = new Sql2oTodoDao(sql2o);
        Gson gson = new Gson();

        post("/api/v1/todos", "application/json", (req, res) -> {
            Todo todo = gson.fromJson(req.body(), Todo.class);
            todoDao.add(todo);
            res.status(200);
            return todo;
        }, gson::toJson);

        get("/api/v1/todos", "application/json",
                (req, res) -> todoDao.findAll(), gson::toJson);

        get("api/v1/todos/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Todo todo = todoDao.findById(id);
            return todo;
        }, gson::toJson);

        put("/api/v1/todos/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Todo existingTodo = todoDao.findById(id);
            existingTodo.setCompleted(gson.fromJson(req.body(), Todo.class).isCompleted());
            existingTodo.setName(gson.fromJson(req.body(), Todo.class).getName());
            todoDao.update(existingTodo);
            res.status(200);
            return existingTodo;
        }, gson::toJson);

        delete("/api/v1/todos/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Todo todo = todoDao.findById(id);
            todoDao.delete(todo);
            res.status(204);
            return todo;
        }, gson::toJson);

        after((req, res) -> {
            res.type("application/json");
        });
    }
}
