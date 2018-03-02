/**
 * requires jquery 11+ ,bootstrap3.0+
 * 0.1.0.20151217
 */


var Common = {};
/**
 * Common.bs.js
 * 1.alert
 * 2.confirm
 * 3.dialog
 * 4.msg
 */
Common.on = function(obj, event, func){
	$(document).off(event, obj).on(event, obj, func);
};
Common.bs 	= {};
Common.bs.modaloptions = {
	id		: 'bsmodal',
	url 	: '',
	fade	: 'fade',
	close	: true,
	title	: 'title',
	head	: true,
	foot	: true,
	btn		: false,
	okbtn	: '确定',
	qubtn	: '取消',
	msg		: 'msg',
	big		: false,
	show	: false,
	remote	: false,
	backdrop: false,
	keyboard: true,
	style	: 'height:100%',
	mstyle	: 'width: 400px',
	callback: null
};
Common.bs.modalstr = function(opt){
	var start = '<div class="modal '+opt.fade+'" id="' + opt.id + '" tabindex="-1" role="dialog" aria-labelledby="bsmodaltitle" aria-hidden="true" style="position:fixed;top:20px;'+opt.style+'">';
	if(opt.big){
		start += '<div class="modal-dialog modal-lg" style="'+opt.mstyle+'"><div class="modal-content">';
	}else{
		start += '<div class="modal-dialog" style="'+opt.mstyle+'"><div class="modal-content">';
	}
	var end = '</div></div></div>';
	
	var head = '';
	if(opt.head){
		head += '<div class="modal-header">';
		if(opt.close){
			head += '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>';
		}
		head += '<h4 class="modal-title" id="bsmodaltitle">'+opt.title+'</h4></div>';
	}
	
	var body = '<div class="modal-body"><p><h4>'+opt.msg+'</h4></p></div>';
	
	var foot = '';
	if(opt.foot){
		foot += '<div class="modal-footer"><button type="button" class="btn btn-primary bsok">'+opt.okbtn+'</button>';
		if(opt.btn){
			foot += '<button type="button" class="btn btn-default bscancel">'+opt.qubtn+'</button>';
		}
		foot += '</div>';
	}
	
	return start + head + body + foot + end;
};
Common.bs.alert = function(options, func){
	// options
	var opt = $.extend({}, Common.bs.modaloptions);
	
	opt.title = '系统提示';
	if(typeof options == 'string'){
		opt.msg = options;
	}else{
		$.extend(opt, options);
	}
	
	// add
	$('body').append(Common.bs.modalstr(opt));
	
	// init
	var $modal = $('#' + opt.id);

	$modal.modal(opt);
	// bind
	Common.on('button.bsok', 'click', function(){
		if(func) func();
		$modal.modal('hide');
	});
	Common.on('#' + opt.id, 'hidden.bs.modal', function(){
		$modal.remove();
	});
	
	// show
	$modal.modal('show');
};
Common.bs.confirm = function(options, ok, cancel){
	// options
	var opt = $.extend({}, Common.bs.modaloptions);
	opt.id = "bs-confirm";
	opt.title = '确认操作';
	if(typeof options == 'string'){
		opt.msg = options;
	}else{
		$.extend(opt, options);
	}
	opt.btn = true;
	
	// append
	$('body').append(Common.bs.modalstr(opt));
	
	// init
	var $modal = $('#' + opt.id); 
	$modal.modal(opt);
	
	// bind
	Common.on('button.bsok', 'click', function(){
		if(ok) ok();
		$modal.modal('hide');
	});
	Common.on('button.bscancel', 'click', function(){
		if(cancel) cancel();
		$modal.modal('hide');
	});
	Common.on('#' + opt.id, 'hidden.bs.modal', function(){
		$modal.remove();
	});
	
	// show
	$modal.modal('show');
};
Common.bs.dialog = function(options, func){
	// options
	var opt = $.extend({}, Common.bs.modaloptions, options);
	opt.big = true;
	
	// append
	$('body').append(Common.bs.modalstr(opt));
	
	// ajax page
	Common.ajax({
		url:options.url,
		dataType:'html'
	}, function(html){
		$('#' + opt.id + ' div.modal-body').empty().append(html);
		if(options.callback) options.callback();
	});
		
	// init
	var $modal = $('#' + opt.id); 
	$modal.modal(opt);
	
	// bind
	Common.on('button.bsok', 'click', function(){
		var flag = true;
		if(func){
			flag = func();
		}
		
		if(flag){
			$modal.modal('hide');
		}
	});
	Common.on('button.bscancel', 'click', function(){
		$modal.modal('hide');
	});
	Common.on('#' + opt.id, 'hidden.bs.modal', function(){
		$modal.remove();
	});
	
	// show
	$modal.modal('show');
};
Common.bs.msgoptions = {
	msg: 'msg',
	type: 'info',
	time: 2000,
	position: 'top',
};
Common.bs.msgstr = function (msg, type, position) {
	return '<div class="alert alert-' + type + ' alert-dismissible" role="alert" style="display:none;position:fixed;' + position + ':0;left:0;width:100%;z-index:2001;margin:0;text-align:center; border-radius:0;background-color: #FFFADD !important;height:45px;font-size:16px;color: black !important;" id="bsalert"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' + msg + '</div>';
};
Common.bs.msg = function (options) {
	var opt = $.extend({}, Common.bs.msgoptions);

	if (typeof options == 'string') {
		opt.msg = options;
	} else {
		$.extend(opt, options);
	}
	if (window.top.frames) {
		var str = window.top.frames.Common.bs.msgstr(opt.msg, opt.type, opt.position);
		var parentDoc = window.top.document;
		$('body', parentDoc).prepend(str);
		var $bspAlert = $('#bsalert', parentDoc);
		$bspAlert.slideDown();
		setTimeout(function () {
			$bspAlert.slideUp(function () {
				$bspAlert.remove();
			});
		}, opt.time);
	} else {
		$('body').prepend(Common.bs.msgstr(opt.msg, opt.type, opt.position));
		var $bsAlert = $('#bsalert');
		$bsAlert.slideDown();
		setTimeout(function () {
			$bsAlert.slideUp(function () {
				$bsAlert.remove();
			});
		}, opt.time);
	}
};






