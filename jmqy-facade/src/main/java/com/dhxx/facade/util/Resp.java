package com.dhxx.facade.util;

import com.dhxx.facade.message.ResponseMessage;

public class Resp {
	
    public static ResponseMessage SUCCESS() {
        return new ResponseMessage();
    }

    public static ResponseMessage SUCCESS(Object result) {
        return new ResponseMessage(result);
    }

}
