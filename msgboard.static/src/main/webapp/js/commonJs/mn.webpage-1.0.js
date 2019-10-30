function createPageHTML(_nPageCount, _nCurrIndex, _sPageName, _sPageExt) {
	if (_nPageCount == null || _nPageCount <= 1) {
		return;
	}
	var nCurrIndex = _nCurrIndex || 0;
	if (nCurrIndex == 0) {
		document.write("<a><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
		document.write("<span class='current'>1</span>&nbsp;");
	} else {
		var prePageNum = nCurrIndex - 1;
		if (prePageNum == 0)
			document.write("<a href=\"" + _sPageName + "." + _sPageExt
					+ "\" ><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
		else
			document.write("<a href=\"" + _sPageName + "_" + prePageNum + "."
					+ _sPageExt
					+ "\" ><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
		document.write("<a href=\"" + _sPageName + "." + _sPageExt
				+ "\" >1</a>&nbsp;");
	}
	var startNum;
	var endNum;
	if (nCurrIndex - 2 > 1)
		startNum = nCurrIndex - 2;
	else
		startNum = 1;
	if (nCurrIndex + 3 < _nPageCount)
		endNum = nCurrIndex + 3;
	else
		endNum = _nPageCount - 1;
	if (startNum > 1)
		document.write("...&nbsp;");
	for ( var i = startNum; i < endNum; i++) {
		if (nCurrIndex == i)
			document
					.write("<span class='current'>" + (i + 1) + "</span>&nbsp;");
		else
			document.write("<a href=\"" + _sPageName + "_" + i + "."
					+ _sPageExt + "\" >" + (i + 1) + "</a>&nbsp;");
	}
	if (endNum < _nPageCount - 1)
		document.write("...&nbsp;");
	var nextPageNum = nCurrIndex + 1;
	var lastPageNum = _nPageCount - 1;
	if (nCurrIndex == _nPageCount - 1) {
		document
				.write("<span class='current'>" + _nPageCount + "</span>&nbsp;");
		document.write("<a>下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
	} else {
		document.write("<a href=\"" + _sPageName + "_" + lastPageNum + "."
				+ _sPageExt + "\" >" + _nPageCount + "</a>&nbsp;");
		document.write("<a href=\"" + _sPageName + "_" + nextPageNum + "."
				+ _sPageExt
				+ "\" >下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
	}
}