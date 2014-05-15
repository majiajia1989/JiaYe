package com.e1858.wuye.pojo;


public class AjaxResult
{

        private boolean flag;
        private String message;
        private String returnUrl;
        private Object attachment;
        
		public boolean isFlag()
		{
			return flag;
		}
		public void setFlag(boolean flag)
		{
			this.flag = flag;
		}
		public String getMessage()
		{
			return message;
		}
		public void setMessage(String message)
		{
			this.message = message;
		}
		public String getReturnUrl()
		{
			return returnUrl;
		}
		public void setReturnUrl(String returnUrl)
		{
			this.returnUrl = returnUrl;
		}
		public Object getAttachment()
		{
			return attachment;
		}
		public void setAttachment(Object attachment)
		{
			this.attachment = attachment;
		}
        
	
}
