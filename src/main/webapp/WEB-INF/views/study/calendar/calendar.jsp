<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>calendar.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
  	#td1,#td8,#td15,#td22,#td29,#td36 {color:red}
  	#td7,#td14,#td21,#td28,#td35,#td42 {color:blue}
  	
  	td.today {
  		background-color: pink;
  		color: #fff;
  		font-size: 1.5em;
  	}
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" /> 
<p><br/></p>
<div class="container">
  <div>
  	<button onclick="location.href='calendar?yy=${yy-1}&mm=${mm}';" class="btn btn-secondary btn-sm" title="이전년도">◁◁</button>
  	<button onclick="location.href='calendar?yy=${yy}&mm=${mm-1}';" class="btn btn-secondary btn-sm" title="이전월">◀</button>
  	<font size="5">${yy}년 ${mm+1}월</font>
  	<button onclick="location.href='calendar?yy=${yy}&mm=${mm+1}';" class="btn btn-secondary btn-sm" title="다음월">▶</button>
  	<button onclick="location.href='calendar?yy=${yy+1}&mm=${mm}';" class="btn btn-secondary btn-sm" title="다음년도">▷▷</button>
  	&nbsp;&nbsp;
  	<button onclick="location.href='calendar';" class="btn btn-secondary btn-sm" title="오늘날짜">🏩</button>
  </div>
  <br/>
  <div class="text-center" style="height:450px;">
  	<table class="table table-bordered" style="height:100%">
  		<tr>
  			<th style="vertical-align:middle;width:13%;color:red">일</th>
  			<th style="vertical-align:middle;width:13%">월</th>
  			<th style="vertical-align:middle;width:13%">화</th>
  			<th style="vertical-align:middle;width:13%">수</th>
  			<th style="vertical-align:middle;width:13%">목</th>
  			<th style="vertical-align:middle;width:13%">금</th>
  			<th style="vertical-align:middle;width:13%;color:blue">토</th>
  		</tr>
  		<tr>
  			<c:set var="cnt" value="1"/>
  			<!-- 이전월의 일자를 빈공간에 채운다. -->
  			<c:forEach var="prevDay" begin="${prevLastDay-(startWeek-2)}" end="${prevLastDay}">
  				<td style="font-size:0.8em;color:#aaa">
  					${prevYear}-${prevMonth+1}-${prevDay}
  				</td>
  				<c:set var="cnt" value="${cnt=cnt+1}"/>
  			</c:forEach>
  			
  			<!-- 실제 화면에 출력시켜주는 날짜 -->
  			<c:forEach begin="1" end="${lastDay}" varStatus="st">
  				<c:set var="todaySw" value="${yy==toYear && mm==toMonth && st.count==toDay ? 1 : 0}"/>
  				<td id="td${cnt}" ${todaySw == 1 ? 'class=today': ''}>
  					${st.count}
  				</td>
  				<c:if test="${cnt % 7 == 0}"><tr></tr></c:if>
  				<c:set var="cnt" value="${cnt=cnt+1}"></c:set>
  			</c:forEach>
  			
  			<!-- 다음월의 일자를 빈공간에 채운다.-->
  				<c:forEach var="nextDay" begin="${nextStartWeek}" end="7" varStatus="st">
  				<td style="font-size:0.8em;color:#aaa">
  					${nextYear}-${nextMonth+1}-${st.count}
  				</td>
  				<c:set var="cnt" value="${cnt=cnt+1}"/>
  			</c:forEach>
  		</tr>
  	</table>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>