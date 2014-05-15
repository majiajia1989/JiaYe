var HouseFloor = function() {
	var resolveRootUrl = "";
	var initList = function() {
		var url = resolveRootUrl + 'house/readHouseFloorByCommunity/'
				+ $("#communityId").val();
		var deleteHouseUrl = resolveRootUrl + 'house/deleteHouseFloor/';

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
										"sTitle" : "楼层号",
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
												+ "javascript:HouseFloor.editInfo(\'"
												+ jsonData.aData.id + "\',\'" + jsonData.aData.name +"\');" + '" >编辑</button>';
											var deleteInfo = '<button class="btn btn-xs iframe-btn btn-delete" style=" cursor:hand;" onclick="'
													+ "javascript:HouseFloor.deleteInfo(\'"
													+ jsonData.aData.id
													+ "\');" + '" >删除</button>';

											return editInfo + '&nbsp' + deleteInfo;

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
			$('#houseFloorId').val(-1);
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
							$.post(resolveRootUrl + 'house/deleteHouseFloor',
									"houseFloorId=" + id, function(json) {
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
						label : "<i class='fa fa-check'/>&nbsp取&nbsp消",
						className : "fa fa-times",
						callback : function() {
						}
					}
				}
			});

		},
		editInfo:function(id,name){
			$('#name').val(name);
			$('#houseFloorId').val(id);
			
			$('#modalHouseFloorTitle').html("<i class='fa fa-edit'></i>&nbsp;修改楼层");
			$('#modalHouseFloor').modal();
		},
		addInfo : function() {
			$('#modalHouseFloorTitle').html("<i class='fa fa-plus'></i>&nbsp;添加楼层");
			$('#modalHouseFloor').modal();
		},
		submitForm : function() {
			$('#editForm').submit();
		}
	};
}();