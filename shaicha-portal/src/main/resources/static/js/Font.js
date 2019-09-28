(function (doc, win) {
	var docEl = doc.documentElement,
		resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
		recalc = function () {
			var clientWidth = docEl.clientWidth;
			if (!clientWidth) return;
			if(clientWidth>=750){
				docEl.style.fontSize = '100px';
			}else{
				docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
			}
		};

	if (!doc.addEventListener) return;
	win.addEventListener(resizeEvt, recalc, false);
	doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);

/*当屏幕宽度大于等于750px的时候，根元素字体恒为100px，否则按屏幕大小自行缩放大小*/