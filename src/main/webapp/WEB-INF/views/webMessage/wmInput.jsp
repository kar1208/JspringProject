<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>wmInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel="stylesheet" type="text/css" href="${ctp}/css/linkBlue.css"/>
  <script>
    'use strict';
    
    function fCheck() {
    	let receiveId = myform.receiveId.value;
    	let title = myform.title.value;
    	let content = myform.content.value;
    	
    	if(receiveId.trim() == "") {
    		alert("받는 사람 아이디를 입력하세요");
    		myform.receive.focus();
    	}
    	else if(title.trim() == "") {
    		alert("메세지 제목을 입력하세요");
    		myform.title.focus();
    	}
    	else if(content.trim() == "") {
    		alert("메세지 내용을 입력하세요");
    		myform.content.focus();
    	}
    	else myform.submit();
    }
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
  <form name="myform" method="post" action="wmInputOk">
    <table class="table table-bordered">
      <tr>
        <th>보내는사람</th>
        <td><input type="text" name="sendId" value="${sMid}" readonly class="form-control"/></td>
      </tr>
      <tr>
        <th>받는사람</th>
        <td>
          <div class="input-group">
            <input type="text" name="receiveId" value="" class="form-control"/>
            <input type="button" value="주소록" onclick="" class="btn btn-success"/>
          </div>
        </td>
      </tr>
      <tr>
        <th>메세지 제목</th>
        <td>
          <div class="input-group">
            <input type="text" name="title" placeholder="메세지 제목을 입력하세요" class="form-control"/>
          </div>
        </td>
      </tr>
      <tr>
        <th>메세지 내용</th>
        <td>
          <div class="input-group">
            <textarea rows="5" name="content" placeholder="메세지 내용을 입력하세요" class="form-control"></textarea>
          </div>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="button" value="메세지전송" onclick="fCheck()" class="btn btn-primary me-2"/>
          <input type="reset" value="다시쓰기" class="btn btn-info me-2"/>
          <input type="button" value="돌아가기" onclick="location.href='webMessage?mSw=1';" class="btn btn-warning"/>
        </td>
      </tr>
    </table>
  </form>
</div>
<p><br/></p>
</body>
</html>