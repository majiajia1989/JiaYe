var AddImages = function() {

	var handleEdit = function(ctxUrl) {
		var uploadUrl = ctxUrl + 'kindeditor/upload';
		var filesUrl = ctxUrl + 'kindeditor/files';
		App.kindeditor('textarea[name="content"]', uploadUrl, filesUrl);

		KindEditor.ready(function(K) {
			var upload = KindEditor.editor({
				themeType : "simple",
				uploadJson : uploadUrl,
				fileManagerJson : filesUrl,
				allowFileManager : true
			});
			$('#insert-img-a').click(
					function() {
						upload.loadPlugin('smimage', function() {
							upload.plugin.imageDialog({
								imageUrl : K('#thumb').val(),
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

	var initUrl = function(url) {
		url = url || '';
		$('#thumb').attr("value", url);
		if (url.length > 0) {
			$('#insert-img-a').attr("class", "");
			$('#thumb-img').attr("src", url);
			$("#thumb-img").show();
		} else {
			$("#thumb-img").hide();
		}
	}

	return {
		init : function(ctxUrl) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			handleEdit(ctxUrl);
			initUrl($('#thumb').val());
		}
	};
}();
