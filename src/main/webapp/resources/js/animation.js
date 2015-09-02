/**
 * 
 */
function next() {
	try {
		$("#div_step_one").hide("drop", {
			direction : "right"
		}, 1000);
		if (document.getElementById("div_step_two").className == "div_hidden") {
			$("#div_step_two").switchClass("div_hidden", "div_show", 1000);
		}else{
		setTimeout(function() {
			$("#div_step_two").show("drop", {
				direction : "left"
			}, 1000);
		}, 1000);
		;}
	} catch (err) {
	}
}

function nextNormal() {
	try {
		$("#div_step_one").hide("drop", {
			direction : "right"
		}, 1000);
		setTimeout(function() {
			$("#div_step_two").show("drop", {
				direction : "left"
			}, 1000);
		}, 1000);

	} catch (err) {
		alert(err);
	}

}

function back() {
	try {
		$("#div_step_two").hide("drop", {
			direction : "left"
		}, 1000);
		setTimeout(function() {
			$("#div_step_one").show("drop", {
				direction : "right"
			}, 1000);
		}, 1000);

	} catch (err) {
	}

}

function timer() {
}

function readOnly(element) {
	element.readOnly = true;
}

function notReadOnly(element) {
	element.readOnly = false;
}