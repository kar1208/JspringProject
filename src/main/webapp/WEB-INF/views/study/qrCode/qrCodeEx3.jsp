<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>qrCodeEx3.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
  	'use strict';
  	
  	function qrCodeCreate3() {
  		let mid = $("#mid").val();
  		let name = $("#name").val();
  		let email = $("#email").val();
  		let movieName = $("#movieName").val();
  		let movieDate = $("#movieDate").val();
  		let movieTime = $("#movieTime").val();
  		let movieAdult = $("#movieAdult").val();
  		let movieChild = $("#movieChild").val();
  		
  		if(mid.trim()=="" || name.trim()=="" || email.trim()=="") {
  			alert("개인정보를 입력해 주세요.");
  			$("#mid").focus();
  			return false;
  		}
  		
  		let query = {
  				mid : mid,
  				name : name,
  				email : email,
  				movieName : movieName,
  				movieDate : movieDate,
  				movieTime : movieTime,
  				movieAdult : movieAdult,
  				movieChild : movieChild,
  		}
  		$.ajax({
  			url : "qrCodeCreate3",
  			type : "post",
  			data : query,
 			success:function(res) {
  				if(res != "") {
	  				let qrCode = "QR Code 명 : " + res + "<br/>";
	  				qrCode += '<img src="${ctp}/ticket/'+res+'.png" />';
	  				$("#demo").html(qrCode);
	  				$("#qrCode").val(res);
  				}
  				else alert("QR코드 생성 실패~~~");
  			},
  			error: function() { alert("전송 오류!"); }
  		});
  	}
  	
  	// QR코드 검색결과(DB)
  	function qrCodeCheck() {
  		let qrCode = $("#qrCode").val();
  		$.ajax({
  			url : "qrCodeSearch",
  			type : "post",
  			data : {qrCode : qrCode},
  			success:function(vo) {
  				if(vo != "") {
	  				let str = '';
	  			//	$("#qrCodeView").show();
	  				str += '구매자 ID : '+vo.mid+'<br/>';
	  				str += '예매(발행)일자 '+vo.publishDate+'<br/>: ';
	  				str += '구매자성명 : '+vo.name+'<br/>';
	  				str += '구매자 이메일: '+vo.email+'<br/>';
	  				str += '영화제목 : '+vo.movieName+'<br/>';
	  				str += '상영일자 : '+vo.movieDate+'<br/>';
	  				str += '상영시간 : '+vo.movieTime+'<br/>';
	  				str += '성인티켓 구매수 : '+vo.movieAdult+'<br/>';
	  				str += '어린이티켓 구매수 : '+vo.movieChild;
	  				$("#demoQrCode").html(str);
  					
  				}
  				else alert("티켓 구매 내역이 없습니다. 확인하세요.");
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
  <h2>티켓 정보를 QR코드로 생성하기</h2>
  <div>영화티켓 예매정보를 QR코드로 생성</div>
  <div>(생성된 QR Code를 메일로 보내드립니다. QR코드를 입장시 매표소에 제시해 주세요)</div>
  <form name="myform" method="post">
  	<table class="table table-bordered text-center">
  		<tr>
  			<th>아이디</th>
  			<td><input type="text" name="mid" id="mid" value="${sMid}" required class="form-control"></td>
  		</tr>
  		<tr>
  			<th>성명</th>
  			<td><input type="text" name="name" id="name" value="${sNickName}" required class="form-control"></td>
  		</tr>
  		<tr>
  			<th>이메일</th>
  			<td><input type="text" name="email" id="email" value="beorc8686@naver.com" required class="form-control"></td>
  		</tr>
  		<tr>
  			<th>영화명선택</th>
  			<td>
  				<select name="movieName" id="movieName" class="form-select">
  					<option value="">영화를 선택해 주세요.</option>
  					<option selected>승부</option>
  					<option>로비</option>
  					<option>헤레틱</option>
  					<option>케이온</option>
  					<option>너의이름은</option>
  					<option>패왕별희</option>
  				</select>
  			</td>
  		</tr>
  		<tr>
  			<th>상영일자</th>
  			<td><input type="date" name="movieDate" id="movieDate" value="<%=LocalDate.now() %>" required class="form-control"></td>
  		</tr>
  		<tr>
  			<th>상영시간</th>
  			<td>
  				<select name="movieTime" id="movieTime" class="form-select">
  					<option value="">상영시간을 선택해 주세요.</option>
  					<option>10시00분</option>
  					<option>12시30분</option>
  					<option>15시00분</option>
  					<option>17시30분</option>
  					<option selected>20시00분</option>
  					<option>22시30분</option>
  				</select>
  			</td>
  		</tr>
  		<tr>
  			<th>인원수</th>
  			<td>
  				성인 <input type="number" name="movieAdult" id="movieAdult" value="1" min="1" required class="form-control">
  				어린이 <input type="number" name="movieChild" id="movieChild" value="0" min="0" required class="form-control">
  			</td>
  		</tr>
  		<tr>
  			<td colspan="2">
  				<input type="button" value="티켓발행" onclick="qrCodeCreate3()" class="btn btn-success"/>
  				<input type="reset" value="다시입력" class="btn btn-warning"/>
  				<input type="button" value="돌아가기" onclick="location.href='qrCodeForm';" class="btn btn-info"/>
  			</td>
  		</tr>
  	</table>
  </form>
  <hr class="border-secondary">
  <div>생성된 QR 코드</div>
  <div id="demo"></div>
  <hr class="border-secondary">
  <!-- <div id="qrCodeView" style="display:none"> -->
  <div>
		<h4>생성된 QR코드의 내용</h4>
		<div>(QR코드를 리더기로 스캔한 결과...DB와 비교처리한다.)</div>
		<div class="input-group">
			<div><input type="text" name="qrCode" id="qrCode" class="form-control" /></div>
			<div><input type="button" value="DB검색하기" onclick="qrCodeCheck()" class="btn btn-success"/></div>
		</div>
		<div id="demoQrCode"></div>  	
	</div>

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>