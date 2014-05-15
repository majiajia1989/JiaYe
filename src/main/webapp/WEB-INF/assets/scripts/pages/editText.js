var EditText = function() {
	var handleeditText = function() {
		$('.editText-form textarea').keypress(function(e) {
					if (e.which == 13) {
						if ($('.editText-form').validate().form()) {
							$('.editText-form').submit();
						}
						return false;
					}
				});
		var editor;
		KindEditor.ready(function(K) {
					editor = K.create('textarea[name="content"]', {
								resizeType : 1,
								width:'100%',
								allowPreviewEmoticons : true,
								items : ['emoticons'],
								afterCreate : function() {
									var self = this;
									K.ctrl(document, 13, function() {
												self.sync();
											});
									K.ctrl(self.edit.doc, 13, function() {
												self.sync();
											});
								},
								afterBlur : function() {
									// 编辑器失去焦点时直接同步，可以取到值
									this.sync();
								}
							});

				});
	}

	return {
		// main function to initiate the module
		init : function() {
			handleeditText();
		}
	};
}();