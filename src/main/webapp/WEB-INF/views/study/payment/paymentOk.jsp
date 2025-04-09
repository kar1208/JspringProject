<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>paymentOk.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" /> 
<p><br/></p>
<div class="container">
  <h2>결제가 정상적으로 처리 되었습니다.</h2>
  <div>(주문/결제하신 내용은 다음과 같습니다.)</div>
  <hr class="border-secondary">
  <table class="table table-bordered">
  	<tr>
  		<th>결제금액</th>
  		<td>${vo.amount}</td>
  	</tr>
  	<tr>
  		<th>구매 물품명</th>
  		<td>${vo.name}</td>
  	</tr>
  	<tr>
  		<th>이메일</th>
  		<td>${vo.buyer_email}</td>
  	</tr>
  	<tr>
  		<th>주문자</th>
  		<td>${vo.buyer_name}</td>
  	</tr>
  	<tr>
  		<th>연락처</th>
  		<td>${vo.buyer_tel}</td>
  	</tr>
  	<tr>
  		<th>주소</th>
  		<td>${vo.buyer_addr}</td>
  	</tr>
  	<tr>
  		<th>우편번호</th>
  		<td>${vo.buyer_postcode}</td>
  	</tr>
  </table>
  <hr class="border-secondary">
  <div><a href="${ctp}/study/payment/paymentForm" class="btn btn-success">다시결제하기</a></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>