package com.codecool;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TaskDaoImpl implements TaskDao {
    private Connection connection;

    @SneakyThrows
    @Override
    public long addTask(Task task) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.ADD_TASK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, task.getDescription());
            statement.setBoolean(2, task.isFinished());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        }

        return 0L;
    }

    @SneakyThrows
    @Override
    public Task getTask(long id) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.GET_TASK)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String description = resultSet.getString("description");
                    boolean finished = resultSet.getBoolean("finished");

                    return new Task(id, description, finished);
                }
            }
        }

        return null;
    }

    @SneakyThrows
    @Override
    public List<Task> getAll() {
        List<Task> resultList = new ArrayList<>();

        try (Statement getAllStatement = connection.createStatement();
             ResultSet resultSet = getAllStatement.executeQuery(Queries.GET_ALL_TASKS)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String description = resultSet.getString("description");
                boolean finished = resultSet.getBoolean("finished");

                resultList.add(new Task(id, description, finished));
            }
        }

        return resultList;
    }

    @SneakyThrows
    @Override
    public void updateTask(Task task) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_TASK)) {
            statement.setString(1, task.getDescription());
            statement.setBoolean(2, task.isFinished());
            statement.setLong(3, task.getId());
            statement.executeUpdate();
        }
    }

    @SneakyThrows
    @Override
    public void deleteTask(long id) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.DELETE_TASK)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
