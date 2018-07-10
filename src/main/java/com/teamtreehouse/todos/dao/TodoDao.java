package com.teamtreehouse.todos.dao;

import com.teamtreehouse.todos.exc.DaoException;
import com.teamtreehouse.todos.model.Todo;

import java.util.List;

public interface TodoDao {
    void add(Todo todo) throws DaoException;

    List<Todo> findAll();

    Todo findById(int id);

    void update(Todo todo);

    void delete(Todo todo);
}
