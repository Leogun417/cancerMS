/**
 * 扩展textbox 下面增加删除标志图标
 */
$.extend($.fn.textbox.methods, {
	addClearBtn : function(jq, iconCls) {
		return jq.each(function() {
			var t = $(this);
			var opts = t.textbox('options');
			opts.icons = opts.icons || [];
			opts.icons.unshift({
				iconCls : iconCls,
				handler : function(e) {
					$(e.data.target).textbox('clear').textbox('textbox')
							.focus();
					$(this).css('visibility', 'hidden');
				}
			});
			t.textbox();
			if (!t.textbox('getText')) {
				t.textbox('getIcon', 0).css('visibility', 'hidden');
			}
			t.textbox('textbox').bind('keyup', function() {
				var icon = t.textbox('getIcon', 0);
				if ($(this).val()) {
					icon.css('visibility', 'visible');
				} else {
					icon.css('visibility', 'hidden');
				}
			});
		});
	}
});

/**
 * 扩展textbox 增加按下enter键触发方法时间
 */
$.extend($.fn.textbox.defaults, {
	inputEvents : $.extend({}, $.fn.textbox.defaults.inputEvents, {
		keydown : function(e) {
			if (e.keyCode == 13) {
				e.preventDefault();
				var t = $(e.data.target);
				var attrId = t.attr("id");
				t.textbox("setValue", $(this).val());
				searchTextBox(t.textbox("getValue"),attrId);
			}
		}
	})
});