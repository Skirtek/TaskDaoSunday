package com.codecool;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private TaskDao taskDao;

    @Override
    public void update(Task task) {
        // "    asdas             " - before trim
        // "asdas" - after trim

        if (task.getDescription() == null || task.getDescription().trim().length() == 0) {
            return;
        }

        taskDao.updateTask(task);
    }
}
