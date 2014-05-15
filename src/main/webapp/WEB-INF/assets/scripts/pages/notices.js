var Notices = function() {
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}

		var dataUrl = ctxUrl + 'community/queryAllNotices';
		// begin first table
		$('#data_table')
				.dataTable(
						{
							"bServerSide" : true,
							"sServerMethod" : "GET",
							'bPaginate' : true,
							"bProcessing" : true,
							'bFilter' : false,
							"sAjaxSource" : dataUrl,
							"aoColumns" : [
									{
										"mDataProp" : "title",
										"sDefaultContent" : "",
										"sTitle" : "标题",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												var title = "<span id='title_" + full.id + "'>" + htmlEncode(data)  + "</span>";
												var content = "<div style='display: none;' id='content_"+ full.id+ "'>" + htmlEncode(full.content)  + "</div>";
											return title + content;
											}
										}
									},	
									{
										"mDataProp" : "type",
										"sDefaultContent" : "",
										"sTitle" : "类型",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												return data==0?"公开":"私有";
											}
										}
									},	
									{
										"mDataProp" : "createTime",
										"sDefaultContent" : "",
										"sTitle" : "创建时间",
										"bSortable" : false
									},
									{
										"sTitle" : "审核状态",
										"mDataProp" : "auditStatus",
										"sDefaultContent" : "",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												return data==0?"未审核":"审核通过";
											}
										}
									},
									{
										"mDataProp" : "sendStatus",
										"sDefaultContent" : "",
										"sTitle" : "发送状态",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												return data==0?"未发送":"已发送";
											}
										}
									},
									{
										"sTitle" : "操作",
										"mDataProp" : "id",
										"sDefaultContent" : "",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												var showUrl = ctxUrl + "community/noticeDetail/" + data;
												var showA = "<a class='btn btn-edit btn-xs' href=" + showUrl + '><span>查看</span></a>';

												var editUrl = ctxUrl + "community/editNotice/" + data;
												var editA = "<a class='btn btn-edit btn-xs' href=" + editUrl + '><span>编辑</span></a>';
												var removeUrl = 'Notices.removeNoticeByID("'
														+ ctxUrl + '",' + full.id + ');';
												var removeA = "<a href='#' class='btn btn-delete btn-xs' onclick='"
														+ removeUrl
														+ "'><span>删除</span></a>";
												var sendUrl = ctxUrl + "community/sendNotice/" + data;
												var sendA = "<a class='btn btn-send btn-xs' href='"
														+ sendUrl
														+ "'><span>发送</span></a>";
												var auditUrl = 'Notices.auditNoticeByID("'
														+ ctxUrl + '",'  + full.id + ');';
												var auditA = "<a class='btn btn-send btn-xs' href='#' onclick='"
														+ auditUrl
														+ "'><span>审核</span></a>";
												if (full.auditStatus) {
													if (full.sendStatus)
													return showA + "&nbsp" + sendA;
													else 
													return showA + "&nbsp" + sendA ;
												} else {
													return editA + "&nbsp"+auditA + "&nbsp" + removeA;
												}												
											}
										}
									} ],
							// set the initial value
							"iDisplayLength" : 10,
							"sPaginationType" : "bootstrap"
						});
	}

	function htmlEncode(value) {
		value = $('<div/>').text(value).html();
		return value.replace(/\n/g,"<br />");
	}
	
	var doRemoveNoticeByID = function(ctxUrl, id) {
		var url = ctxUrl + "community/removeNoticeByID/" + id;
		$.post(url, '', function(json) {
			if (json.success) {
				$('#data_table').dataTable().fnDestroy();
				initTable(ctxUrl);
			} else {
				toastr.warning(json.errMsg, '提示');
			}
		},"json");
	}
	
	var doAuditNoticeByID = function(ctxUrl, id) {
		var url = ctxUrl + "community/auditNoticeByID/" + id;
		$.post(url, '', function(json) {
			if (json.success) {
				$('#data_table').dataTable().fnDestroy();
				initTable(ctxUrl);
			} else {
				toastr.warning(json.errMsg, '提示');
			}
		},"json");
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			initTable(ctxUrl);
		},
		removeNoticeByID : function(ctxUrl, id) {
			bootbox.dialog({
				title:'确定删除这条通知吗?',
				message : $('#title_'+id).html(),
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn btn-primary",
						callback : function() {
							doRemoveNoticeByID(ctxUrl, id);
						}
					},
					danger : {
						label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
						className : "btn btn-default"
					}
				}
			});
		},
		auditNoticeByID : function(ctxUrl, id) {
			var title = "<div ><span style='color:#000;font-weight:bold;'>标题:</span>" + $('#title_'+id).html()+"</div>";
			var content = "<div class='notice' style='line-height:32px;'><span style='color:#000;font-weight:bold;'>内容:</span>"+$('#content_'+id).html()+"</div>"
			bootbox.dialog({
				title:'确定通过审核这条通知吗?',
				message : title + content,
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn btn-primary",
						callback : function() {
							doAuditNoticeByID(ctxUrl, id);
						}
					},
					danger : {
						label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
						className : "btn btn-default"
					}
				}
			});
		}
	};

}();