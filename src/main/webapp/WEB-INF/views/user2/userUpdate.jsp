<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>userInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <form method="post">
  	<table class="table table-bordered text-center">
      <tr>
        <td colspan="2"><font size="5">회 원 정 보 수 정</font></td>
      </tr>
      <tr>
        <th>아이디</th>
        <td class="text-start">${vo.mid}</td>
      </tr>
      <tr>
        <th>비밀번호</th>
        <td><input type="password" name="pwd" id="pwd" value="${vo.pwd}" placeholder="비밀번호를 입력하세요." required class="form-control" /></td>
      </tr>
      <tr>
        <th>성명</th>
        <td><input type="text" name="name" id="name" value="${vo.name}" placeholder="성명을 입력하세요" required class="form-control" /></td>
      </tr>
      <tr>
        <th>나이</th>
        <td><input type="number" name="age" id="age" value="${vo.age}" placeholder="나이를 입력하세요" required class="form-control" /></td>
      </tr>
      <tr>
        <th>성별</th>
        <td>
        	<input type="radio" name="gender" id="gender1" value="남자" ${vo.gender=='남자' ? 'checked' : ''} />남자 &nbsp;
        	<input type="radio" name="gender" id="gender2" value="여자" ${vo.gender=='여자' ? 'checked' : ''} />여자
        </td>
      </tr>
      <tr>
        <th>주소</th>
        <td><input type="text" name="address" id="address" value="${vo.address}" class="form-control" /></td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="submit" value="회원정보수정" class="btn btn-success me-2"/>
          <input type="reset" value="다시입력" class="btn btn-warning me-2"/>
          <input type="button" value="돌아가기" onclick="location.href='userList';" class="btn btn-primary"/>
        </td>
      </tr>
    </table>
    <input type="hidden" name="idx" value="${vo.idx}"/>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>