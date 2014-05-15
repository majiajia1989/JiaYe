var ComplaintResponses = function() {
	var initTable = function(ctxUrl, dataUrl) {
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
			"aoColumns" : [
			        {
						"sClass":"word-wrap",
						"sWidth":500,
						"sTitle" : "回复内容",
						"mDataProp" : "content",
						"sDefaultContent" : "",
						"bSortable" : false,
						"mRender" : function(data, type, full) {
							if (type == "display") {												
								return "<span id='content_" + full.id + "'>" + htmlEncode(data) + "</span>";
							}
						}
					}, {
						"mDataProp" : "createTime",
						"sDefaultContent" : "",
						"sTitle" : "回复时间",
						"bSortable" : false
					}, {
						"mDataProp" : "creator",
						"sDefaultContent" : "",
						"sTitle" : "回复人",
						"bSortable" : false
					}],
			// set the initial value
			"iDisplayLength" : 10,
			"sPaginationType" : "bootstrap"
		});
	}

	function htmlEncode(value) {
		value = $('<div/>').text(value).html();
		return value.replace(/\n/g,"<br />");
	}
	
	var bindReplyBtn = function(ctxUrl,complaintID, complaintCreator, complaintContent) {
		$('#data_table_reply_btn').click(function() {
					$('#response-modal input[name="complaintID"]').attr("value", complaintID);
					$('#response-modal #complaint-creator').html(complaintCreator);
					$('#response-modal #complaint-content').html(complaintContent);
					$('#response-modal textarea[name="content"]').attr("value","");
					$('#response-modal').modal('show');
					$('#response-submit-btn').bind("click", function() {
								if ($("#repsonse-form").valid()) {
									$('#response-modal').modal('hide');
									$("#repsonse-form").submit();
								}
							});
				});
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl, complaintID) {
			if (typeof(ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			var complaintCreator = $("#creator").val();
			var complaintContent = $("#content").val()
			bindReplyBtn(ctxUrl,complaintID, htmlEncode(complaintCreator), htmlEncode(complaintContent));
			initTable(ctxUrl, ctxUrl + 'community/queryComplaintResponses?complaintID='+complaintID);
		}
	};

}();