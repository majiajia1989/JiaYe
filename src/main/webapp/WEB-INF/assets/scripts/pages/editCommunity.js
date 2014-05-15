var EditCommunity = function() {
	var resolveRootUrl = "";
	var handleCommunity = function() {
		$('.community-form input textarea').keypress(function(e) {
			if (e.which == 13) {
				if ($('.community-form').validate().form()) {
					$('.community-form').submit();
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
			handleCommunity();
			
			new Region('province','city','county',$('#communityProvince').val(),$('#communityCity').val(),$('#communityCounty').val());

		}

	};
}();
