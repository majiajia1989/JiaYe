var Community = function() {
	var resolveRootUrl = "";

	var initTable = function() {
		if (!jQuery().dataTable) {
			return;
		}
		var url = resolveRootUrl + 'community/readCommunitys';
		var editUrl = resolveRootUrl + 'community/editCommunity/';
		$('#dtList')
				.dataTable(
						{
							"bServerSide" : true,
							"sServerMethod" : "GET",
							'bPaginate' : true,
							"bProcessing" : true,
							'bFilter' : false,
							"sAjaxSource" : url,
							"iDisplayLength" : 5,
							"sPaginationType" : "bootstrap",
							"oLanguage" : {
								"sLengthMenu" : '每页显示 <select>'
										+ '<option value="5">5</option>'
										+ '<option value="10">10</option>'
										+ '<option value="25">25</option>'
										+ '<option value="50">50</option>'
										+ '<option value="100">100</option>'
										+ '</select> 条记录'
							},
							"aoColumns" : [
									{
										"sTitle" : "二维码",
										"bSortable" : false,
										"mData" : "qrcode",
										"fnRender" : function(jsonData) {
											var status = jsonData.aData;
											return '<a href="' + resolveRootUrl
													+ "main?community="
													+ jsonData.aData.id + '" >'
													+ '<image src="'
													+ jsonData.aData.qrcode
													+ '" width=100 />' + '</a>';
										}
									},
									{
										"sTitle" : "名称",
										"sClass" : "center",
										"mData" : "name",
										"bSortable" : false,
										"fnRender" : function(jsonData) {
											var status = jsonData.aData;
											return '<a href="' + resolveRootUrl
													+ "main?community="
													+ jsonData.aData.id + '" >'
													+ jsonData.aData.name
													+ '</a>';
										}
									},
									{
										"sTitle" : "省",
										"sClass" : "center",
										"mDataProp" : "province",
										"bSortable" : false
									},
									{
										"sTitle" : "市",
										"sClass" : "center",
										"mDataProp" : "city",
										"bSortable" : false
									},
									{
										"sTitle" : "县（区）",
										"sClass" : "center",
										"mDataProp" : "county",
										"bSortable" : false
									},
									{
										"sTitle" : "操作",
										"sClass" : "center",
										"mDataProp" : "id",
										"bSortable" : false,
										"sDefaultContent" : "",
										"mRender" : function(data, type, full) {
											if (type == "display") {
												var editA = '<a href="'
														+ editUrl
														+ data
														+ '" class="btn btn-xs iframe-btn btn-edit">编辑</a>';
												var removeUrl = "javascript:Texts.removeCommunity('"
														+ data + "');";
												var removeA = '<a href="#" class="btn btn-xs iframe-btn btn-delete" onclick='
														+ removeUrl + '>删除</a>';
												return editA + "&nbsp";// +
												// removeA;
											}
										}
									}

							]

						});
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			resolveRootUrl = ctxUrl;

			initTable();
		},
		removeCommunity : function(msgID) {
			bootbox.dialog({
				message : '确定删除吗?',
				title : "提示",
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn btn-primary",
						callback : function() {
							var url = resolveRootUrl
									+ "community/removeCommunity/" + msgID;
							$.post(url, '', function(json) {
								if (json.success) {
									$('#text_table').dataTable().fnDestroy();
									initTable();
									toastr.success(json.message, '提示');
								} else {
									toastr.warning(json.message, '提示');
								}
							});
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