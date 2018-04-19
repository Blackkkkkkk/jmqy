package com.dhxx.common.wechat;

import java.util.ArrayList;
import java.util.List;

public class MyTemplates {
	
	/**      
	 * 开发者调用模板消息接口时需提供模板ID:_aOV_1j-X0XiGsf3qE1YkJCzOS791YoDYXg5eg2RqEg
	 * 标题：匹配提醒
	 * {{first.DATA}}
	 * 类型：{{keyword1.DATA}}
	 * 进度：{{keyword2.DATA}}
	 * {{remark.DATA}}
	 */
	public static Template matchTemplate(String openId, String first, int type){
		Template tem=new Template();
		tem.setTemplateId("_aOV_1j-X0XiGsf3qE1YkJCzOS791YoDYXg5eg2RqEg");
		tem.setTopColor("#00DD00");
		tem.setToUser(openId);
		tem.setUrl(WechatUtils.downloadURL);
				
		List<TemplateParam> paras=new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first",first,"#FF3333"));
		if(type == 1)
			paras.add(new TemplateParam("keyword1","系统匹配","#0044BB"));
		if(type == 2)
			paras.add(new TemplateParam("keyword1","手动预订","#0044BB"));
		paras.add(new TemplateParam("keyword2","匹配中","#0044BB"));
		paras.add(new TemplateParam("remark","请点击下载app查看详情！","#AAAAAA"));
				
		tem.setTemplateParamList(paras);
				
		return tem;
	}
	
	/**      
	 * 开发者调用模板消息接口时需提供模板ID:Qw_lcgdReOKgtSk0TkDsG1cnLJEeV_j-QPkrpzKC3xY
	 * 标题：收到报价通知
	 * {{first.DATA}}
	 * 发送时间：{{keyword1.DATA}}
	 * 报价方：{{keyword2.DATA}}
	 * 报价产品：{{keyword3.DATA}}
	 * 报价详情：{{keyword4.DATA}}
	 * {{remark.DATA}}
	 */
	public static Template quoteTemplate(String openId, String first, int type){
		Template tem=new Template();
		tem.setTemplateId("Qw_lcgdReOKgtSk0TkDsG1cnLJEeV_j-QPkrpzKC3xY");
		tem.setTopColor("#00DD00");
		tem.setToUser(openId);
		tem.setUrl(WechatUtils.downloadURL);
				
		List<TemplateParam> paras=new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first",first,"#FF3333"));
		paras.add(new TemplateParam("keyword1","系统匹配","#0044BB"));
		paras.add(new TemplateParam("keyword2","手动预订","#0044BB"));
		paras.add(new TemplateParam("keyword3","匹配中","#0044BB"));
		paras.add(new TemplateParam("keyword4","匹配中","#0044BB"));
		paras.add(new TemplateParam("remark","请点击下载app查看详情！","#AAAAAA"));
				
		tem.setTemplateParamList(paras);
				
		return tem;
	}

}
