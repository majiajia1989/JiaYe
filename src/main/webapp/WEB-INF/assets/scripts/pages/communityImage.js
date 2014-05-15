var CommunityImage = function() {
	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}

		var dataUrl = ctxUrl + 'community/readCommunityImage';
		$('#data_table')
				.dataTable(
						{
							"bServerSide" : true,
							"sServerMethod" : "GET",
							'bPaginate' : true,
							"bProcessing" : true,
							'bFilter' : false,
							"sAjaxSource" : dataUrl,
							"aoColumns" : [
									{
										"mDataProp" : "title",
										"sDefaultContent" : "",
										"sClass":"word-wrap",
										"sTitle" : "标题",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {												
												return "<span>" + htmlEncode(data) + "</span>";
											}
										}
									},								
									{
										"sTitle" : "图片",
										"mDataProp" : "imageURL",
										"sWidth":"80px",
										"sDefaultContent" : "",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											return "<img src='"
													+ full.imageUrl
													+ "' width='40px' height='30px' alt='' />";
										}
									},
									{
										"mDataProp" : "url",
										"sDefaultContent" : "",
										"sTitle" : "链接地址",
										"bSortable" : false
									},
									{
										"mDataProp" : "descript",
										"sDefaultContent" : "",
										"sClass":"word-wrap",
										"sWidth":"450px",
										"sTitle" : "描述",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {												
												return "<span>" + htmlEncode(data) + "</span>";
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
												var removeUrl = "javascript:CommunityImage.removeByID('"
														+ ctxUrl
														+ "','"
														+ data
														+ "');";
												var removeA = '<a href="#" class="btn iframe-btn btn-delete  btn-xs" onclick='
													+ removeUrl + '>删除</a>';												
												var setDefaultUrl="javascript:CommunityImage.setDefaultImageByID('"+ctxUrl+"','"+data+"');";
												var cancleDefaultUrl="javascript:CommunityImage.cancleDefaultImageByID('"+ctxUrl+"','"+data+"');";
												
												if (full.defalutImage) {
													removeA = "<a href='#' class='btn iframe-btn btn-delete  btn-xs' disabled='true'><span>删除</span></a>";
												}
												
												if(full.defalutImage){
													setDefault='<a href="#" class="btn default btn-xs blue" onclick='+cancleDefaultUrl+'>取消欢迎图片</a>'
												}else{
													setDefault='<a href="#" class="btn default btn-xs blue" onclick='+setDefaultUrl+'>设为欢迎图片</a>';
												}												
												return removeA + "&nbsp"+setDefault;;
											}
										}
									}, ],
							"iDisplayLength" : 10,
							"sPaginationType" : "bootstrap",
						});
	};
	
	var deleteMsg = function(ctxUrl, id) {
		var url = ctxUrl + "community/removeImageByID/" + id;
		$.post(url,'', function(json) {
					if (json.success) {
						$('#data_table').dataTable().fnDestroy();
						initTable(ctxUrl);
					} else {
						toastr.warning(json.errMsg, '提示');
					}
				},"json");
	}
	
	var setDefaultImage=function(ctxUrl,id){
		var url=ctxUrl+"community/setDefaultImage/"+id;
		$.post(url,'',function(json){
			if(json.success){
				$('#data_table').dataTable().fnDestroy();
				initTable(ctxUrl);
			}else{
				toastr.warning(json.errMsg, '提示');
			}
		},"json");
	}
	
	var cancleDefaultImage=function(ctxUrl,id){
		var url=ctxUrl+"community/cancleDefaultImage/"+id;
		$.post(url,'',function(json){
			if(json.success){
				$('#data_table').dataTable().fnDestroy();
				initTable(ctxUrl);
			}else{
				toastr.warning(json.errMsg, '提示');
			}
		},"json");
	}	

	function htmlEncode(value) {
		value = $('<div/>').text(value).html();
		return value.replace(/\n/g,"<br />");
	}
	
	return {
		init : function(ctxUrl) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			initTable(ctxUrl);
		},
		removeByID : function(ctxUrl, id) {
			bootbox.dialog({
				message : "确定删除图片吗？",
				title : "提示",
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn iframe-btn",
						callback : function() {
							deleteMsg(ctxUrl, id);
						}
					},
					danger : {
						label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
						className : "btn btn-default",
						callback : function() {
						}
					}
				}
			});
		},
		setDefaultImageByID : function(ctxUrl, id) {			
			bootbox.dialog({
				message : "设为欢迎图片？",
				title : "提示",
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn iframe-btn",
						callback : function() {
							setDefaultImage(ctxUrl, id);
						}
					},
					danger : {
						label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
						className : "btn btn-default",
						callback : function() {
						}
					}
				}
			});							
					
		},
		cancleDefaultImageByID : function(ctxUrl, id) {
			bootbox.dialog({
				message : "取消欢迎图片？",
				title : "提示",
				buttons : {
					success : {
						label : "<i class='fa fa-check'/>&nbsp确&nbsp认",
						className : "btn iframe-btn",
						callback : function() {
							cancleDefaultImage(ctxUrl, id);
						}
					},
					danger : {
						label : "<i class='fa fa-times'/>&nbsp取&nbsp消",
						className : "btn btn-default",
						callback : function() {
						}
					}
				}
			});							
		}
		
	};		

}();