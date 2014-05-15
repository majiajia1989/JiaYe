var LoginLog = function() {
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		var url = ctxUrl + 'user/queryAllLog';
		$('#data_table').dataTable({
			"bServerSide" : true,
			"sServerMethod" : "GET",
			'bPaginate' : true,
			"bProcessing" : true,
			'bFilter' : false,
			"sAjaxSource" : url,
			"fnRowCallback" : function(nRow, data, iDisplayIndex,
					iDisplayIndexFull) {
				var index = iDisplayIndex + 1;
				$('td:eq(0)', nRow).html(index);
				return nRow;
			},
			"iDisplayLength" : 10,
			"sPaginationType" : "bootstrap",
			"aoColumns" : [{
						"sTitle" : "",
						"sDefaultContent" : "",
						"mDataProp" : null,
						"bSortable" : false

					}, {
						"sTitle" : "登录IP",
						"sClass" : "center",
						"mDataProp" : "ip",
						"bSortable" : false
					}, {
						"sTitle" : "登录时间",
						"sClass":"center",
						"mDataProp" : "loginDate",
						"bSortable" : false
						
					}
			]
		});
	}
	return {
		init : function(ctxUrl) {
			initTable(ctxUrl);
		}	
	};
}();