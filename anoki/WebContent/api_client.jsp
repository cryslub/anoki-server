<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gabia.api.ApiClass" %>
<%@ page import="com.gabia.api.ApiResult" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%

String api_id = "_SMS_ID_";		// sms.gabia.com 이용 ID
String api_key = "_API_KEY_";	// 환결설정에서 확인 가능한 SMS API KEY

ApiClass api = new ApiClass(api_id, api_key);

// 단문 발송 테스트
String arr[] = new String[7];
arr[0] = "sms";								// 발송 타입 sms or lms
arr[1] = "_UNIQUE_KEY_";				// 결과 확인을 위한 KEY ( 중복되지 않도록 생성하여 전달해 주시기 바랍니다. )
arr[2] = "_TITLE_";							//  LMS 발송시 제목으로 사용 SMS 발송시는 수신자에게 내용이 보이지 않음.
arr[3] = "_MESSAGE_";					// 본문 (90byte 제한)
arr[4] = "_CALLBACK_NUM_";			// 발신 번호
arr[5] = "_PHONE_NUM_";				// 수신 번호
arr[6] = "0";									//예약 일자 "2013-07-30 12:00:00" 또는 "0" 0또는 빈값(null)은 즉시 발송 

String responseXml = api.send(arr);
System.out.println("response xml : \n" + responseXml);

ApiResult res = api.getResult( responseXml );
System.out.println( "code = [" + res.getCode() + "] mesg=[" + res.getMesg() + "]" );

if( res.getCode().compareTo("0000") == 0 )
{
	String resultXml = api.getResultXml(responseXml);
	System.out.println("result xml : \n" + resultXml);
}


 //실제 발송 결과 조회
String arr[] = new String[7];
arr[0] = "status_by_ref";
arr[1] = "_UNIQUE_KEY_";	// 발송 시 사용하였던 _UNIQUE_KEY_ 로 조회
// 확인할 수 있는 건수는 제한이 될수도 있습니다. 같은 키가 존재하는 경우 최근의 한건이 나오게 변경될 예정입니다.

//ApiClass api = new ApiClass(arr[0], arr[1]);
String responseXml = api.send(arr);
System.out.println("response xml : \n" + responseXml);


ApiResult res = api.getResult( responseXml );
System.out.println( "code = [" + res.getCode() + "] mesg=[" + res.getMesg() + "]" );

if( res.getCode().compareTo("0000") == 0 )
{
	String resultXml = api.getResultXml(responseXml);
	System.out.println("result xml : \n" + resultXml);
}



/*  //SMS 발송 테스트 - 한번에 여러건 발송

ApiClass api = new ApiClass(api_id, api_key);
String phone_arr[] = {"01000000001","01000000002","01000000003"};
String phone_num = api.MakeToString(phone_arr);
//수신 번호를 배열에서 01000000001,01000000002,01000000003 의 String 형태로 변환
//따로 MakeToString 함수를 호출할 필요 없이 _phone_num_,_phone_num_,_phone_num_ 의 String 형태로 넘겨줘도 됩니다.

/*String arr[] = new String[7];
arr[0] = "multi_sms";					// SMS 발송
arr[1] = "_UNIQUE_KEY_";			// 결과 확인을 위한 KEY ( 중복되지 않도록 생성하여 전달해 주시기 바랍니다. )
arr[2] = "_TITLE_";						//  LMS 발송시 제목으로 사용 SMS 발송시는 수신자에게 내용이 보이지 않음.
arr[3] = "_MESSAGE_";				// 본문 (80byte 제한)
arr[4] = "_CALLBACK_NUM_";		// 발신 번호
arr[5] = phone_num;					// 수신 번호
arr[6] = "0";								//예약 일자 "2013-07-30 12:00:00" 또는 "0" 0또는 빈값(null)은 즉시 발송 

String responseXml = api.send(arr);
System.out.println("response xml : \n" + responseXml);

ApiResult res = api.getResult( responseXml );
System.out.println( "code = [" + res.getCode() + "] mesg=[" + res.getMesg() + "]" );

if( res.getCode().compareTo("0000") == 0 )
{
	String resultXml = api.getResultXml(responseXml);
	System.out.println("result xml : \n" + resultXml);
}*/


/* //LMS 발송 테스트 - 한번에 여러건 발송
String arr[] = new String[7];
arr[0] = "multi_lms";	// LMS 발송
arr[1] = "_UNIQUE_KEY_";			// 결과 확인을 위한 KEY ( 중복되지 않도록 생성하여 전달해 주시기 바랍니다. )
arr[2] = "_TITLE_";						//  LMS 발송시 제목으로 사용 SMS 발송시는 수신자에게 내용이 보이지 않음.
arr[3] = "_MESSAGE_";				// 본문 (80byte 제한)
arr[4] = "_CALLBACK_NUM_";		// 발신 번호
arr[5] = phone_num;					// 수신 번호
arr[6] = "0";								//예약 일자 "2013-07-30 12:00:00" 또는 "0" 0또는 빈값(null)은 즉시 발송 

String responseXml = api.send(arr);
System.out.println("response xml : \n" + responseXml);

ApiResult res = api.getResult( responseXml );
System.out.println( "code = [" + res.getCode() + "] mesg=[" + res.getMesg() + "]" );

if( res.getCode().compareTo("0000") == 0 )
{
	String resultXml = api.getResultXml(responseXml);
	System.out.println("result xml : \n" + resultXml);
}
*/

/*// 발송 결과 조회
String arr[] = new String[7];
arr[0] = "status_by_ref_all";	// LMS 발송
arr[1] = "_UNIQUE_KEY_";	// 결과 확인을 위한 KEY - 같은 Key 값으로 발송한 여러건의 문자 조회

String responseXml = api.send(arr);
System.out.println("response xml : \n" + responseXml);


ApiResult res = api.getResult( responseXml );
System.out.println( "code = [" + res.getCode() + "] mesg=[" + res.getMesg() + "]" );

if( res.getCode().compareTo("0000") == 0 )
{
	System.out.println("------------------");
	String[][] resultXml = api.getResultXml_All(responseXml);
	if(resultXml != null){
		for(int i=0; i<resultXml.length;i++){
			System.out.println("PHONENUM : " + resultXml[i][0]);
			System.out.println("MESSAGE : " + resultXml[i][1]);
		}
	}
}

*/

/* //예약 취소
String arr[] = new String[7];
arr[0] = "reserveCancel";
arr[1] = "_UNIQUE_KEY_";	// 발송 시 사용하였던 _UNIQUE_KEY_
arr[2] = "sms"; // 발송시 선택한 문자 타입 - sms, lms로 구분
arr[3] = ""; // 취소할 전화번호 - 여러개일 경우 , 로 구분 - 01000000001,01000000002

String responseXml = api.send(arr);
ApiResult res = api.getResult( responseXml );
if( res.getCode().compareTo("0000") == 0 )
{
	String resultXml = api.getResultXml(responseXml);
	System.out.println("result xml : \n" + resultXml);
}
*/
%>
</body>
</html>
