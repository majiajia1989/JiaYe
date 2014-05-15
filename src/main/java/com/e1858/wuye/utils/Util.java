package com.e1858.wuye.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util
{
	public static String byte2HexString(byte abyte0[])
	{
		StringBuffer stringbuffer = new StringBuffer();
		int i = abyte0.length;
		int j = 0;
		do
		{
			if (j >= i) return stringbuffer.toString();
			stringbuffer.append(Integer.toHexString(0x100 | 0xff & abyte0[j]).substring(1));
			j++;
		}
		while (true);
	}

	public static String getPassword(String name, String password)
	{
		return Encrypt.MD5(password + "{" + name + "}");
	}

	public static boolean verifyUrl(String content)
	{
		String urlRegex = "^(https?://)" + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" + "|" + "([0-9a-z_!~*'()-]+\\.)*" + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." + "[a-z]{2,6})" + "(:[0-9]{1,4})?" + "((/?)|" + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
		Pattern p = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(content);
		if (m.find())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean isLong(String resourceStr)
	{
		try
		{
			Long.parseLong(resourceStr);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	public static String HtmlEncode(String theString)
	{
		theString = theString.replace(">", "&gt;");
		theString = theString.replace("<", "&lt;");
		theString = theString.replace(" ", " &nbsp;");
		theString = theString.replace(" ", " &nbsp;");
		theString = theString.replace("\"", "&quot;");
		theString = theString.replace("\'", "&#39;");
		theString = theString.replace("\n", "<br/> ");
		return theString;
	}

	public static String HtmlDiscode(String theString)
	{
		theString = theString.replace("&gt;", ">");
		theString = theString.replace("&lt;", "<");
		theString = theString.replace("&nbsp;", " ");
		theString = theString.replace(" &nbsp;", " ");
		theString = theString.replace("&quot;", "\"");
		theString = theString.replace("&#39;", "\'");
		theString = theString.replace("<br/> ", "\n");
		return theString;
	}
}
