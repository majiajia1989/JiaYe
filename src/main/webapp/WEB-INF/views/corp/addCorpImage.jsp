<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>新增公司图片</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/themes/default.css' />"
	id="style_color" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style-wechat.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style-responsive.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/plugins.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap-toastr/toastr.css'/>" />
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/data-tables/DT_bootstrap.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/public.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/addCorpImage.css'/>" />	
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->

<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<div class="col-md-12 ">
		<div class="portlet box iframe-box ">
			<div class="portlet-title col-md-12">
				<div class="caption">
					<i class="fa fa-edit"></i>添加公司图片
				</div>
				<div class="actions">
					<button type="button" class="btn iframe-btn btn-return"
						onclick="Javascript:{window.location='${ctx}/corp/corpImage';}">
						<i class="fa fa-chevron-left"></i>&nbsp;返&nbsp;回
					</button>
				</div>
			</div>
			<div class="portlet-body form">
				<f:form role="form" class="form-horizontal form-validate"
					name="image-form" method="post" action="${ctx}/corp/addCorpImage"
					commandName="imageCommand">
					<f:input class="form-control" placeholder="" type="hidden"
						path="id" name="id" />
					<c:if test="${!empty errMsg}">
						<div class="alert alert-danger">
							<button class="close" data-close="alert"></button>
							<span>${errMsg}</span>
						</div>
					</c:if>
					<div class="form-body">
						<div
							class="form-group <f:errors path="title">has-error</f:errors>">
							<label class="col-md-2 control-label">标题</label>
							<div class="col-md-5">
								<f:input type="text" class="form-control" maxlength="50"
									placeholder="标题" name="title" path="title" />
							</div>
							<f:errors cssClass="help-block" element="span" path="title" />
						</div>
						<div
							class="form-group <f:errors path="imageUrl">has-error</f:errors>">
							<label class="col-md-2 control-label">图片资源</label>
							<div class="col-md-5">
								<a href="#" class="insert-img" id="cover-thumb-a"> <img
									id="cover-thumb-img" alt="" src=""></a>
								<f:input id="imageUrl" type="hidden" path="imageUrl"
									name="imageUrl" class="form-control required" />
							</div>
							<f:errors cssClass="help-block" element="span" path="imageUrl" />
							<label class="control-label"><span class="required">*</span></label>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">链接地址</label>
							<div class="col-md-5">
								<div id="articleTitle" class="" style="margin-bottom:5px;">
									<input id="articleName" type="text" name="articleName" class="form-control" readonly>
									<input id="url" type="hidden" name="url" class="form-control">
								</div>
								<button id="show-child-btn" class="btn iframe-btn"
									data-toggle="modal" onclick="javascript:AddCorpImage.response(${ctx}/)">
									<i class="fa fa-check-square-o"></i>&nbsp;选&nbsp;择
								</button>
							</div>
						</div>			
						<div
							class="form-group <f:errors path="descript">has-error</f:errors>"
							id="description-form-group">
							<label class="control-label  col-md-2">描述</label>
							<div class="col-md-8">
								<f:textarea class="form-control" rows="3" maxlength="200"
									autocomplete="off" placeholder="描述" path="descript" />
							</div>
							<f:errors cssClass="help-block" element="span" path="descript" />
						</div>
					</div>
					<div class="form-actions fluid">
						<div class="col-md-offset-2 col-md-5 pull-left">
							<button type="submit" id="submit-btn" class="btn iframe-btn">
								<i class="fa fa-check"></i>&nbsp;保&nbsp;存</button>
						</div>
					</div>
				</f:form>
			</div>
		</div>
	</div>
	
	<div class="modal fade bootstrap-dialog type-primary "
		id="corpimage-modal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="bootstrap-dialog-title">选择资源</h4>
				</div>
				<div class="modal-body">
					<table class="table table-striped table-bordered table-hover"
						id="article_Table">
						<thead>
							<tr role="row">
								<th />
								<th />
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>	
	<!-- END weixin FORM -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
	<script type="text/javascript" src="<c:url value='/assets/assets/plugins/respond.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/excanvas.min.js'/>"></script> 
	<![endif]-->
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery-1.10.2.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery-migrate-1.2.1.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootstrap/js/bootstrap.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery.blockui.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery.cookie.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/uniform/jquery.uniform.min.js'/>"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery-validation/dist/jquery.validate.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/backstretch/jquery.backstretch.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/select2/select2.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/kindeditor/kindeditor.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/kindeditor/lang/zh_CN.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/kindeditor/plugins/code/prettify.js'/>"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
		<script type="text/javascript"
		src="<c:url value='/assets/plugins/data-tables/jquery.dataTables.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/data-tables/DT_bootstrap.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootbox/bootbox.min.js'/>"></script>	
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/pages/addCorpImage.js'/>"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		$(function() {
			AddCorpImage.init("<c:url value='/' />");
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>