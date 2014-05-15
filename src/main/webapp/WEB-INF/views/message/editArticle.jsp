<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="community" value="${sessionScope.CONTEXT_APP.community}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>编辑图文自定义内容</title>
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
	href="<c:url value='/assets/plugins/bootstrap-modal/css/bootstrap-modal.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap-multiselect/css/bootstrap-multiselect.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap-toastr/toastr.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/uniform/css/uniform.default.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/themes/default.css'/>" id="style_color" />
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
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/public.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/editArticle.css'/>" />
<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="editArticle">
	<c:if test="${(empty community) and !(articleCommand.attentionMsg)}">
		<div class="col-md-12 public-prompt">
			<img src="<c:url value='/assets/img/omg.png'/>" alt="" />
		</div>
	</c:if>

	<c:if test="${(!empty community) or articleCommand.attentionMsg}">
		<div class="col-md-12 ">
			<div class="portlet box iframe-box">
				<div class="portlet-title col-md-12">
					<div class="caption col-md-2">
						<i class="fa fa-edit"></i>
						<c:if
							test="${articleCommand.msgID eq -1 and !(articleCommand.attentionMsg or articleCommand.defaultMsg)}">添加图文消息</c:if>
						<c:if
							test="${articleCommand.msgID ne -1 and !(articleCommand.attentionMsg or articleCommand.defaultMsg)}">编辑图文消息</c:if>
						<c:if test="${articleCommand.attentionMsg}">关注时回复消息</c:if>
					</div>
					<div class="actions">
						<c:if
							test="${!(articleCommand.defaultMsg or articleCommand.attentionMsg)}">
							<button type="button" class="btn default btn-return"
								onclick="Javascript:{window.location='${ctx}/message/articles';}">
								<i class="fa fa-chevron-left"></i>&nbsp;返&nbsp;回
							</button>
						</c:if>
					</div>
				</div>
				<div class="portlet-body form">
					<c:if test="${articleCommand.attentionMsg}">
						<ul class="nav nav-tabs margin-top20">
							<li><a href="${ctx}/message/attentionReply/text">文本回复</a></li>
							<li class="active"><a
								href="${ctx}/message/attentionReply/article">图文回复</a></li>
						</ul>
					</c:if>
					<f:form class="editArticle-form form-horizontal form-validate"
						name="example" method="post" action="${ctx}/message/saveArticle"
						commandName="articleCommand">
						<f:input class="form-control" type="hidden" path="msgID"
							name="msgID" />
						<f:input class="form-control" type="hidden" path="defaultMsg"
							name="defaultMsg" />
						<f:input class="form-control" type="hidden" path="attentionMsg"
							name="attentionMsg" />

						<div class="form-body">
							<c:if
								test="${!(articleCommand.defaultMsg or articleCommand.attentionMsg)}">
								<div
									class="form-group <f:errors path="keyword">has-error</f:errors>">
									<label class="col-md-2 control-label">关键词:</label>
									<div class="col-md-5">
										<f:input class="form-control" placeholder="" type="text"
											path="keyword" name="keyword" />
										<p class="help-block">多个关键词请用空格格开：例如: 美丽 漂亮 好看</p>
									</div>
									<div class="col-md-3 radio-list">
										<label class="radio-inline"> <input type="radio"
											name="matching" value="0"
											<c:out value="${articleCommand.matching==0?'checked':''}"/>>完全匹配
										</label> <label class="radio-inline"> <input type="radio"
											name="matching" value="1"
											<c:out value="${articleCommand.matching==1?'checked':''}"/>>包含匹配
										</label>
									</div>
								</div>
							</c:if>
							<div
								class="form-group <f:errors path="title">has-error</f:errors>">
								<label class="col-md-2 control-label">标题:</label>
								<div class="col-md-8">
									<f:input class="form-control required" placeholder=""
										type="text" path="title" name="title" id="title" />
								</div>
								<label class="control-label"><span class="required">*</span></label>
								<f:errors cssClass="help-block" element="span" path="title" />
							</div>

							<div
								class="form-group <f:errors path="sort">has-error</f:errors>">
								<label class="col-md-2 control-label">排序:</label>
								<div class="col-md-8">
									<f:input class="form-control required digits" min="0" max="127"
										placeholder="" type="text" path="sort" name="sort" id="sort" />
									<p class="tooltip-gray">(id越大，在所属官网分类文章列表中显示越靠前,
										必须在0-127之间)</p>
								</div>
								<f:errors cssClass="help-block" element="span" path="sort" />
							</div>
							<div
								class="form-group <f:errors path="pictureUrl">has-error</f:errors>">
								<label class="col-md-2 control-label">封面图片</label>
								<div class="col-md-8">
									<a href="#" class="insert-img" id="cover-thumb-a"><img
										id="cover-thumb-img" alt="" src=""></a>
									<f:input type="hidden" name="pictureUrl" id="pictureUrl"
										class="form-control required" path="pictureUrl" />
								</div>
								<label class="control-label"><span class="required">*</span></label>
								<f:errors cssClass="help-block" element="span" path="pictureUrl" />
							</div>
							<div
								class="form-group <f:errors path="description">has-error</f:errors>">
								<label class="col-md-2 control-label">简介:</label>
								<div class="col-md-8">
									<f:textarea class="form-control required" placeholder=""
										type="text" path="description" name="description"
										id="description" />
								</div>
								<label class="control-label"><span class="required">*</span></label>
								<f:errors cssClass="help-block" element="span"
									path="description" />
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label">图文外链类型:</label>
								<div class="col-md-8 radio-list">
									<label class="radio-inline"> <input type="radio"
										name="linkType" value="0"
										<c:out value="${articleCommand.linkType==0?'checked':''}"/>>编辑详细内容
									</label> <label class="radio-inline"> <input type="radio"
										name="linkType" value="1"
										<c:out value="${articleCommand.linkType==1?'checked':''}"/>>外部链接
									</label>
								</div>
							</div>
							<div
								class="form-group <f:errors path="content">has-error</f:errors>"
								id="linkType-0">
								<label class="col-md-2 control-label">图文详细内容:</label>
								<div class="col-md-8">
									<f:textarea name="content" class="form-control required"
										path="content"></f:textarea>
								</div>
								<f:errors cssClass="help-block" element="span" path="content" />
								<div class="col-md-1">
									<label class="control-label"><span class="required">*</span></label>
								</div>
							</div>

							<div class="form-group <f:errors path="url">has-error</f:errors>"
								id="linkType-1">
								<label class="col-md-2 control-label">外部链接:</label>
								<div class="col-md-8">
									<f:input class="form-control url required" placeholder=""
										type="url" path="url" name="url" />
								</div>
								<label class="control-label"><span class="required">*</span></label>
								<f:errors cssClass="help-block" element="span" path="url" />
							</div>

							<div class="form-group">
								<label class="col-md-2 control-label">多图文:</label>
								<div class="col-md-8">
									<table class="table table-striped table-hover col-md-12"
										id="child-input-table"
										data-set="#child-input-table .child-input-tr">

									</table>
									<button id="show-child-btn" class="btn iframe-btn"
										data-toggle="modal">
										<i class="fa fa-plus"></i>&nbsp;添&nbsp;加
									</button>
								</div>
							</div>
						</div>

						<div class="form-actions fluid">
							<div class="col-md-offset-2 col-md-7 pull-left">
								<button type="submit" id="submit-btn" class="btn iframe-btn">
									<i class="fa fa-check"></i>&nbsp;保&nbsp;存
								</button>
								<!-- 
							<c:if test="${articleCommand.attentionMsg}">
								<button class="btn iframe-btn" type="button"
									onclick="Javascript:{window.location='${ctx}/message/attentionReply/text';}">切换到文字</button>
							</c:if>
							<c:if test="${articleCommand.defaultMsg}">
								<button class="btn iframe-btn" type="button"
									onclick="Javascript:{window.location='${ctx}/message/defaultReply/text';}">切换到文字</button>
							</c:if>
							 -->
							</div>
						</div>
					</f:form>
				</div>
			</div>
		</div>

		<div class="modal fade bootstrap-dialog type-primary "
			id="child-data-modal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="bootstrap-dialog-title">选择图文集</h4>
					</div>
					<div class="modal-body">
						<table class="table table-striped table-bordered table-hover"
							id="child-data-table">
							<thead>
								<tr role="row">
									<th />
									<th />
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="add-child-btn">
							<i class="fa fa-check"></i>&nbsp;确&nbsp;定
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<i class="fa fa-times"></i>&nbsp;关&nbsp;闭
						</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->


		<!-- END COPYRIGHT -->
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
			src="<c:url value='/assets/plugins/bootstrap-modal/js/bootstrap-modal.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/plugins/bootbox/bootbox.min.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/plugins/bootstrap-toastr/toastr.min.js'/>"></script>
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
			src="<c:url value='/assets/scripts/app.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/scripts/pages/editArticle.js'/>"></script>
		<!-- END PAGE LEVEL SCRIPTS -->
		<script>
			$(function() {
				var childIDs = new Array();
				var childTitles = new Array();
				<c:forEach var="childID" items="${articleCommand.childIDs}">
				childIDs.push("${childID}");
				</c:forEach>
				<c:forEach var="childTitle" items="${articleCommand.childTitles}">
				childTitles.push("${childTitle}");
				</c:forEach>
				EditArticle.init("<c:url value='/' />", childIDs, childTitles);
				
				<c:if test="${!empty errMsg}">toastr.warning('${errMsg}', '提示');</c:if>
				<c:if test="${!empty successMsg}">toastr.warning('${successMsg}', '提示');</c:if>
			});
		</script>
		<!-- END JAVASCRIPTS -->
	</c:if>
</body>
<!-- END BODY -->
</html>