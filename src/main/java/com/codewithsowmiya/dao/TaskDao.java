package com.codewithsowmiya.dao;

import com.codewithsowmiya.Model.TaskApp;
import com.codewithsowmiya.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    private final Connection connection;
    private final String INSERT_QUERY = "INSERT INTO TASKAPP(task,description,completeFlag) VALUES(?,?,?);";
    private final String UPDATE_QUERY = "UPDATE TASKAPP SET completeFlag = ? WHERE id = ?";
    private final String SELECT_ALL_TASKS = "SELECT * FROM TASKAPP;";
    private final String SELECT_ALL_IDS = "SELECT id FROM TASKAPP;";
    private final String DELETE_TASK = "DELETE FROM TASKAPP WHERE id=?;";

    private final String DELETE_COMPLETETASK = "DELETE FROM TASKAPP WHERE completeFlag=1";



    public TaskDao() throws SQLException {
        connection = Database.getConnection();
    }

    public void addTask(String task, String description, int completeFlag) throws SQLException {
        try {
            // Insert new task
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1, task);
            ps.setString(2, description);
            ps.setInt(3, completeFlag);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }

    }

    public void updateTaskStatus(int taskId, boolean completeFlag) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
            ps.setBoolean(1, completeFlag);
            ps.setInt(2, taskId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }


    public List<TaskApp> getAllTasks() {
        List<TaskApp> taskList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TASKS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TaskApp taskApp = new TaskApp();
                taskApp.setId(resultSet.getInt("id"));
                taskApp.setTask(resultSet.getString("task"));
                taskApp.setDescription(resultSet.getString("description"));
                taskApp.setCompleteFlag(resultSet.getBoolean("completeFlag"));
                taskList.add(taskApp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskList;
    }

    public void deleteTask(int deleteId) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_TASK)) {
            ps.setInt(1, deleteId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




    public List<String> getAllIds() {
        List<String> idList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_IDS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String values = String.valueOf(resultSet.getInt("id"));
                idList.add(values);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idList;
    }

    public void deleteAllCompletedTask() throws SQLException {
        PreparedStatement ps= connection.prepareStatement(DELETE_COMPLETETASK);
        ps.executeUpdate();
    }
}
