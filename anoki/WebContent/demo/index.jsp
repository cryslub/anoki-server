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
	  type: "POST",
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
	number : <input name="number" value="01012345678"/>
	country : <input name="country" value="82"/>
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
	text : <input name="text" value="hi"/>
	back : <input name="back" value="ffffff"/>
	user : <input name="user" value="2"/>	
	<input type="button" value="메시지  전송" onClick="send('#sendMessage')"/>
</form>
</html>
