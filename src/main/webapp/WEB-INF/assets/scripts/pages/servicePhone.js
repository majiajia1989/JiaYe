var ServicePhone = function() {
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		var url = ctxUrl + 'community/readServicePhone';
		var editUrl = ctxUrl + 'community/editServicePhone/';
		$('#servicePhone_Table')
				.dataTable(
						{
							"bServerSide" : true,
							"sServerMethod" : "GET",
							'bPaginate' : true,
							"bProcessing" : true,
							'bFilter' : false,
							"sAjaxSource" : url,
								"iDisplayLength" : 10,
							"sPaginationType" : "bootstrap",
							"aoColumns" : [
									{
										"sTitle" : "名称",
										"sDefaultContent" : "",
										"sClass" : "center",
										"mDataProp" : "title",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {												
												return "<span id='title_" + full.id + "'>" + data + "</span>";
											}
										}
									},
									{
										"sTitle" : "电话",
										"sDefaultContent" : "",
										"sClass" : "center",
										"mDataProp" : "phone",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {												
												return "<span id='phone_" + full.id + "'>" + data + "</span>";
											}
										}										
									},
									{
										"sTitle" : "描述",
										"sDefaultContent" : "",
										"sClass" : "center",
										"sClass":"word-wrap",
										"mDataProp" : "description",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {												
												return "<span style='' id='description_" + full.id + "'>" + htmlEncode(data) + "</span>";
											}
										}				
									},
									{
										"sTitle" : "操作",
										"sClass" : "center",
										"mDataProp" : "id",
										"sDefaultContent" : "",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												var responseUrl = "ServicePhone.response('"	+ full.id + "');";
												var responseA = "<button class='btn iframe-btn btn-edit btn-xs' onclick=\""
														+ responseUrl
														+ "\"><span>编辑</span></button>";
												var removeUrl = "javascript:ServicePhone.removeByID('"
														+ ctxUrl
														+ "','"
														+ data
														+ "');";
												var removeA = '<button href="#" class="btn iframe-btn btn-delete btn-xs" onclick='
														+ removeUrl + '>删除</button>';
												return responseA + "&nbsp"
														+ removeA + "&nbsp";
											}
										}
									}

							]

						});
	}
	var deleteMsg = function(ctxUrl, id) {
		var url = ctxUrl + "community/removeByID/" + id;
		$.post(url, '', function(json) {
			if (json.success) {
				$('#servicePhone_Table').dataTable().fnDestroy();
				initTable(ctxUrl);
			} else {
				BootstrapDialog.show({
					message : '删除失败!',
					buttons : [ {
						label : '关闭',
						action : function(dialogItself) {
							dialogItself.close();
						}
					} ]
				});
			}
		}, "json");
	}
	
	function htmlEncode(value) {
		value = $('<div/>').text(value).html();
		return value.replace(/\n/g,"<br />");
	}

	return {
		init : function(ctxUrl) {
			initTable(ctxUrl);
		},
		removeByID : function(ctxUrl, id) {
			bootbox.dialog({
				message : "确定删除吗？",
				title : "提示",
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn iframe-btn",
						callback : function() {
							deleteMsg(ctxUrl, id);
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
		response : function(id) {
			$('#modalServicePhone input[name="title"]').attr("value", "");
			$('#modalServicePhone input[name="phone"]').attr("value", "");
			$('#modalServicePhone textarea[name="description"]').attr("value","");	
			if (id == -1) {		
				$('#tt').html("<i class='fa fa-edit'></i> 添加常用号码");
			} else {
				$('#tt').html("<i class='fa fa-edit'></i> 编辑常用号码");
				$('#modalServicePhone input[name="id"]').attr("value", id);
				$('#modalServicePhone input[name="title"]').attr("value", $("#title_"+id).html());
				$('#modalServicePhone input[name="phone"]').attr("value", $("#phone_"+id).html());
				$('#modalServicePhone textarea[name="description"]').attr("value",$("#description_"+id).text());				
			}	
			$('#modalServicePhone').modal('show');
			$('#response-submit-btn').bind("click", function() {
				if ($("#response-form").valid()) {
					$('#modalServicePhone').modal('hide');
					$("#response-form").submit();
				}
			});
		},
	};
}();