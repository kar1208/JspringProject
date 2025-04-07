<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>alphaNumericForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
  	'use strict';
  	
  	let cnt = 0;
  	let str = '';
  	function randomAlphaNumeric() {
  		$.ajax({
  			url : "${ctp}/study/randomAlphaNumeric",
  			type : "post",
  			success:function(res) {
  				cnt++;
  				str += cnt + " : " + res + "<br/>";
  				$("#demo").html(str);
  			},
  			error: function() { alert("전송오류!"); }
  		});
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" /> 
<p><br/></p>
<div class="container">
  <h2>'알파벳 + 숫자' 랜덤하게 생성하기</h2>
  <hr class="border-secondary">
  <div><input type="button" value="랜덤문자생성" onclick="randomAlphaNumeric()" class="btn btn-success"></div>
  <div><input type="button" value="새로고침" onclick="location.reload();" class="btn btn-warning"></div>
  <hr class="border-secondary">
  <div id="demo"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>