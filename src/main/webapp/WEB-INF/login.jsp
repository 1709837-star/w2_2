<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<c:if test="${param.result == 'error'}">
    <h1>로그인 에러</h1>
</c:if>
<!--
조건이 맞으면 안쪽 HTML을 보여줘! (java의 if 문법과 비슷)
${} : 안에 있는 값을 가져와서 사용해줘. (JSP에서 값을 꺼내거나 계산하는 표현식)
로그인 실패했을 때 LoginController에서 resp.sendRedirect("/login?result=error");
즉, result가 'error'라면 "로그인 에러" 출력
-->

<form action="/login" method="post">
    <input type="text" name="mid">
    <input type="text" name="mpw">
    <input type="checkbox" name="auto">
    <button type="submit">LOGIN</button>
</form>

<!--
사용자가 LOGIN 버튼 누르면 LoginController의 @WebServlet("/login")로 인해
브라우저에서 LoginController.doPost()로 감 (method="post")
-->

</body>
</html>
