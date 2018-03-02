$.extend({
    Confirm: function(){
        var template = multiline(function(){/*!@preserve
            <style>
                .jquery-confirm-content p{margin:.7em 0 0;}
                .jquery-confirm-cancel:hover,.jquery-confirm-ok:hover{background:#f0f0f0;}
                .jquery-confirm-ok.disabled{width:50%;cursor:not-allowed;color:gray!important;}
            </style>
            <div style="position:fixed;width:100%;height:100%;display:table;text-align:center;background:rgba(0,0,0,.3);z-index:100001;top:0;left:0;">
                <div style="display:table-cell;vertical-align:middle;">
                    <div class="jquery-confirm-body" style="display:inline-block;background:#fff;border-radius:10px;text-align:left;overflow:hidden;max-width:90%;">
                        <div style="text-align:left;padding:20px;line-height:1.5;">
                            <h4 class="jquery-confirm-title" style="margin:0;font-size:16px;"></h4>
                            <div class="jquery-confirm-content" style="font-size:14px;padding:1em 0 0;"></div>
                        </div>
                        <div style="display:inline-table;width:100%;line-height:48px;height:48px;border-top:1px solid rgba(0,0,0,.1);font-size:16px;text-align:center;">
                            <a class="jquery-confirm-cancel" href="javascript:;" style="display:table-cell;box-sizing:border-box;color:#3498DB;text-decoration:none;text-align:center;">
                                取消
                            </a>
                            <a class="jquery-confirm-ok" href="javascript:;" style="display:table-cell;box-sizing:border-box;color:#3498DB;text-decoration:none;text-align:center;">
                              强制登录
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        */console.log()});

        return function(title,content,btns_num,click_bg_cancel,confirm_delay_seconds){
            var title = title||'你确认要进行该操作',
                content = content||'',
                btns_num = btns_num||2,
                click_bg_cancel = click_bg_cancel||false,
                confirm_delay_seconds = confirm_delay_seconds||0,
                ok,cancel,result = {
                    ok:function(func){ok=func;return result;},
                    cancel:function(func){cancel=func;return result;}
                };

            var dom = $('<div>').html(template);

            btns_num==1
                ? dom.find('.jquery-confirm-cancel').remove()
                : dom.find('.jquery-confirm-ok').css('border-left','1px solid rgba(0,0,0,.1)');

            dom.on('touchmove',function(){return false;});
            dom.find('.jquery-confirm-title').html(title);
            dom.find('.jquery-confirm-content').html(content);

            click_bg_cancel && dom.on('click',function(){
                cancel && cancel();
                distroyAll();
            }).find('.jquery-confirm-body').on('click',function(){
                return false;
            }) && $(window).on('keyup', tryEscape);

            dom.find('.jquery-confirm-ok').on('click',function(){
                if( $(this).is('.disabled') ){return false;}
                $(this).addClass('disabled');

                ok && ok();
                distroyAll();
            });
            dom.find('.jquery-confirm-cancel').one('click',function(){
                cancel && cancel();
                distroyAll();
            });

            //Prevent hands mistake 闃叉墜鎶栧姛鑳�
            confirm_delay_seconds && dom.find('.jquery-confirm-ok').addClass('disabled');
            var delay_seconds_timer = confirm_delay_seconds && setInterval(function(){
                if(confirm_delay_seconds<0){
                    clearInterval(delay_seconds_timer);
                    renderDelaySeconds(0);
                    dom.find('.jquery-confirm-ok').removeClass('disabled');
                    return;
                }
                renderDelaySeconds(confirm_delay_seconds);
                confirm_delay_seconds--;
            },1000);

            dom.appendTo( $('body') );

            function renderDelaySeconds(seconds){
                dom.find('.delay-seconds').html(
                    seconds ? ('&nbsp;( ' + seconds + 's )') : ''
                );
            }

            function tryEscape(event){
                event.key === 'Escape' && distroyAll();
            }

            function distroyAll(){
                $(window).off('keyup',tryEscape);
                clearInterval(delay_seconds_timer);
                dom.remove();
            }

            return result;
        }

        function multiline(fn){ //https://github.com/sindresorhus/multiline
            var reCommentContents = /\/\*!?(?:\@preserve)?[ \t]*(?:\r\n|\n)([\s\S]*?)(?:\r\n|\n)[ \t]*\*\//;
            if(typeof fn !== 'function'){throw new TypeError('Expected a function');}
            var match = reCommentContents.exec(fn.toString());
            if(!match){throw new TypeError('Multiline comment missing.');}
            return match[1];
        };
    }()
});