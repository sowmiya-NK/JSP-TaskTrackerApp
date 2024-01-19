<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        table {
            width: 50%;
            border-collapse: collapse;
            margin-top: 20px;
            margin-left: 300px;
            border: 1px solid black;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #4682B4;
        }
        .actions select {
            padding: 6px;
        }

        #dropdown {
            width: 200px;
        }
        button {
            border: none;
            color: black;
            padding: 10px;
            text-align: center;
            text-decoration: none;
            width: 100px;
            border-radius: 10px;
        }
        .err {
            color: red;
        }



    </style>
</head>
<body style="text-align:center; background-color: lightblue;margin-top:70px">
    <h1>Task Track Application</h1>

    <form method="post" action="taskdetail">
        <label>Select a Task Type:</label>
        <select name="task" id="dropdown">
            <option value="">--Select Type--</option>
            <option value="remainder">remainder</option>
            <option value="todo">todo</option>
            <option value="follow-up">follow-up</option>
        </select><br><br>

        <label>Enter task description : </label>
        <textarea placeholder="enter task details ..." name="description"></textarea><br><br>
        <p class="err">${error}</p>

        <button type="submit" name="action" value="add" >Add</button>
</form>

    <form method="get" action="taskdetail">
        <table id="taskTable">
            <thead>
                <tr>
                    <th>S.No</th>
                    <th>Task Name</th>
                    <th>Task Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="task" items="${Data}">
                 <tr>
                     <td>${task.id}</td>
                     <td>${task.task}</td>
                     <td>${task.description}</td>
                     <td>
                      <input type="checkbox" name="completeIdFlag" value="${task.id}" onchange="this.form.submit()" ${task.completeFlag ? 'checked':''} >
                     <button type="submit" name="deleteId" value="${task.id}" formaction="taskdetail" ${task.completeFlag? 'disabled':''}>Delete</button>
                    </td>
                 </tr>
                </c:forEach>
            </tbody>

        </table>
    </form>
    <br>
    <form method="post" action="taskdetail">
<button type="submit" name="action" value="save" >save</button>
</form>
</body>

</html>
