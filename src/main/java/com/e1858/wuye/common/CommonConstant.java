package com.e1858.wuye.common;

import org.apache.log4j.Logger;

public class CommonConstant
{
	public final static String CONTEXT_APP="CONTEXT_APP";
	public final static String CONTEXT_USER="CONTEXT_USER";
	public final static String CONTEXT_CACHE="CONTEXT_CACHE";
	public final static String CONTEXT_CORP="CONTEXT_CORP";
	public final static String CONTEXT_BOARD = "CONTEXT_BOARD";
	public final static String CONTEXT_BOARDS = "CONTEXT_BOARDS";
	public final static String CONTEXT_TOPIC = "CONTEXT_TOPIC";
	public final static String CONTEXT_TOPICS = "CONTEXT_TOPICS";
	public final static String CONTEXT_POST= "CONTEXT_POST";
	public final static String CONTEXT_POSTS = "CONTEXT_POSTS";
	public final static String PAGE_CURRENT = "PAGE_CURRENT";
	public final static String PAGE_NEXT = "PAGE_NEXT";
	public final static String PAGE_PREVIOUS = "PAGE_PREVIOUS";
	public final static int PAGE_SIZE = 10;
	public final static String CONTEXT_COMMUNITY="CONTEXT_COMMUNITY";
	public final static String UPLOAD_PATH="/UploadFiles";
	public final static String ENCODING="UTF-8";
	public final static String KEYWORD_SEPARATOR = " ";
	public final static String ARTICLECHILD_SEPARATOR = ",";
	public final static String COMMUNITY_IMAGE_PATH="Community/";
	public final static String CORP_IMAGE_PATH="Corp/";
	public final static String SHOP_IMAGE_PATH="Shop/";
	public final static Logger exceptionLogger = Logger.getLogger("exception");
	public final static String ANONYMOUS_SUBCRIBER_OPENID = "-1";

	public static final class ArticleLinkType {
		public static final int Outer = 1;
		public static final int Inner = 0;
	}
	
	public static final class MsgType
	{
		public static final String Article = "news";
		public static final String Text = "text";
	}
	public static final class DefaultMsgType{
		public static final byte AttentionReply=0;
		public static final byte NoMatchReply=1;
	}
	public static final class TreeNodeType{
		public static final String Folder="folder";
		public static final String Item="item";
	}		
	public static final class ConsumeType{
		public static final long Electricity=1l;
		public static final long Water=2l;
		public static final long Gas=3l;
	}		
	
	
	public static final class ValidMessage
	{
		public static final String NotBlank = "此内容为必填项,请输入";
		public static final String NotEmpty = "此内容为必填项,请输入";
		public static final String EMAIL = "请输入正确格式的电子邮件";
		public static final String URL = "请输入合法的网址";
		public static final String Digits = "只能输入整数";
		public static final String CreditCardNumber = "请输入合法的信用卡号";
		public static final String MobilePhone = "请输入正确的手机号码";
		public static final String TelePhone = "请输入正确的电话号码";
		public static final String Mobile_TelePhone = "请输入正确的手机或电话号码";
		public static final String QQ = "请输入正确的QQ号码";
		public static final String MIN = "请输入一个最小为{0}的值";
		public static final String MAX = "请输入一个最大为{0}的值";
		public static final String RANGE = "请输入一个范围为{min}到{max}的值";
	}
}
