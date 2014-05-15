var Corp = function() {
	var resolveRootUrl = "";

	var initTable = function() {
		if (!jQuery().dataTable) {
			return;
		}
		var url = resolveRootUrl + 'corp/readCorps';
		var editUrl = resolveRootUrl + 'corp/editCorp/';
		$('#dtList').dataTable({
			"bServerSide" : false,
			"sServerMethod" : "GET",
			'bPaginate' : true,
			"bProcessing" : true,
			'bFilter' : false,
			"sAjaxSource" : url,
			"fnRowCallback" : function(nRow, data, iDisplayIndex,
					iDisplayIndexFull) {
				var index = iDisplayIndex + 1;
				$('td:eq(0)', nRow).html(index);
				return nRow;
			},
			"iDisplayLength" : 10,
			"sPaginationType" : "bootstrap",
			"aoColumns" : [{
						"sTitle" : "序号",
						"sDefaultContent" : "",
						"mDataProp" : null,
						"bSortable" : false

					}, {
						"sTitle" : "名称",
						"sClass" : "center",
						"mDataProp" : "name",
						"bSortable" : false
					}, {
						"sTitle" : "省",
						"sClass" : "center",
						"mDataProp" : "province",
						"bSortable" : false
					}, {
						"sTitle" : "市",
						"sClass" : "center",
						"mDataProp" : "city",
						"bSortable" : false
					}, {
						"sTitle" : "县（区）",
						"sClass" : "center",
						"mDataProp" : "county",
						"bSortable" : false
					}, {
						"sTitle" : "操作",
						"sClass" : "center",
						"mDataProp" : "id",
						"sDefaultContent" : "",
						"mRender" : function(data, type, full) {
							if (type == "display") {
								var editA = '<a href="' + editUrl + data
										+ '" class="btn iframe-btn btn-xs btn-edit">编辑</a>';
								var removeUrl = "javascript:Texts.removeCorp('" + data + "');";
								var removeA = '<a href="#" class="btn btn-xs iframe-btn btn-delete" onclick='
										+ removeUrl + '>删除</a>';
								return editA ;//+ "&nbsp" + removeA;
							}
						}
					}

			]

		});
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			if (typeof(ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			resolveRootUrl = ctxUrl;
			
			initTable();
		},
		removeCorp : function( msgID) {
			BootstrapDialog.show({
						title : '提示',
						message : '确定删除吗?',
						buttons : [{
									label : '取消',
									action : function(dialogItself) {
										dialogItself.close();
									}
								}, {
									label : '确定',
									cssClass : 'btn-primary',
									action : function(dialogItself) {
										dialogItself.close();
										
										var url = resolveRootUrl + "corp/removeCorp/" + msgID;
										$.post(url, '', function(json) {
													if (json.success) {
														$('#text_table').dataTable().fnDestroy();
														initTable();
													} else {
														BootstrapDialog.show({
																	message : '删除失败!',
																	buttons : [{
																				label : '关闭',
																				action : function(dialogItself) {
																					dialogItself.close();
																				}
																			}]
																});
													}
												});
									}
								}]
					});
		}
	};
}();