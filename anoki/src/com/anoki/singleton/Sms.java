package com.anoki.singleton;

import com.gabia.api.ApiClass;

public class Sms {
	public static void sendSms(String type,String number,String text){

		String api_id = "goodwill2015";		// sms.gabia.com 이용 ID
		String api_key = "940d3909388277177c9fcf12bb90beaa";	// 환결설정에서 확인 가능한 SMS API KEY
		
		ApiClass api = new ApiClass(api_id, api_key);
		
		// 단문 발송 테스트
		String arr[] = new String[7];
		arr[0] = type;								// 발송 타입 sms or lms
		arr[1] = "1";				// 결과 확인을 위한 KEY ( 중복되지 않도록 생성하여 전달해 주시기 바랍니다. )
		arr[2] = "1";							//  LMS 발송시 제목으로 사용 SMS 발송시는 수신자에게 내용이 보이지 않음.
		arr[3] = text;					// 본문 (90byte 제한)
		arr[4] = "0231519997";			// 발신 번호
		arr[5] = number;				// 수신 번호
		arr[6] = "0";									//예약 일자 "2013-07-30 12:00:00" 또는 "0" 0또는 빈값(null)은 즉시 발송 
		
		System.out.print(arr.toString());
		
		String responseXml = api.send(arr);
		System.out.println("response xml : \n" + responseXml);

	}
}
