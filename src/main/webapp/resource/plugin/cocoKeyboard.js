(function(){
	$.fn.cocoKeyboard = function(){
		var $write,
			$inputKeyboard = $('#keyboardContainer'),
			keyWidth = 672,
			me = this,
			inputHeight = this.height() + parseInt(this.css('padding'))*2 + parseInt(this.css('borderWidth')),
			inputWidth = (parseInt(this.css('borderWidth')) * 2 + this.width() + parseInt(this.css('padding')) * 2)
		$(document).find('head').append('<style>.keyboard{list-style:none;overflow:hidden;position:absolute;background:#eee;border-radius:5px;padding:5px 0 0 8px;z-index:99}.keyboard li{float:left;margin:0 5px 5px 0;width:40px;height:40px;line-height:40px;text-align:center;background:#fff;border:1px solid #ddd;-moz-border-radius:5px;-webkit-border-radius:5px;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none}.keyboard .capslock,.keyboard .tab,.keyboard .left-shift{clear:left}.keyboard .tab,.keyboard .delete{width:70px}.keyboard .capslock{width:80px}.keyboard .return{width:77px}.keyboard .left-shift{width:95px}.keyboard .right-shift{width:109px}.keyboard  .lastitem{margin-right:0}.keyboard .uppercase{text-transform:uppercase}.keyboard .space{width:518px}.keyboard .sp-hide{display:none}.keyboard li:hover{border-color:#ddd;background:#f3f3f3;cursor:pointer}.keyboard li:active{transition:all .3s;box-shadow:0 0 3px rgba(0,0,0,0.5) inset;background:#ddd;border-color:#aaa}.keyboard li.active{box-shadow:0 0 3px rgba(0,0,0,0.5) inset;background:#ddd;border-color:#aaa}</style>')
		function bindKeyboardEvent(input){
			if(me){
				if($inputKeyboard){
					var shift = false,
						capslock = false;
					$inputKeyboard.find('li').off().on('click',function(e){
						e.stopPropagation();
						var $value = $write.val();
							$this = $(this),
							character = $this.find('span').length ? $this.find('span').html() : $this.html();
						$write.val($write.val() + character);
					});
				}
			}
		}
		
		me.off().on('click',function(e){
			e.stopPropagation();
			$write = $(this);
			$inputKeyboard = $('<ul id="keyboardContainer" style="width:'+ keyWidth +'px;display:block;" class="keyboard"><li class="capslock"><span class="off">Esc</span></li><li class="symbol"><span class="off">F1</span></li><li class="symbol"><span class="off">F2</span></li><li class="symbol"><span class="off">F3</span></li><li class="symbol"><span class="off">F4</span></li><li class="symbol"><span class="off">F5</span></li><li class="symbol"><span class="off">F6</span></li><li class="symbol"><span class="off">F7</span></li><li class="symbol"><span class="off">F8</span></li><li class="symbol"><span class="off">F9</span></li><li class="symbol"><span class="off">F10</span></li><li class="symbol"><span class="off">F11</span></li><li class="symbol lastitem"><span class="off">F12</span></li><li class="symbol"><span class="off">`</span><span class="sp-hide">~</span></li><li class="symbol"><span class="off">1</span><span class="sp-hide">!</span></li><li class="symbol"><span class="off">2</span><span class="sp-hide">@</span></li><li class="symbol"><span class="off">3</span><span class="sp-hide">#</span></li><li class="symbol"><span class="off">4</span><span class="sp-hide">$</span></li><li class="symbol"><span class="off">5</span><span class="sp-hide">%</span></li><li class="symbol"><span class="off">6</span><span class="sp-hide">^</span></li><li class="symbol"><span class="off">7</span><span class="sp-hide">&amp;</span></li><li class="symbol"><span class="off">8</span><span class="sp-hide">*</span></li><li class="symbol"><span class="off">9</span><span class="sp-hide">(</span></li><li class="symbol"><span class="off">0</span><span class="sp-hide">)</span></li><li class="symbol"><span class="off">-</span><span class="sp-hide">_</span></li><li class="symbol"><span class="off">=</span><span class="sp-hide">+</span></li><li class="delete lastitem">delete</li><li class="tab">tab</li><li class="letter">q</li><li class="letter">w</li><li class="letter">e</li><li class="letter">r</li><li class="letter">t</li><li class="letter">y</li><li class="letter">u</li><li class="letter">i</li><li class="letter">o</li><li class="letter">p</li><li class="symbol"><span class="off">[</span><span class="sp-hide">{</span></li><li class="symbol"><span class="off">]</span><span class="sp-hide">}</span></li><li class="symbol lastitem"><span class="off">\\</span><span class="sp-hide">|</span></li><li class="capslock">caps lock</li><li class="letter">a</li><li class="letter">s</li><li class="letter">d</li><li class="letter">f</li><li class="letter">g</li><li class="letter">h</li><li class="letter">j</li><li class="letter">k</li><li class="letter">l</li><li class="symbol"><span class="off">;</span><span class="sp-hide">:</span></li><li class="symbol"><span class="off">\'</span><span class="sp-hide">&quot;</span></li><li class="return lastitem">Enter</li><li class="left-shift">shift</li><li class="letter">z</li><li class="letter">x</li><li class="letter">c</li><li class="letter">v</li><li class="letter">b</li><li class="letter">n</li><li class="letter">m</li><li class="symbol"><span class="off">,</span><span class="sp-hide">&lt;</span></li><li class="symbol"><span class="off">.</span><span class="sp-hide">&gt;</span></li><li class="symbol"><span class="off">/</span><span class="sp-hide">?</span></li><li class="right-shift lastitem">shift</li><li class="symbol">Ctrl</li><li class="symbol">Win</li><li class="symbol">Alt</li><li class="space lastitem">space</li></ul>');
			$(this).parent().append($inputKeyboard);
			bindKeyboardEvent();
			if($(this).parent().css('position') === 'static'){
				$(this).parent().css('position','relative');
			}
			$inputKeyboard.css('top', inputHeight + 5);
			if((keyWidth - inputWidth) > 0){
				$inputKeyboard.css('left', -(keyWidth - inputWidth)/2);
			}else{
				$inputKeyboard.css('left', (keyWidth - inputWidth)/2);
			}
		});

		$(document).on('click',function(){
			$('body').find('#keyboardContainer').remove();
		});

	}
})(jQuery)
