<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
  <head>
    <title>Todo List</title>
  </head>
  <body>
  <h1>Todo List</h1>

  <h2>${appName}</h2>
  <h2>${loginInfo}</h2>
  <h3>${loginInfo.mname}</h3>

  <ul>
    <c:forEach items="${dtoList}" var="dto">
    <!-- dtolist 배열에 있는 애들을 하나씩 꺼내서 dto에 넣음 -->
        <li>
            <span><a href="/todo/read?tno=${dto.tno}">${dto.tno}</a></span>
            <span>${dto.title}</span>
            <span>${dto.dueDate}</span>
            <span>${dto.finished? "DONE" : "NOT YET"}</span>
        </li>
    </c:forEach>
  </ul>

  <form action="/logout" method="post">
    <button>LOGOUT</button>
  </form>

  </body>
</html>
