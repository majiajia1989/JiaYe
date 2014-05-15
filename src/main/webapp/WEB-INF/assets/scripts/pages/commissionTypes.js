var CommissionTypes = function() {
	var handleCorp = function(ctxUrl) {
		$('.addCommisionType-form input').keypress(function(e) {
					if (e.which == 13) {
						if ($('.addCommisionType-form').validate().form()) {
							$('.addCommisionType-form').submit();
						}
						return false;
					}
				});
	}
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		var url = ctxUrl + 'commission/queryAllType';
		$('#commissiontype_table').dataTable({
			"bServerSide" : true,
			"sServerMethod" : "GET",
			'bPaginate' : true,
			"bProcessing" : true,
			'bFilter' : false,
			"sAjaxSource" : url,
			"iDisplayLength" : 10,
			"sPaginationType" : "bootstrap",
			"aoColumns" : [{
						"sTitle" : "类型名称",
						"sClass" : "center",
						"mDataProp" : "name",
						"bSortable" : false
					}, {
						"sTitle" : "模版个数",
						"sClass" : "center",
						"sDefaultContent" : "",
						"mDataProp" : "template_count",
						"bSortable" : false
					}, {
						"sTitle" : "操作",
						"sClass" : "center",
						"mDataProp" : "id",
						"sDefaultContent" : "",
						"bSortable" : false,
						"mRender" : function(data, type, full) {
							if (type == "display") {
								var templateUrl = ctxUrl
										+ "commission/commissionTemplates/";
								var templateA = '<a href="'
										+ templateUrl
										+ data
										+ '" class="btn iframe-btn btn-xs btn-edit">常用模版管理</a>';
								var editUrl = "javascript:CommissionTypes.editType('"
										+ data + "','" + full.name + "');";

								var editA = '<a href="'
										+ editUrl
										+ '" class="btn iframe-btn btn-xs btn-edit">编辑</a>';

								var removeUrl = "javascript:CommissionTypes.removeTypeByID('"
										+ ctxUrl + "','" + data + "');";

								var removeA = '<a href="#" class="btn iframe-btn btn-delete btn-xs" onclick='
										+ removeUrl + '>删除</a>';
								if (full.isCommissionuse) {
									removeA = "<a href='#' class='btn iframe-btn btn-delete btn-xs' disabled='true'><span>删除</span></a>";
								}
								return templateA + "&nbsp" + editA + "&nbsp"
										+ removeA;
							}
						}
					}

			]

		});
	}
	var deleteType = function(ctxUrl, id) {
		var url = ctxUrl + "commission/removeTypeByID/" + id;
		$.post(url, '', function(json) {
					if (json.success) {
						$('#commissiontype_table').dataTable().fnClearTable(0);
						$('#commissiontype_table').dataTable().fnDraw();
					} else {
						toastr.warning('删除出错!', '提示');;
					}
				}, "json");
	}
	var checkCommissionType = function(commission_value, ctxUrl) {
		var url = ctxUrl + "commission/checkCommissionType/" + commission_value
		$.post(url, '', function(json) {
					if (json.success) {
						toastr.warning('事务类型已存在!', '提示');
					}
				}, "json");
	}
	$('#type-submit-btn').bind("click", function() {
				if ($("#type-form").valid()) {
					$("#type-modal").modal('hide');
					$("#type-form").submit();
				}
			});

	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			handleCorp(ctxUrl);
			initTable(ctxUrl);
		},

		removeTypeByID : function(ctxUrl, id) {
			$("#deleteType-modal").modal('show');
			$('#deleteType-btn').bind("click", function() {
						deleteType(ctxUrl, id);
						$("#deleteType-modal").modal('hide');
					});
		},
		openTypeModal : function() {
			$("#id").attr("value", -1);
			$("#type_modal").modal('show');
			$("#input_type").attr("value", " ");
		},
		checkType : function(input, ctxUrl) {
			var commissiontype_value = input.value;
			checkCommissionType(commissiontype_value, ctxUrl);
		},
		editType : function(id, content) {
			$("#type_modal").modal('show');
			$("#id").attr("value", id);
			$("#input_type").attr("value", content);
		}

	};
}();