if (window.top != window) {
	window.top.document.location.href = window.location.href;
}
var Lock = function() {

	return {
		// main function to initiate the module
		init : function(url) {
			if (typeof(url) == 'undefined') {
				url = '';
			}
			$.backstretch([url + "assets/img/bg/1.jpg",
							url + "assets/img/bg/2.jpg",
							url + "assets/img/bg/3.jpg",
							url + "assets/img/bg/4.jpg"], {
						fade : 1000,
						duration : 8000
					});
		}
	};
}();