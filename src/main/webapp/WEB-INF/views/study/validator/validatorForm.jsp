<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>validatorForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
  	'use strict';
  	
  	function idCheck() {
  		let mid = $("#mid").val();
  		if(mid.trim() == "") {
  			alert("아이디를 입력하세요.");
  			$("#mid").focus();
  			return false;
  		}
  		
  		$.ajax({
  			url : "userIdCheck",
  			type : "post",
  			data : {mid : mid},
  			success:function(res) {
  				alert(res);
  			},
  			error:function() { alert("전송오류!"); }
  		});
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <form method="post">
  	<table class="table table-bordered text-center">
      <tr>
        <td colspan="2"><font size="5">회 원 가 입</font></td>
      </tr>
      <tr>
        <th>아이디</th>
        <td>
        	<div class="input-group">
	        	<input type="text" name="mid" id="mid" placeholder="아이디를 입력하세요" autofocus required class="form-control" />
	        	<input type="button" value="아이디중복체크" onclick="idCheck()" class="btn btn-success"/>
        	</div>
        </td>
      </tr>
      <tr>
        <th>성명</th>
        <td><input type="text" name="name" id="name" placeholder="성명을 입력하세요" required class="form-control" /></td>
      </tr>
      <tr>
        <th>나이</th>
        <td><input type="number" name="age" id="age" placeholder="나이를 입력하세요" required class="form-control" /></td>
      </tr>
      <tr>
        <th>주소</th>
        <td><input type="text" name="address" id="address" class="form-control" /></td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="submit" value="회원가입" class="btn btn-success me-2"/>
          <input type="reset" value="다시입력" class="btn btn-warning me-2"/>
        </td>
      </tr>
    </table>
  </form>
  <br/>
  <table class="table table-hover text-center">
    <tr class="table-secondary">
      <th>번호</th>
      <th>아이디</th>
      <th>성명</th>
      <th>비밀번호</th>
      <th>나이</th>
      <th>성별</th>
      <th>주소</th>
      <th>비고</th>
    </tr>
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <tr>
        <td>${vo.idx}</td>
        <td>${vo.mid}</td>
        <td>${vo.name}</td>
        <td>${vo.pwd}</td>
        <td>${vo.age}</td>
        <td>${vo.gender}</td>
        <td>${vo.address}</td>
        <td>
          <a href="${ctp}/user/userUpdate?idx=${vo.idx}" class="badge bg-warning text-decoration-none">수정</a> /
          <a href="javascript:delCheck(${vo.idx})" class="badge bg-danger text-decoration-none">삭제</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>