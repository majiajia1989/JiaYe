<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="community"
	value='<%=request.getAttribute("communityCommand")%>' />
<c:set var="communityCorp" value='2' />
<c:set var="communityID" value="-1" />
<c:set var="communityProvince" value="" />
<c:set var="communityCity" value="" />
<c:set var="communityCounty" value="" />
<c:if test="${!empty communityCommand}">
	<c:set var="communityID" value="${community.id}" />
	<c:set var="communityCorp" value="${community.corp}" />
	<c:set var="communityProvince" value="${community.province}" />
	<c:set var="communityCity" value="${community.city}" />
	<c:set var="communityCounty" value="${community.county}" />
</c:if>
<c:if test="${empty communityProvince}">
	<c:set var="communityProvince" value="河南省" />
	<c:set var="communityCity" value="郑州市" />
	<c:set var="communityCounty" value="中原区" />
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>小区信息设置</title>
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
	href="<c:url value='/assets/css/plugins.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/select2/select2_metro.css'/>" />
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style-wechat.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style-responsive.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/plugins.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/public.css'/>" />
<!-- END PAGE LEVEL STYLES -->
</head>
<body>
	<div class="col-md-12 ">
		<div class="portlet box iframe-box ">
			<div class="portlet-title">
				<div class="caption">
					<c:if test="${communityID>0}">
						<i class="fa fa-edit"></i>&nbsp;修改小区</c:if>
					<c:if test="${communityID==-1}">
						<i class="fa fa-plus"></i>&nbsp;添加小区</c:if>
				</div>
				<div class="actions">
					<a id="data_table_new_btn" class="btn iframe-btn btn-return"
						href="${ctx}/community/community"><i
						class="fa fa-chevron-left"></i>&nbsp;返&nbsp;回</a>
				</div>
			</div>
			<div class="portlet-body form">
				<!-- 内容区域开始-->
				<f:form class="community-form form-validate  form-horizontal"
					action="${ctx}/community/community" method="post"
					commandName="communityCommand">
					<input type="hidden" id="id" name="id" value="${communityID}" />
					<input type="hidden" id="communityProvince"
						name="communityProvince" value="${communityProvince}" />
					<input type="hidden" id="communityCity" name="communityCity"
						value="${communityCity}" />
					<input type="hidden" id="communityCounty" name="communityCounty"
						value="${communityCounty}" />
					<input type="hidden" id="corp" name="corp" value="${communityCorp}" />
					<div class="form-body">
						<div class="form-group">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label  col-md-2">地区</label>
							<div class="col-md-8">
								<div class="col-md-4"
									style="padding-left: 0px; padding-right: 5px;">
									<select class="form-control dropdown-select " id="province"
										name="province"></select>
								</div>
								<div class=" col-md-4"
									style="padding-left: 5px; padding-right: 5px;">
									<select class="form-control dropdown-select" id="city"
										name="city"></select>
								</div>
								<div class=" col-md-4"
									style="padding-left: 5px; padding-right: 0px">
									<select class="form-control dropdown-select" id="county"
										name="county">
									</select>
								</div>
							</div>
						</div>
						<div class="form-group <f:errors path="name">has-error</f:errors>">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label  col-md-2">小区名称</label>
							<div class="col-md-8">
								<f:input class="form-control required" type="text"
									autocomplete="off" placeholder="小区名称" path="name" />
							</div>
							<label class="control-label"><span class="required">*</span></label>
							<f:errors cssClass="help-block" element="span" path="name" />
						</div>
						<div
							class="form-group <f:errors path="mobilephone">has-error</f:errors>">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label col-md-2">手机号</label>
							<div class="col-md-8">
								<f:input class="form-control required mobile" type="text"
									autocomplete="off" placeholder="手机号" path="mobilephone" />
								<f:errors cssClass="help-block" element="span"
									path="mobilephone" />
							</div>
							<label class="control-label"><span class="required">*</span></label>
						</div>
						<div
							class="form-group <f:errors path="telephone">has-error</f:errors>">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label col-md-2">电话号码</label>
							<div class="col-md-8">
								<f:input class="form-control required phone" type="text"
									autocomplete="off" placeholder="电话号码" path="telephone" />
								<f:errors cssClass="help-block" element="span" path="telephone" />
							</div>
							<label class="control-label"> <span class="required">*</span>
							</label>

						</div>
						<div
							class="form-group <f:errors path="address">has-error</f:errors>">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label col-md-2">地址</label>
							<div class="col-md-8">
								<f:input class="form-control required" type="text"
									autocomplete="off" placeholder="地址" path="address" />
								<f:errors cssClass="help-block" element="span" path="address" />
							</div>
							<label class="control-label"><span class="required">*</span></label>
						</div>
						<div
							class="form-group <f:errors path="copyright">has-error</f:errors>">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label col-md-2">版权</label>
							<div class="col-md-8">
								<f:input class="form-control  required" type="text"
									autocomplete="off" placeholder="版权" path="copyright" />
							</div>
							<label class="control-label"><span class="required">*</span></label>
							<f:errors cssClass="help-block" element="span" path="copyright" />
						</div>
						<div
							class="form-group <f:errors path="descript">has-error</f:errors>">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label col-md-2">描述</label>
							<div class="col-md-8">
								<f:textarea type="text" class="form-control required" rows="4"
									placeholder="描述" path="descript"></f:textarea>
							</div>
							<label class="control-label"><span class="required">*</span></label>
							<f:errors cssClass="help-block" element="span" path="descript" />
						</div>
					</div>

					<div class="form-actions fluid">
						<div class="col-md-5 col-md-offset-2 pull-left">
							<button class="btn iframe-btn" type="submit">
								<i class="fa fa-check"></i>&nbsp;保&nbsp;存
							</button>
						</div>
					</div>


				</f:form>
				<!--内容区域结束-->
			</div>
		</div>
	</div>
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
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/pages/region.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/pages/editCommunity.js'/>"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		$(function() {
			var infoMsg = '${infoMsg}';
			if (infoMsg.length > 0) {
				toastr.warning(infoMsg, '提示');
			}
			EditCommunity.init("<c:url value='/' />");
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>