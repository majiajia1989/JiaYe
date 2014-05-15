var Complaints = function() {
	var handleInit = function(ctxUrl, dataUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		// begin first table
		var oTable = $('#data_table').dataTable(
						{
							"bServerSide" : true,
							"sServerMethod" : "GET",
							'bPaginate' : true,
							"bProcessing" : true,
							'bFilter' : false,
							"sAjaxSource" : dataUrl,
							"fnServerParams" : fnServerParams,
							"aoColumns" : [
									{
										"mDataProp" : "creator",
										"sDefaultContent" : "",
										"sTitle" : "投诉用户",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {												
												return "<span id='creator_" + full.id + "'>" + data + "</span>";
											}
										}
									},									
									{
										"sClass":"word-wrap",
										"sWidth":500,
										"sTitle" : "投诉内容",
										"mDataProp" : "content",
										"sDefaultContent" : "",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {												
												return "<span id='content_" + full.id + "'>" + htmlEncode(data) + "</span>";
											}
										}
									},
									{
										"mDataProp" : "createTime",
										"sDefaultContent" : "",
										"sTitle" : "投诉时间",
										"bSortable" : false
									},
									{
										"mDataProp" : "responsed",
										"sDefaultContent" : "",
										"sTitle" : "回复情况",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {												
												return data ? "已回复" : "未回复";
											}
										}
									},
									{
										"sTitle" : "操作",
										"mDataProp" : "id",
										"sDefaultContent" : "",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												var responseUrl = "Complaints.response(" + data + ")";
												var responseA = "<a class='btn default btn-xs grey' href='#' onclick=\""
														+ responseUrl
														+ "\"><span>回复</span></a>";
												var responsesUrl = ctxUrl + "community/complaintResponses/" + data;
												var responsesA = "<a class='btn default btn-xs grey' href='" + responsesUrl + "'><span>历史回复</span></a>";	
												return full.responsed?responsesA:responseA;
											}
										}
									} ],
							// set the initial value
							"iDisplayLength" : 10,
							"sPaginationType" : "bootstrap"
						});
		
		$('#search-btn').click( function() {
			oTable.fnClearTable(0);
			oTable.fnDraw();
		});
		$("#anonymous-select").on("change",function(){
			oTable.fnClearTable(0);
			oTable.fnDraw();
		});
		$("#responsed-select").on("change",function(){
			oTable.fnClearTable(0);
			oTable.fnDraw();
		});
	}
	
	var fnServerParams = function(aaData) {
		var anonymous = $("#anonymous-select").val(); 
		var responsed = $("#responsed-select").val(); 
		var begin = $('#begin').data('datepicker').getDate();
		var end = $("#end").data("datepicker").getDate();
		if (anonymous != '0') {
			aaData.push({
				"name" : "anonymous",
				"value" : (anonymous == 1)
			});
		}
		if (responsed != '0') {
			aaData.push({
				"name" : "responsed",
				"value" : (responsed == 1)
			});
		}
		if (begin) {
			aaData.push({
				"name" : "begin",
				"value" : begin.format("yyyy-MM-dd")
			});
		}
		if (end) {
			aaData.push({
				"name" : "end",
				"value" : end.format("yyyy-MM-dd")
			});
		}
	}
	
	function htmlEncode(value) {
		value = $('<div/>').text(value).html();
		return value.replace(/\n/g,"<br />");
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			handleInit(ctxUrl, ctxUrl + 'community/queryAllComplaints');
		},
		response : function(id) {
			$('#response-modal input[name="complaintID"]').attr("value", id);
			$('#response-modal #complaint-creator').html($("#creator_" + id).html());
			$('#response-modal #complaint-content').html($("#content_" + id).html());
			$('#response-modal textarea[name="content"]').attr("value", "");
			$('#response-modal').modal('show');
			$('#response-submit-btn').bind("click", function() {
				if($("#repsonse-form").valid())	{
					$('#response-modal').modal('hide');
				    $("#repsonse-form").submit();				    
				}
			});
		}
	};

}();