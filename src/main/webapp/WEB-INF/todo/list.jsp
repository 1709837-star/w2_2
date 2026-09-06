<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!--
(1) loginController에서 sendRedirect("/todo/list") 실행
(2) @WebServlet(name = "todoListController", value = "/todo/list")에 의해
TodoListController.doGet()이 실행됨
(3) List<TodoDTO> dtoList = todoService.listAll();로 DB에서 Todo들 전부 가져옴
(4) req.setAttribute("dtoList", dtoList) 여기서 req에 dtoList 저장
-->

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
    <!--
    dtolist 배열에 있는 애들을 하나씩 꺼내서 dto에 넣음 (java의 for문)
    items="${dtoList}" : 반복할 대상
    var="dto" : 반복하면서 하나씩 꺼낸 값을 담아놓을 변수 이름
    즉, 첫 번째 dto = TodoDTO(tno=1, title="공부") ... 반복
    -->
        <li>
            <span><a href="/todo/read?tno=${dto.tno}">${dto.tno}</a></span>
            <span>${dto.title}</span>
            <span>${dto.dueDate}</span>
            <span>${dto.finished? "DONE" : "NOT YET"}</span>
            <!-- 참이면 "DONE", 거짓이면 "NOT YET" 출력 -->
        </li>
    </c:forEach>
  </ul>

  <form action="/logout" method="post">
    <button>LOGOUT</button>
  </form>
<!--
사용자가 LOGOUT 버튼 누르면 LogoutController의 @WebServlet("/logout")로 인해
브라우저에서 LoginController.doPost()로 감 (method="post")
-->


  </body>
</html>
