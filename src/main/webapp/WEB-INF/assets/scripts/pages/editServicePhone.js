var EditServicePhone = function() {
	var handleServicePhone = function(ctxUrl) {
		$('.editServicePhone-form textarea').keypress(function(e) {
					if (e.which == 13) {
						if ($('.editServicePhone-form').validate().form()) {
							$('.editServicePhone-form').submit();
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
			handleServicePhone(ctxUrl);
		}

	};

}();