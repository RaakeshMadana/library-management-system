window.onload = attach;

function attach() {
	$("#search").on("click", search);
}

function search() {
	var searchQuery = $("#searchQuery").val();
	$.ajax({
		type: "GET",
		url: "/search",
		data: {
			query: searchQuery
		},
		dataType: "json",
		success: function(data) {
			console.log(data.length);
			var res = "";
			for(var i = 0; i < data.length; i++) {
				res += "<div>" + JSON.stringify(data[i]) + "</div>";
			}
			$("#content").html(res);
		}
	});
}
