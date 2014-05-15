var CommissionTemplates = function() {
	var handleCorp = function(ctxUrl) {
		$('.addCommisionTemplate-form input').keypress(function(e) {
			if (e.which == 13) {
				if ($('.addCommisionTemplate-form').validate().form()) {
					$('.addCommisionTemplate-form').submit();
				}
				return false;
			}
		});
	}
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		var url = ctxUrl + 'commission/commissionTemplateQueryAll/'
		+$("#type_id").val();
		$('#commissiontemplate_table').dataTable({
			"bServerSide" : true,
			"sServerMethod" : "GET",
			'bPaginate' : true,
			"bProcessing" : true,
			'bFilter' : false,
			"sAjaxSource" : url,
			"iDisplayLength" : 10,
			"sPaginationType" : "bootstrap",
			"aoColumns" : [{
						"sTitle" : "内容",
						"sClass" : "center",
						"mDataProp" : "content",
						"bSortable" : false
					},{
						"sTitle" : "操作",
						"sClass" : "center",
						"mDataProp" : "id",
						"sDefaultContent" : "",
						"bSortable" : false,
						"mRender" : function(data, type, full) {
							if (type == "display") {
								
								var editUrl="javascript:CommissionTemplates.editTemplate('"
									+data+"','"+full.content+"');";
								
								var editA='<a href="' +editUrl
								+ '" class="btn iframe-btn btn-xs btn-edit">编辑</a>';
								
								var removeUrl = "javascript:CommissionTemplates.removeTemplateByID('"
										+ ctxUrl + "','" + data + "');";
							
								var removeA = '<a href="#" class="btn iframe-btn btn-delete btn-xs" onclick='
										+ removeUrl + '>删除</a>';
								
								return editA+"&nbsp"+removeA;
							}
						}
					}

			]

		});
	}
	var deleteTemplate = function(ctxUrl, id) {
		var url = ctxUrl + "commission/removeTemplateByID/" + id;
		$.post(url,'', function(json) {
					if (json.success) {
//						$('#commissiontemplate_table').dataTable().fnDestroy();
//						initTable(ctxUrl);
						$('#commissiontemplate_table').dataTable().fnClearTable(0);
						$('#commissiontemplate_table').dataTable().fnDraw();
					} else {
						toastr.warning('删除出错!', '提示');
					}
				},"json");
	}

	$('#template-submit-btn').bind("click", function(){
		if($("#template-form").valid())	{
			$("#template-modal").modal('hide');
			$("#template-form").submit();
		}
		
	});
	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			handleCorp(ctxUrl);
			initTable(ctxUrl);
		},
		openTemplateModal:function(){
			$("#template_modal").modal('show');
			$("#id").attr("value",-1);
			$("#input_template").attr("value"," ");
		},

		removeTemplateByID : function(ctxUrl, id) {
			$("#deleteTemplate-modal").modal('show');
			$('#deleteTemplate-btn').bind("click", function() {
				deleteTemplate(ctxUrl, id);
				$("#deleteTemplate-modal").modal('hide');
			});
		},
		editTemplate:function(id,content){
			$("#template_modal").modal('show');
			$("#id").attr("value",id);
			$("#input_template").attr("value",content);
		}

		
	};
}();