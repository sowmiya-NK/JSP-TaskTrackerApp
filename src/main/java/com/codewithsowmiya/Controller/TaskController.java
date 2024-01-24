package com.codewithsowmiya.Controller;

import com.codewithsowmiya.Model.TaskApp;
import com.codewithsowmiya.dao.TaskDao;


import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class TaskController extends HttpServlet implements Servlet {
    private final TaskDao taskDao;

    List<TaskApp> data = new ArrayList<>();
    List<TaskApp> values = new ArrayList<>();
    List<TaskApp> tempArr = new ArrayList<>();
    ;


    public TaskController() throws SQLException, ClassNotFoundException {
        taskDao = new TaskDao();
        data = taskDao.getAllTasks();
        values = taskDao.getAllTasks();
        System.out.println("construcor " + data.size());
        System.out.println(tempArr.size());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String task = req.getParameter("task");
        String description = req.getParameter("description");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            int length = tempArr.size();
            TaskApp taskApp = new TaskApp(length + 1, task, description, false, true);
            tempArr.add(taskApp);
            System.out.println("temparr" + tempArr);
            showData(tempArr.get(tempArr.size() - 1));

        } else if ("save".equals(action)) {
            try {
                for (TaskApp taskApp1 : tempArr) {
                    taskDao.addTask(taskApp1.getTask(), taskApp1.getDescription(), 0);
                }
                data = taskDao.getAllTasks();
                tempArr = new ArrayList<>();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else if ("clearall".equals(action)) {
            try {
                taskDao.deleteAllCompletedTask();

                data = taskDao.getAllTasks();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        req.setAttribute("Data", data);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteIdParameter = req.getParameter("deleteId");
        System.out.println(deleteIdParameter + "deleted");

        if (deleteIdParameter != null && !deleteIdParameter.isEmpty()) {
            System.out.println("Checking");
            try {
                taskDao.deleteTask(Integer.parseInt(deleteIdParameter));
                data = taskDao.getAllTasks();

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }

        String[] ids = req.getParameterValues("completeIdFlag");

        if (ids != null && ids.length > 0) {
            System.out.println("arrayId " + Arrays.asList(ids));

            try {
                for (String id : ids) {
                    taskDao.updateTaskStatus(Integer.parseInt(id), true);
                }
                List<String> allIds = taskDao.getAllIds();
                allIds.removeAll(Arrays.asList(ids));
                for (String id : allIds) {
                    taskDao.updateTaskStatus(Integer.parseInt(id), false);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            List<String> allIds = taskDao.getAllIds();
            for (String id : allIds) {
                try {
                    taskDao.updateTaskStatus(Integer.parseInt(id), false);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        List<TaskApp> data = taskDao.getAllTasks();
        req.setAttribute("Data", data);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }


    public void showData(TaskApp value) {
        data.add(value);
    }


}