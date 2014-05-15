var OwnerWeixins = function() {
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		var dataUrl = ctxUrl + 'community/queryAllWeixin';
		$('#data_table').dataTable({
			"bServerSide" : true,
			"sServerMethod" : "GET",
			'bPaginate' : true,
			"bProcessing" : true,
			'bFilter' : false,
			"sAjaxSource" : dataUrl,
			"fnServerParams" : function(aaData) {
				var house = $("#house-select").val();
				var houseunit = $("#houseUnit-select").val();
				var mobile = $("#mobilephone-select").val();
				var car = $("#car-select").val();
				var keyword = $("#keyword").val();
				aaData.push({
							"name" : "house",
							"value" : house
						}, {
							"name" : "houseunit",
							"value" : houseunit
						}, {
							"name" : "keyword",
							"value" : keyword
						});

				if (mobile != "0") {
					aaData.push({
								"name" : "mobile",
								"value" : (mobile == 1)
							})
				}
				if (car != "0") {
					aaData.push({
								"name" : "car",
								"value" : (car == 1)
							})
				}
			},
			"aoColumns" : [{
						"sTitle" : "楼号",
						"mDataProp" : "house_name",
						"sDefaultContent" : "",
						"bSortable" : false
					}, {
						"mDataProp" : "house_unit",
						"sDefaultContent" : "",
						"sTitle" : "单元号",
						"bSortable" : false
					}, {
						"mDataProp" : "house_room",
						"sDefaultContent" : "",
						"sTitle" : "门牌号",
						"bSortable" : false
					},

					{
						"mDataProp" : "nick_name",
						"sDefaultContent" : "",
						"sTitle" : "昵称",
						"bSortable" : false
					}, {
						"sTitle" : "操作",
						"mDataProp" : "openid",
						"sDefaultContent" : "",
						"bSortable" : false,
						"mRender" : function(data, type, full) {
							if (type == "display") {
								var sendUrl = "OwnerWeixins.sendWeixin('"
										+ data + "')";
								var sendA = "<a class='btn iframe-btn btn-xs btn-edit' href='#' onclick=\""
										+ sendUrl + "\"><span>发送微信</span></a>";
								var unbundleUrl = "OwnerWeixins.unbundling('"
										+ data + "','" + ctxUrl + "')";
								var unbundleA = "<a class='btn iframe-btn btn-delete  btn-xs' href='#' onclick=\""
										+ unbundleUrl
										+ "\"><span>解绑</span></a>";
								return sendA + "&nbsp" + unbundleA;
							}
						}
					}],
			// set the initial value
			"iDisplayLength" : 10,
			"sPaginationType" : "bootstrap"
		});
		$("#house-select").on("change", function() {
					$('#data_table').dataTable().fnClearTable(0);
					$('#data_table').dataTable().fnDraw();
				});
		$("#houseUnit-select").on("change", function() {
					$('#data_table').dataTable().fnClearTable(0);
					$('#data_table').dataTable().fnDraw();
				});
		$("#mobilephone-select").on("change", function() {
					$('#data_table').dataTable().fnClearTable(0);
					$('#data_table').dataTable().fnDraw();
				});
		$("#car-select").on("change", function() {
					$('#data_table').dataTable().fnClearTable(0);
					$('#data_table').dataTable().fnDraw();
				});

	}

	var bindSearchBtn = function(ctxUrl) {
		$('#search-btn').click(function() {
					$('#data_table').dataTable().fnClearTable(0);
					$('#data_table').dataTable().fnDraw();
				});
	}

	var initHouseSelect = function(houseInfo) {
		$("#house-select").append("<option value='0'>全部</option>");
		$("#houseUnit-select").append("<option value='0'>全部</option>");
		if (houseInfo.length != 0) {
			for (var i = 0; i < houseInfo.length; i++) {
				$("#house-select").append("<option value='" + houseInfo[i].id
						+ "'>" + houseInfo[i].text + "</option>");
			}
		}

		changeChild($("#house-select").val(), houseInfo);

		$("#house-select").change(function() {
					changeChild($(this).val(), houseInfo);
				});
	}
	var changeChild = function(id, houseInfo) {
		for (var i = 0; i < houseInfo.length; i++) {
			$("#houseUnit-select").html("");
			$("#houseUnit-select").append("<option value='0'>全部</option>");
			if (houseInfo[i].id == id) {
				var unit_result = houseInfo[i].children;
				if (unit_result.length != 0) {
					for (var i = 0; i < unit_result.length; i++) {
						$("#houseUnit-select").append("<option value='"
								+ unit_result[i].id + "'>"
								+ unit_result[i].text + "</option>");
					}
				}
				return false;
			}
		}

	}
	var remove = function(ctxUrl, openid) {
		var url = ctxUrl + "community/removeBundlingByOpenID/" + openid;
		$.post(url, '', function(json) {
					if (json.success) {
					$('#data_table').dataTable().fnClearTable(0);
					$('#data_table').dataTable().fnDraw();
					} else {
						toastr.warning(json.errMsg, '提示');
					}
				}, "json");
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl, houseInfo) {
			if (typeof(ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			initHouseSelect(houseInfo);
			bindSearchBtn(ctxUrl);
			initTable(ctxUrl);
			if ($("#err").val() != null) {
				toastr.warning($("#err").val(), '提示');
			}
			if ($("#info").val() != null) {
				toastr.info($("#info").val(), '提示');
			}
		},
		unbundling : function(openid, ctxUrl) {
			bootbox.dialog({
						message : "确定解邦吗？",
						title : "提示",
						buttons : {
							success : {
								label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
								className : "btn iframe-btn",
								callback : function() {
									remove(ctxUrl, openid);
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
		sendWeixin : function(openid) {
			$('#response-modal input[name="openid"]').attr("value", openid);
			$('#response-modal textarea[name="content"]').attr("value", "");
			$('#response-modal').modal('show');
			$('#response-submit-btn').bind("click", function() {
						if ($("#repsonse-form").valid()) {
							$('#response-modal').modal('hide');
							$("#repsonse-form").submit();
						}
					});
		}
	};

}();