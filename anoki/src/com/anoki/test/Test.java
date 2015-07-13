package com.anoki.test;

import com.gabia.api.ApiClass;
import com.gabia.api.ApiResult;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String api_id = "goodwill2015";		// sms.gabia.com 이용 ID
		String api_key = "940d3909388277177c9fcf12bb90beaa";	// 환결설정에서 확인 가능한 SMS API KEY
		
		ApiClass api = new ApiClass(api_id, api_key);
		
		// 단문 발송 테스트
		String arr[] = new String[7];
		arr[0] = "sms";								// 발송 타입 sms or lms
		arr[1] = "1";				// 결과 확인을 위한 KEY ( 중복되지 않도록 생성하여 전달해 주시기 바랍니다. )
		arr[2] = "1";							//  LMS 발송시 제목으로 사용 SMS 발송시는 수신자에게 내용이 보이지 않음.
		arr[3] = "아노키 인증번호 를 인증번호 입력란에 입력해 주세요";					// 본문 (90byte 제한)
		arr[4] = "01012345678";			// 발신 번호
		arr[5] = "01099109969";				// 수신 번호
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

		
	}

}
