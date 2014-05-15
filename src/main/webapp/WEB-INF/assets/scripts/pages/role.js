var Role = function() {
	var resolveRootUrl = "";
	var initList = function() {
		var url = resolveRootUrl + 'role/readRoleByCorp/';

		$("#dtList")
				.dataTable(
						{

							"bServerSide" : false,
							"sServerMethod" : "GET",
							'bPaginate' : true,
							"bLengthChange" : true,
							"iDisplayLength" : 10,
							"bProcessing" : true,
							'bFilter' : false,
							"sAjaxSource" : url,
							"sPaginationType" : "bootstrap",
							"bInfo" : true,
							"aoColumns" : [
									{
										"sTitle" : "名称",
										"sWidth" : "100px",
										"sClass" : "left",
										"mDataProp" : "name"
									},
									{
										"sTitle" : "",
										"sWidth" : "40px",
										"sClass" : "center",
										"mDataProp" : "id",
										"bSortable" : false,
										"fnRender" : function(jsonData) {
											if (jsonData.aData.id == 1) {
												return "";
											}
											var editInfo = '<a class="btn btn-xs iframe-btn btn-edit" style=" cursor:hand;" href="' + resolveRootUrl + 'role/editRole/'
													+ jsonData.aData.id
													+ '" >编辑</a>';
											var deleteInfo = '<button class="btn btn-xs iframe-btn btn-delete" style=" cursor:hand;" onclick="'
													+ "javascript:Role.deleteInfo(\'"
													+ jsonData.aData.id
													+ "\');" + '" >删除</button>';

											return editInfo + '&nbsp'
													+ deleteInfo;

										} // 
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
			$('#role_id').val(-1);
			$('#name').val("");
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
							$.post(resolveRootUrl + 'role/deleteRole',
									"roleId=" + id, function(json) {
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
		editInfo : function(id, name) {
			$('#role_id').val(id);
			$('#name').val(name);

			$('#editModal').modal();
		},
		submitForm : function() {
			$('#editForm').submit();
		}
	};
}();