var Texts = function() {
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		var url = ctxUrl + 'texts/queryAll';
		var editUrl = ctxUrl + 'texts/editText/';
		$('#text_table').dataTable({
			"bServerSide" : true,
			"sServerMethod" : "GET",
			'bPaginate' : true,
			"bProcessing" : true,
			'bFilter' : false,
			"sAjaxSource" : url,
			"iDisplayLength" : 10,
			"sPaginationType" : "bootstrap",
			"aoColumns" : [{
						"sTitle" : "关键字",
						"sClass" : "center",
						"mDataProp" : "keyword",
						"bSortable" : false
					}, {
						"sTitle" : "匹配类型",
						"sClass" : "center",
						"sDefaultContent" : "",
						"mDataProp" : "matching",
						"bSortable" : false,
						"mRender" : function(data, type, full) {
							if (type == "display") {
								var text = "完全匹配";
								if (data == 1) {
									text = "包含匹配";
								}
								// var text = data = 0 ? "完全匹配" : "包含匹配";
								return '<span>' + text + '</span>';
							}
						}
					}, {
						"sTitle" : "内容",
						"sClass" : "center",
						"mDataProp" : "content",
						"sDefaultContent" : "",
						"bSortable" : false,
						"mRender" : function(data, type, full) {
							if (type == "display") {
								return '<span style="display:block;max-width:500px;overflow:hidden;">'
										+ data + '</span>';
							}
						}
					}, {
						"sTitle" : "操作",
						"sClass" : "center",
						"mDataProp" : "msgID",
						"sDefaultContent" : "",
						"bSortable" : false,
						"mRender" : function(data, type, full) {
							if (type == "display") {
								var editA = '<a href="'
										+ editUrl
										+ data
										+ '" class="btn iframe-btn btn-edit btn-xs">编辑</a>';
								var removeUrl = "javascript:Texts.removeMsgByMsgID('"
										+ ctxUrl + "','" + data + "');";
								var removeA = '<a href="#" class="btn iframe-btn btn-delete  btn-xs" onclick='
										+ removeUrl + '>删除</a>';
								var setDefaultUrl = "javascript:Texts.setDefaultMsgByMsgID('"
										+ ctxUrl + "','" + data + "');";
								var cancleDefaultUrl = "javascript:Texts.cancleDefaultMsgByMsgID('"
										+ ctxUrl + "','" + data + "');";

								if (data == full.defaultMsgID
										|| data == full.attentionMsgID) {
									removeA = "<a href='#' class='btn iframe-btn btn-delete  btn-xs' disabled='true'><span>删除</span></a>";
								}

								var setDefault = '';

								if (data == full.defaultMsgID) {
									setDefault = '<a href="#" class="btn default btn-xs blue" onclick='
											+ cancleDefaultUrl + '>取消默认回复</a>'
								} else {
									setDefault = '<a href="#" class="btn default btn-xs blue" onclick='
											+ setDefaultUrl + '>设为默认回复</a>';
								}

								return editA + "&nbsp" + removeA + "&nbsp"
										+ setDefault;
							}
						}
					}

			]

		});
	}
	var deleteMsg = function(ctxUrl, msgID) {
		var url = ctxUrl + "message/removeMsgByID/" + msgID;
		$.post(url, '', function(json) {
					if (json.success) {
						$('#text_table').dataTable().fnClearTable(0);
						$('#text_table').dataTable().fnDraw();
					} else {
						toastr.warning(json.errMsg, '提示');
					}
				}, "json");
	}

	var setDefaultMsg = function(ctxUrl, msgID) {
		var url = ctxUrl + "message/setDefaultMsg/" + msgID;
		$.post(url, '', function(json) {
					if (json.success) {
						$('#text_table').dataTable().fnClearTable(0);
						$('#text_table').dataTable().fnDraw();
					} else {
						toastr.warning(json.errMsg, '提示');
					}
				}, "json");
	}
	var cancleDefaultMsg = function(ctxUrl, msgID) {
		var url = ctxUrl + "message/cancleDefaultMsg/" + msgID;
		$.post(url, '', function(json) {
					if (json.success) {
						$('#text_table').dataTable().fnClearTable(0);
						$('#text_table').dataTable().fnDraw();
					} else {
						toastr.warning(json.errMsg, '提示');
					}
				}, "json");
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			initTable(ctxUrl);
		},
		removeMsgByMsgID : function(ctxUrl, msgID) {
			bootbox.dialog({
						message : "确定删除吗？",
						title : "提示",
						buttons : {
							success : {
								label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
								className : "btn iframe-btn",
								callback : function() {
									deleteMsg(ctxUrl, msgID);
								}
							},
							danger : {
								label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
								className : "btn btn-default",
								callback : function() {
								}
							}
						}
					});
		},
		setDefaultMsgByMsgID : function(ctxUrl, msgID) {

			bootbox.dialog({
						message : "设为默认消息？",
						title : "提示",
						buttons : {
							success : {
								label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
								className : "btn iframe-btn",
								callback : function() {
									setDefaultMsg(ctxUrl, msgID);
								}
							},
							danger : {
								label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
								className : "btn btn-default",
								callback : function() {
								}
							}
						}
					});

		},
		cancleDefaultMsgByMsgID : function(ctxUrl, msgID) {
			bootbox.dialog({
						message : "取消默认设置？",
						title : "提示",
						buttons : {
							success : {
								label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
								className : "btn iframe-btn",
								callback : function() {
									cancleDefaultMsg(ctxUrl, msgID);
								}
							},
							danger : {
								label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
								className : "btn btn-default",
								callback : function() {
								}
							}
						}
					});
		}

	};
}();