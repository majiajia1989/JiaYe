var House = function() {
	var resolveRootUrl = "";
	var initList = function() {
		var url = resolveRootUrl + 'house/readHouses/'
				+ $("#communityId").val();
		var deleteHouseUrl = resolveRootUrl + 'house/deleteHouse/';

		$("#dtList")
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
							"bInfo" : true,
							"aoColumns" : [
									{
										"sTitle" : "楼号",
										"sWidth" : "100px",
										"sClass" : "left",
										"bSortable" : false,
										"mDataProp" : "name"
									},
									{
										"sTitle" : "",
										"sWidth" : "40px",
										"sClass" : "center",
										"mDataProp" : "id",
										"bSortable" : false,
										"fnRender" : function(jsonData) {

											var editInfo = '<button class="btn btn-xs iframe-btn btn-edit" style=" cursor:hand;" onclick="'
													+ "javascript:House.editInfo(\'"
													+ jsonData.aData.corp.id
													+ "\',\'"
													+ jsonData.aData.community.id
													+ "\',\'"
													+ jsonData.aData.id
													+ "\',\'"
													+ jsonData.aData.name
													+ "\');" + '" >编辑</button>';
											var deleteInfo = '<button class="btn btn-xs iframe-btn btn-delete" style=" cursor:hand;" onclick="'
													+ "javascript:House.deleteInfo(\'"
													+ jsonData.aData.id
													+ "\');" + '" >删除</button>';
/*
											var editHouseUnit = '<a href="'
													+ editHouseUnitUrl
													+ jsonData.aData.id
													+ '" class="btn iframe-btn btn-xs btn-edit">单元信息</a>';

											var editHouseFloor = '<a href="'
													+ editHouseFloorUrl
													+ jsonData.aData.id
													+ '" class="btn iframe-btn btn-xs btn-edit">楼层信息</a>';

											var editHouseRoom = '<a href="'
													+ editHouseRoomUrl
													+ jsonData.aData.id
													+ '" class="btn iframe-btn btn-xs btn-edit">门牌信息</a>';
*/
											return editInfo + '&nbsp'
													+ deleteInfo;

										} // 自定义列的样式
									} ]
						});
	};

	return {
		init : function(url) {
			if (typeof (url) == 'undefined') {
				url = '';
			}
			$('#houseId').val(-1);
			$('#hoses').val("");
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
							$.post(resolveRootUrl + 'house/deleteHouse',
									"houseId=" + id, function(json) {
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
		editInfo : function(corpId, communityId, hosesId, hosesName) {
			$('#corpId').val(corpId);
			$('#communityId').val(communityId);
			$('#houseId').val(hosesId);
			$('#hoses').val(hosesName);

			$('#modalHouseTitle').html("<i class='fa fa-edit'></i>&nbsp;修改楼号");
			$('#modalHouse').modal();
		},
		addInfo : function() {
			$('#modalHouseTitle').html("<i class='fa fa-plus'></i>&nbsp;添加楼号");
			$('#modalHouse').modal();
		},
		submitForm : function() {

			$('#editForm').submit();
		},
		submitImportForm : function() {

			$('#formImport').submit();
		}

	};
}();