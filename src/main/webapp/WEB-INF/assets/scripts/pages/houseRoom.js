var HouseRoom = function() {
	var resolveRootUrl = "";
	var initList = function() {
		var url = resolveRootUrl + 'house/readHouseRoomByCommunity/'
				+ $("#communityId").val();
		var deleteHouseUrl = resolveRootUrl + 'house/deleteHouseRoom/';

		$("#dtList")
				.dataTable(
						{

							"bServerSide" : true,
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
										"sTitle" : "门牌号",
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
											var editInfo = '<button class="btn btn-xs iframe-btn btn-edit" style=" cursor:hand;" onclick="'
												+ "javascript:HouseRoom.editInfo(\'"
												+ jsonData.aData.id + "\',\'" + jsonData.aData.name + "\');" + '" >编辑</button>';
											var deleteInfo = '<button class="btn btn-xs iframe-btn btn-delete" style=" cursor:hand;" onclick="'
													+ "javascript:HouseRoom.deleteInfo(\'"
													+ jsonData.aData.id
													+ "\');" + '" >删除</button>';

											return editInfo + '&nbsp' + deleteInfo;

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
			$('#houseRoomId').val(-1);
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
							$.post(resolveRootUrl + 'house/deleteHouseRoom',
									"houseRoomId=" + id, function(json) {
										if (json.flag) {
											toastr.success(json.message, '提示');
											$('#dtList').dataTable().fnDestroy();
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
		editInfo:function(id,name){
			//$('#houseUnitId').val(houseUnitId);
			$('#houseRoomId').val(id);
			$('#name').val(name);
			
			$('#modalHouseRoomTitle').html("<i class='fa fa-edit'></i>&nbsp;修改门牌号");
			$('#modalHouseRoom').modal();
		},
		addInfo : function() {
			$('#modalHouseRoomTitle').html("<i class='fa fa-plus'></i>&nbsp;添加门牌号");
			$('#modalHouseRoom').modal();
		},
		submitForm : function() {
			$('#editForm').submit();
		}
	};
}();