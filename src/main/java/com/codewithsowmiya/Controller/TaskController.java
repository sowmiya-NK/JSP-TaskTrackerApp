package com.codewithsowmiya.Controller;

import com.codewithsowmiya.Model.TaskApp;
import com.codewithsowmiya.dao.TaskDao;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;



public class TaskController extends HttpServlet {
    private final TaskDao taskDao;

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

        if (deleteIdParameter != null && !deleteIdParameter.isEmpty()) {
            try {
                final int deleteId = Integer.parseInt(deleteIdParameter);
                taskDao.deleteTask(deleteId);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String[] ids = req.getParameterValues("completeIdFlag");

        if (ids != null && ids.length>0) {
            System.out.println(Arrays.asList(ids));
            try {
                for (String id : ids) {
                    taskDao.updateTaskStatus(Integer.parseInt(id), 1);
                }
                List<String> allIds = taskDao.getAllIds();
                allIds.removeAll(Arrays.asList(ids));
                for(String id: allIds){
                    taskDao.updateTaskStatus(Integer.parseInt(id), 0);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            List<String> allIds = taskDao.getAllIds();
            for(String id: allIds){
                try {
                    taskDao.updateTaskStatus(Integer.parseInt(id), 0);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        List<TaskApp> data = taskDao.getAllTasks();
        req.setAttribute("Data", data);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

}
