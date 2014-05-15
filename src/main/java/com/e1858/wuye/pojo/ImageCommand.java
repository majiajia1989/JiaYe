package com.e1858.wuye.pojo;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.e1858.wuye.common.CommonConstant;


public class ImageCommand {

	private long id = -1;
	private String title;
	private String descript;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	//@URL(message= CommonConstant.ValidMessage.URL)
	private String imageUrl;
	private String url = "";
	private boolean defalutImage = false;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isDefalutImage() {
		return defalutImage;
	}
	public void setDefalutImage(boolean defalutImage) {
		this.defalutImage = defalutImage;
	}
}
