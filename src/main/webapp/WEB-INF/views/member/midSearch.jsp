<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>midSearch.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" /> 
<p><br/></p>
<div class="container">
  <form class="myform">
		<c:forEach var="vo" items="${vos}" varStatus="st">
			<div>
				<c:choose>
					<c:when test="${empty vo}">
						<p>조회결과가 없습니다.</p>
					</c:when>
					<c:otherwise>
						<p>${vo.mid}</p>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach>	
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>