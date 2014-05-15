var EditArticle = function() {
	var changeLinkType = function() {
		var selected = $('input[name="linkType"]:checked').val();
		$('textarea[name="content"]').removeClass("ignore-validate");
		$('input[name="url"]').removeClass("ignore-validate");
		if (selected == 0) {
			$('#linkType-0').css('display', '');
			$('#linkType-1').css('display', 'none');
			//$('input[name="url"]').val('');
			$('input[name="url"]').addClass("ignore-validate");
		} else {
			$('#linkType-0').css('display', 'none');
			$('#linkType-1').css('display', '');
			// $('textarea[name="content"]').val('');
			//KindEditor.instances[0].html("");
			$('textarea[name="content"]').addClass("ignore-validate");
		}
	}

	var handleEdit = function(ctxUrl) {
		initCover($('input[name="pictureUrl"]').val());
		changeLinkType();

		$('input[name="linkType"]').change(function() {
			changeLinkType();
		});

		$('#show-child-btn').bind("click", function() {
			showChildrenDialog(ctxUrl);
		});

		var uploadUrl = ctxUrl + 'kindeditor/uploadCommunity';
		var filesUrl = ctxUrl + 'kindeditor/filesCommunity';
		App.kindeditor('textarea[name="content"]', uploadUrl, filesUrl);

		KindEditor.ready(function(K) {
			var upload = KindEditor.editor({
				themeType : "simple",
				uploadJson : uploadUrl,
				fileManagerJson : filesUrl,
				allowFileManager : true
			});
			$('#cover-thumb-a').click(
					function() {
						upload.loadPlugin('smimage', function() {
							upload.plugin.imageDialog({
								imageUrl : $('input[name="pictureUrl"]').val(),
								clickFn : function(url, title, width, height,
										border, align) {
									initCover(url);
									upload.hideDialog();
								}
							});
						});
					});
		});

	}

	var initCover = function(coverUrl) {
		coverUrl = coverUrl || '';
		$('input[name="pictureUrl"]').attr("value", coverUrl);
		if (coverUrl.length > 0) {
			$('#cover-thumb-a').attr("class", "");
			$('#cover-thumb-img').attr('src', coverUrl);
		}
	}

	var childDataTable = null;
	var initChildDataTable = function(ctxUrl) {
		if (childDataTable) {
			childDataTable.fnDestroy();
		}
		var dataUrl = ctxUrl + 'message/queryAllArticles';
		// begin first table
		childDataTable = $('#child-data-table')
				.dataTable(
						{
							"bServerSide" : true,
							"sServerMethod" : "GET",
							'bPaginate' : true,
							"bProcessing" : true,
							'bFilter' : false,
							"sAjaxSource" : dataUrl,
							"aoColumns" : [
									{
										"sTitle" : '<input type="checkbox" class="group-checkable" data-set="#child-data-table .checkboxes"/>',
										"sDefaultContent" : '',
										"mDataProp" : "msgID",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											if (type == "display") {
												return '<input type="checkbox" class="checkboxes" value="'
														+ data + '"/>';
											}
										}
									}, {
										"sTitle" : "回答",
										"sDefaultContent" : "",
										"mDataProp" : "title",
										"bSortable" : false
									} ],
							// set the initial value
							"iDisplayLength" : 10,
							"sPaginationType" : "bootstrap"
						});
		jQuery('#child-data-table .group-checkable').change(function() {
			var set = jQuery(this).attr("data-set");
			var checked = jQuery(this).is(":checked");
			jQuery(set).each(function() {
				if (checked) {
					$(this).attr("checked", true);
				} else {
					$(this).attr("checked", false);
				}
				$(this).parents('tr').toggleClass("active");
			});
			jQuery.uniform.update(set);
		});

		jQuery('#child-data-table tbody tr .checkboxes').change(function() {
			$(this).parents('tr').toggleClass("active");
		});

		$('#add-child-btn').unbind("click").bind("click", function() {
			addChildrenToDocument();
		});

	}

	var addChildrenToDocument = function() {
		var inputSet = $('#child-input-table').attr("data-set");

		var existArray = new Array();
		jQuery(inputSet).each(function(index, element) {
			var input = $(element).find("input[type='hidden']")[0];
			existArray.push($(input).val());
		});

		var newArray = new Array();
		var set = jQuery('#child-data-table .group-checkable').attr("data-set");
		jQuery(set).each(function(index, element) {
			var checked = jQuery(this).is(":checked");
			var msgID = ""+childDataTable.fnGetData(index, 0);
			if (checked && $.inArray(msgID, existArray) == -1) {
				newArray.push({
					msgID : msgID,
					title : childDataTable.fnGetData(index, 1)
				});
			}
		});

		var count = jQuery(inputSet).length;
		$.each(newArray, function(index, element) {
			$('#child-input-table').append(
					newChildTrHtml(element.msgID, element.title, count));
			count++;
		});

		$('#child-data-modal').modal('hide');
	}

	var newChildTrHtml = function(msgID, title, index) {
		return "<tr class='child-input-tr col-md-10'><td class='table-bordered' style='width:300px;overflow:hidden;'><input type='hidden' name='childIDs["
				+ index
				+ "]' value='"
				+ msgID
				+ "'/>"
				+ title
				+ "</td><td class='child-input-tr table-bordered'><button class='btn iframe-btn btn-delete btn-xs' onclick='EditArticle.removeChild(this)'>删除</button></td></tr>";
	}
	
	var initChildInputTable = function(childIDs, childTitles) {
		$('#child-input-table').empty();
		if (childIDs && childTitles) {
			for (var i = 0; i < childIDs.length; i++) {
				$('#child-input-table').append(
						newChildTrHtml(childIDs[i], childTitles[i], i));
			}
		}
	}

	var showChildrenDialog = function(ctxUrl) {
		$('#child-data-modal').modal('show');
		initChildDataTable(ctxUrl);
	}

	return {
		// main function to initiate the module
		init : function(ctxUrl,childIDs, childTitles) {
			if (typeof (ctxUrl) == 'undefined') {
				ctxUrl = '';
			}
			handleEdit(ctxUrl);
			initChildInputTable(childIDs, childTitles);
		},
		removeChild : function(domEle) {
			var tr = $(domEle).parents("tr")[0];
			tr.remove();

			// 重新排序
			var inputSet = $('#child-input-table').attr("data-set");
			jQuery(inputSet).each(function(index, element) {
				var input = $(element).find("input[type='hidden']")[0];
				$(input).attr("name", "childIDs[" + index + "]");
			});
		}
	};
}();