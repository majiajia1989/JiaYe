var ElectricityTemplate = function() {
	var resolveRootUrl = "";
	var initList = function() {
		var url = resolveRootUrl
				+ 'consumeTemplate/readConsumeTemplate?community='
				+ $("#community").val() + '&consumeType='
				+ $("#consumeType").val();
		$("#dtList")
				.dataTable(
						{
							"bServerSide" : false,
							"sServerMethod" : "GET",
							'bPaginate' : true,
							"bProcessing" : true,
							'bFilter' : false,
							"sAjaxSource" : url,
							"iDisplayLength" : 10,
							"sPaginationType" : "bootstrap",
							"bInfo" : true,
							"bAutoWidth" : true,
							"aoColumns" : [
									{
										"sWidth" : "100px",
										"sTitle" : "标题",
										"sClass" : "left",
										"bSortable" : false,
										"mDataProp" : "title"
									},
									{
										"sWidth" : "100px",
										"sTitle" : "操作",
										"sClass" : "center",
										"mDataProp" : "id",
										"bSortable" : false,
										"fnRender" : function(jsonData) {

											var editInfo = '<button class="btn btn-xs iframe-btn btn-edit" style=" cursor:hand;" onclick="'
													+ "javascript:ElectricityTemplate.editInfo(\'"
													+ jsonData.aData.id
													+ "\',\'"
													+ jsonData.aData.title
													+ "\',\'"
													+ jsonData.aData.content
													+ "\');" + '" >编辑</button>';
											var deleteInfo = '<button class="btn btn-xs iframe-btn btn-delete" style=" cursor:hand;" onclick="'
													+ "javascript:ElectricityTemplate.deleteInfo(\'"
													+ jsonData.aData.id
													+ "\');" + '" >删除</button>';
											return '<div width="100px">'
													+ editInfo + '&nbsp'
													+ deleteInfo + '</div>';

										} // 自定义列的样式
									} ]
						});
	};
	
	return {
		init : function(url) {
			if (typeof (url) == 'undefined') {
				url = '';
			}
			resolveRootUrl = url;
			initList();

		},
		deleteInfo : function(id) {
			bootbox.dialog({
				message : "你确定删除该信息吗？",
				title : "提示",
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn btn-primary",
						callback : function() {
							$.post(resolveRootUrl
									+ 'consumeTemplate/deleteConsumeTemplate',
									"consumeTemplateId=" + id, function(json) {
										if (json.flag) {
											toastr.success(json.message, '提示');
											$('#dtList').dataTable()
													.fnDestroy();
											initList();
										} else {
											toastr.warning(json.message, '提示');
										}
									}, "json");
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
		editInfo : function(id, title, content) {
			$("#id").val(id);
			$("#title").val(title);
			$("#content").val(content);
			$('#modalEditTitle').html("<i class='fa fa-edit'></i>&nbsp;修改模板");
			$('#modalEdit').modal();
		},
		submitForm : function() {
			$('#editForm').submit();
		},
		addInfo : function(id, title, content) {
			$("#id").val(-1);
			$("#title").val("");
			$("#content").val("");
			$('#modalEditTitle').html("<i class='fa fa-plus'></i>&nbsp;添加模板");
			$('#modalEdit').modal();
		},
		insertFiled:function(insertContent) {
			$("#content").insertContent('${' + insertContent+'}'); 
		}
	};
}();