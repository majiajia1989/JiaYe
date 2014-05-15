var EditRole = function() {
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

	var initMenuTree = function(json) {
		$('#resourceTree')
				.jstree(
						{
							'plugins' : [ "wholerow", "checkbox" ],
							'core' : {
								'data' : json
							}
						});

	};

	return {
		// main function to initiate the module
		init : function(ctxUrl) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			resolveRootUrl = ctxUrl;
			handleCorp();

			$.get(resolveRootUrl + 'role/readResourceTreeByRole', "editRoleId="
					+ $("#role_id").val(), function(json) {
				if (json != null) {
					initMenuTree(json);
				} else {

				}
			}, "json");
		},
		submitForm : function() {
			var selectIds = "";
			$("#resourceTree .jstree-clicked").each(function() {
				var $this = $(this);
				selectIds = selectIds + $this.parent().attr("id") + ",";
			});
			$("#roleResources").val(selectIds);
		}

	};
}();
