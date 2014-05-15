var AddCommunityImage = function() {

	var handleEdit = function(ctxUrl) {
		var uploadUrl = ctxUrl + 'kindeditor/uploadCommunity';
		var filesUrl = ctxUrl + 'kindeditor/filesCommunity';
		App.kindeditor('textarea[name="content"]', uploadUrl, filesUrl);

		KindEditor.ready(function(K) {
			var upload = KindEditor.editor({
				themeType : "simple",
				uploadJson : uploadUrl,
				fileManagerJson : filesUrl,
				allowFileManager : true
			});
			$('#cover-thumb-a').click(
					function() {
						upload.loadPlugin('smimage', function() {
							upload.plugin.imageDialog({
								imageUrl : $('input[name="imageUrl"]').val(),
								clickFn : function(url, title, width, height,
										border, align) {
									initUrl(url);
									upload.hideDialog();
								}
							});
						});
					});
		});

	}

	var initTable = function(ctxUrl) {
		if (!jQuery().dataTable) {
			return;
		}
		var url = ctxUrl + 'message/queryAllArticles';
		$('#article_Table')
				.dataTable(
						{
							"bServerSide" : true,
							"sServerMethod" : "GET",
							'bPaginate' : true,
							"bProcessing" : true,
							'bFilter' : false,
							"sAjaxSource" : url,
							"iDisplayLength" : 10,
							"sPaginationType" : "bootstrap",
							"aoColumns" : [
									{
										"sTitle" : "标题",
										"sClass" : "center",
										"mDataProp" : "title",
										"bSortable" : false
									},
									{
										"sTitle" : "操作",
										"mDataProp" : "msgID",
										"sDefaultContent" : "",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												var selectUrl = "AddCommunityImage.selectID("+ full.msgID + ", '"+ full.title +"');";
												var removeA = '<a href="#" class="btn iframe-btn btn-xs" onclick="'
														+ selectUrl + '">选中</a>';
												return removeA;
											}
										}
									}, ]

						});
	}

	var initUrl = function(url) {
		url = url || '';
		$('input[name="imageUrl"]').attr("value", url);
		if (url.length > 0) {
			$('#cover-thumb-a').attr("class", "");
			$('#cover-thumb-img').attr('src', url);
			$("#cover-thumb-img").show();
		} else {
			$("#cover-thumb-img").hide();
		}
	}

	return {
		init : function(ctxUrl) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			$('#articleTitle').hide();
			handleEdit(ctxUrl);
			initUrl($('input[name="imageUrl"]').val());
		},
		response : function(ctxUrl) {
			$('#article_Table').dataTable().fnDestroy();
			initTable(ctxUrl);
			$('#communityimage-modal').modal('show');
		},
		selectID : function(id,title) {
			$('#url').attr("value", id);
			$('#articleTitle').show();
			$('#articleName').attr("value",title)
			$('#communityimage-modal').modal('hide');
		}

	};
}();
