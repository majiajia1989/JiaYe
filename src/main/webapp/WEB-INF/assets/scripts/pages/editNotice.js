var EditNotice = function() {
	var handleEdit = function(ctxUrl) {
		initCover($('input[name="pictureUrl"]').val());

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
								imageUrl : $('input[name="pictureUrl"]').val(),
								clickFn : function(url, title, width, height,
										border, align) {
									initCover(url);
									upload.hideDialog();
								}
							});
						});
					});
		});

	}

	var initCover = function(coverUrl) {
		coverUrl = coverUrl || '';
		$('input[name="pictureUrl"]').attr("value", coverUrl);
		if (coverUrl.length > 0) {
			$('#cover-thumb-a').attr("class", "");
			$('#cover-thumb-img').attr('src', coverUrl);
		}
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			handleEdit(ctxUrl);
		}
	};
}();