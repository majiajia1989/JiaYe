var Menu = function() {
	var resolveRootUrl = "";
	var menuFormContainer = $("#editForm");
	var resolveRootUrl = "";
	var comboboxStatusContainer = $('#comboboxStatus');
	var dgMenuUrl = "";
	var submitAction = "";
	var menuTypeInitUrl = "";

	var isChangeMenuData = false;
	var parentID = -1;
	var data = null;
	var currentOperate = "";
	var cancleFlag = false;
	var showAlert = function(alertContent) {
		$('#alertContent').html(alertContent);
		$('#alertMessage').show();
	};
	var closeAlert = function(alertContent) {
		// $('#alertMessage').alert('close');
		$('#alertMessage').hide();
	};
	menuTypeInit = function() {
		$('#menuType').change(function() {
					var selectVal = $(this).children('option:selected').val();
					if (selectVal == "click") {
						$("#parameter").rules("remove", "url");
						$("#paraText").html("参数：");
						$("#parameter").attr("placeholder", "参数值");
					} else {
						$("#paraText").html("URL：");
						$("#parameter").attr("placeholder", "URL");

						$("#parameter").rules("remove", "url");
						$("#parameter").rules("add", {
									url : true
								});
					}
				});
	};

	var initMenuData = function() {
		$.get(resolveRootUrl + '/menu/readMenuByUser', "cancleFlag="
						+ cancleFlag, function(json) {
					data = json;
					changeMenu(data);
				});
		cancleFlag = false;
	};

	var changeMenu = function() {
		for (var i = 1; i <= 3; i++) {
			// $("#divSubMenu" + i).hide();
			if (data == null || data == "undefined") {
				$("#index" + i).val("");
				$("#btnMenu" + i).text("");
				$("#tdMenu" + i).addClass("menu-item3");
			} else {
				if (data[i - 1] == null || data[i - 1] == "undefined") {
					$("#index" + i).val("");
					$("#btnMenu" + i).text("");
					$("#tdMenu" + i).addClass("menu-item3");
				} else {
					$("#index" + i).val(data[i - 1].id);
					$("#btnMenu" + i).text(data[i - 1].name);
					$("#tdMenu" + i).removeClass("menu-item3");
					if (data[i - 1].children.length >= 5) {
						$("#divSubMenuItemAdd" + i).hide();
					} else {
						$("#divSubMenuItemAdd" + i).show();
					}
				}
			}
			for (var j = 1; j <= 5; j++) {
				var menuIndex = i * 10 + j;
				$("#divSubMenuItem" + menuIndex).hide();
				if (data == null || data == "undefined") {
					$("#index" + menuIndex).val("");
					$("#btnMenu" + menuIndex).text("");
				} else {
					if (data[i - 1] == null || data[i - 1] == "undefined") {
						$("#index" + menuIndex).val("");
						$("#btnMenu" + menuIndex).text("");
					} else {
						if (data[i - 1].children[j - 1] == null
								|| data[i - 1].children[j - 1] == "undefined") {
							$("#index" + menuIndex).val("");
							$("#btnMenu" + menuIndex).text("");
						} else {
							$("#divSubMenuItem" + menuIndex).show();
							$("#index" + menuIndex).val(data[i - 1].children[j
									- 1].id);
							$("#btnMenu" + menuIndex)
									.text(data[i - 1].children[j - 1].name);
						}
					}
				}
			}
		}
		reset();
	};

	var reset = function() {
		var ss = $("#currentOperate").val();
		/*$("id").val(-1);
		$("#name").val("");
		$("#parameter").val("");*/

		$("#currentOperate").val("");
		var sss = $("#currentOperate").val();
		$("#btnAdd").hide();
		$("#btnUpdate").hide();
		$("#btnRemove").hide();
		$("#btnReset").hide();
		$("#btnCreate").hide();

		isChangeMenuData = $("#changeFlag").val();
		if (isChangeMenuData == "true") {
			$("#btnReset").show();
			$("#btnCreate").show();
		} else {
			$("#btnReset").hide();
			$("#btnCreate").hide();
		}

		for (var i = 1; i <= 3; i++) {
			if (data != null && data.length >= i
					&& data[i - 1].id == $("#parent_id").val()) {
				$("#divSubMenu" + i).show();
			} else {
				$("#divSubMenu" + i).hide();
			}
		}
		menuFormContainer.validate().resetForm();
	};

	return {
		init : function(url) {
			closeAlert();
			if (typeof(url) == 'undefined') {
				url = '';
			}
			resolveRootUrl = url;

			comboboxStatusContainer = $('#comboboxStatus');
			dgMenuUrl = resolveRootUrl + '/menu/readMenuByParentID';
			submitAction = resolveRootUrl + '/menu/save';
			menuTypeInitUrl = resolveRootUrl + '/menu/readAllMenuType';

			isChangeMenuData = false;
			parentID = -1;
			menuTypeInit();
			data = null;
			$("#btnCreate").hide();
			$("#btnReset").hide();
			initMenuData();

		},
		addMenu : function(parentBtnIndex, isChildren) {
			menuFormContainer.validate().resetForm();
			if (isChildren) {
				for (var i = 1; i <= 3; i++) {
					if (parentBtnIndex == i) {
						$('#parent_id').val(data[i - 1].id);
					}
				}
			} else {
				$("#parent_id").val(1);
			}

			if (isChildren && $('#parent_id').val() <= 1) {
				return;
			}
			$("#btnAdd").show();
			$("#btnUpdate").hide();
			$("#btnRemove").hide();
			$("#name").rules("remove", "maxByteLength");
			if (isChildren) {
				$("#name").rules("add", {
							maxByteLength : 14
						});

				$("#id").val(-1);
				$("#name").val("");
				$("#parameter").val("");
				$("#menuType").val("click");
			} else {
				$("#name").rules("add", {
							maxByteLength : 8
						});

				$("#id").val(-1);
				$("#parent_id").val("1");
				$("#name").val("");
				$("#parameter").val("");
				$("#menuType").val("click");
			}
		},

		editMenu : function(btnIndex, isChildren) {
			menuFormContainer.validate().resetForm();
			var editMenu = null;
			var index = $("#index" + btnIndex).val();
			for (var i = 1; i <= 3; i++) {
				if (i != btnIndex && !isChildren) {
					$("#divSubMenu" + i).hide();
				}
			}
			for (var i = 0; i < data.length; i++) {

				if (data[i].id == index) {
					editMenu = data[i];
				}
				if (data[i].children == null) {
					break;
				}
				for (var j = 0; j < data[i].children.length; j++) {
					if (data[i].children[j].id == index) {
						editMenu = data[i].children[j];
						break;
					}
				}
				if (editMenu != null) {
					break;
				}
			}

			$("#name").rules("remove", "maxByteLength");
			if (!isChildren) {
				$("#name").rules("add", {
							maxByteLength : 8
						});

			} else {
				$("#name").rules("add", {
							maxByteLength : 14
						});
			}

			if (editMenu == null) {
				if (!isChildren) {
					Menu.addMenu(1, false);
					return;
				} else {
					return;
				}
			} else {
				$("#btnAdd").hide();
				$("#btnUpdate").show();
				$("#btnRemove").show();

				$("#id").val(editMenu.id);

				if (isChildren) {
					$("#divSubMenu" + btnIndex).show();
					$("#parent_id").val(editMenu.parent_id);
				} else {
					$("#divSubMenu" + btnIndex).slideToggle();
					$("#parent_id").val(1);
				}
				$("#name").val(editMenu.name);
				$("#menuType").val(editMenu.menuType);// .combobox("setValue",
				// editMenu.menuType);
				$("#parameter").val(editMenu.parameter);
			}
		},

		addMenuItem : function() {
			$("#currentOperate").val("add");
			$("#menuToken").val("");
			menuFormContainer.attr("action", resolveRootUrl + 'menu/menu');
			// menuFormContainer.submit();
			if (menuFormContainer.validate().form()) {
				menuFormContainer.submit();
			}
		},
		editMenuItem : function() {
			$("#currentOperate").val("edit");
			menuFormContainer.attr("action", resolveRootUrl + 'menu/menu');
			if (menuFormContainer.validate().form()) {
				menuFormContainer.submit();
			}
		},
		remove : function() {
			$("#currentOperate").val("remove");
			menuFormContainer
					.attr("action", resolveRootUrl + 'menu/menu');
			menuFormContainer.submit();
		},
		save : function() {
			$.post(resolveRootUrl + '/menu/save', "menus="
							+ JSON.stringify(data), function(json) {
						if (json.flag) {
							initMenuData();
							showAlert(json.message);
						} else {
							showAlert(json.message);
						}
					});
		},
		cancle : function() {
			cancleFlag = true;
			$("#changeFlag").val("false")
			Menu.init(resolveRootUrl);
		}
	};
}();