var Weixin = function(){
	var handleWeixin = function() {
		$('.weixin-form input').keypress(function(e) {
					if (e.which == 13) {
						if ($('.weixin-form').validate().form()) {
							$('.weixin-form').submit();
						}
						return false;
					}
				});
	}
	return {
        //main function to initiate the module
        init: function () {

        	handleWeixin();

        }

    };
}();
