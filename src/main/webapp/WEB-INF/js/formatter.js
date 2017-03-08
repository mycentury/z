var nextLine = "<br />";
var collapsed = "/img/collapsed.gif";
var expanded = "/img/expanded.gif";
var lines = 0;
var color_key = "#FF6633";
var color_value = "green";
var color_null = "#993300";
var color_quote = "red";
var max_level = 0;

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
	} catch (e) {
		general_ajax("/function/format", "POST", {
			source : source
		}, null, function(result) {
			if (get_type(result) == "[object String]") {
				result = json_parse(result);
			}
			$("#dest").html(result.data);
			$("#validate_result").html("Next is the result from the server!")
					.css("color", "red");
		}, null, null);
		$("#validate_result").html("json校验失败，正从服务器格式化！").css("color", "red");
		$("#line-number").html("");
	}
	refreshLineNumbers();
	generateLevelOptions();
}

function parseObject(obj, tab, level, isObject, highLight) {
	var formatJson = "";
	var tabs = multiplyString(tab, level);
	var quote = highLight ? "<span style=\"color:" + color_quote
			+ ";\">\"</span>" : "\"";
	var type = typeof obj;
	max_level = max_level < level - 1 ? level - 1 : max_level;
	if (isArray(obj)) {
		var arrayStart = (level == 0 ? "" : nextLine) + tabs + "[<img src=\""
				+ expanded
				+ "\" onclick=\"collapse_by_img(this);\" /><span level=\""
				+ level + "\">" + nextLine;
		formatJson += (level == 0 && isObject) ? "" : arrayStart;
		level = level == 0 && isObject ? level : level + 1;
		var array = [];
		for (var i = 0; i < obj.length; i++) {
			var objJson = multiplyString(tab, level)
					+ parseObject(obj[i], tab, level, true, highLight);
			array.push(objJson);
		}
		formatJson += array.join("," + nextLine);
		formatJson += (level == 0 && isObject) ? ""
				: ((array.length > 0 ? nextLine : "") + tabs + "</span>]");
	} else if (type == "object") {
		if (obj == null) {
			formatJson += highLight ? ("<span style=\"color:" + color_null + ";\">null</span>")
					: "null";
		} else if (obj.constructor == Date.constructor) {
			formatJson += obj.toLocaleString();
		} else if (obj.constructor == RegExp.constructor) {
			formatJson += obj;
		} else {
			formatJson += (isObject ? "" : nextLine + tabs) + "{<img src=\""
					+ expanded
					+ "\" onclick=\"collapse_by_img(this);\" /><span level=\""
					+ level + "\">" + nextLine;
			var array = [];
			for ( var key in obj) {
				var value = obj[key];
				key = tabs
						+ tab
						+ quote
						+ (highLight ? "<span style=\"color:" + color_key
								+ ";\">" + key + "</span>" : key) + quote;
				value = parseObject(value, tab, level + 1, false, highLight);
				array.push(key + ":" + value);
			}
			formatJson += array.join("," + nextLine)
					+ (array.length > 0 ? nextLine : "") + tabs + "</span>}";
		}
		// 直接类型
	} else {
		var value = highLight ? ("<span style=\"color:" + color_value + ";\">"
				+ obj + "</span>") : obj;
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

function collapse_by_img(img) {
	var span = $($(img).next());
	if (span.is(":visible")) {
		span.hide();
		$(img).attr("src", collapsed);
	} else {
		span.show();
		$(img).attr("src", expanded);
	}
	refreshLineNumbers();
}

function collapse_by_action(action) {
	var level = Number($("#level").val());
	if (level <= 0) {
		level = 0;
	}
	for (var i = 0; i < level; i++) {
		collapse_by_action_level("expend", i);
	}
	collapse_by_action_level(action, level);
	refreshLineNumbers();
}

function collapse_by_action_level(action, level) {
	var spans = $("span[level='" + level + "']");
	for (var i = 0; i < spans.length; i++) {
		var span = $(spans.get(i));
		var img = span.prev();
		if (action == "collapse" && span.is(":visible")) {
			span.hide();
			$(img).attr("src", collapsed);
		} else if (action == "expend" && span.is(":hidden")) {
			span.show();
			$(img).attr("src", expanded);
		}
	}
}

function generateLevelOptions() {
	var options = "";
	for (var i = 0; i <= max_level; i++) {
		options += "<option value=\"" + i + "\">" + i + "</option>";
	}
	$("#level").html(options);
}

function refreshLineNumbers() {
	var hiddenLines = countAllHiddenLines();
	var indexArray = [];
	for (var i = 0; i < lines - hiddenLines; i++) {
		indexArray.push(i);
	}
	$("#line-number").html(indexArray.join(nextLine));
}

function countAllHiddenLines() {
	var hiddenLines = 0;
	var children = $("#dest").find("span[level='" + 0 + "']");
	for (var j = 0; j < children.length; j++) {
		var child = $(children.get(j));
		hiddenLines += countHiddenLines(child, 0);
	}
	return hiddenLines;
}

function countHiddenLines(span, level) {
	var hiddenLines = 0;
	if (level > max_level) {
		return hiddenLines;
	}
	if (span.is(":hidden")) {
		hiddenLines += countLines(span.html()) - 1;
	} else {
		var children = span.find("span[level='" + (level + 1) + "']");
		for (var j = 0; j < children.length; j++) {
			var child = $(children.get(j));
			hiddenLines += countHiddenLines(child, level + 1);
		}
	}
	return hiddenLines;
}

function countLines(content) {
	var reg = /<br\s{0,1}\/{0,1}>/g;
	var i = 0;
	while (reg.exec(content) != null) {// exec使arr返回匹配的第一个，while循环一次将使re在g作用寻找下一个匹配。
		i++;
	}
	return i + 1;
}