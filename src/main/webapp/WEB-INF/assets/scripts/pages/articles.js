var Articles = function() {
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}

		var dataUrl = ctxUrl + 'message/queryAllArticles';
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
										"mDataProp" : "keyword",
										"sDefaultContent" : "",
										"sTitle" : "关键词",
										"bSortable" : false
									},									
									{
										"sTitle" : "匹配类型",
										"mDataProp" : "matching",
										"sDefaultContent" : "",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												var text = data == 0 ? "完全匹配"
														: "包含匹配";
												return '<span style="">' + text
														+ '</span>';
											}
										}
									},
									{
										"mDataProp" : "title",
										"sDefaultContent" : "",
										"sTitle" : "回答",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												return '<span style="display:block;max-width:500px;overflow:hidden;">' + htmlEncode(data)
														+ '</span>';
											}
										}
									},
									{
										"mDataProp" : "sort",
										"sDefaultContent" : "",
										"sTitle" : "排序",
										"bSortable" : false
									},
									{
										"sTitle" : "操作",
										"mDataProp" : "msgID",
										"sDefaultContent" : "",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												var editUrl = ctxUrl
														+ "message/editArticle/"
														+ data;
												var editA = "<a class='btn btn-edit btn-xs' href="
														+ editUrl
														+ '><span>编辑</span></a>';
												var removeUrl = 'Articles.removeMsgByID("'
														+ ctxUrl + '","'  + full.title + '",' + full.msgID + ');';
												var removeA = "<a href='#' class='btn btn-delete btn-xs' onclick='"
														+ removeUrl
														+ "'><span>删除</span></a>";
												if (full.defaultMsg || full.attentionMsg) {
													removeA = "<a href='#' class='btn default btn-xs' disabled='true'><span>删除</span></a>";
												}
												var setDefaultUrl="javascript:Articles.setDefaultMsgByMsgID('"+ctxUrl+"','"+data+"');";
												var cancleDefaultUrl="javascript:Articles.cancleDefaultMsgByMsgID('"+ctxUrl+"','"+data+"');";
												
												var setDefault='';
												
												if(full.defaultMsg){
													setDefault='<a href="#" class="btn default btn-xs blue" onclick='+cancleDefaultUrl+'>取消默认回复</a>'
												}else{
													setDefault='<a href="#" class="btn default btn-xs blue" onclick='+setDefaultUrl+'>设为默认回复</a>';
												}
												
												return editA + "&nbsp"+setDefault;
											}
										}
									} ],
							// set the initial value
							"iDisplayLength" : 10,
							"sPaginationType" : "bootstrap"
						});
	}
	
	var setDefaultMsg=function(ctxUrl,msgID){
		var url=ctxUrl+"message/setDefaultMsg/"+msgID;
		$.post(url,'',function(json){
			if(json.success){
				$('#data_table').dataTable().fnDestroy();
				initTable(ctxUrl);
			}else{
				toastr.warning('设置失败!', '提示');				
			}
		},"json");
	}
	var cancleDefaultMsg=function(ctxUrl,msgID){
		var url=ctxUrl+"message/cancleDefaultMsg/"+msgID;
		$.post(url,'',function(json){
			if(json.success){
				$('#data_table').dataTable().fnDestroy();
				initTable(ctxUrl);
			}else{
				toastr.warning('设置失败!', '提示');
			}
		},"json");
	}

	var doRemoveMsgByID = function(ctxUrl, msgID) {
		var url = ctxUrl + "message/removeMsgByID/" + msgID;
		$.post(url, '', function(json) {
			if (json.success) {
				$('#data_table').dataTable().fnDestroy();
				initTable(ctxUrl);
			} else {
				toastr.warning('删除出错!', '提示');
			}
		},"json");
	}
	
	function htmlEncode(value) {
		value = $('<div/>').text(value).html();
		return value.replace(/\n/g,"<br />");
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			initTable(ctxUrl);
		},

		removeMsgByID : function(ctxUrl, title, msgID) {
			bootbox.dialog({
				title:'确定删除这条图文消息吗?',
				message : title,
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn btn-primary",
						callback : function() {
							doRemoveMsgByID(ctxUrl, msgID);
						}
					},
					danger : {
						label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
						className : "btn btn-default"
					}
				}
			});
		},
		setDefaultMsgByMsgID : function(ctxUrl, msgID) {
			bootbox.dialog({
				title : '提示',
				message : '确定设置为默认回复?',
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn btn-primary",
						callback : function() {
							setDefaultMsg(ctxUrl, msgID);
						}
					},
					danger : {
						label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
						className : "btn btn-default"
					}
				}
			});
		},
		cancleDefaultMsgByMsgID : function(ctxUrl, msgID) {
			bootbox.dialog({
				title : '提示',
				message : '确定取消默认回复?',
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn btn-primary",
						callback : function() {
							cancleDefaultMsg(ctxUrl, msgID);
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