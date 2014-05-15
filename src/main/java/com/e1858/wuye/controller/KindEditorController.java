package com.e1858.wuye.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.utils.JsonUtil;
import com.e1858.wuye.utils.MimeUtil;

@Controller
@RequestMapping("/kindeditor")
public class KindEditorController
{
	private SimpleDateFormat dirFormater = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat fileFormater = new SimpleDateFormat("yyyyMMddhhmmss-SSS");
	private SimpleDateFormat fileDateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String uploadPath;
	private String fileSeparator = "/";//System.getProperty("file.separator");
	private static HashMap<String, String> extMap;
	private long maxSize = 104857600;

	// 定义允许上传的文件扩展名
	static
	{
		extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "pdf,doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,gif,jpg,jpeg,png,bmp");
	}

	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public String demo()
	{
		return "kindeditor/demo";
	}

	@RequestMapping(value = "/demo", method = RequestMethod.POST)
	public String demo(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println(request.getParameter("key"));
		System.out.println(request.getParameter("picurl"));
		System.out.println(request.getParameter("content"));
		return "kindeditor/demo";
	}

	@RequestMapping(value = "/uploadCorp", method = RequestMethod.POST)
	public void uploadCorp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileUploadException
	{
		String midPath;
		String savePath;
		String saveFile;
		String downloadFile;
		String fileName;
		String fileExt;
		String fileUrl;
		File uploadDir;
		Date now;
		long fileSize;
		String dirName = request.getParameter("dir");
		UploadJson uploadJson = new UploadJson();
		uploadPath = CommonConstant.UPLOAD_PATH;
		midPath = applyCorpUploadPath(request);
		uploadDir = new File(uploadPath);
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		// 检查是否有附件
		if (!ServletFileUpload.isMultipartContent(request))
		{
			uploadJson.setError(1);
			uploadJson.setMessage("上传文件为空,请选择文件。");
			response.getWriter().write(JsonUtil.toJson(uploadJson));
			return;
		}
		// 检查上传目录
		if (!uploadDir.exists())
		{
			uploadDir.mkdirs();
		}

		if (dirName == null)
		{
			dirName = "image";
		}
		if (!extMap.containsKey(dirName))
		{
			uploadJson.setError(1);
			uploadJson.setMessage("目录名不正确。");
			response.getWriter().write(JsonUtil.toJson(uploadJson));
			return;
		}
		fileUrl = request.getRequestURL().toString();
		fileUrl = fileUrl.substring(0,fileUrl.lastIndexOf(fileSeparator) + 1);
		fileUrl =	fileUrl.concat("file?file=");
		savePath = dirName.concat(fileSeparator);
		Collection<MultipartFile> files = ((DefaultMultipartHttpServletRequest) request).getFileMap().values();
		Iterator iterator = files.iterator();
		while (iterator.hasNext())
		{
			CommonsMultipartFile file = (CommonsMultipartFile) iterator.next();
			if (file.isEmpty())
			{
				uploadJson.setError(1);
				uploadJson.setMessage("上传文件为空,请选择文件。");
				response.getWriter().write(JsonUtil.toJson(uploadJson));
				return;
			}
			fileSize = file.getSize(); // 文件大小
			fileName = file.getOriginalFilename(); // 文件名
			fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); // 文件后缀
			if (fileSize > maxSize)
			{
				uploadJson.setError(1);
				uploadJson.setMessage("上传文件大小超过限制100M");
				response.getWriter().write(JsonUtil.toJson(uploadJson));
				return;
			}
			if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt))
			{
				uploadJson.setError(1);
				uploadJson.setMessage("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
				response.getWriter().write(JsonUtil.toJson(uploadJson));
				return;
			}
			savePath += dirFormater.format(now = new Date()) + "/";
			saveFile = fileFormater.format(now) + "." + fileExt;
			downloadFile = midPath + savePath + saveFile;
			uploadDir = new File(uploadPath +midPath + savePath);
			if (uploadDir.isFile() || !uploadDir.exists())
			{
				uploadDir.mkdirs();
			}
			try
			{
				file.transferTo(new File(uploadPath + midPath + savePath, saveFile));
				uploadJson.setUrl(fileUrl.concat(java.net.URLEncoder.encode(downloadFile, CommonConstant.ENCODING)));
			}
			catch (Exception ex)
			{
				uploadJson.setError(1);
				uploadJson.setMessage("保存文件失败,请重新提交!");
				ex.printStackTrace();
			}
			response.getWriter().write(JsonUtil.toJson(uploadJson));
		}
	}

	@RequestMapping("/filesCorp")
	public void filesCorp(@RequestParam String path, @RequestParam String order, @RequestParam String dir, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding(CommonConstant.ENCODING);
		// 上传目录路径，可以指定绝对路径，比如 /var/www/attached/
		String midpath;
		//uploadPath = applyUploadPath(request);
		uploadPath = CommonConstant.UPLOAD_PATH;
		
		String fileUrl = request.getRequestURL().toString();
		fileUrl = fileUrl.substring(0,fileUrl.lastIndexOf(fileSeparator) + 1);
		fileUrl =	fileUrl.concat("file?file=");
		order = order.toLowerCase();
		if (!extMap.containsKey(dir))
		{
			response.getWriter().println("Invalid Directory name.");
			return;
		}
		midpath = applyCorpUploadPath(request);
		uploadPath = uploadPath.concat(midpath).concat(dir).concat(fileSeparator);
		fileUrl = fileUrl.concat(midpath).concat(dir).concat(fileSeparator);
		File saveDirFile = new File(uploadPath);
		if (!saveDirFile.exists())
		{
			saveDirFile.mkdirs();
		}
		String currentPath = uploadPath + path;
		String currentUrl = fileUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path))
		{
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		// 不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0)
		{
			response.getWriter().println("Access is not allowed.");
			return;
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/"))
		{
			response.getWriter().println("Parameter is not valid.");
			return;
		}
		// 目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory())
		{
			response.getWriter().println("Directory does not exist.");
			return;
		}
		// 遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if (currentPathFile.listFiles() != null)
		{
			for (File file : currentPathFile.listFiles())
			{
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory())
				{
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				}
				else if (file.isFile())
				{
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", extMap.get("image").contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", fileDateFormater.format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if (order.equals("size"))
		{
			Collections.sort(fileList, new SizeComparator());
		}
		else if (order.equals("type"))
		{
			Collections.sort(fileList, new TypeComparator());
		}
		else
		{
			Collections.sort(fileList, new NameComparator());
		}
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("moveup_dir_path", moveupDirPath);
		msg.put("current_dir_path", currentDirPath);
		msg.put("current_url", currentUrl);
		msg.put("total_count", fileList.size());
		msg.put("file_list", fileList);
		response.getWriter().println(JSON.toJSONString(msg));
	}
	
	@RequestMapping(value = "/uploadCommunity", method = RequestMethod.POST)
	public void uploadCommunity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileUploadException
	{
		String midPath;
		String savePath;
		String saveFile;
		String downloadFile;
		String fileName;
		String fileExt;
		String fileUrl;
		File uploadDir;
		Date now;
		long fileSize;
		String dirName = request.getParameter("dir");
		UploadJson uploadJson = new UploadJson();
		uploadPath = CommonConstant.UPLOAD_PATH;
		midPath = applyCommunityUploadPath(request);
		uploadDir = new File(uploadPath);
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		// 检查是否有附件
		if (!ServletFileUpload.isMultipartContent(request))
		{
			uploadJson.setError(1);
			uploadJson.setMessage("上传文件为空,请选择文件。");
			response.getWriter().write(JsonUtil.toJson(uploadJson));
			return;
		}
		// 检查上传目录
		if (!uploadDir.exists())
		{
			uploadDir.mkdirs();
		}

		if (dirName == null)
		{
			dirName = "image";
		}
		if (!extMap.containsKey(dirName))
		{
			uploadJson.setError(1);
			uploadJson.setMessage("目录名不正确。");
			response.getWriter().write(JsonUtil.toJson(uploadJson));
			return;
		}
		fileUrl = request.getRequestURL().toString();
		fileUrl = fileUrl.substring(0,fileUrl.lastIndexOf(fileSeparator) + 1);
		fileUrl =	fileUrl.concat("file?file=");
		savePath = dirName.concat(fileSeparator);
		Collection<MultipartFile> files = ((DefaultMultipartHttpServletRequest) request).getFileMap().values();
		Iterator iterator = files.iterator();
		while (iterator.hasNext())
		{
			CommonsMultipartFile file = (CommonsMultipartFile) iterator.next();
			if (file.isEmpty())
			{
				uploadJson.setError(1);
				uploadJson.setMessage("上传文件为空,请选择文件。");
				response.getWriter().write(JsonUtil.toJson(uploadJson));
				return;
			}
			fileSize = file.getSize(); // 文件大小
			fileName = file.getOriginalFilename(); // 文件名
			fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); // 文件后缀
			if (fileSize > maxSize)
			{
				uploadJson.setError(1);
				uploadJson.setMessage("上传文件大小超过限制100M");
				response.getWriter().write(JsonUtil.toJson(uploadJson));
				return;
			}
			if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt))
			{
				uploadJson.setError(1);
				uploadJson.setMessage("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
				response.getWriter().write(JsonUtil.toJson(uploadJson));
				return;
			}
			savePath += dirFormater.format(now = new Date()) + "/";
			saveFile = fileFormater.format(now) + "." + fileExt;
			downloadFile = midPath + savePath + saveFile;
			uploadDir = new File(uploadPath +midPath + savePath);
			if (uploadDir.isFile() || !uploadDir.exists())
			{
				uploadDir.mkdirs();
			}
			try
			{
				file.transferTo(new File(uploadPath + midPath + savePath, saveFile));
				uploadJson.setUrl(fileUrl.concat(java.net.URLEncoder.encode(downloadFile, CommonConstant.ENCODING)));
			}
			catch (Exception ex)
			{
				uploadJson.setError(1);
				uploadJson.setMessage("保存文件失败,请重新提交!");
				ex.printStackTrace();
			}
			response.getWriter().write(JsonUtil.toJson(uploadJson));
		}
	}

	@RequestMapping("/filesCommunity")
	public void filesCommunity(@RequestParam String path, @RequestParam String order, @RequestParam String dir, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding(CommonConstant.ENCODING);
		// 上传目录路径，可以指定绝对路径，比如 /var/www/attached/
		String midpath;
		//uploadPath = applyUploadPath(request);
		uploadPath = CommonConstant.UPLOAD_PATH;
		
		String fileUrl = request.getRequestURL().toString();
		fileUrl = fileUrl.substring(0,fileUrl.lastIndexOf(fileSeparator) + 1);
		fileUrl =	fileUrl.concat("file?file=");
		order = order.toLowerCase();
		if (!extMap.containsKey(dir))
		{
			response.getWriter().println("Invalid Directory name.");
			return;
		}
		midpath = applyCommunityUploadPath(request);
		uploadPath = uploadPath.concat(midpath).concat(dir).concat(fileSeparator);
		fileUrl = fileUrl.concat(midpath).concat(dir).concat(fileSeparator);
		File saveDirFile = new File(uploadPath);
		if (!saveDirFile.exists())
		{
			saveDirFile.mkdirs();
		}
		String currentPath = uploadPath + path;
		String currentUrl = fileUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path))
		{
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		// 不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0)
		{
			response.getWriter().println("Access is not allowed.");
			return;
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/"))
		{
			response.getWriter().println("Parameter is not valid.");
			return;
		}
		// 目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory())
		{
			response.getWriter().println("Directory does not exist.");
			return;
		}
		// 遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if (currentPathFile.listFiles() != null)
		{
			for (File file : currentPathFile.listFiles())
			{
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory())
				{
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				}
				else if (file.isFile())
				{
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", extMap.get("image").contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", fileDateFormater.format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if (order.equals("size"))
		{
			Collections.sort(fileList, new SizeComparator());
		}
		else if (order.equals("type"))
		{
			Collections.sort(fileList, new TypeComparator());
		}
		else
		{
			Collections.sort(fileList, new NameComparator());
		}
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("moveup_dir_path", moveupDirPath);
		msg.put("current_dir_path", currentDirPath);
		msg.put("current_url", currentUrl);
		msg.put("total_count", fileList.size());
		msg.put("file_list", fileList);
		response.getWriter().println(JSON.toJSONString(msg));
	}	

	@RequestMapping("/file")
	public void file(@RequestParam String file, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding(CommonConstant.ENCODING);
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		uploadPath = CommonConstant.UPLOAD_PATH;;
		String downLoadPath = uploadPath + file;
		String fileExt = file.substring(file.lastIndexOf(".") + 1).toLowerCase();
		try
		{
			long fileLength = new File(downLoadPath).length();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType(MimeUtil.getMimeByExt(fileExt));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
			{
				bos.write(buff, 0, bytesRead);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (bis != null) bis.close();
			if (bos != null) bos.close();
		}
	}

	@RequestMapping("/download")
	public void download(@RequestParam String file, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding(CommonConstant.ENCODING);
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		uploadPath = applyUploadPath(request);
		String downLoadPath = uploadPath + file;
		try
		{
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(file.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
			{
				bos.write(buff, 0, bytesRead);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (bis != null) bis.close();
			if (bos != null) bos.close();
		}
	}
	
	//按用户私有路径
	private String applyUploadPath(HttpServletRequest request)
	{	
		String result = CommonConstant.UPLOAD_PATH;
		if(!result.endsWith(fileSeparator))
		{
			result = result.concat(fileSeparator);
		}
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		if(null != appContext)
		{
			SysUser user = appContext.getUser();
			if(null != user)
			{
				result = result.concat(String.valueOf(user.getId())).concat(fileSeparator);
			}
		}
		return result;
	}
	
	//按公司私有路径
	private String applyCorpUploadPath(HttpServletRequest request)
	{	
		String result = "/";
		if(!result.endsWith(fileSeparator))
		{
			result = result.concat(fileSeparator);
		}
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		if(null != appContext)
		{
				SysUser user = appContext.getUser();
			if(null != user)
			{
				result = result.concat(CommonConstant.CORP_IMAGE_PATH).concat(String.valueOf(user.getCorp().getId())).concat(fileSeparator);
			}
		}
		return result;
	}	
	
	//按小区私有路径
	private String applyCommunityUploadPath(HttpServletRequest request)
	{	
		String result = "/";
		if(!result.endsWith(fileSeparator))
		{
			result = result.concat(fileSeparator);
		}
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		if(null != appContext)
		{
				Community community= appContext.getCommunity();
			if(null != community)
			{
				result = result.concat(CommonConstant.COMMUNITY_IMAGE_PATH).concat(String.valueOf(community.getId())).concat(fileSeparator);
			}
		}
		return result;
	}	
	
	class UploadJson
	{
		private int error = 0;
		private String url;
		private String message;

		public int getError()
		{
			return error;
		}

		public void setError(int error)
		{
			this.error = error;
		}

		public String getUrl()
		{
			return url;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}

		public String getMessage()
		{
			return message;
		}

		public void setMessage(String message)
		{
			this.message = message;
		}
	}

	class FileJson
	{
		private String datetime;
		private String filename;
		private long filesize;
		private String filetype;
		private boolean has_file;
		private boolean is_dir;
		private boolean is_photo;

		public String getDatetime()
		{
			return datetime;
		}

		public void setDatetime(String datetime)
		{
			this.datetime = datetime;
		}

		public String getFilename()
		{
			return filename;
		}

		public void setFilename(String filename)
		{
			this.filename = filename;
		}

		public long getFilesize()
		{
			return filesize;
		}

		public void setFilesize(long filesize)
		{
			this.filesize = filesize;
		}

		public String getFiletype()
		{
			return filetype;
		}

		public void setFiletype(String filetype)
		{
			this.filetype = filetype;
		}

		public boolean isHas_file()
		{
			return has_file;
		}

		public void setHas_file(boolean has_file)
		{
			this.has_file = has_file;
		}

		public boolean isIs_dir()
		{
			return is_dir;
		}

		public void setIs_dir(boolean is_dir)
		{
			this.is_dir = is_dir;
		}

		public boolean isIs_photo()
		{
			return is_photo;
		}

		public void setIs_photo(boolean is_photo)
		{
			this.is_photo = is_photo;
		}

	}

	class FilesJson
	{
		private String current_dir_path;
		private String current_url;
		private String file_list;
		private String moveup_dir_path;
		private long total_count;

		public String getCurrent_dir_path()
		{
			return current_dir_path;
		}

		public void setCurrent_dir_path(String current_dir_path)
		{
			this.current_dir_path = current_dir_path;
		}

		public String getCurrent_url()
		{
			return current_url;
		}

		public void setCurrent_url(String current_url)
		{
			this.current_url = current_url;
		}

		public String getFile_list()
		{
			return file_list;
		}

		public void setFile_list(String file_list)
		{
			this.file_list = file_list;
		}

		public String getMoveup_dir_path()
		{
			return moveup_dir_path;
		}

		public void setMoveup_dir_path(String moveup_dir_path)
		{
			this.moveup_dir_path = moveup_dir_path;
		}

		public long getTotal_count()
		{
			return total_count;
		}

		public void setTotal_count(long total_count)
		{
			this.total_count = total_count;
		}
	}

	class NameComparator implements Comparator
	{
		public int compare(Object a, Object b)
		{
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir")))
			{
				return -1;
			}
			else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir")))
			{
				return 1;
			}
			else
			{
				return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	class SizeComparator implements Comparator
	{
		public int compare(Object a, Object b)
		{
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir")))
			{
				return -1;
			}
			else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir")))
			{
				return 1;
			}
			else
			{
				if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize")))
				{
					return 1;
				}
				else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize")))
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
		}
	}

	class TypeComparator implements Comparator
	{
		public int compare(Object a, Object b)
		{
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir")))
			{
				return -1;
			}
			else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir")))
			{
				return 1;
			}
			else
			{
				return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
			}
		}
	}
}
