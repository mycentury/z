function list_files(progress_id, files_id, submit_id) {
	var files = $("#" + files_id).get(0).files;
	var progress = $("#" + progress_id);
	progress.children().remove();
	if (files.length == 0) {
		$("#" + submit_id).attr("disabled", true);
	} else {
		$("#" + submit_id).attr("disabled", false);
	}
	var size = 0;
	for (var i = 0; i < files.length; i++) {
		size += files[i].size;
	}
	var max_size = 1000 * 1024 * 1024;
	if (size > max_size) {
		file_input.outerHTML = file_input.outerHTML;
		file_input.value = "";
		return false;
	}
	for (var i = 0; i < files.length; i++) {
		var element = "<div id=\"progress" + i + "\" class=\"progress progress-striped active\">";
		element += "<div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuenow=\"60\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 0%;\">"
		element += "<span class=\"sr-only\"></span>"
		element += "</div></div>";
		progress.html(progress.html() + element);
	}

}

function update_progress(progress_id, percent) {
	$($("#" + progress_id).children("div")[0]).css("width", percent + "%");
	$($("#" + progress_id).children("span")[0]).html(percent + "%");
}

function start_upload(form_id, files_id, submit_id, show_img_id) {
	var timer = null;
	var submit = $("#" + submit_id)[0];
	var url = "/file/upload";
	var fd = new FormData($("#" + form_id)[0]);
	var success = function(msg) {
		if (get_type(msg) == "[object String]") {
			msg = json_parse(msg);
		}
		if (msg.result == "fail") {
			alert(msg.error_msg + "请重新上传！");
			$(submit).attr("disabled", false);
			clearInterval(timer);
		} else {
			$("#" + show_img_id).attr("src", msg.targetNames);
		}
		// 上传结束，不一定成功，success是针对ajax请求，不是业务
		$(submit).attr("disabled", false);
	};
	var error = function(msg) {
		// 上传错误
		$(submit).attr("disabled", false);
		clearInterval(timer);
	};
	var beforeSend = function() {
	};
	var complete = function() {
	};
	// 禁止重复提交
	$(submit).attr("disabled", true);
	// 开始上传
	media_ajax(url, "POST", fd, beforeSend, success, complete, error);

	// 开始检查进度
	timer = setInterval(function() {
		// 检查进度
		var xhr = create_traditional_ajax();
		get_progress(xhr, files_id, timer);
	}, 100);
}

function get_progress(xhr, files_id, timer) {
	var url = "/file/progress";
	var files = $("#" + files_id).get(0).files;
	var fileNames = "";
	var fileSizes = "";
	for (var i = 0; i < files.length; i++) {
		if (i > 0) {
			fileNames += "&";
		}
		fileNames += "fileNames=" + files[i].name;
		fileSizes += "&fileSizes=" + files[i].size;
	}
	var data = fileNames + fileSizes;
	var success = function(msg) {
		if (get_type(msg) == "[object String]") {
			// alert(typeof msg == "string");
			msg = json_parse(msg);
		}
		var flag = true;
		for ( var key in msg) {
			var value = msg[key];
			update_progress(key, value);
			if (key.startWith("progress") && value != "100") {
				flag = false;
			}
			$("#" + key).removeClass("active");
		}
		if (flag) {
			clearInterval(timer);
		}
	};
	var error = function(msg) {
		alert(msg);
		clearInterval(timer);
	};
	traditionalAjax(xhr, url, "POST", data, success, error);
}