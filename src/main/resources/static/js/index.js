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
				var div = '';
				div = '<div class="book" id="' + data[i]["isbn13"] + '">';
				div += '<img src="' + data[i]["cover"] + '" alt="' + data[i]["isbn13"] + '" />';
				div += '<h4 class="title">' + data[i]["title"] + '</h4>';
				div += '<p class="authors">';
				for(var j = 0; j < data[i]["authors"].length; j++) {
					div += data[i]["authors"][j];
					div += ', ';
				}
				div = div.slice(0, -2);
				div += '</p>';
				div += '<p><span class="isbn13"><span class="isbn">ISBN13:</span>' + data[i]["isbn13"] + '</span><span class="isbn10"><span class="isbn">ISBN10:</span>' + data[i]["isbn10"] + '</span></p>'; 
				div += '<p><span class="pages"><span class="pagepub">Pages:</span>' + data[i]["pages"] + '</span><span class="publisher"><span class="pagepub">Publisher:</span>' + data[i]["publisher"] + '</span></p>'; 
				div += '</div>';
				res += div;
			}
			$("#content").html(res);
		}
	});
}
