var nextLine = "<br />";
var collapsed = "/img/collapsed.gif";
var expanded = "/img/expanded.gif";
var lines = 0;

function format_json() {
	var tab = $("#tab").val();
	var highLight = $("#highLight").prop("checked");
	$("#validate_result").html("正在解析。。。");
	$("#dest").html("");
	tab = tab == "0" ? "<pre>&#09;</pre>" : multiplyString("&nbsp;",
			Number(tab));
	var level = 0;
	var source = $("#source").val();
	var isObject = false;
	if (source == "") {
		source = "\"\"";
	}
	var capital = source.trim().charAt(0);
	if (capital == "{") {
		source = "[" + source + "]"
		isObject = true;
	}
	try {
		var sourceObj = eval(source);
		var formatHtml = parseObject(sourceObj, tab, level, isObject, highLight);
		$("#validate_result").html("解析成功").css("color", "green");
		$("#dest").html(formatHtml);
		lines = countLines(formatHtml);
		refreshLineNumbers();
	} catch (e) {
		general_ajax("/function/format", "POST", {
			source : source
		}, null, function(result) {
			if (get_type(result) == "[object String]") {
				result = json_parse(result);
			}
			$("#dest").val(result.data);
		}, null, null);
		$("#validate_result").html("json校验失败，正从服务器格式化！").css("color", "red");
	}
}

function parseObject(obj, tab, level, isObject, highLight) {
	var formatJson = "";
	var tabs = multiplyString(tab, level);
	var quote = highLight ? "<span style=\"color:purple;\">\"</span>" : "\"";
	var type = typeof obj;
	if (isArray(obj)) {
		var arrayStart = (level == 0 ? "" : nextLine) + tabs + "[<img src=\""
				+ expanded + "\" onclick=\"collapse(this);\" /><span>"
				+ nextLine;
		formatJson += (level == 0 && isObject) ? "" : arrayStart;
		level = level == 0 && isObject ? level : level + 1;
		var array = [];
		for (var i = 0; i < obj.length; i++) {
			var objJson = parseObject(obj[i], tab, level, isObject, highLight);
			array.push(objJson);
		}
		formatJson += array.join("," + nextLine);
		formatJson += (level == 0 && isObject) ? ""
				: ((array.length > 0 ? nextLine : "") + tabs + "</span>]");
	} else if (type == "object") {
		if (obj == null) {
			formatJson += tabs + "null";
		} else if (obj.constructor == Date.constructor) {
			formatJson += tabs + obj.toLocaleString();
		} else if (obj.constructor == RegExp.constructor) {
			formatJson += tabs + obj;
		} else {
			formatJson += tabs + "{<img src=\"" + expanded
					+ "\" onclick=\"collapse(this);\" /><span>" + nextLine;
			var array = [];
			for ( var key in obj) {
				var value = obj[key];
				key = tabs
						+ tab
						+ quote
						+ (highLight ? "<span style=\"color:red;\">" + key
								+ "</span>" : key) + quote;
				value = parseObject(value, tab, level + 1, isObject, highLight);
				array.push(key + ":" + value);
			}
			formatJson += array.join("," + nextLine)
					+ (array.length > 0 ? nextLine : "") + tabs + "</span>}";
		}
		// 直接类型
	} else {
		var value = highLight ? ("<span style=\"color:green;\">" + obj + "</span>")
				: obj;
		if (type == "number") {
			formatJson += value;
		} else if (type == "boolean") {
			formatJson += value;
		} else if (type == "function") {
			if (obj.constructor == window.RegExp.constructor) {
				formatJson += value;
			} else {
				formatJson += value;
			}
		} else {
			formatJson += quote + value + quote;
		}
	}
	return formatJson;
}

function isArray(obj) {
	return obj && typeof obj === "object" && typeof obj.length === "number"
			&& !(obj.propertyIsEnumerable("length"));
}

function multiplyString(str, count) {
	var array = [];
	for (var i = 0; i < count; i++) {
		array.push(str);
	}
	return array.join("");
}

function collapse(img) {
	var next = $($(img).next());
	var changedLines = countLines(next.html()) - 1;
	if (next.is(":visible")) {
		next.hide();
		$(img).attr("src", collapsed);
		lines -= changedLines;
	} else {
		next.show();
		$(img).attr("src", expanded);
		lines += changedLines;
	}
	refreshLineNumbers();
}

function countLines(content) {
	var reg = /<br\s{0,1}\/{0,1}>/g;
	var i = 0;
	while (reg.exec(content) != null) {// exec使arr返回匹配的第一个，while循环一次将使re在g作用寻找下一个匹配。
		i++;
	}
	return i + 1;
}

function refreshLineNumbers() {
	var indexArray = [];
	for (var i = 0; i < lines; i++) {
		indexArray.push(i);
	}
	$("#line-number").html(indexArray.join(nextLine));
}