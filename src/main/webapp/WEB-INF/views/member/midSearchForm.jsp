<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberLogin.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>

  </script>
  <style>
  	a {text-decoration: none}
  	a:hover {
    	text-decoration: underline;
    	color: orange;
  	}
	</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" /> 
<p><br/></p>
<div class="container">
	<form name="myform" method="post" action="${ctp}/member/midSearchForm">
	  	<table class="table table-bordered text-center">
	      <tr>
	        <td colspan="2"><font size="5">아이디 찾기</font></td>
	      </tr>
	      <tr>
	        <th>이메일주소</th>
	        <td><input type="email" name="email" id="email" placeholder="가입할 때 등록한 이메일주소를 입력하세요." autofocus required class="form-control" /></td>
	      </tr>
	      <tr>
	        <td colspan="2">
	          <input type="submit" value="아이디찾기" class="btn btn-warning me-2"/>
	          <input type="reset" value="다시입력" class="btn btn-warning me-2"/>
	          <input type="button" value="뒤로가기" onclick="location.href='${ctp}/member/memberLogin';" class="btn btn-primary"/><br/>
	          <div id = "result"></div>
	        </td>
	      </tr>
	    </table>
	  </form> 
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html></html>