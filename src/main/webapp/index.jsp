<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
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
 <div class="container">
        <div class="row">
            <div class="col-6">
    <form method="get" action="taskdetail">
            <h3>Pending task</h3>

        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th>S.No</th>
                    <th>Task Name</th>
                    <th>Task Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="task" items="${Data}">
                <c:if test="${task.completeFlag eq false}">
                 <tr>
                     <td>${task.id}</td>
                     <td>${task.task}</td>
                     <td>${task.description}</td>
                     <td>
                        <c:if test="${task.flagId eq false}">
                      <input type="checkbox" name="completeIdFlag" value="${task.id}" onchange="this.form.submit()" ${task.completeFlag ? 'checked':''} >
                     <button type="submit" name="deleteId" value="${task.id}" ${task.completeFlag? 'disabled':''}>Delete</button></c:if>
                    </td>
                 </tr>
                 </c:if>
                </c:forEach>
            </tbody>

            </table>

                    </div>
                    <div class="col-6">



        <h3>Completed task</h3>

         <table class="table">
                    <thead class="thead-dark" >
                        <tr>
                            <th>S.No</th>
                            <th>Task Name</th>
                            <th>Task Description</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="task" items="${Data}">
                        <c:if test="${task.completeFlag eq true}">
                         <tr>
                             <td>${task.id}</td>
                             <td>${task.task}</td>
                             <td>${task.description}</td>
                             <td>
                                <c:if test="${task.flagId eq false}">
                              <input type="checkbox" name="completeIdFlag" value="${task.id}" onchange="this.form.submit()" ${task.completeFlag ? 'checked':''} >
                             <button type="submit" name="deleteId" value="${task.id}" ${task.completeFlag? 'disabled':''}>Delete</button></c:if>
                            </td>
                         </tr>
                         </c:if>
                        </c:forEach>
                    </tbody>

                </table>
    </form>
   </div>
        </div>
    </div>
        <br>
        <div class="row">
        <div class="col-6">
           <form method="post" action="taskdetail">
                <button type="submit" name="action" value="save" >save</button>

                </div>
                <div class="col-6">

                                <button type="submit" name="action" value="clearall" >clear all</button>
                                </form>
                                </div>
                </div>
</body>

</html>
