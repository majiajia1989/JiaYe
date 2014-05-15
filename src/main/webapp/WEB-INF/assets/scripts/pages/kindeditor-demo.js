var Demo = function() {
	return {
		init : function() {
			App.kindeditor('textarea[name="content"]','upload','files');
		}
	}
}();
KindEditor.ready(function(K) {
			var upload = KindEditor.editor({
						themeType : "simple",
						uploadJson : 'upload',
						fileManagerJson : 'files',
						allowFileManager : true
					});
			$('#insertimage').click(function() {
				upload.loadPlugin('smimage', function() {
							upload.plugin.imageDialog({
										imageUrl : K('#thumb').val(),
										clickFn : function(url, title, width,
												height, border, align) {
											console.debug(url);
											K('#thumb').val(url);
											if (K('#thumb_img')) { //
												K('#thumb').hide();
												K('#thumb_img')
														.attr('src', url);
												K('#thumb_img').show();
											}
											upload.hideDialog();
										}
									});
						});
			});
		});
