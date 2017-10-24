window.onload = attach;

function attach() {
	$("#search").on("click", search);
}

$("#searchQuery").on("keyup", function(event) {
	if(event.keyCode == 13) {
		search();
	}
});

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
				var availability = "";
				var availabilityClass = "";
				if(data[i]["availability"]){
					var availability = "Available";
					var availabilityClass = "available";
					var availCheckOut = 1;
				}
				else{
					availability = "Not Available";
					availabilityClass = "notavailable";
					var availCheckOut = 0;
				}
				var div = '';
				div = '<div class="book">';
				div += '<img src="' + data[i]["cover"] + '" alt="' + data[i]["isbn13"] + '" />';
				div += '<h4 class="title">' + data[i]["title"] + '</h4>';
				div += '<p class="authors">';
				for(var j = 0; j < data[i]["authors"].length; j++) {
					div += data[i]["authors"][j];
					div += ', ';
				}
				div = div.slice(0, -2);
				div += '</p>';
				div += '<p><span class="isbn13"><span class="isbn">ISBN13:</span><span id="' + i + '">' + data[i]["isbn13"] + '</span></span><span class="isbn10"><span class="isbn">ISBN10:</span>' + data[i]["isbn10"] + '</span></p>'; 
				div += '<p><span class="pages"><span class="pagepub">Pages:</span>' + data[i]["pages"] + '</span><span class="publisher"><span class="pagepub">Publisher:</span>' + data[i]["publisher"] + '</span></p>'; 
				div += '<p><span class="' + availabilityClass + '">' + availability + '</span></p>';
				div += '<button type="button" onclick="checkAvailability(' + i + ',' + availCheckOut + ')">Checkout</button>';
				div += '</div>';
				res += div;
			}
			$("#content").html(res);
		}
	});
}

function checkAvailability(id, availability) {
	var id = "#" + id;
	var span = $(id);
	var isbn13 = span.text();
	console.log(availability);
	console.log(isbn13);
	if(availability){
		var res = '<h4>Enter Borrower Id</h4>';
		res += '<form>';
		res += '<input type="text" id="borrower" placeholder="Borrower Id" />';
		res += '<input type="hidden" id="isbn13" value="' + isbn13 + '" />';
		res += '<input type="button" id="checkout" value="Checkout" />';
		$("#content").html(res);
		$("#checkout").on("click", checkOut);
	}
	else{
		alert("Book not available");
	}
}

function checkOut() {
	var borrower = $("#borrower").val();
	var isbn13 = $("#isbn13").val();
	$.ajax({
		type: "GET",
		url: "/checkout",
		data: {
			borrower: borrower,
			isbn13: isbn13
		},
		dataType: "json",
		success: function(data) {
			if(data[0] == 1) {
				$("#content").html("<h5>Book checked out!</h5>");
			}
			else {
				var res = "";
				if(data[1] < 1){
					res += "<h5>Borrower does not exist</h5>";
				}
				if(!data[2]){
					res += "<h5>Book not available</h5>";
				}
				if(data[3] >= 3){
					res += "<h5>Borrower cannot borrow more than three books</h5>";
				}
				$("#content").html(res);
			}
		}
	});
}

