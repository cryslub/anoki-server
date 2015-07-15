<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<style>
	textarea{
		width:900px;
		height:100px;
	}
</style>
<script language="javascript" type="text/javascript" src="jquery-1.11.3.min.js"></script>  
<script>

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function send(id){	
	var form = $(id);
	var formData = JSON.stringify(form.serializeObject());
	$.ajax({
	  type: form.attr("method"),
	  url: form.attr("action"),
	  data: formData,
	  success: function(){},
	  dataType: "json",
	  contentType : "application/json",
	  success:function(response){
		  $("#response").html(JSON.stringify(response));
	  }
	});
}



function sendJson(id){	
	var form = $(id);
	$.ajax({
	  type: form.attr("method"),
	  url: form.attr("action"),
	  data: form.find("textarea").val(),
	  success: function(){},
	  dataType: "json",
	  contentType : "application/json",
	  success:function(response){
		  $("#response").html(JSON.stringify(response));
	  }
	});
}


</script>

<div id="response">

</div>

<form id="login" enctype='application/json' action="/anoki/rest/user/log" method="post">
	계정 account : <input name="account" value="someone@somewhere.com"/>
	암호 pass : <input name="pass" value="dkfeweql@3!"/>
	<input type="button" value="로그인" onClick="send('#login')"/>
</form>


<form id="requestAuth" enctype='application/json' action="/anoki/rest/auth/request" method="post">
	number : <input name="number" value="01012345678"/>
	country : <input name="country" value="82"/>
	device : <input name="device" value="12345678"/>
	<input type="button" value="인증요청" onClick="send('#requestAuth')"/>
</form>

<form id="authNumber" enctype='application/json' action="/anoki/rest/auth/send/number" method="post">
	number : <input name="number" value="01012345678"/>
	country : <input name="country" value="82"/>
	device : <input name="device" value="12345678"/>
	auth : <input name="auth" value="1234"/>
	<input type="button" value="인증번호 입력" onClick="send('#authNumber')"/>
</form>


<form id="user" enctype='application/json' action="/anoki/rest/user" method="post">
	계정 account : <input name="account" value="someone@somewhere.com"/>
	암호 pass : <input name="pass" value="dkfeweql@3!"/>
	국가번호 country: <input name="country" value="82"/>
	전화번호 phone: <input name="phone" value="01012345678"/>
	<input type="button" value="계정생성" onClick="send('#user')"/>
</form>


<form id="account" enctype='application/json' action="/anoki/rest/auth/account" method="post">
	국가번호 country: <input name="country" value="82"/>
	전화번호 phone: <input name="number" value="01012345678"/>
	<input type="button" value="계정확인" onClick="send('#account')"/>
</form>

<form id="pass" enctype='application/json' action="/anoki/rest/auth/pass" method="post">
	계정 account : <input name="account" value="someone@somewhere.com"/>
	암호 pass : <input name="pass" value="dkfeweql@3!"/>
	<input type="button" value="암호확인" onClick="send('#pass')"/>
</form>



<form id="updateUser" enctype='application/json' action="/anoki/rest/user" method="put">
	apiKey : <input name="apiKey" value="549918230"/>
	사용자명 name : <input name="name" value="사용자명"/>	
	picture : <input name="picture" value="-1"/>
	문구 text : <input name="text" value="문구"/>
	번호공개여부 showPhone(Y/N) : <input name="showPhone" value="Y"/>
	암호 pass : <input name="pass" value="felawef#@"/>
	국가번호 country: <input name="country" value="82"/>
	전화번호 phone: <input name="phone" value="01012345678"/>
	
	<input type="button" value="사용자수정" onClick="send('#updateUser')"/>
</form>


<form id="userDetail" enctype='application/json' action="/anoki/rest/user/detail" method="post">
	id : <input name="id" value="2"/>	
	<input type="button" value="사용지상세 조회" onClick="send('#userDetail')"/>
</form>



<form id="prayer" enctype='application/json' action="/anoki/rest/prayer" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	기도제목 text : <input name="text" value="기도제목"/>
	기도배경 back : <input name="back" value="기도배경"/>
	그룹 id team : <input name="team" value="2"/>	
	공개설정 (Y/N)pub : <input name="pub" value="Y"/> <br/>

	<input type="button" value="기도작성" onClick="send('#prayer')"/>
</form>


<form id="prayerJson" enctype='application/json' action="/anoki/rest/prayer" method="post">
	<textarea>{"apiKey":"549918230","text":"기도제목","back":"기도배경","team":"2","pub":"Y","friends":[4,5,],"phone":[{"number":"0103224555","country":"82"},{"number":"0102223455","country":"82"}]}
	</textarea>
	<input type="button" value="기도작성" onClick="sendJson('#prayerJson')"/>
