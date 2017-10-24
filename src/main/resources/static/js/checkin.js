window.onload = attach;

var loanId = [];

function attach() {
	$("#searchLoaned").on("click", searchLoaned);
}

$("#searchQueryLoaned").on("keyup", function(event) {
	if(event.keyCode == 13) {
		search();
	}
});

function searchLoaned() {
	var searchQuery = $("#searchQueryLoaned").val();
	$.ajax({
		type: "GET",
		url: "/searchloaned",
		data: {
			query: searchQuery
		},
		dataType: "json",
		success: function(data) {
			console.log(data.length);
			for(var i = 0; i < data.length; i++) {
				loanId.push(data[i]["loanId"]);
				var res = '<input type="button" id="checkin" value="Check In" />';
				var div = '';
				div = '<div class="book">';
				div += '<img src="' + data[i]["cover"] + '" alt="' + data[i]["isbn13"] + '" />';
				div += '<input type="checkbox id="' + data[i]["loanId"] + '" />';
				div += '<label for="'+ data[i]["loanId"] + '"><h4 class="title">' + data[i]["title"] + '</h4></label>';
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
				$("$content").html(res);
				$("#checkin").on("click", checkIn);
			}
		}
	});
}

function checkIn() {
	var selectedLoanIds = [];
	for(var i = 0; i < loanId.length; i++) {
		var loanId = "#" + loanId[i];
		if($(loanId).prop("checked")) {
			selectedLoanIds.push(loanId[i]);
		}
	}
	$.ajax({
		type: "GET",
		url: "/checkin",
		data: {
			loanIds: selectedLoanIds
		},
		dataType: "json",
		success: function(data){
		}
	});
}
