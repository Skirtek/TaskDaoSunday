package com.codecool;

import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/");
        dataSource.setUser("postgres");
        dataSource.setPassword("@W#E$R%T^Y7u8i");
        dataSource.setDatabaseName("todo_sunday");

        try(Connection connection = dataSource.getConnection()){
            TaskDao taskDao = new TaskDaoImpl(connection);
            taskDao.getAll().forEach(System.out::println);

            System.out.println("-----------------------------------------------------------------");
            System.out.println("-----------------------------------------------------------------");
            Task task = taskDao.getTask(2);
            Task nonExistentTask = taskDao.getTask(9999);
            System.out.println(task);
            System.out.println(nonExistentTask);

            System.out.println("-----------------------------------------------------------------");
            System.out.println("-----------------------------------------------------------------");
            Task taskToAdd = new Task("Finish lessons", false);
            long id = taskDao.addTask(taskToAdd);
            System.out.println("New entity created with ID: " + id);

            System.out.println("-----------------------------------------------------------------");
            System.out.println("-----------------------------------------------------------------");
            // no update made because empty description
            Task taskToUpdate = new Task(id, "", true);
            TaskRepository taskRepository = new TaskRepositoryImpl(taskDao);
            taskRepository.update(taskToUpdate);

            System.out.println("-----------------------------------------------------------------");
            System.out.println("-----------------------------------------------------------------");
            taskDao.deleteTask(id);
        }
    }
}