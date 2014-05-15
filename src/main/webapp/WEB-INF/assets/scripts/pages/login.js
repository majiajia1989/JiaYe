if (window.top != window) {
	window.top.document.location.href = window.location.href;
}
var Login = function() {
	var handleLogin = function(ctxUrl) {
		var captchaUrl = ctxUrl + '/auth/captcha';
		var captchaDiv = $('<div class="form-group"></div>');
		var captchaImg = $('<img border="0" title="验证码看不清楚?请点击刷新验证码"/>');
		var refreshAnchor = $('<a href="#">看不清，换一张</a>');
		var captchaInput = $('.login-form input[name=captcha]')
		captchaDiv.data('show', false);
		captchaDiv.css({
					display : "none"
				});
		captchaDiv.insertAfter(captchaInput.parent().parent());
		captchaImg.appendTo(captchaDiv);
		captchaImg.css("cursor", "pointer");
		captchaImg.attr('src', captchaUrl + '?d=' + new Date().getTime());
		captchaImg.bind('click', function() {
					captchaInput.focus();
					if (captchaDiv.data('event')) {
						clearTimeout(captchaDiv.data('event'));
					}
					this.src = captchaUrl + '?d=' + new Date().getTime();
				});

		refreshAnchor.bind('click', function() {
					captchaInput.focus();
					if (captchaDiv.data('event')) {
						clearTimeout(captchaDiv.data('event'));
					}
					captchaImg.attr('src', captchaUrl + '?d='
									+ new Date().getTime());
				});
		refreshAnchor.appendTo(captchaDiv);

		captchaInput.bind('focus', function() {
					captchaDiv.show();
					captchaDiv.data('show', true);
				}).bind('blur', function() {
					if (captchaDiv.data('show')) {
						captchaDiv.data('event', setTimeout(function() {
											captchaDiv.hide();
											captchaDiv.data('show', false);
										}, 200));
					}
				});

		$('.login-form input').keypress(function(e) {
					if (e.which == 13) {
						if ($('.login-form').validate().form()) {
							$('.login-form').submit();
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
			handleLogin(ctxUrl);
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