</form>

<form id="recent" enctype='application/json' action="/anoki/rest/prayer/recent" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="최근 조회" onClick="send('#recent')"/>
</form>

<form id="alarm" enctype='application/json' action="/anoki/rest/etc/alarm" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	<input type="button" value="알림 조회" onClick="send('#alarm')"/>
</form>


<form id="sendMessage" enctype='application/json' action="/anoki/rest/etc/send/message" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	내용 text : <input name="text" value="hi"/>
	back : <input name="back" value="ffffff"/>
	user : <input name="user" value="2"/>	
	<input type="button" value="메시지  전송" onClick="send('#sendMessage')"/>
</form>


<form id="message" enctype='application/json' action="/anoki/rest/etc/message" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="메시지 조회" onClick="send('#message')"/>
</form>

<form id="friend" enctype='application/json' action="/anoki/rest/friend/list" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	<input type="button" value="친구 조회" onClick="send('#friend')"/>
</form>



<form id="userPrayer" enctype='application/json' action="/anoki/rest/user/prayer" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	사용자 ID searchId : <input name="searchId" value="2"/>	
	완료여부 (Y/N) searchKey : <input name="searchKey" value="N"/>	
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="사용자기도 조회" onClick="send('#userPrayer')"/>
</form>


<form id="scrap" enctype='application/json' action="/anoki/rest/prayer/scrap" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	id : <input name="id" value="2"/>	
	<input type="button" value="담기" onClick="send('#scrap')"/>
</form>


<form id="scrapdPrayer" enctype='application/json' action="/anoki/rest/prayer/scrapd" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	완료여부 (Y/N) searchKey : <input name="searchKey" value="N"/>	
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="담은 기도 조회" onClick="send('#scrapdPrayer')"/>
</form>


<form id="pray" enctype='application/json' action="/anoki/rest/prayer/pray" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	id : <input name="id" value="2"/>	
	<input type="button" value="기도하기" onClick="send('#pray')"/>
</form>

<form id="prayerDetail" enctype='application/json' action="/anoki/rest/prayer/detail" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	searchId : <input name="searchId" value="2"/>	
	<input type="button" value="기도상세 조회" onClick="send('#prayerDetail')"/>
</form>

<form id="reply" enctype='application/json' action="/anoki/rest/prayer/reply" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	기도 ID prayer : <input name="prayer" value="2"/>	
	내용 text : <input name="text" value="댓글"/>	
	사진 ID picture : <input name="picture" value="-1"/>	
	구분(댓글/응답) type(S/R) : <input name="type" value="S"/>	
	공개여부(Y/N) pub : <input name="pub" value="Y"/>	

	<input type="button" value="댓글 작성" onClick="send('#reply')"/>
</form>




<form id="requestedPrayer" enctype='application/json' action="/anoki/rest/prayer/request" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="기도 요청 조회" onClick="send('#requestedPrayer')"/>
</form>



<form id="team" enctype='application/json' action="/anoki/rest/team" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	name : <input name="name" value="그룹명"/>	
	picture : <input name="picture" value="-1"/>
	text : <input name="text" value="소개"/>
	공개설정 (전체공개P/이름만N/비밀S)scope : <input name="scope" value="P"/>
	맴버 가입 승인 (Y/N)joinAck : <input name="joinAck" value="Y"/>
	월 결제 달란트 dalant  <input name="dalant" value="2000"/>
	<input type="button" value="그룹 생성" onClick="send('#team')"/>
</form>


<form id="userTeam" enctype='application/json' action="/anoki/rest/user/team" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	<input type="button" value="사용자 그룹 조회" onClick="send('#userTeam')"/>
</form>

<form id="searchTeam" enctype='application/json' action="/anoki/rest/team/search" method="post">
	searchKey : <input name="searchKey" value="검색어"/>
	<input type="button" value="그룹 검색" onClick="send('#searchTeam')"/>
</form>


<form id="teamDetail" enctype='application/json' action="/anoki/rest/team/detail" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	그룹 ID searchId : <input name="searchId" value="2"/>
	<input type="button" value="그룹 상세 조회" onClick="send('#teamDetail')"/>
</form>


<form id="teamPrayer" enctype='application/json' action="/anoki/rest/team/prayer" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	그룹 ID  searchId : <input name="searchId" value="2"/>	
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="그룹기도 조회" onClick="send('#teamPrayer')"/>
</form>


