package com.dhxx.common.sms;


import java.rmi.RemoteException;
import java.util.Random;

public class SingletonSMSClient {
	
	private static String softwareSerialNo="8SDK-EMY-6699-RFYUP";
	private static String key="917080";
	public static String msg = "【马上走】您的验证码是code，5分钟内有效，请尽快使用。如非本人操作，请忽略本短信。";

	private static Client client=null;
	private SingletonSMSClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo,String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient(){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }
	public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }


	/*public static void main(String[] args) {
		try {
			SingletonSMSClient.getClient().sendSMS(new String[]{"13148913794"}, "【马上走】您已经成功升级为企业用户！", "", 5);

		//	SingletonSMSClient.getClient().sendSMS(new String[] {"13148913794"},"您已经成功升级为企业用户！", "",5);
		} catch (RemoteException e) {
			System.out.println(e);
		}
	}*/
	
}
