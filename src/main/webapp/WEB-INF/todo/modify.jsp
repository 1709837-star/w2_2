<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Todo Modify/Remove</title>
</head>
<body>

<form id="form1" action="/todo/modify" method="post">
    <div>
        <input type="text" name="tno" value="${dto.tno}" readonly>
    </div>
    <div>
        <input type="text" name="title" value="${dto.title}">
    </div>
    <div>
        <input type="date" name="dueDate" value="${dto.dueDate}">
    </div>
    <div>
        <input type="checkbox" name="finished" ${dto.finished ? "checked":""}>
    </div>
    <div>
        <button type="submit">Modify</button>
    </div>
    <!--
    사용자가 modify 버튼 누르면 TodoModifyController의 @WebServlet("/todo/modify")로 인해
    브라우저에서 TodoModifyController.doPost()로 감 (method="post")
    -->
</form>

<form id="form2" action="/todo/remove" method="post">
    <input type="hidden" name="tno" value="${dto.tno}">
    <div>
        <button type="submit">Remove</button>
    </div>
      <!--
        사용자가 Remove 버튼 누르면 TodoRemoveController의 @WebServlet("/todo/modify")로 인해
        브라우저에서 TodoRemoveController.doPost()로 감 (method="post")
        todoService.remove(2L) ...로 DB에서 tno=2 삭제 ... list.jsp로 이동
        -->
</form>

</body>
</html>