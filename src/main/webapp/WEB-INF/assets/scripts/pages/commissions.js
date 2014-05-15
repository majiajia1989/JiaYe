var Commissions = function() {
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		var dataUrl=ctxUrl + "commission/queryAll";
		$('#data_table').dataTable({
			"bServerSide" : true,
			"sServerMethod" : "GET",
			'bPaginate' : true,
			"bProcessing" : true,
			'bFilter' : false,
			"sAjaxSource" : dataUrl,
			"fnServerParams" : function(aaData) {
				var type = $("#commission-type").val();
				var response = $("#response-select").val();
				var keyword = $("#keyword").val();
				var begin = $('#begin').data('datepicker').getDate();
				var end = $("#end").data("datepicker").getDate();
				aaData.push({
							"name" : "type",
							"value" : type
						}, {
							"name" : "response",
							"value" : response
						}, {
							"name" : "keyword",
							"value" : keyword
						},{
							"name":"begin",
							"value":encodeURIComponent(begin.format("yyyy-MM-dd"))
						},{
							"name":"end",
							"value":encodeURIComponent(end.format("yyyy-MM-dd"))
						});
			},
			"aoColumns" : [{
				"mDataProp" : "creator",
				"sDefaultContent" : "",
				"sTitle" : "代办用户",
				"bSortable" : false,
				"mRender" : function(data, type, full) {
					if (type == "display") {
						var creator = "<div  id='creator_" + full.id + "'>"
								+ full.creator + "</div>";
						return creator;
					}
				}
			}, {
				"sTitle" : "内容",
				"mDataProp" : "content",
				"sClass" : "word-wrap",
				"sWidth" : "500px",
				"sDefaultContent" : "",
				"bSortable" : false,
				"mRender" : function(data, type, full) {
					if (type == "display") {
						var content = "<span id='content_" + full.id + "'>"
								+ data + "</span>";
						return content;
					}
				}
			}, {
				"mDataProp" : "createTime",
				"sDefaultContent" : "",
				"sTitle" : "时间",
				"bSortable" : false
			}, {
				"mDataProp" : "responsed",
				"sDefaultContent" : "",
				"sTitle" : "回复情况",
				"bSortable" : false,
				"mRender" : function(data, type, full) {
					if (type == "display") {
						return data ? "已回复" : "未回复";
					}
				}
			}, {
				"sTitle" : "操作",
				"mDataProp" : "id",
				"sDefaultContent" : "",
				"bSortable" : false,
				"mRender" : function(data, type, full) {
					if (type == "display") {
						var responseUrl = "Commissions.response('" + data
								+ "');";
						var responseA;
						var historyReply = ctxUrl + "/commission/historyReply/"
								+ data;
						if (full.responsed) {
							responseA = "<a class='btn default btn-xs grey' href='"
									+ historyReply + "'><span>历史回复</span></a>";
						} else {
							responseA = "<a class='btn default btn-xs grey' href='#' onclick=\""
									+ responseUrl + "\"><span>回复</span></a>";
						}
						return responseA;
					}
				}
			}],
			"iDisplayLength" : 10,
			"sPaginationType" : "bootstrap"
		});
		$("#commission-type").on("change", function() {
					$('#data_table').dataTable().fnClearTable(0);
					$('#data_table').dataTable().fnDraw();
				});
		$("#response-select").on("change", function() {
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

	return {
		init : function(ctxUrl) {
			if (typeof(ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			bindSearchBtn(ctxUrl);
			initTable(ctxUrl);

		},
		response : function(id) {
			$('#response-modal input[name="commissionID"]').attr("value", id);
			$('#response-modal #commission-creator').html($("#creator_" + id)
					.html());
			$('#response-modal #commission-content').html($("#content_" + id)
					.html());
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