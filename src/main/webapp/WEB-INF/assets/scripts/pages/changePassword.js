var ChangePassword = function() {
	var handleChangePassword = function(ctxUrl) {
		$('.changePassword-form input').keypress(function(e) {
					if (e.which == 13) {
						if ($('.changePassword-form').validate().form()) {
							$('.changePassword-form').submit();
						}
						return false;
					}
				});
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			if (typeof(ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			handleChangePassword(ctxUrl);
		}

	};

}();