<form id="teamInvite" enctype='application/json' action="/anoki/rest/prayer" method="post">
	<textarea>{"team":2,"friends":[4,5,],"phone":[{"number":"0103224555","country":"82"},{"number":"0102223455","country":"82"}]}
	</textarea>
	<input type="button" value="그룹원 초대" onClick="sendJson('#teamInvite')"/>
</form>

<form id="teamMembers" enctype='application/json' action="/anoki/rest/team/members" method="post">
	그룹 아이디 searchId : <input name="searchId" value="2"/>	
	상태(가입/요청/초대) searchKey(J/R/I) : <input name="searchKey" value="J"/>
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>

	<input type="button" value="그룹원 조회" onClick="send('#teamMembers')"/>
</form>

<form id="updateMember" enctype='application/json' action="/anoki/rest/team/member" method="put">
	그룹 아이디 team : <input name="team" value="2"/>	
	user : <input name="user" value="3"/>	
	역할(리더/청지기/일반)role(3/2/1) : <input name="role" value="3"/>	

	<input type="button" value="그룹원 역할변경" onClick="send('#updateMember')"/>
</form>


<form id="updateTeam" enctype='application/json' action="/anoki/rest/team" method="put">
	id : <input name="id" value="2"/>	
	name : <input name="name" value="그룹명"/>	
	picture : <input name="picture" value="-1"/>
	text : <input name="text" value="소개"/>
	공개설정 (전체공개P/이름만N/비밀S)scope : <input name="scope" value="P"/>
	맴버 가입 승인 (Y/N)joinAck : <input name="joinAck" value="Y"/>
	프로필 권한 (리더/청지기/일반)profileAuth(3/2/1) : <input name="profileAuth" value="3"/>
	등록 권한 (리더/청지기/일반)registerAuth(3/2/1) : <input name="registerAuth" value="3"/>
	초대 권한 (리더/청지기/일반)inviteAuth(3/2/1) : <input name="inviteAuth" value="3"/>
	강퇴 권한 (리더/청지기/일반)leaveAuth(3/2/1) : <input name="leaveAuth" value="3"/>
	월 결제 달란트 dalant  <input name="dalant" value="2000"/>
	<input type="button" value="그룹 수정" onClick="send('#updateTeam')"/>
</form>

<form id="join" enctype='application/json' action="/anoki/rest/team/member/join" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	그룹 아이디 team : <input name="team" value="2"/>	
	state(가입/신청) state(J/R) : <input name="state" value="J"/>	

	<input type="button" value="그룹 가입" onClick="send('#join')"/>
</form>


<form id="updateMemberState" enctype='application/json' action="/anoki/rest/team/member/state" method="put">
	user : <input name="user" value="3"/>	
	그룹 아이디 team : <input name="team" value="2"/>	
	state(가입/신청) state(J/B) : <input name="state" value="J"/>	

	<input type="button" value="그룹원 상태 수정" onClick="send('#updateMemberState')"/>
</form>

<form id="updateFriendState" enctype='application/json' action="/anoki/rest/friend" method="put">
	apiKey : <input name="apiKey" value="549918230"/>

	friend : <input name="friend" value="3"/>	
	state(수락/차단) state(A/B) : <input name="state" value="A"/>	

	<input type="button" value="친구 상태 수정" onClick="send('#updateFriendState')"/>
</form>


<form id="addFriend" enctype='application/json' action="/anoki/rest/friend" method="post">
	<textarea>{"apiKey":"549918230","phone":[{"number":"0103224555","country":"82"},{"number":"0102223455","country":"82"}]}
	</textarea>
	<input type="button" value="친구추가" onClick="sendJson('#addFriend')"/>
</form>

<!-- <form id="charge" enctype='application/json' action="/anoki/rest/user/charge" method="post"> -->
<!-- 	apiKey : <input name="apiKey" value="549918230"/> -->
<!-- 	dalant : <input name="dalant" value="1000"/>	 -->
<!-- 	<input type="button" value="충전" onClick="send('#charge')"/> -->
<!-- </form> -->


<!-- <form id="dalant" enctype='application/json' action="/anoki/rest/user/dalant" method="post"> -->
<!-- 	apiKey : <input name="apiKey" value="549918230"/> -->
<!-- 	<input type="button" value="달란트 내역조회" onClick="send('#dalant')"/> -->
<!-- </form> -->


<form id="notice" enctype='application/json' action="/anoki/rest/etc/notice" method="post">
	<input type="button" value="공지 조회" onClick="send('#notice')"/>
</form>
</html>
