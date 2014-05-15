var Boards = function() {
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		var url = ctxUrl + 'bbs/allBoards';
		var oTable = $('#data_table').dataTable({
			"bServerSide" : true,
			"sServerMethod" : "GET",
			'bPaginate' : true,
			"bProcessing" : true,
			'bFilter' : false,
			"sAjaxSource" : url,
			"oLanguage" : {
				"sProcessing" : '<i class="fa fa-coffee"></i>&nbsp;请稍候...'
			},
			"fnServerParams" : function(aaData) {
				var type=$("#board-type").val();
				aaData.push({
							"name" : "type",
							"value" : type
						});
			},
			"iDisplayLength" : 10,
			"sPaginationType" : "bootstrap",
			"aoColumns" : [{
						"sTitle" : "",
						"sDefaultContent" : "",
						"mDataProp" : null,
						"bSortable" : false

					}, {
						"sTitle" : "板块名称",
						"sClass" : "center",
						"mDataProp" : "name",
						"bSortable" : false
					}, {
						"sTitle" : "板块说明",
						"sClass" : "center",
						"mDataProp" : "description",
						"bSortable" : false
					}]
		});
		$("#board-type").on("change",function(){
			oTable.fnClearTable(0);
			oTable.fnDraw();
		});
	}
	return {
		init : function(ctxUrl) {
			initTable(ctxUrl);
		}
	};
}();