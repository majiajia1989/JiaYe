var EditUser = function() {
	var resolveRootUrl = "";
	var handleUser = function() {
		$('.corp-form input textarea').keypress(function(e) {
			if (e.which == 13) {
				if ($('.corp-form').validate().form()) {
					$('.corp-form').submit();
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
			resolveRootUrl = ctxUrl;
			handleUser();
			
			//selectProvinceInit();
			//selectCityInit();
			//selectCoutyInit();
		}

	};
}();
