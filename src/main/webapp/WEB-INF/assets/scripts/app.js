/**
 * Core script to handle the entire theme and core functions
 */

Date.prototype.format = function(format) // author: meizz
{
	var o = {

		'M+' : this.getMonth() + 1, // month
		'd+' : this.getDate(), // day
		'h+' : this.getHours(), // hour
		'm+' : this.getMinutes(), // minute
		's+' : this.getSeconds(), // second
		'q+' : Math.floor((this.getMonth() + 3) / 3), // quarter
		'S' : this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp('(' + k + ')').test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ('00' + o[k]).substr(('' + o[k]).length));
	return format;
};

var App = function() {

	// IE mode
	var isRTL = false;
	var isIE8 = false;
	var isIE9 = false;
	var isIE10 = false;

	var sidebarWidth = 225;
	var sidebarCollapsedWidth = 35;

	var responsiveHandlers = [];

	// theme layout color set
	var layoutColorCodes = {
		'blue' : '#4b8df8',
		'red' : '#e02222',
		'green' : '#35aa47',
		'purple' : '#852b99',
		'grey' : '#555555',
		'light-grey' : '#fafafa',
		'yellow' : '#ffb848'
	};

	// To get the correct viewport width based on
	// http://andylangton.co.uk/articles/javascript/get-viewport-size-javascript/
	var _getViewPort = function() {
		var e = window, a = 'inner';
		if (!('innerWidth' in window)) {
			a = 'client';
			e = document.documentElement || document.body;
		}
		return {
			width : e[a + 'Width'],
			height : e[a + 'Height']
		}
	}

	// initializes main settings
	var handleInit = function() {

		if ($('body').css('direction') === 'rtl') {
			isRTL = true;
		}

		isIE8 = !!navigator.userAgent.match(/MSIE 8.0/);
		isIE9 = !!navigator.userAgent.match(/MSIE 9.0/);
		isIE10 = !!navigator.userAgent.match(/MSIE 10.0/);

		if (isIE10) {
			$('html').addClass('ie10'); // detect IE10 version
		}

		if (isIE10 || isIE9 || isIE8) {
			$('html').addClass('ie'); // detect IE10 version
		}

		/*
		 * Virtual keyboards: Also, note that if you're using inputs in your
		 * modal – iOS has a rendering bug which doesn't update the position of
		 * fixed elements when the virtual keyboard is triggered
		 */
		var deviceAgent = navigator.userAgent.toLowerCase();
		if (deviceAgent.match(/(iphone|ipod|ipad)/)) {
			$(document).on('focus', 'input, textarea', function() {
				$('.header').hide();
				$('.footer').hide();
			});
			$(document).on('blur', 'input, textarea', function() {
				$('.header').show();
				$('.footer').show();
			});
		}
	}

	var handleSidebarState = function() {
		// remove sidebar toggler if window width smaller than 992(for tablet
		// and phone mode)
		var viewport = _getViewPort();
		if (viewport.width < 992) {
			$('body').removeClass("page-sidebar-closed");
		}
	}

	// runs callback functions set by App.addResponsiveHandler().
	var runResponsiveHandlers = function() {
		// reinitialize other subscribed elements
		for ( var i in responsiveHandlers) {
			var each = responsiveHandlers[i];
			each.call();
		}
	}

	// reinitialize the laypot on window resize
	var handleResponsive = function() {
		handleSidebarState();
		handleSidebarAndContentHeight();
		handleFixedSidebar();
		runResponsiveHandlers();
	}

	// initialize the layout on page load
	var handleResponsiveOnInit = function() {
		handleSidebarState();
		handleSidebarAndContentHeight();
	}

	// handle the layout reinitialization on window resize
	var handleResponsiveOnResize = function() {
		var resize;
		if (isIE8) {
			var currheight;
			$(window).resize(function() {
				if (currheight == document.documentElement.clientHeight) {
					return; // quite event since only body resized not
					// window.
				}
				if (resize) {
					clearTimeout(resize);
				}
				resize = setTimeout(function() {
					handleResponsive();
				}, 50); // wait 50ms until window resize
				// finishes.
				currheight = document.documentElement.clientHeight; // store
				// last
				// body
				// client
				// height
			});
		} else {
			$(window).resize(function() {
				if (resize) {
					clearTimeout(resize);
				}
				resize = setTimeout(function() {
					handleResponsive();
				}, 50); // wait 50ms until window resize
				// finishes.
			});
		}
	}

	// * BEGIN:CORE HANDLERS *//
	// this function handles responsive layout on screen size resize or mobile
	// device rotate.

	// Set proper height for sidebar and content. The content and sidebar height
	// must be synced always.
	var handleSidebarAndContentHeight = function() {
		var content = $('.page-content');
		var sidebar = $('.page-sidebar');
		var body = $('body');
		var height;
		var width = $(window).width() - sidebar.outerWidth();

		if (body.hasClass("page-footer-fixed") === true
				&& body.hasClass("page-sidebar-fixed") === false) {
			var height = $(window).height() - $('.footer').outerHeight();
			if (content.height() < available_height) {
				content
						.attr('style', 'min-height:' + height
								+ 'px !important;max-width:' + width
								+ 'px !important;');
			} else {
				content.attr('style', 'max-width:' + width + 'px !important;');
			}
		} else {
			if (body.hasClass('page-sidebar-fixed')) {
				height = _calculateFixedSidebarViewportHeight();
			} else {
				height = sidebar.height() + 20;
			}
			if (height >= content.height()) {
				content.attr('style', 'min-height:' + height
						+ 'px !important!important;max-width:' + width
						+ 'px !important;');
			} else {
				content.attr('style', 'max-width:' + width + 'px !important;');
			}
		}
	}

	// Handle sidebar menu
	var handleSidebarMenu = function() {
		$('.page-sidebar')
				.on(
						'click',
						'li > a',
						function(e) {
							if ($(this).next().hasClass('sub-menu') == false) {
								if ($('.btn-navbar').hasClass('collapsed') == false) {
									$('.btn-navbar').click();
								}
								return;
							}

							if ($(this).next().hasClass('sub-menu.always-open')) {
								return;
							}

							var parent = $(this).parent().parent();
							var the = $(this);

							parent.children('li.open').children('a').children(
									'.arrow').removeClass('open');
							parent.children('li.open').children('.sub-menu')
									.slideUp(200);
							parent.children('li.open').removeClass('open');

							var sub = $(this).next();
							var slideOffeset = -200;
							var slideSpeed = 200;

							if (sub.is(":visible")) {
								$('.arrow', $(this)).removeClass("open");
								$(this).parent().removeClass("open");
								sub
										.slideUp(
												slideSpeed,
												function() {
													if ($('body')
															.hasClass(
																	'page-sidebar-fixed') == false
															&& $('body')
																	.hasClass(
																			'page-sidebar-closed') == false) {
														App.scrollTo(the,
																slideOffeset);
													}
													handleSidebarAndContentHeight();
												});
							} else {
								$('.arrow', $(this)).addClass("open");
								$(this).parent().addClass("open");
								sub
										.slideDown(
												slideSpeed,
												function() {
													if ($('body')
															.hasClass(
																	'page-sidebar-fixed') == false
															&& $('body')
																	.hasClass(
																			'page-sidebar-closed') == false) {
														App.scrollTo(the,
																slideOffeset);
													}
													handleSidebarAndContentHeight();
												});
							}
							activeMenuBar = $('.page-sidebar-menu > li.active');
							$('.page-sidebar-menu > li > ul.sub-menu')
									.on(
											'click',
											'li > a',
											function(e) {
												var menuBar = $(this)
														.parents(
																'.page-sidebar-menu > li');
												if (activeMenuBar != undefined) {
													activeMenuBar
															.removeClass('active');
													activeMenuBar = menuBar;
												}
												if (menuBar.hasClass('active') == false) {
													menuBar.addClass('active')
												}
												if (menuBar.children('a')
														.children('span:eq(2)')
														.hasClass('selected') == false) {
													menuBar
															.children('a')
															.children(
																	'span:eq(2)')
															.addClass(
																	'selected')
												}
											});

							e.preventDefault();
						});

		// handle ajax links
		$('.page-sidebar')
				.on(
						'click',
						' li > a.ajaxify',
						function(e) {
							e.preventDefault();
							App.scrollTop();

							var url = $(this).attr("href");
							var menuContainer = $('.page-sidebar ul');
							var pageContent = $('.page-content');
							var pageContentBody = $('.page-content .page-content-body');

							menuContainer.children('li.active').removeClass(
									'active');
							menuContainer.children('arrow.open').removeClass(
									'open');

							$(this).parents('li').each(
									function() {
										$(this).addClass('active');
										$(this).children('a > span.arrow')
												.addClass('open');
									});
							$(this).parents('li').addClass('active');

							App.blockUI(pageContent, false);

							$
									.ajax({
										type : "GET",
										cache : false,
										url : url,
										dataType : "html",
										success : function(res) {
											App.unblockUI(pageContent);
											pageContentBody.html(res);
											App.fixContentHeight(); // fix
																	// content
																	// height
											App.initAjax(); // initialize core
															// stuff
										},
										error : function(xhr, ajaxOptions,
												thrownError) {
											pageContentBody
													.html('<h4>Could not load the requested content.</h4>');
											App.unblockUI(pageContent);
										},
										async : false
									});
						});
	}

	// Helper function to calculate sidebar height for fixed sidebar layout.
	var _calculateFixedSidebarViewportHeight = function() {
		var sidebarHeight = $(window).height() - $('.header').height() + 1;
		if ($('body').hasClass("page-footer-fixed")) {
			sidebarHeight = sidebarHeight - $('.footer').outerHeight();
		}

		return sidebarHeight;
	}

	// Handles fixed sidebar
	var handleFixedSidebar = function() {
		var menu = $('.page-sidebar-menu');

		if (menu.parent('.slimScrollDiv').size() === 1) { // destroy existing
			// instance before
			// updating the
			// height
			menu.slimScroll({
				destroy : true
			});
			menu.removeAttr('style');
			$('.page-sidebar').removeAttr('style');
		}

		if ($('.page-sidebar-fixed').size() === 0) {
			handleSidebarAndContentHeight();
			return;
		}

		var viewport = _getViewPort();
		if (viewport.width >= 992) {
			var sidebarHeight = _calculateFixedSidebarViewportHeight();

			menu.slimScroll({
				size : '7px',
				color : '#a1b2bd',
				opacity : .3,
				position : isRTL ? 'left' : 'right',
				height : sidebarHeight,
				allowPageScroll : false,
				disableFadeOut : false
			});
			handleSidebarAndContentHeight();
		}
	}

	// Handles the sidebar menu hover effect for fixed sidebar.
	var handleFixedSidebarHoverable = function() {
		if ($('body').hasClass('page-sidebar-fixed') === false) {
			return;
		}

		$('.page-sidebar').off('mouseenter').on(
				'mouseenter',
				function() {
					var body = $('body');

					if ((body.hasClass('page-sidebar-closed') === false || body
							.hasClass('page-sidebar-fixed') === false)
							|| $(this).hasClass('page-sidebar-hovering')) {
						return;
					}

					body.removeClass('page-sidebar-closed').addClass(
							'page-sidebar-hover-on');
					$(this).addClass('page-sidebar-hovering');
					$(this).animate({
						width : sidebarWidth
					}, 400, '', function() {
						$(this).removeClass('page-sidebar-hovering');
					});
				});

		$('.page-sidebar')
				.off('mouseleave')
				.on(
						'mouseleave',
						function() {
							var body = $('body');

							if ((body.hasClass('page-sidebar-hover-on') === false || body
									.hasClass('page-sidebar-fixed') === false)
									|| $(this)
											.hasClass('page-sidebar-hovering')) {
								return;
							}

							$(this).addClass('page-sidebar-hovering');
							$(this)
									.animate(
											{
												width : sidebarCollapsedWidth
											},
											400,
											'',
											function() {
												$('body')
														.addClass(
																'page-sidebar-closed')
														.removeClass(
																'page-sidebar-hover-on');
												$(this)
														.removeClass(
																'page-sidebar-hovering');
											});
						});
	}

	// Handles sidebar toggler to close/hide the sidebar.
	var handleSidebarToggler = function() {
		var viewport = _getViewPort();
		if ($.cookie('sidebar_closed') === '1' && viewport.width >= 992) {
			$('body').addClass('page-sidebar-closed');
		}

		// handle sidebar show/hide
		$('.page-sidebar, .header').on(
				'click',
				'.sidebar-toggler',
				function(e) {
					var body = $('body');
					var sidebar = $('.page-sidebar');

					if ((body.hasClass("page-sidebar-hover-on") && body
							.hasClass('page-sidebar-fixed'))
							|| sidebar.hasClass('page-sidebar-hovering')) {
						body.removeClass('page-sidebar-hover-on');
						sidebar.css('width', '').hide().show();
						$.cookie('sidebar_closed', '0');
						e.stopPropagation();
						runResponsiveHandlers();
						return;
					}

					$(".sidebar-search", sidebar).removeClass("open");

					if (body.hasClass("page-sidebar-closed")) {
						body.removeClass("page-sidebar-closed");
						if (body.hasClass('page-sidebar-fixed')) {
							sidebar.css('width', '');
						}
						$.cookie('sidebar_closed', '0');
					} else {
						body.addClass("page-sidebar-closed");
						$.cookie('sidebar_closed', '1');
					}
					runResponsiveHandlers();
					handleSidebarAndContentHeight()
				});

		// handle the search bar close
		$('.page-sidebar').on('click', '.sidebar-search .remove', function(e) {
			e.preventDefault();
			$('.sidebar-search').removeClass("open");
		});

		// handle the search query submit on enter press
		$('.page-sidebar').on('keypress', '.sidebar-search input', function(e) {
			if (e.which == 13) {
				$('.sidebar-search').submit();
				return false; // <---- Add this line
			}
		});

		// handle the search submit
		$('.sidebar-search .submit').on('click', function(e) {
			e.preventDefault();
			if ($('body').hasClass("page-sidebar-closed")) {
				if ($('.sidebar-search').hasClass('open') == false) {
					if ($('.page-sidebar-fixed').size() === 1) {
						$('.page-sidebar .sidebar-toggler').click(); // trigger
						// sidebar
						// toggle
						// button
					}
					$('.sidebar-search').addClass("open");
				} else {
					$('.sidebar-search').submit();
				}
			} else {
				$('.sidebar-search').submit();
			}
		});
	}

	// Handles the horizontal menu
	var handleHorizontalMenu = function() {
		// handle hor menu search form toggler click
		$('.header').on('click', '.hor-menu .hor-menu-search-form-toggler',
				function(e) {
					if ($(this).hasClass('off')) {
						$(this).removeClass('off');
						$('.header .hor-menu .search-form').hide();
					} else {
						$(this).addClass('off');
						$('.header .hor-menu .search-form').show();
					}
					e.preventDefault();
				});

		// handle hor menu search button click
		$('.header').on('click', '.hor-menu .search-form .btn', function(e) {
			$('.form-search').submit();
			e.preventDefault();
		});

		// handle hor menu search form on enter press
		$('.header').on('keypress', '.hor-menu .search-form input',
				function(e) {
					if (e.which == 13) {
						$('.form-search').submit();
						return false;
					}
				});
	}

	// Handles the go to top button at the footer
	var handleGoTop = function() {
		/* set variables locally for increased performance */
		$('.footer').on('click', '.go-top', function(e) {
			App.scrollTo();
			e.preventDefault();
		});
	}

	// Handles portlet tools & actions
	var handlePortletTools = function() {
		$('body').on('click', '.portlet > .portlet-title > .tools > a.remove',
				function(e) {
					e.preventDefault();
					$(this).closest(".portlet").remove();
				});

		$('body').on(
				'click',
				'.portlet > .portlet-title > .tools > a.reload',
				function(e) {
					e.preventDefault();
					var el = $(this).closest(".portlet").children(
							".portlet-body");
					App.blockUI(el);
					window.setTimeout(function() {
						App.unblockUI(el);
					}, 1000);
				});

		$('body')
				.on(
						'click',
						'.portlet > .portlet-title > .tools > .collapse, .portlet .portlet-title > .tools > .expand',
						function(e) {
							e.preventDefault();
							var el = $(this).closest(".portlet").children(
									".portlet-body");
							if ($(this).hasClass("collapse")) {
								$(this).removeClass("collapse").addClass(
										"expand");
								el.slideUp(200);
							} else {
								$(this).removeClass("expand").addClass(
										"collapse");
								el.slideDown(200);
							}
						});
	}

	// Handles custom checkboxes & radios using jQuery Uniform plugin
	var handleUniform = function() {
		if (!$().uniform) {
			return;
		}
		var test = $("input[type=checkbox]:not(.toggle), input[type=radio]:not(.toggle, .star)");
		if (test.size() > 0) {
			test.each(function() {
				if ($(this).parents(".checker").size() == 0) {
					$(this).show();
					$(this).uniform();
				}
			});
		}
	}

	// Handles Bootstrap Accordions.
	var handleAccordions = function() {
		var lastClicked;
		// add scrollable class name if you need scrollable panes
		$('body').on('click', '.accordion.scrollable .accordion-toggle',
				function() {
					lastClicked = $(this);
				}); // move to faq section

		$('body').on('show.bs.collapse', '.accordion.scrollable', function() {
			$('html,body').animate({
				scrollTop : lastClicked.offset().top - 150
			}, 'slow');
		});
	}

	// Handles Bootstrap Tabs.
	var handleTabs = function() {
		// fix content height on tab click
		$('body').on('shown.bs.tab', '.nav.nav-tabs', function() {
			handleSidebarAndContentHeight();
		});

		// activate tab if tab id provided in the URL
		if (location.hash) {
			var tabid = location.hash.substr(1);
			$('a[href="#' + tabid + '"]').parents('.tab-pane:hidden').each(
					function() {
						var tabid = $(this).attr("id");
						$('a[href="#' + tabid + '"]').click();
					});
			$('a[href="#' + tabid + '"]').click();
		}
	}

	// Handles Bootstrap Modals.
	var handleModals = function() {
		// fix stackable modal issue: when 2 or more modals opened, closing one
		// of modal will remove .modal-open class.
		$('body').on(
				'hide.bs.modal',
				function() {
					if ($('.modal:visible').size() > 1
							&& $('html').hasClass('modal-open') == false) {
						$('html').addClass('modal-open');
					} else if ($('.modal:visible').size() <= 1) {
						$('html').removeClass('modal-open');
					}
				});

		$('body').on('show.bs.modal', '.modal', function() {
			if ($(this).hasClass("modal-scroll")) {
				$('body').addClass("modal-open-noscroll");
			}
		});

		$('body').on('hide.bs.modal', '.modal', function() {
			$('body').removeClass("modal-open-noscroll");
		});
	}

	// Handles Bootstrap Tooltips.
	var handleTooltips = function() {
		$('.tooltips').tooltip();
	}

	// Handles Bootstrap Dropdowns
	var handleDropdowns = function() {
		/*
		 * For touch supported devices disable the hoverable dropdowns -
		 * data-hover="dropdown"
		 */
		if (App.isTouchDevice()) {
			$('[data-hover="dropdown"]').each(function() {
				$(this).parent().off("hover");
				$(this).off("hover");
			});
		}
		/*
		 * Hold dropdown on click
		 */
		$('body').on('click', '.dropdown-menu.hold-on-click', function(e) {
			e.stopPropagation();
		});
	}

	// Handle Hower Dropdowns
	var handleDropdownHover = function() {
		$('[data-hover="dropdown"]').dropdownHover();
	}

	var handleAlerts = function() {
		$('body').on('click', '[data-close="alert"]', function(e) {
			$(this).parent('.alert').hide();
			e.preventDefault();
		});
	}

	// Handles Bootstrap Popovers

	// last popep popover
	var lastPopedPopover;

	var handlePopovers = function() {
		$('.popovers').popover();

		// close last poped popover

		$(document).on('click.bs.popover.data-api', function(e) {
			if (lastPopedPopover) {
				lastPopedPopover.popover('hide');
			}
		});
	}

	// Handles scrollable contents using jQuery SlimScroll plugin.
	var handleScrollers = function() {
		$('.scroller')
				.each(
						function() {
							var height;
							if ($(this).attr("data-height")) {
								height = $(this).attr("data-height");
							} else {
								height = $(this).css('height');
							}
							$(this)
									.slimScroll(
											{
												size : '7px',
												color : ($(this).attr(
														"data-handle-color") ? $(
														this).attr(
														"data-handle-color")
														: '#a1b2bd'),
												railColor : ($(this).attr(
														"data-rail-color") ? $(
														this).attr(
														"data-rail-color")
														: '#333'),
												position : isRTL ? 'left'
														: 'right',
												height : height,
												alwaysVisible : ($(this).attr(
														"data-always-visible") == "1" ? true
														: false),
												railVisible : ($(this).attr(
														"data-rail-visible") == "1" ? true
														: false),
												disableFadeOut : true
											});
						});
	}

	// Handles Image Preview using jQuery Fancybox plugin
	var handleFancybox = function() {
		if (!jQuery.fancybox) {
			return;
		}

		if ($(".fancybox-button").size() > 0) {
			$(".fancybox-button").fancybox({
				groupAttr : 'data-rel',
				prevEffect : 'none',
				nextEffect : 'none',
				closeBtn : true,
				helpers : {
					title : {
						type : 'inside'
					}
				}
			});
		}
	}

	// Fix input placeholder issue for IE8 and IE9
	var handleFixInputPlaceholderForIE = function() {
		// fix html5 placeholder attribute for ie7 & ie8
		if (isIE8 || isIE9) { // ie8 & ie9
			// this is html5 placeholder fix for inputs, inputs with
			// placeholder-no-fix class will be skipped(e.g: we need this for
			// password fields)
			$(
					'input[placeholder]:not(.placeholder-no-fix), textarea[placeholder]:not(.placeholder-no-fix)')
					.each(
							function() {

								var input = $(this);

								if (input.val() == ''
										&& input.attr("placeholder") != '') {
									input.addClass("placeholder").val(
											input.attr('placeholder'));
								}

								input.focus(function() {
									if (input.val() == input
											.attr('placeholder')) {
										input.val('');
									}
								});

								input.blur(function() {
									if (input.val() == ''
											|| input.val() == input
													.attr('placeholder')) {
										input.val(input.attr('placeholder'));
									}
								});
							});
		}
	}

	// Handle full screen mode toggle
	var handleFullScreenMode = function() {
		// mozfullscreenerror event handler

		// toggle full screen
		function toggleFullScreen() {
			if (!document.fullscreenElement && // alternative standard method
			!document.mozFullScreenElement && !document.webkitFullscreenElement) { // current
																					// working
				// methods
				if (document.documentElement.requestFullscreen) {
					document.documentElement.requestFullscreen();
				} else if (document.documentElement.mozRequestFullScreen) {
					document.documentElement.mozRequestFullScreen();
				} else if (document.documentElement.webkitRequestFullscreen) {
					document.documentElement
							.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
				}
			} else {
				if (document.cancelFullScreen) {
					document.cancelFullScreen();
				} else if (document.mozCancelFullScreen) {
					document.mozCancelFullScreen();
				} else if (document.webkitCancelFullScreen) {
					document.webkitCancelFullScreen();
				}
			}
		}

		$('#trigger_fullscreen').click(function() {
			toggleFullScreen();
		});
	}

	// Handle Select2 Dropdowns
	var handleSelect2 = function() {
		if ($().select2) {
			$('.select2me').select2({
				placeholder : "Select",
				allowClear : true
			});
		}
	}

	// Handle Theme Settings
	var handleTheme = function() {

		var theme_panel = $('.theme-panel');
		var theme_menu = $('.theme-menu');

		if ($('body').hasClass('page-boxed') == false) {
			$('.layout-option', theme_panel).val("fluid");
		}

		$('.sidebar-option', theme_panel).val("default");
		$('.header-option', theme_panel).val("fixed");
		$('.footer-option', theme_panel).val("default");

		// handle theme layout
		var resetLayout = function() {
			$("body").removeClass("page-boxed")
					.removeClass("page-footer-fixed").removeClass(
							"page-sidebar-fixed").removeClass(
							"page-header-fixed");

			$('.header > .header-inner').removeClass("container");

			if ($('.page-container').parent(".container").size() === 1) {
				$('.page-container').insertAfter('body > .clearfix');
			}

			if ($('.footer > .container').size() === 1) {
				$('.footer').html($('.footer > .container').html());
			} else if ($('.footer').parent(".container").size() === 1) {
				$('.footer').insertAfter('.page-container');
			}

			$('body > .container').remove();
		}

		var lastSelectedLayout = '';

		var setLayout = function() {

			var layoutOption = $('.layout-option', theme_panel).val();
			var sidebarOption = $('.sidebar-option', theme_panel).val();
			var headerOption = $('.header-option', theme_panel).val();
			var footerOption = $('.footer-option', theme_panel).val();

			if (sidebarOption == "fixed" && headerOption == "default") {
				alert('Default Header with Fixed Sidebar option is not supported. Proceed with Fixed Header with Fixed Sidebar.');
				$('.header-option', theme_panel).val("fixed");
				$('.sidebar-option', theme_panel).val("fixed");
				sidebarOption = 'fixed';
				headerOption = 'fixed';
			}

			resetLayout(); // reset layout to default state

			if (layoutOption === "boxed") {
				$("body").addClass("page-boxed");

				// set header
				$('.header > .header-inner').addClass("container");
				var cont = $('body > .clearfix').after(
						'<div class="container"></div>');

				// set content
				$('.page-container').appendTo('body > .container');

				// set footer
				if (footerOption === 'fixed') {
					$('.footer').html(
							'<div class="container">' + $('.footer').html()
									+ '</div>');
				} else {
					$('.footer').appendTo('body > .container');
				}
			}

			if (lastSelectedLayout != layoutOption) {
				// layout changed, run responsive handler:
				runResponsiveHandlers();
			}
			lastSelectedLayout = layoutOption;

			// header
			if (headerOption === 'fixed') {
				$("body").addClass("page-header-fixed");
				$(".header").removeClass("navbar-static-top").addClass(
						"navbar-fixed-top");
			} else {
				$("body").removeClass("page-header-fixed");
				$(".header").removeClass("navbar-fixed-top").addClass(
						"navbar-static-top");
			}

			// sidebar
			if (sidebarOption === 'fixed') {
				$("body").addClass("page-sidebar-fixed");
			} else {
				$("body").removeClass("page-sidebar-fixed");
			}

			// footer
			if (footerOption === 'fixed') {
				$("body").addClass("page-footer-fixed");
			} else {
				$("body").removeClass("page-footer-fixed");
			}

			handleSidebarAndContentHeight(); // fix content height
			handleFixedSidebar(); // reinitialize fixed sidebar
			handleFixedSidebarHoverable(); // reinitialize fixed sidebar hover
			// effect
		}

		// handle theme colors
		var setColor = function(color) {
			var css = $('#style_color');
			if ($('#style_color').length > 0) {
				var href = css.attr("href");
				href = href.substring(0, href.lastIndexOf("/") + 1)
				css.attr("href", href + color + ".css");
			}
			if (typeof (window.frames["mainFrame"]) != "undefined") {
				css = $(window.frames["mainFrame"].document).find(
						"#style_color");
				if (css.length > 0) {
					var href = css.attr("href");
					href = href.substring(0, href.lastIndexOf("/") + 1)
					css.attr("href", href + color + ".css");
				}
				$.cookie('style_color', color);
			}
		};

		$('.toggler', theme_panel).click(function() {
			$('.toggler').hide();
			$('.toggler-close').show();
			$('.theme-panel > .theme-options').show();
		});

		$('.toggler-close', theme_panel).click(function() {
			$('.toggler').show();
			$('.toggler-close').hide();
			$('.theme-panel > .theme-options').hide();
		});

		$('.theme-colors > ul > li', theme_panel).click(function() {
			var color = $(this).attr("data-style");
			setColor(color);
			$('ul > li', theme_panel).removeClass("current");
			$(this).addClass("current");
		});

		$('.layout-option, .header-option, .sidebar-option, .footer-option',
				theme_panel).change(setLayout);

		$('.theme-colors > ul > li', theme_menu).click(function() {
			var color = $(this).attr("data-style");
			setColor(color);
			$('ul > li', theme_menu).removeClass("current");
			$(this).addClass("current");
		});

		if ($.cookie('style_color')) {
			setColor($.cookie('style_color'));
		}
	}

	// handle form validate
	var handleFormValidate = function() {
		var $from = $("form.form-validate");
		if ($from.length > 0) {
			$from.each(function() {
				$(this).validate(
						{
							ignore : ".ignore-validate",
							highlight : function(element) {
								$(element).closest('.form-group').addClass(
										'has-error');
							},
							unhighlight : function(element) {
								$(element).closest('.form-group').removeClass(
										'has-error');
							},
							errorElement : 'span',
							errorClass : 'help-block',
							errorPlacement : function(error, element) {
								if (element.parent('.input-group').length) {
									error.insertAfter(element.parent());
								} else {
									error.insertAfter(element);
								}
							},
							submitHandler : function(form) {
								form.submit();
							}
						});
			});
		}
	}

	var handleInput = function() {
		/* 在textarea处插入文本--Start */
		(function($) {
			$.fn.extend({
				insertContent : function(myValue, t) {
					var $t = $(this)[0];
					if (document.selection) { // ie
						this.focus();
						var sel = document.selection.createRange();
						sel.text = myValue;
						this.focus();
						sel.moveStart('character', -l);
						var wee = sel.text.length;
						if (arguments.length == 2) {
							var l = $t.value.length;
							sel.moveEnd("character", wee + t);
							t <= 0 ? sel.moveStart("character", wee - 2 * t
									- myValue.length) : sel.moveStart(
									"character", wee - t - myValue.length);
							sel.select();
						}
					} else if ($t.selectionStart || $t.selectionStart == '0') {
						var startPos = $t.selectionStart;
						var endPos = $t.selectionEnd;
						var scrollTop = $t.scrollTop;
						$t.value = $t.value.substring(0, startPos) + myValue
								+ $t.value.substring(endPos, $t.value.length);
						this.focus();
						$t.selectionStart = startPos + myValue.length;
						$t.selectionEnd = startPos + myValue.length;
						$t.scrollTop = scrollTop;
						if (arguments.length == 2) {
							$t.setSelectionRange(startPos - t, $t.selectionEnd
									+ t);
							this.focus();
						}
					} else {
						this.value += myValue;
						this.focus();
					}
				}
			})
		})(jQuery);
	}
	// * END:CORE HANDLERS *//

	return {

		// main function to initiate the theme
		init : function() {
			// IMPORTANT!!!: Do not modify the core handlers call order.

			// core handlers
			handleInit(); // initialize core variables
			handleResponsiveOnResize(); // set and handle responsive
			handleUniform(); // hanfle custom radio & checkboxes
			handleScrollers(); // handles slim scrolling contents
			handleResponsiveOnInit(); // handler responsive elements on page
			// load

			// layout handlers
			handleFixedSidebar(); // handles fixed sidebar menu
			handleFixedSidebarHoverable(); // handles fixed sidebar on hover
			// effect
			handleSidebarMenu(); // handles main menu
			handleHorizontalMenu(); // handles horizontal menu
			handleSidebarToggler(); // handles sidebar hide/show
			handleFixInputPlaceholderForIE(); // fixes/enables html5
			// placeholder attribute for
			// IE9, IE8
			handleGoTop(); // handles scroll to top functionality in the footer
			handleTheme(); // handles style customer tool

			// ui component handlers
			handleFancybox() // handle fancy box
			handleSelect2(); // handle custom Select2 dropdowns
			handlePortletTools(); // handles portlet action bar
			// functionality(refresh, configure, toggle,
			// remove)
			handleAlerts(); // handle closabled alerts
			handleDropdowns(); // handle dropdowns
			handleTabs(); // handle tabs
			handleTooltips(); // handle bootstrap tooltips
			handlePopovers(); // handles bootstrap popovers
			handleAccordions(); // handles accordions
			handleModals(); // handle modals
			handleFullScreenMode(); // handles full screen
			handleFormValidate();// handles form validate
			handleInput();
		},

		// main function to initiate core javascript after ajax complete
		initAjax : function() {
			handleSelect2(); // handle custom Select2 dropdowns
			handleDropdowns(); // handle dropdowns
			handleTooltips(); // handle bootstrap tooltips
			handlePopovers(); // handles bootstrap popovers
			handleAccordions(); // handles accordions
			handleUniform(); // hanfle custom radio & checkboxes
			handleDropdownHover() // handles dropdown hover
		},

		// public function to fix the sidebar and content height accordingly
		fixContentHeight : function() {
			handleSidebarAndContentHeight();
		},

		// public function to remember last opened popover that needs to be
		// closed on click
		setLastPopedPopover : function(el) {
			lastPopedPopover = el;
		},

		// public function to add callback a function which will be called on
		// window resize
		addResponsiveHandler : function(func) {
			responsiveHandlers.push(func);
		},

		// useful function to make equal height for contacts stand side by side
		setEqualHeight : function(els) {
			var tallestEl = 0;
			els = $(els);
			els.each(function() {
				var currentHeight = $(this).height();
				if (currentHeight > tallestEl) {
					tallestColumn = currentHeight;
				}
			});
			els.height(tallestEl);
		},

		// wrapper function to scroll(focus) to an element
		scrollTo : function(el, offeset) {
			pos = (el && el.size() > 0) ? el.offset().top : 0;
			$('html,body').animate({
				scrollTop : pos + (offeset ? offeset : 0)
			}, 'slow');
		},

		// function to scroll to the top
		scrollTop : function() {
			App.scrollTo();
		},

		// wrapper function to block element(indicate loading)
		blockUI : function(el, centerY) {
			var el = $(el);
			if (el.height() <= 400) {
				centerY = true;
			}
			el.block({
				message : '<img src="./assets/img/ajax-loading.gif" align="">',
				centerY : centerY != undefined ? centerY : true,
				css : {
					top : '10%',
					border : 'none',
					padding : '2px',
					backgroundColor : 'none'
				},
				overlayCSS : {
					backgroundColor : '#000',
					opacity : 0.05,
					cursor : 'wait'
				}
			});
		},

		// wrapper function to un-block element(finish loading)
		unblockUI : function(el, clean) {
			$(el).unblock({
				onUnblock : function() {
					$(el).css('position', '');
					$(el).css('zoom', '');
				}
			});
		},

		// initializes uniform elements
		initUniform : function(els) {
			if (els) {
				$(els).each(function() {
					if ($(this).parents(".checker").size() == 0) {
						$(this).show();
						$(this).uniform();
					}
				});
			} else {
				handleUniform();
			}
		},

		// wrapper function to update/sync jquery uniform checkbox & radios
		updateUniform : function(els) {
			$.uniform.update(els); // update the uniform checkbox & radios UI
			// after the actual input control state
			// changed
		},

		// public function to initialize the fancybox plugin
		initFancybox : function() {
			handleFancybox();
		},

		// public helper function to get actual input value(used in IE9 and IE8
		// due to placeholder attribute not supported)
		getActualVal : function(el) {
			var el = $(el);
			if (el.val() === el.attr("placeholder")) {
				return "";
			}
			return el.val();
		},

		// public function to get a paremeter by name from URL
		getURLParameter : function(paramName) {
			var searchString = window.location.search.substring(1), i, val, params = searchString
					.split("&");

			for (i = 0; i < params.length; i++) {
				val = params[i].split("=");
				if (val[0] == paramName) {
					return unescape(val[1]);
				}
			}
			return null;
		},

		// check for device touch support
		isTouchDevice : function() {
			try {
				document.createEvent("TouchEvent");
				return true;
			} catch (e) {
				return false;
			}
		},

		// check IE8 mode
		isIE8 : function() {
			return isIE8;
		},

		// check IE9 mode
		isIE9 : function() {
			return isIE9;
		},

		// check RTL mode
		isRTL : function() {
			return isRTL;
		},
		// get layout color code by color name
		getLayoutColorCode : function(name) {
			if (layoutColorCodes[name]) {
				return layoutColorCodes[name];
			} else {
				return '';
			}
		},
		kindeditor : function(selector, uploadUrl, filesUrl) {
			var editor = KindEditor.create(selector, {
				heigth : 200,
				width : '100%',// 窗口最大化时会出问题
				items : [ 'source', '|', 'undo', 'redo', '|', 'preview',
						'print', 'template', 'cut', 'copy', 'paste',
						'plainpaste', 'wordpaste', '|', 'justifyleft',
						'justifycenter', 'justifyright', 'justifyfull',
						'insertorderedlist', 'insertunorderedlist', 'indent',
						'outdent', 'subscript', 'superscript', 'clearhtml',
						'quickformat', 'selectall', '|', 'fullscreen', '/',
						'formatblock', 'fontname', 'fontsize', '|',
						'forecolor', 'hilitecolor', 'bold', 'italic',
						'underline', 'strikethrough', 'lineheight',
						'removeformat', '|', 'image', 'table', 'hr',
						'emoticons', 'baidumap', 'code', 'pagebreak', 'link',
						'unlink', 'music', 'video' ],
				uploadJson : uploadUrl,
				fileManagerJson : filesUrl,
				allowImageUpload : true,
				allowFlashUpload : true,
				allowMediaUpload : true,
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					KindEditor.ctrl(document, 13, function() {
						self.sync();
					});
					KindEditor.ctrl(self.edit.doc, 13, function() {
						self.sync();
					});
				},
				afterBlur : function() {
					// 编辑器失去焦点时直接同步，可以取到值
					this.sync();
					$(selector).trigger("blur");
				}
			});
			return editor;
		},
		upload : function(selector) {

		}

	};

}();

$(function() {
	App.init();
});