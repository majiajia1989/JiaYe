var HistoryReply = function() {
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		var url = ctxUrl + 'commission/queryAllReply/'+$("#commissionID").val();
		$('#data_table')
				.dataTable(
						{
							"bServerSide" : true,
							"sServerMethod" : "GET",
							'bPaginate' : true,
							"bProcessing" : true,
							'bFilter' : false,
							"sAjaxSource" : url,
							"aoColumns" : [
									{
										"mDataProp" : "content",
										"sDefaultContent" : "",
										"sClass":"word-wrap",
										"sWidth":"500px",
										"sTitle" : "回复内容",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												return '<span style="display:block;max-width:500px;overflow:hidden;">'
														+ data + '</span>';
											}
										}
									},									
									{
										"sTitle" : "回复时间",
										"mDataProp" : "replyTime",
										"sDefaultContent" : "",
										"bSortable" : false
									}
									,									
									{
										"sTitle" : "回复人",
										"mDataProp" : "creator",
										"sDefaultContent" : "",
										"bSortable" : false
									}
									],
							// set the initial value
							"iDisplayLength" : 10,
							"sPaginationType" : "bootstrap"
						});
	}
	$('#response-submit-btn').bind("click", function() {
		if($("#repsonse-form").valid())	{
			$('#reply_modal').modal('hide');
		    $("#repsonse-form").submit();				    
		}
	});
	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			initTable(ctxUrl);
			
		},
		openReplyModal:function(){
			$("#reply_modal").modal('show');
			$('#reply_modal #commission-creator').html($("#commissionCreator").val());
			$('#reply_modal #commission-content').html($("#commissionContent").val());
			$("#content").attr("value"," ");
		}
	};

}();