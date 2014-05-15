var Forget = function() {
	var handleForget = function(ctxUrl) {
		var captchaUrl = ctxUrl + '/auth/captcha';
		var loginUrl = ctxUrl + '/auth/login';
		$('#forget-back-btn').bind('click',function(){
			window.location.href = loginUrl;
		});
				
		$('.registe-form input').keypress(function(e) {
					if (e.which == 13) {
						if ($('.registe-form').validate().form()) {
							$('.registe-form').submit();
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
			handleForget(ctxUrl);
			$.backstretch([ctxUrl + "assets/img/bg/1.jpg",
							ctxUrl + "assets/img/bg/2.jpg",
							ctxUrl + "assets/img/bg/3.jpg",
							ctxUrl + "assets/img/bg/4.jpg"], {
						fade : 1000,
						duration : 8000
					});
		}

	};

}();