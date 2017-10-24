window.onload = attach;

function attach() {
	$("#borrower").on("click", addBorrower);
}

function addBorrower() {
	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	var email = $("#email").val();
	var address = $("#address").val();
	var city = $("#city").val();
	var state = $("#state").val();
	var phone = $("#phone").val();
	$.ajax({
		type: "POST",
		url: "/addborrower",
		data: {
			firstName: firstName,
			lastName: lastName,
			email: email,
			address: address,
			city: city,
			state: state,
			phone: phone
		},
		dataType: "json",
		success: function(data) {
			var res = "";
			if(data[0] == 1) {
				res += "<h5>Borrower added</h5>";
			}
			else {
				res += "<h5>Borrower not added; borrower already exists</h5>";
			}
			$("#content").html(res);
		}
	});
}
