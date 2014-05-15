var SendNotice = function() {
	var initTable = function(dataUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		// begin first table
		$('#data_table').dataTable({
					"bServerSide" : true,
					"sServerMethod" : "GET",
					'bPaginate' : true,
					"bProcessing" : true,
					'bFilter' : false,
					"sAjaxSource" : dataUrl,
					"aoColumns" : [{
								"sTitle" : "小区",
								"sDefaultContent" : "",
								"mDataProp" : "community",
								"bSortable" : false
							}, {
								"sTitle" : "楼号",
								"sDefaultContent" : "",
								"mDataProp" : "house",
								"bSortable" : false
							}, {
								"sTitle" : "单元号",
								"sDefaultContent" : "",
								"mDataProp" : "houseUnit",
								"bSortable" : false
							}, {
								"sTitle" : "楼层",
								"sDefaultContent" : "",
								"mDataProp" : "houseFloor",
								"bSortable" : false
							}, {
								"sTitle" : "门牌号",
								"sDefaultContent" : "",
								"mDataProp" : "houseRoom",
								"bSortable" : false
							}],
					// set the initial value
					"iDisplayLength" : 10,
					"sPaginationType" : "bootstrap"
				});
	}

	var getQueryArray = function() {
		var query = new Array();

		var houses = $('#house-select option:selected');
		var floors = $('#floor-select option:selected');
		var units = $('#unit-select option:selected');
		var hasCar = $("#hascar-select").val();
		if (hasCar != '0') {
			query.push("hasCar=" + (hasCar == '1'));
		}
		if (houses) {
			var vals = new Array();
			houses.each(function() {
						vals.push($(this).val());
					});
			if (vals.length == 1 && vals[0] == '-1') {
			} else if (vals.length > 0) {
				query.push("houses=" + encodeURIComponent(vals.join(',')));
			}
		}
		if (floors) {
			var vals = new Array();
			floors.each(function() {
						vals.push($(this).val());
					});
			if (vals.length == 1 && vals[0] == '-1') {
			} else if (vals.length > 0) {
				query.push("floors=" + encodeURIComponent(vals.join(',')));
			}
		}
		if (units) {
			var vals = new Array();
			units.each(function() {
						vals.push($(this).val());
					});
			if (vals.length == 1 && vals[0] == '-1') {
			} else if (vals.length > 0) {
				query.push("units=" + encodeURIComponent(vals.join(',')));
			}
		}
		return query;
	}

	var bindSearchBtn = function(ctxUrl) {
		$('#search-btn').click(function() {
					var query = getQueryArray();
					var queryUrl = ctxUrl + 'community/queryAllReceivers';
					if (query.length > 0) {
						queryUrl += "?" + query.join("&");
					}
					$('#data_table').dataTable().fnDestroy();
					initTable(queryUrl);
				});
	}

	var bindSendBtn = function(ctxUrl, noticeID) {
		$('#send-btn').click(function() {
			bootbox.dialog({
				title:'提示',
				message:'确定发送这条通知吗?',
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn btn-primary",
						callback : function() {
							doSendByID(ctxUrl, noticeID);
						}
					},
					danger : {
						label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
						className : "btn btn-default"
					}
				}
			});
		});
	}
	
	var doSendByID = function(ctxUrl, noticeID) {
		var query = getQueryArray();
		query.push("noticeID=" + noticeID);
		var sendUrl = ctxUrl + "community/sendNotice";
		if (query.length > 0) {
			sendUrl += "?" + query.join("&");
		}
		$.post(sendUrl, '', function(json) {
					if (json.success) {
						toastr.warning('发送成功!', '提示');
						 window.setTimeout(function(){  
							 window.location = ctxUrl + "community/notices";
			                }, 1200);
					} else {
						toastr.warning('发送失败!', '提示');
					}
				}, "json");
	}
	

	var onSelectAllChange = function(selectName, value, checked) {
		if (value != "-1") {
			$('#' + selectName).multiselect('deselect',"-1");
			return;
		}
		var nonSelectedOptions = $('#' + selectName + ' option').filter(
				function() {
					return $(this).val() != "-1";
				});
		if (checked) {
			nonSelectedOptions.each(function() {
//						var input = $('input[value="' + $(this).val() + '"]');
//						input.prop('disabled', true);
//						input.parent('li').addClass('disabled');
						$('#' + selectName).multiselect('deselect',
								$(this).val());
					});
		} else {
			nonSelectedOptions.each(function() {
//						var input = $('input[value="' + $(this).val() + '"]');
//						input.prop('disabled', false);
//						input.parent('li').addClass('disabled');
					});
		}
	}

	var handleSelect = function() {
		$('#house-select').multiselect({
					maxHeight : 400,
					onChange : function(option, checked) {
						onSelectAllChange('house-select', $(option).val(),
								checked);
					}
				});
		$('#unit-select').multiselect({
					maxHeight : 400,
					onChange : function(option, checked) {
						onSelectAllChange('unit-select', $(option).val(),
								checked);
					}
				});
		$('#floor-select').multiselect({
					maxHeight : 400,
					onChange : function(option, checked) {
						onSelectAllChange('floor-select', $(option).val(),
								checked);
					}
				});
		$('#house-select').multiselect('select', "-1");
		$('#unit-select').multiselect('select', "-1");
		$('#floor-select').multiselect('select', "-1");
		onSelectAllChange('house-select', "-1", true);
		onSelectAllChange('unit-select', "-1", true);
		onSelectAllChange('floor-select', "-1", true);
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl, noticeID) {
			if (typeof(ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			handleSelect();
			bindSendBtn(ctxUrl, noticeID);
			bindSearchBtn(ctxUrl);
			$("#search-btn").trigger("click");
		}
	};

}();