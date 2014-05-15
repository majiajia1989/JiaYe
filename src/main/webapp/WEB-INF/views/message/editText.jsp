<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="community" value="${sessionScope.CONTEXT_APP.community}" />
<c:set var="msg" value='<%=request.getAttribute("msg")%>' />
<c:set var="matching" value='<%=request.getAttribute("matching")%>' />
<c:set var="msgID" value="-1" />
<c:if test="${!empty msg}">
	<c:set var="msgID" value="${msg.id}" />
</c:if>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>文本消息</title>
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
	href="<c:url value='/assets/plugins/uniform/css/uniform.default.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/themes/default.css' />"
	id="style_color" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/kindeditor/plugins/code/prettify.js'/>" />
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
	href="<c:url value='/assets/css/pages/public.css'/>" />
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->

<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<c:if test="${empty community and !(textCommand.attentionMsg)}">
		<div class="col-md-12 public-prompt">
			<img src="<c:url value='/assets/img/omg.png'/>" alt="" />
		</div>
	</c:if>
	<c:if test="${!(empty community) or (textCommand.attentionMsg)}">
		<div class="col-md-12 ">
			<div class="portlet box iframe-box ">
				<div class="portlet-title col-md-12">
					<div class="caption col-md-2">
						<i class="fa fa-edit"></i>
						<c:if
							test="${msgID eq -1 and !(textCommand.attentionMsg or textCommand.defaultMsg)}">添加文本消息</c:if>
						<c:if
							test="${msgID ne -1 and !(textCommand.attentionMsg or textCommand.defaultMsg)}">编辑文本消息</c:if>
						<c:if test="${textCommand.attentionMsg}">关注时回复消息</c:if>
					</div>


					<c:if
						test="${!(textCommand.attentionMsg or textCommand.defaultMsg)}">
						<div class="actions">
							<button type="button" class="btn iframe-btn btn-return"
								onclick="Javascript:{window.location='${ctx}/message/texts';}">
								<i class="fa fa-chevron-left"></i>&nbsp;返&nbsp;回
							</button>
						</div>
					</c:if>
				</div>
				<div class="portlet-body form">

					<c:if test="${textCommand.attentionMsg}">
						<ul class="nav nav-tabs margin-top20">
							<li class="active"><a
								href="${ctx}/message/attentionReply/text">文本回复</a></li>
							<li><a href="${ctx}/message/attentionReply/article">图文回复</a></li>
						</ul>
					</c:if>
					<f:form action="${ctx}/texts/editText/${msgID}"
						class="editText-form form-validate form-horizontal" method="post"
						commandName="textCommand">
						<f:input class="form-control" type="hidden" path="defaultMsg"
							name="defaultMsg" />
						<f:input class="form-control" type="hidden" path="attentionMsg"
							name="attentionMsg" />

						<c:if test="${!empty errMsg}">
							<div class="alert alert-danger">
								<button class="close" data-close="alert">X</button>
								<span>${errMsg}</span>
							</div>
						</c:if>
						<div class="form-body">
							<c:if
								test="${!(textCommand.attentionMsg or textCommand.defaultMsg)}">
								<div
									class="form-group <f:errors path="keyword">has-error</f:errors>"
									id="keyword-form-group">
									<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
									<label class="control-label  col-md-2">关键词</label>
									<div class="col-md-5">
										<f:input class="form-control" type="text" autocomplete="off"
											placeholder="关键词" path="keyword" />
										<span class="help-block">多个关键词用空格分开 例如:美丽 漂亮</span>
									</div>
									<f:errors cssClass="help-block" element="span" path="keyword" />
									<div class="col-md-5 radio-list">

										<label class="radio-inline"> <input type="radio"
											name="matching" value="0"
											<c:out value="${textCommand.matching==0?'checked':''}"/>>完全匹配
										</label> <label class="radio-inline"> <input type="radio"
											name="matching" value="1"
											<c:out value="${textCommand.matching==1?'checked':''}"/>>包含匹配
										</label>

									</div>

								</div>
							</c:if>
							<div
								class="form-group <f:errors path="content">has-error</f:errors>">
								<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
								<label class="control-label  col-md-2">回复内容</label>
								<div class="col-md-8">
									<f:textarea class="form-control required" path="content"
										rows="6" placeholder="回复内容"></f:textarea>
									<f:errors cssClass="help-block" element="span" path="content" />
								</div>
								<label class="control-label"><span class="required">*</span></label>

							</div>
						</div>
						<div class="form-actions fluid">
							<div class="col-md-offset-2 col-md-5 pull-left">
								<button class="btn iframe-btn" type="submit">
									<i class="fa fa-check"></i>&nbsp;保&nbsp;存
								</button>
								<!-- 
							<c:if test="${textCommand.attentionMsg}">
								<button class="btn iframe-btn" type="button"
									onclick="Javascript:{window.location='${ctx}/message/attentionReply/article';}">切换到图文</button>
							</c:if>
							<c:if test="${textCommand.defaultMsg}">
								<button class="btn iframe-btn" type="button"
									onclick="Javascript:{window.location='${ctx}/message/defaultReply/article';}">切换到图文</button>
							</c:if>
							-->
							</div>

						</div>
					</f:form>
				</div>
			</div>

		</div>
	</c:if>
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
		src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/pages/editText.js'/>"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		$(function() {
			EditText.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->

</body>
</html>