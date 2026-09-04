<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Todo Register</title>
  </head>
  <body>
  <form action="${pageContext.request.contextPath}/todo/register" method="post">
    <div>
        <input type="text" name="title" placeholder="INSERT TITLE">
    </div>
    <div>
        <input type="date" name="dueDate">
    </div>
    <div>
        <button type="reset">RESET</button>
        <button type="submit">RESISTER</button>
    </div>
  </form>
  </body>
</html>
