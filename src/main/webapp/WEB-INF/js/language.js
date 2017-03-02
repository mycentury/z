function changeLanguage(language) {
	general_ajax("/language/change", "POST", {
		language : language
	}, null, function(result) {
		window.location.href = window.location.href;
	}, null, null);
}