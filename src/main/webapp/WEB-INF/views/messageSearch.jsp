<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="user" value="${sessionScope.CONTEXT_APP.user}" />
<c:set var="menu_h" value="${sessionScope.CONTEXT_APP.menu_h.children}" />
<c:set var="menu_v" value="${sessionScope.CONTEXT_APP.menu_v.children}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>main</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<base target="mainFrame">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/uniform/css/uniform.default.css' />" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-wechat.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-responsive.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/plugins.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/pages/public.css' />" />
<!-- END THEME STYLES -->
</head>
<!-- BEGIN BODY -->
<body>
	<div class="col-md-12 ">
		<div class="portlet box iframe-box">
			<div class="portlet-title col-md-12">
				<div class="caption col-md-2">
					<i class="fa fa-edit"></i> 
					<c:if test="${articleCommand.msgID eq -1}">添加图文消息</c:if>
					<c:if test="${articleCommand.msgID ne -1}">编辑图文消息</c:if>
				</div>
				<div class="actions">
				<c:if test="${!(articleCommand.defaultMsg or articleCommand.attentionMsg)}">
					<button type="button" class="btn default"
						onclick="Javascript:{window.location='${ctx}/message/articles';}">
						<i class="m-icon-swapleft m-icon-white"></i>返回</button>
				</c:if>
				</div>
			</div>
			<div class="portlet-body form">
				<form class="form-horizontal" role="form">
					<div class="form-body">
						<div class="form-group">
							<label class="col-md-1 control-label">小区名称：</label>
							<div class="col-md-3">
								<select class="form-control"  multiple="multiple">
									<option VALUE="1">河南</option>
									<option VALUE="2">山西</option>
								</select>
							</div>	
							<label class="col-md-1 control-label">楼号：</label>
							<div class="col-md-3">
								<select class="form-control">
									<option>河南</option>
									<option>山西</option>			
								</select>
							</div>
							<label class="col-md-1 control-label">单元号：</label>
							<div class="col-md-3">
								<select class="form-control">
									<option>河南</option>
									<option>山西</option>			
								</select>
							</div>	
						</div>
						<div class="form-group">
							<label class="col-md-1 control-label">是否等级电话：</label>
							<div class="col-md-3">
								<select class="form-control">
									<option>河南</option>
									<option>山西</option>			
								</select>
							</div>	
							<label class="col-md-1 control-label">是否有车：</label>
							<div class="col-md-3">
								<select class="form-control">
									<option>河南</option>
									<option>山西</option>			
								</select>
							</div>	
						</div>			

						<div id="data_table_wrapper" class="dataTables_wrapper form-inline" role="grid">
						<div class="row">
						<div class="col-sm-12">
						<div id="data_table_length" class="dataTables_length col-sm-6">
						<label>
						<span style="color:grey;">每页显示</span>
						 <select size="1" name="data_table_length" aria-controls="data_table">
						 <option value="10" selected="selected">10</option>
						 <option value="25">25</option>
						 <option value="50">50</option>
						 <option value="100">100</option>
						 </select> 
						 <span style="color:grey;">条记录</span>
						 </label>
						 </div>
						 <div class="form-group col-sm-6">
							<div class="pull-right">
								<input type="text" class="form-control" placeholder="Enter text">
								<a class="btn iframe-btn"><i class="fa fa-search"></i>&nbsp;搜&nbsp;索</a>
							</div>
						</div>
						 </div>
						 <div class="col-md-12 col-sm-12"></div>
						 <div id="data_table_processing" class="dataTables_processing" style="visibility: hidden;">Processing...</div>
						 </div>
						 <div class="table-scrollable">
						 <table class="table table-striped table-bordered table-hover dataTable" id="data_table" aria-describedby="data_table_info">
							<thead>
								<tr role="row">
								<th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1" style="width: 70px;" aria-label=""></th><th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1" style="width: 321px;" aria-label="关键词">关键词</th>
								<th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1" style="width: 403px;" aria-label="匹配类型">匹配类型</th><th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1" style="width: 239px;" aria-label="回答">回答</th>
								<th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1" style="width: 240px;" aria-label="排序">排序</th><th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1" style="width: 241px;" aria-label="操作">操作</th></tr>
							</thead>
							
						<tbody role="alert" aria-live="polite" aria-relevant="all">
						<tr class="odd">
						<td class=" sorting_1">1</td>
						<td class=""></td>
						<td class=""><span style="">完全匹配</span></td>
						<td class=""><span style="display:block;max-width:500px;overflow:hidden;">sdhdfh 地方很多返回</span></td>
						<td class="">0</td>
						<td class="">
						<a class="btn default btn-xs grey" href="/WuYe/message/editArticle/32">
						<span>编辑</span>
						</a>&nbsp;
						<a href="#" class="btn default btn-xs red" disabled="true">
						<span>删除</span>
						</a>&nbsp;
						<a href="#" class="btn default btn-xs blue" onclick="javascript:Articles.cancleDefaultMsgByMsgID('/WuYe/','32');">取消默认回复
						</a>
						</td>
						</tr>
						<tr class="even">
						<td class=" sorting_1">2</td>
						<td class=""></td>
						<td class="">
						<span style="">完全匹配</span>
						</td>
						<td class=""><span style="display:block;max-width:500px;overflow:hidden;">asdfgs</span></td>
						<td class="">0</td>
						<td class=""><a class="btn default btn-xs grey" href="/WuYe/message/editArticle/31"><span>编辑</span></a>&nbsp;<a href="#" class="btn default btn-xs red" onclick="Articles.removeMsgByID(&quot;/WuYe/&quot;,&quot;asdfgs&quot;,31);"><span>删除</span></a>&nbsp;<a href="#" class="btn default btn-xs blue" onclick="javascript:Articles.setDefaultMsgByMsgID('/WuYe/','31');">设为默认回复</a></td></tr></tbody></table></div>
						<div class="row">
						<div class="col-md-6">
						<div class="dataTables_info" id="data_table_info"><span style="color:grey;">从 </span>1 <span style="color:grey;">到</span> 2  <span style="color:red;font-weight:bold;">/</span> <span style="color:grey;">共</span> 2 <span style="color:grey;">条记录</span></div>
						</div>
						<div class="col-md-6 left-pull">
						<div class="dataTables_paginate paging_bootstrap pull-right">
						<ul class="pagination" style="visibility: visible;"><li class="prev disabled"><a href="#" title="前一页"><i class="fa fa-angle-left"></i></a></li><li class="active"><a href="#">1</a></li><li class="next disabled"><a href="#" title="后一页"><i class="fa fa-angle-right"></i></a></li>
						</ul>
						</div>
						</div>
						</div>
						</div>																																			
					</div>
					<div class="form-actions fluid">
						<div class="col-md-offset-5 col-md-7">
							<button type="submit" class="btn iframe-btn"><i class="fa fa-check"></i>&nbsp;保&nbsp;存</button>
							<button type="button" class="btn iframe-btn"><i class=""></i>下一步</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--
	[if lt IE 9]> <script type="text/javascript" src="<c:url value='/assets/plugins/excanvas.min.js'/>"></script> <script type="text/javascript" src="<c:url value='/assets/plugins/respond.min.js'/>"></script> <![endif]
-->
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-1.10.2.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-migrate-1.2.1.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/bootstrap/js/bootstrap.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery.blockui.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery.cookie.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/uniform/jquery.uniform.min.js'/>">
		
	</script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript" src="<c:url value='/assets/scripts/app.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/scripts/pages/main.js'/>">
		
	</script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script type="text/javascript">
		$(function() {
			Main.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
