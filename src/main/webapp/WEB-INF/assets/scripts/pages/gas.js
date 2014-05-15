var Gas = function() {
	var resolveRootUrl = "";
	var initList = function() {
		var url = resolveRootUrl + 'consumeInfo/readConsumeInfo?'
				+ $("#formQuery").serialize();
		$("#dtList").dataTable(
				{
					"bServerSide" : true,
					"sServerMethod" : "GET",
					'bPaginate' : true,
					"bProcessing" : true,
					'bFilter' : false,
					"sAjaxSource" : url,
					"iDisplayLength" : 10,
					"sPaginationType" : "bootstrap",
					"bInfo" : true,
					"aoColumns" : [
							{
								"sTitle" : "楼号",
								"sWidth" : "100px",
								"sClass" : "left",
								"bSortable" : false,
								"mDataProp" : "house.name"
							},
							{
								"sTitle" : "门牌号",
								"sWidth" : "100px",
								"sClass" : "left",
								"bSortable" : false,
								"mDataProp" : "houseRoom.name"
							},
							{
								"sTitle" : "缴费户号",
								"sWidth" : "100px",
								"sClass" : "left",
								"bSortable" : false,
								"mDataProp" : "payNumber"
							},
							{
								"sTitle" : "年月",
								"sWidth" : "100px",
								"sClass" : "left",
								"bSortable" : false,
								"mDataProp" : "year",
								"fnRender" : function(jsonData) {
									return jsonData.aData.year + "年"
											+ jsonData.aData.month + "月";
								} // 自定义列的样式
							}, {
								"sTitle" : "上期表数",
								"sWidth" : "100px",
								"sClass" : "left",
								"bSortable" : false,
								"mDataProp" : "postAmount"
							}, {
								"sTitle" : "本期表数",
								"sWidth" : "100px",
								"sClass" : "left",
								"bSortable" : false,
								"mDataProp" : "currentAmount"
							}, {
								"sTitle" : "本期用量",
								"sWidth" : "100px",
								"sClass" : "left",
								"bSortable" : false,
								"mDataProp" : "amount"
							} ]
				});
	};

	var initSendConsumeTemplateSelect = function() {
		$("#sendConsumeTemplate").change(
				function() {
					$("#sendContent").val(
							$("#sendConsumeTemplate").find("option:selected")
									.attr("tag"));
				});
	};
	var initSendModal = function() {
		$('#modalSendWeixin').on('show.bs.modal', function(e) {
			$("#sendPayNumber").val($("#queryPayNumber").val());
			$("#sendYear").val($("#queryYear").val());
			$("#sendMonth").val($("#queryMonth").val());
			$("#sendHouseId").val($("#queryHouseId").val());
			$("#sendHouseRoomId").val($("#queryHouseRoomId").val());
			$("#sendContent").val(
					$("#sendConsumeTemplate").find("option:selected")
							.attr("tag"));
			//$("#modalSendWeixin").modal('show');
		})
	};

	return {
		init : function(url) {
			if (typeof (url) == 'undefined') {
				url = '';
			}
			resolveRootUrl = url;
			$("#queryYear").val((new Date()).getFullYear());
			$("#queryMonth").val((new Date()).getMonth() + 1);

			$("#importYear").val((new Date()).getFullYear());
			$("#importMonth").val((new Date()).getMonth() + 1);

			$("#queryConsumeTypeId").val(3);
			$("#importConsumeTypeId").val(3);
			$("#sendConsumeTypeId").val(3);

			initSendConsumeTemplateSelect();
			initSendModal();
			initList();
		},
		submitQueryForm : function() {
			$('#dtList').dataTable().fnDestroy();
			initList();
			$('#modalQuery').modal('hide');
		},

		submitImportForm : function() {
			$('#formImport').submit();
		},
		submitSendForm : function() {
			$('#formSend').submit();
		}

	};
}();