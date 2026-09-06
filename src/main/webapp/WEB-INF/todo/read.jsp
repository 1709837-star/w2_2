<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Todo Read</title>
  </head>

  <!--
  TodoReadController의 req.setAttribute("dto", todoDTO)로
  ${dto.tno} 등 사용할 수 있게됨
  -->

  <body>
  <div>
    <input type="text" name="tno" value="${dto.tno}" readonly>
  </div>
  <div>
     <input type="text" name="title" value="${dto.title}" readonly>
  </div>
  <div>
     <input type="date" name="dueDate" value="${dto.dueDate}">
  </div>
  <div>
     <input type="checkbox" name="finished" ${dto.finished ? "check" : ""}>
  </div>

  <div>
    <a href="/todo/modify?tno=${dto.tno}">Modify/Remove</a>
    <!--
    (1) 클릭하면 Get /todo/modify?tno=2 로 이동 -> TodoModifyController.doGet() 실행
    (2) todoService.get(tno)로 DB에서 2번 Todo 가져옴
    (3) req.setAttribute("dto", todoDTO);
    (4) req.getRequestDispatcher("/WEB-INF/todo/modify.jsp").forward(req, resp);
    (5) modify.jsp 보여줌
    -->
    <a href="/todo/list">List</a>
  </div>
  </body>
</html>
