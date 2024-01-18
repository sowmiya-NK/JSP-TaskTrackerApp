package com.codewithsowmiya.Controller;

import com.codewithsowmiya.Model.TaskApp;
import com.codewithsowmiya.dao.TaskDao;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;


public class TaskController extends HttpServlet {
    private final TaskDao taskDao;
    String error = "";
    List<TaskApp> tempArr = new ArrayList<>();
    List<TaskApp> data = new ArrayList<>();

    public TaskController() throws SQLException, ClassNotFoundException {
        taskDao = new TaskDao();
        data = taskDao.getAllTasks();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String task = req.getParameter("task");
        String description = req.getParameter("description");
        String action = req.getParameter("action");
        HttpSession session = req.getSession();

        if (session.getAttribute("tempArr") == null) {
            session.setAttribute("tempArr", new ArrayList<TaskApp>());
        }

        List<TaskApp> tempArr = (List<TaskApp>) session.getAttribute("tempArr");

        if ("add".equals(action)) {
            int length = tempArr.size();
            TaskApp taskApp = new TaskApp(length + 1, task, description, false);
            tempArr.add(taskApp);
            System.out.println(tempArr);
            data = tempArr;
            session.setAttribute("tempArr", tempArr);
        } else if ("save".equals(action)) {
            try {
                for (TaskApp taskApp1 : tempArr) {
                    taskDao.addTask(taskApp1.getTask(), taskApp1.getDescription(), 0);
                }
                data = taskDao.getAllTasks();
                tempArr = (List<TaskApp>) session.getAttribute("tempArr");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                tempArr = new ArrayList<>();
                session.setAttribute("tempArr", tempArr);
            }

        }
        req.setAttribute("Data", data);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteIdParameter = req.getParameter("deleteId");
        System.out.println(deleteIdParameter + "deleted");
        String completeFlagIdParameter = req.getParameter("completeIdFlag");
        System.out.println(completeFlagIdParameter + " completedid");


        if (deleteIdParameter != null && !deleteIdParameter.isEmpty()) {
            try {
                final int deleteId = Integer.parseInt(deleteIdParameter);
                taskDao.deleteTask(deleteId);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        boolean completeFlagClicked = false;
        if (completeFlagIdParameter != null) {
            completeFlagClicked = true;
            int status = taskDao.booleanToInt(completeFlagClicked);

            if (completeFlagClicked) {
                try {
                    taskDao.updateTaskStatus(Integer.parseInt(completeFlagIdParameter), status);
                    System.out.println(status + "status");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(completeFlagClicked);
            }

        }

        List<TaskApp> data = taskDao.getAllTasks();
        req.setAttribute("Data", data);
        req.setAttribute("completeFlagClicked", completeFlagClicked);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

}
