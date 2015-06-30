<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

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

</script>

<div id="response">

</div>

<form id="login" enctype='application/json' action="/anoki/rest/auth/log" method="post">
	폰번호 number : <input name="number" value="01012345678"/>
	국가코드 country : <input name="country" value="82"/>
	device : <input name="device" value="12345678"/>
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
	<input type="button" value="인증번호전송" onClick="send('#authNumber')"/>
</form>


<form id="updateUser" enctype='application/json' action="/anoki/rest/user" method="put">
	apiKey : <input name="apiKey" value="549918230"/>
	사용자명 name : <input name="name" value="사용자명"/>	
	picture : <input name="picture" value="-1"/>
	문구 text : <input name="text" value="문구"/>
	번호공개여부 showPhone(Y/N) : <input name="showPhone" value="Y"/>
	계정 account : <input name="account" value="account@somewhere.com"/>
	암호 pass : <input name="pass" value="1234"/>

	<input type="button" value="사용자수정" onClick="send('#updateUser')"/>
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

<form id="message" enctype='application/json' action="/anoki/rest/etc/message" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="메시지 조회" onClick="send('#message')"/>
</form>

<form id="friend" enctype='application/json' action="/anoki/rest/etc/friend" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	<input type="button" value="친구 조회" onClick="send('#friend')"/>
</form>

<form id="sendMessage" enctype='application/json' action="/anoki/rest/etc/send/message" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	내용 text : <input name="text" value="hi"/>
	back : <input name="back" value="ffffff"/>
	user : <input name="user" value="2"/>	
	<input type="button" value="메시지  전송" onClick="send('#sendMessage')"/>
</form>

<form id="prayer" enctype='application/json' action="/anoki/rest/prayer" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	기도제목 text : <input name="text" value="기도제목"/>
	기도배경 back : <input name="back" value="기도배경"/>
	공개설정 (Y/N)pub : <input name="pub" value="Y"/>		
	<input type="button" value="기도작성" onClick="send('#prayer')"/>
</form>

<form id="scrap" enctype='application/json' action="/anoki/rest/prayer/scrap" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	id : <input name="id" value="2"/>	
	<input type="button" value="담기" onClick="send('#scrap')"/>
</form>

<form id="pray" enctype='application/json' action="/anoki/rest/prayer/pray" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	id : <input name="id" value="2"/>	
	<input type="button" value="기도하기" onClick="send('#pray')"/>
</form>

<form id="prayerDetail" enctype='application/json' action="/anoki/rest/prayer/detail" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	id : <input name="id" value="2"/>	
	<input type="button" value="기도상세 조회" onClick="send('#prayerDetail')"/>
</form>

<form id="userDetail" enctype='application/json' action="/anoki/rest/user/detail" method="post">
	id : <input name="id" value="2"/>	
	<input type="button" value="사용지상세 조회" onClick="send('#userDetail')"/>
</form>

<form id="userPrayer" enctype='application/json' action="/anoki/rest/user/prayer" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	searchId : <input name="searchId" value="2"/>	
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="사용자기도 조회" onClick="send('#userPrayer')"/>
</form>


<form id="scrapdPrayer" enctype='application/json' action="/anoki/rest/prayer/scrapd" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="담은 기도 조회" onClick="send('#scrapdPrayer')"/>
</form>


<form id="requestedPrayer" enctype='application/json' action="/anoki/rest/prayer/request" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="기도 요청 조회" onClick="send('#requestedPrayer')"/>
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
	searchId : <input name="searchId" value="2"/>
	<input type="button" value="그룹 상세 조회" onClick="send('#teamDetail')"/>
</form>


<form id="teamPrayer" enctype='application/json' action="/anoki/rest/team/prayer" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	searchId : <input name="searchId" value="2"/>	
	page : <input name="page" value="0"/>
	size : <input name="size" value="10"/>
	<input type="button" value="그룹기도 조회" onClick="send('#teamPrayer')"/>
</form>

<form id="team" enctype='application/json' action="/anoki/rest/team" method="post">
	apiKey : <input name="apiKey" value="549918230"/>
	name : <input name="name" value="그룹명"/>	
	picture : <input name="picture" value="-1"/>
	text : <input name="text" value="소개"/>
	공개설정 (전체공개P/이름만N/비밀S)scope : <input name="scope" value="P"/>
	맴버 가입 승인 (Y/N)joinAck : <input name="joinAck" value="Y"/>

	<input type="button" value="그룹 생성" onClick="send('#team')"/>
</form>

<form id="teamInvite" enctype='application/json' action="/anoki/rest/team/invite" method="post">
	team : <input name="team" value="2"/>	

	<input type="button" value="그룹원 초대" onClick="send('#teamInvite')"/>
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
	<input type="button" value="그룹 수정" onClick="send('#updateTeam')"/>
</form>


</html>
