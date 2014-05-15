var EditCorp = function() {
	var resolveRootUrl = "";
	var handleCorp = function() {
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
			handleCorp();
			$('#type').select2("val",$('#corpType').val());
			new Region('province','city','county',$('#corpProvince').val(),$('#corpCity').val(),$('#corpCounty').val());

		}

	};
}();
