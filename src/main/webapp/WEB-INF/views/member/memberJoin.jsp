<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberJoin.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${ctp}/js/woo.js"></script>
  <script>
    'use strict';
  
  	// 정규식정의...(아이디,닉네임(한글/영문,숫자,밑줄),성명(한글/영문),이메일,전화번호({2,3}/{3,4}/{4}))
  	let regMid = /^[a-zA-Z0-9_]{4,20}$/;
  	let regnickName = /^[a-zA-Z0-9가-힣]{4,20}$/;
  	let regName = /^[가-힣a-zA-Z]{4,30}$/;
  	let regTel = /^\d{2,3}-\d{3,4}-\d{4}$/;
  	let regEmail = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]{4,40}$/;
  	
  	
    // 회원가입에 필요한 정보 체크(정규식)
    function fCheck() {
    	let mid = myform.mid.value.trim();
    	let pwd = myform.pwd.value.trim();
    	let nickName = myform.nickName.value.trim();
    	let name = myform.name.value.trim();
    	
    	let tel1 = myform.tel1.value.trim();
    	let tel2 = myform.tel2.value.trim();
    	let tel3 = myform.tel3.value.trim();
    	let tel = tel1 + "-" + tel2 + "-" + tel3;
    	
    	let email1 = myform.email1.value.trim();
    	let email2 = myform.email2.value.trim();
    	let email = email1 + "@" + email2;
    	
    	
    	// 이메일....등등...
    	
    	let submitFlag = 0;
    	
    	if(!regMid.test(mid)) {
    		alert("아이디는 4~20자리의 영문 대/소문자와 숫자, 언더바(_)만 가능하다.");
    		myform.mid.focus();
    		return false;
    	}
    	else if(!regNickName.test(nickName)) {
    		alert("닉네임은 4~20자리의 영문 대/소문자와 숫자만 가능하다. ");
    		myform.nickName.focus();
    		return false;
    	}
    	else if(!regName.test(name)) {
    		alert("이름은 4~20자리의 영문 대/소문자와 숫자만 가능하다. ");
    		myform.name.focus();
    		return false;
    	}
    	else if(!regEmail.test(email)) {
    		alert("이메일주소는 영문 대/소문자와 숫자로만 이루어진다. ");
    		myform.email1.focus();
    		return false;
    	}
    	else {
    		submitFlag = 1;
    	}
    	
    	// 앞의 개별 필수 항목 체크 완료후, 선택사항 입력시 체크처리
    	if(tel2 != "" && tel3 != "") {
    		//if(!regTel.test()) {
    		//	alert("전화번호형식을 확인하세요.(000-0000-0000)");
    		//	myform.tel2.focus();
    		//	return false;
    		//}
    		//else {
    			submitFlag = 1;
    		//}
    	}
    	
    	// 사진(jpg/gif/png)에 대한 체크, 용량은 1MByte 이내
    	
    	
    	
    	// 앞에서 모든 항목에 대한 유효성검사를 마치면 중복체크(아이디/닉네임)부분 확인처리
    	if(submitFlag == 1) {
    		// 아이디 중복버튼체크
				//	if(){		
    		// 닉네임 중복버튼체크
			//		}    
    	//	else {
    			
	    		myform.submit();
    	//	}
    	}
    	else {
    		alert("회원폼의 내용을 확인하세요");
    	}
    	
    	
    	
    }
    
    
    // 아이디 중복검사
    function idCheck() {
    	let mid = myform.mid.value;
    	
    	if(mid.trim() == "") {
    		alert("아이디를 입력하세요");
    		myform.mid.focus();
    	}
    	else {
    		$.ajax({
    			url : "${ctp}/member/memberIdCheck",
    			type : "get",
    			data : {mid : mid},
    			success:function(res) {
    				if(res != '0') {
    					alert("이미 사용중인 아이디 입니다. 다시 입력하세요");
    					myform.mid.focus();
    				}
    				else alert("사용 가능한 아이디 입니다.");
    			},
    			error : function() { alert("전송오류!"); }
    		});
    	}
    }
    
    
    // 닉네임 중복검사
    
    
    // 이메일 인증번호 받기
    
    
    function emailCertification() {
    	// 필수입력사항 유효성 체크 완료후 인증번호를 받는다.
    	let mid = myform.mid.value;
    	let pwd = myform.pwd.value;
    	let nickName = myform.nickName.value;
    	let name = myform.name.value;
    	let email1 = myform.email1.value.trim();
    	let emailReg = /^[a-zA-Z0-9]{5,18}$/;
    	
    	if(mid == '') {
    		alert('아이디를 입력하세요.');
    		myform.mid.focus();
    		return false;
    	}
    	else if(pwd == '') {
    		alert('비밀번호를 입력하세요.');
    		myform.pwd.focus();
    		return false;
    	}
    	else if(nickName == '') {
    		alert('닉네임을 입력하세요.');
    		myform.nickName.focus();
    		return false;
    	}
    	else if(name == '') {
    		alert('이름을 입력하세요.');
    		myform.name.focus();
    		return false;
    	}
    	else if(email1 == '') {
    		alert('이메일 주소를 입력하세요.');
    		myform.email1.focus();
    		return false;
    	}
    	else if(!emailReg.test(email1)) {
    		alert('이메일 주소의 앞부분은 영어 소문자/대문자/숫자 조합으로 5자 이상 18자 이하입니다.');
    		myform.email1.focus();
    		return false;
    	}
    	else {
    		let email1 = myform.email1.value.trim();
        let email2 = myform.email2.value.trim();
        let email = email1 + "@" + email2;
        let regEmail = /^[a-zA-Z]+[0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]+$/;
        
    	
    		if(!regEmail.test(email)) {
    			alert('이메일 주소는 영어소문자or대문자/숫자의 조합이어야 합니다.');
    			myform.email1.focus();
    			return false;
    		}
    		else {
    	// 인증번호 보내기
		    	$.ajax({
		    		url : "${ctp}/member/memberEmailCheck",
		    		type : "post",
		    		data : {email : email},
		    		success:function(res) {
		    			if(res != '0') {
			    			alert("인증번호가 발송되었습니다. \n메일 확인후 인증번호를 아래에 입력해 주세요.");
		    			}
		    			else {
			    			alert("인증확인버튼을 다시 눌러주세요.");
		    			}
		    		},
		    		error : function() { alert("전송오류!"); }
		    	});
    		}
    	}
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<form name="myform" method="post" enctype="multipart/form-data">
  	<table class="table table-bordered text-center">
      <tr>
        <td colspan="2"><font size="5">회 원 가 입</font></td>
      </tr>
      <tr>
        <th>아이디</th>
        <td>
          <div class="input-group">
	          <input type="text" name="mid" id="mid" placeholder="아이디를 입력하세요" autofocus required class="form-control" />
	          <input type="button" value="아이디중복체크" id="midBtn" onclick="idCheck()" class="btn-secondary btn-sm"/>
          </div>
        </td>
      </tr>
      <tr>
        <th>비밀번호</th>
        <td><input type="password" name="pwd" id="pwd" placeholder="비밀번호를 입력하세요." required class="form-control" /></td>
      </tr>
      <tr>
        <th>닉네임</th>
        <td>
          <div class="input-group">
	          <input type="text" name="nickName" id="nickName" placeholder="닉네임을 입력하세요" required class="form-control" />
	          <input type="button" value="닉네임중복체크" id="nickNameBtn" onclick="nickCheck()" class="btn-secondary btn-sm"/>
          </div>
        </td>
      </tr>
      <tr>
        <th>성명</th>
        <td><input type="text" name="name" id="name" placeholder="성명을 입력하세요" required class="form-control" /></td>
      </tr>
      <tr>
        <th>성별</th>
        <td>
        	<input type="radio" name="gender" id="gender1" value="남자" />남자 &nbsp;
        	<input type="radio" name="gender" id="gender2" value="여자" checked />여자
        </td>
      </tr>
      <tr>
        <th>이메일</th>
        <td>
          <div class="input-group">
	          <input type="text" name="email1" id="email1" class="form-control" required />@
	          <select name="email2" id="email2" class="form-select">
	          	<option>naver.com</option>
	          	<option>hanmail.net</option>
	          	<option>gmail.com</option>
	          	<option>daum.net</option>
	          	<option>yahoo.com</option>
	          	<option>hatmail.com</option>
	          	<option>nate.com</option>
	          </select>
          </div>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="button" value="인증번호받기" onclick="emailCertification()" class="btn btn-success btn-sm" />
        </td>
      </tr>
      
      <tbody id="addContent" style="display: none">
	      <tr>
	        <th>생일</th>
	        <td><input type="date" name="birthday" id="birthday" value="<%=java.time.LocalDate.now()%>" class="form-control" /></td>
	      </tr>
	      <tr>
	        <th>전화번호</th>
	        <td>
	          <div class="input-group">
		          <select name="tel1" class="form-select">
		            <option value="010" selected>010</option>
		            <option value="02">서울</option>
		            <option value="031">경기</option>
		            <option value="032">인천</option>
		            <option value="041">충남</option>
		            <option value="042">대전</option>
		            <option value="043">충북</option>
		            <option value="051">부산</option>
		            <option value="052">울산</option>
		            <option value="051">부산</option>
		            <option value="054">경북</option>
		            <option value="055">경남</option>
		            <option value="061">전남</option>
		            <option value="062">광주</option>
		            <option value="063">전북</option>
		            <option value="064">제주</option>
		          </select>-
		          <input type="text" name="tel2" id="tel2" class="form-control" />-
		          <input type="text" name="tel3" id="tel3" class="form-control" />
	          </div>
	        </td>
	      </tr>
	      <tr>
	        <th>주소</th>
	        <td>
	        	<input type="text" name="postcode" id="sample6_postcode" placeholder="우편번호">
						<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
						<input type="text" name="address" id="sample6_address" placeholder="주소"><br>
						<input type="text" name="detailAddress" id="sample6_detailAddress" placeholder="상세주소">
						<input type="text" name="extraAddress" id="sample6_extraAddress" placeholder="참고항목">
	        </td>
	      </tr>
	      <tr>
	        <th>직업</th>
	        <td>
	          <select class="form-control">
	            <option>학생</option>
	            <option>회사원</option>
	            <option>공무원</option>
	            <option>군인</option>
	            <option>자영업</option>
	            <option>의사</option>
	            <option>법조인</option>
	            <option>세무인</option>
	            <option>가사</option>
	            <option selected>기타</option>
	          </select>
	        </td>
	      </tr>
	      <tr>
	        <th>취미</th>
	        <td>
	          <input type="checkbox" name="hobby" value="등산">등산
	          <input type="checkbox" name="hobby" value="낚시">낚시
	          <input type="checkbox" name="hobby" value="바둑">바둑
	          <input type="checkbox" name="hobby" value="수영">수영
	          <input type="checkbox" name="hobby" value="독서">독서
	          <input type="checkbox" name="hobby" value="승마">승마
	          <input type="checkbox" name="hobby" value="영화감상">영화감상
	          <input type="checkbox" name="hobby" value="축구">축구
	          <input type="checkbox" name="hobby" value="농구">농구
	          <input type="checkbox" name="hobby" value="배구">배구
	          <input type="checkbox" name="hobby" value="기타" checked>기타
	        </td>
	      </tr>
	      <tr>
	        <th>자기소개</th>
	        <td>
	          <textarea rows="5" class="form-control" placeholder="자기소개를 입력하세요."></textarea>
	        </td>
	      </tr>
	      <tr>
	        <th>정보공개</th>
	        <td>
	        	<input type="radio" name="userinfor" id="userinfor1" value="공개" checked />공개 &nbsp;
	        	<input type="radio" name="userinfor" id="userinfor2" value="비공개" />비공개
	        </td>
	      </tr>
	      <tr>
	        <th>회원사진</th>
	        <td>
	          <input type="file" name="fname" id="file" onchange="imgCheck(this)" class="form-control" />
	        </td>
	      </tr>
	      <tr>
	        <td colspan="2">
	          <input type="button" value="회원가입" onclick="fCheck()" class="btn btn-success me-2"/>
	          <input type="reset" value="다시입력" class="btn btn-warning me-2"/>
	          <input type="button" value="돌아가기" onclick="location.href='${ctp}/user/userMain';" class="btn btn-primary"/>
	        </td>
	      </tr>
      </tbody>
    </table>
    <input type="hidden" name="email" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>