var CorpUser = function() {
	var resolveRootUrl = "";
	var initList = function() {
		var url = resolveRootUrl + 'user/readCorps';
		var editUrl = resolveRootUrl + 'user/editCorpUser/';
		$("#dtList").dataTable(
				{

					"bServerSide" : true,
					"sServerMethod" : "GET",
					'bPaginate' : true,
					"bProcessing" : true,
					'bFilter' : false,
					"sAjaxSource" : url,
					"iDisplayLength" : 10,
					"sPaginationType" : "bootstrap",
					"aoColumns" : [ {
						"sTitle" : "单位名称",
						"sWidth" : "100px",
						"sClass" : "left",
						"mDataProp" : "name",
						"bSortable" : false
					}, {
						"sTitle" : "地址",
						"sWidth" : "100px",
						"sClass" : "left",
						"mDataProp" : "address",
						"bSortable" : false
					}, {
						"sTitle" : "创建时间",
						"sWidth" : "180px",
						"sClass" : "left",
						"mDataProp" : "createTime",
						"bSortable" : false
					}, {
						"sTitle" : "类型",
						"sWidth" : "100px",
						"sClass" : "left",
						"mDataProp" : "type.name",
						"bSortable" : false
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
				message : '你确定要锁定【' + name + '】吗？',
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
				message : '你确定要对【' + name + '】解锁吗？',
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