var User = function() {
	var resolveRootUrl = "";
	var initList = function() {
		var url = resolveRootUrl + 'user/readUserByCorp';
		var editUrl = resolveRootUrl + 'user/editUser/';
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
							"aoColumns" : [
									{
										"sTitle" : "用户名",
										"sWidth" : "100px",
										"sClass" : "left",
										"mDataProp" : "name"
									},
									{
										"sTitle" : "邮箱",
										"sWidth" : "100px",
										"sClass" : "left",
										"mDataProp" : "email",
										"bSortable" : false
									},
									{
										"sTitle" : "状态",
										"sWidth" : "80px",
										"sClass" : "left",
										"mDataProp" : "locked",
										"bSortable" : false,
										"fnRender" : function(jsonData) {
											var status = jsonData.aData.locked;
											if (status == "1") {
												return "锁定";
											} else {
												return "正常";
											}
										} // 自定义列的样式
									},
									{
										"sTitle" : "最后登录时间",
										"sWidth" : "180px",
										"sClass" : "left",
										"mDataProp" : "lastVisit",
										"bSortable" : false
									},
									{
										"sTitle" : "最后登录IP",
										"sWidth" : "100px",
										"sClass" : "left",
										"mDataProp" : "lastIp",
										"bSortable" : false
									},
									{
										"sTitle" : "",
										"sWidth" : "40px",
										"sClass" : "center",
										"mDataProp" : "id",
										"bSortable" : false,
										"fnRender" : function(jsonData) {
											if (jsonData.aData.name == "admin") {
												return "";
											} else if (jsonData.aData.locked == "正常") {
												lockButton = '<button class="btn btn-xs iframe-btn btn-edit" style=" cursor:hand;" onclick="'
														+ "javascript:user.lockUser(\'"
														+ jsonData.aData.name
														+ "\');"
														+ '" >锁定</button>';
												return lockButton;
											} else {
												unlockButton = '<button class="btn btn-xs iframe-btn btn-edit" style=" cursor:hand;" onclick="'
														+ "javascript:user.unLockUser(\'"
														+ jsonData.aData.name
														+ "\');"
														+ '" >解锁</button>';

												return unlockButton;

											}
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
		lockUser : function(name) {
			if (name == 'admin') {
				toastr.warning('【' + name + '】是系统管理员,不允许操作！', '警告');
				// showAlert('【' + name + '】是系统管理员,不允许操作！');
				return;
			}
			bootbox.dialog({
				message : '你确定要锁定用户【' + name + '】吗？',
				title : "提示",
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn btn-primary",
						callback : function() {
							var data = 'name=' + name;
							$.post(resolveRootUrl + 'user/lock', data,
									function(json) {
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
		unLockUser : function(name) {
			if (name == 'admin') {
				toastr.warning('【' + name + '】是系统管理员,不允许操作！', '警告');
				return;
			}

			bootbox.dialog({
				message : '你确定要对用户【' + name + '】解锁吗？',
				title : "提示",
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn btn-primary",
						callback : function() {
							var data = 'name=' + name;
							$.post(resolveRootUrl + 'user/unlock', data,
									function(json) {
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
		}
	};
}();