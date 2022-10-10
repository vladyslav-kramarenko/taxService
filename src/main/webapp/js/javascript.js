var completeField;
var completeTable;
var autoRow;
var req;
var isIE;

function init() {
	completeField = document.getElementById("complete-field");
	completeTable = document.getElementById("complete-table");
	autoRow = document.getElementById("auto-row");
	completeTable.style.top = getElementY(autoRow) + "px";
}

function chooseImage() {
	alert("img");
	document.getElementById("productImage");
}

function addChar() {
	var charTable=document.getElementById("charTable");
	var row;
	var cell;
	if (isIE) {
		charTable.style.display = 'block';
		row = charTable.insertRow(charTable.rows.length);
		cell = row.insertCell(0);
	} else {
		charTable.style.display = 'table';
		row = document.createElement("tr");
		cell = document.createElement("td");
		row.appendChild(cell);
		charTable.appendChild(row);
	}

//	cell.className = "popupCell";
	linkElement = document.createElement("div");
	linkElement.className = "popupItem";
	linkElement.onclick = function() {
		var man = name;
		completeField.value = man;
		clearTable();
	};
	linkElement.appendChild(document.createTextNode(name));

	cell.appendChild(linkElement);
}

function doCompletion() {
	var url = "controller?command=complete&action=complete&id="
			+ escape(completeField.value);
	req = initRequest();
	req.open("GET", url, true);
	req.onreadystatechange = callback;
	req.send(null);
}

// function doManufacturerCompletion() {
// 	var url = "controller?command=completeManufacturer&action=complete&id="
// 			+ escape(completeField.value);
// 	req = initRequest();
// 	req.open("GET", url, true);
// 	req.onreadystatechange = callback;
// 	req.send(null);
// }

function initRequest() {
	if (window.XMLHttpRequest) {
		if (navigator.userAgent.indexOf('MSIE') != -1) {
			isIE = true;
		}
		return new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		isIE = true;
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
}
function callback() {
	clearTable();
	if (req.readyState == 4) {
		if (req.status == 200) {
			parseMessages(req.responseXML);
		}
	}
}



// function appendManufacturer(name, itemId) {
//
// 	var row;
// 	var cell;
// 	if (isIE) {
// 		completeTable.style.display = 'block';
// 		row = completeTable.insertRow(completeTable.rows.length);
// 		cell = row.insertCell(0);
// 	} else {
// 		completeTable.style.display = 'table';
// 		row = document.createElement("tr");
// 		cell = document.createElement("td");
// 		row.appendChild(cell);
// 		completeTable.appendChild(row);
// 	}
//
// 	cell.className = "popupCell";
// 	linkElement = document.createElement("div");
// 	linkElement.className = "popupItem";
// 	linkElement.onclick = function() {
// 		var man = name;
// 		completeField.value = man;
// 		clearTable();
// 	};
// 	linkElement.appendChild(document.createTextNode(name));
//
// 	cell.appendChild(linkElement);
// }

function appendProduct(date,name, itemId,reportName,statusName,) {

	var row;
	var cell;
	if (isIE) {
		completeTable.style.display = 'block';
		row = completeTable.insertRow(completeTable.rows.length);
		cell = row.insertCell(0);
	} else {
		completeTable.style.display = 'table';
		row = document.createElement("tr");
		cell = document.createElement("td");
		row.appendChild(cell);
		completeTable.appendChild(row);
	}

	cell.className = "popupCell";

	linkElement = document.createElement("a");
	linkElement.className = "popupItem";
	linkElement.setAttribute("href",
			"controller?command=complete&action=lookup&id=" + itemId);
	linkElement.appendChild(document.createTextNode(manufacturer + " " + name));
	cell.appendChild(linkElement);
}

function getElementY(element) {

	var targetTop = 0;

	if (element.offsetParent) {
		while (element.offsetParent) {
			targetTop += element.offsetTop;
			element = element.offsetParent;
		}
	} else if (element.y) {
		targetTop += element.y;
	}
	return targetTop;
}

function clearTable() {
	if (completeTable.getElementsByTagName("tr").length > 0) {
		completeTable.style.display = 'none';
		for (loop = completeTable.childNodes.length - 1; loop >= 0; loop--) {
			completeTable.removeChild(completeTable.childNodes[loop]);
		}
	}
}

function parseMessages(responseXML) {

	// no matches returned
	if (responseXML == null) {
		return false;
	} else {
		if (responseXML.getElementsByTagName("products")[0] != null) {
			parseProduct(responseXML)
		}
		if (responseXML.getElementsByTagName("manufacturers")[0] != null) {
			parseManufacturer(responseXML)
		}

	}
}

function parseProduct(responseXML) {
	var products = responseXML.getElementsByTagName("products")[0];

	if (products.childNodes.length > 0) {
		completeTable.setAttribute("bordercolor", "black");
		completeTable.setAttribute("border", "1");

		for (loop = 0; loop < products.childNodes.length; loop++) {
			var product = products.childNodes[loop];
			var manufacturer = product.getElementsByTagName("manufacturer")[0];
			var name = product.getElementsByTagName("name")[0];
			var productId = product.getElementsByTagName("id")[0];
			appendProduct(manufacturer.childNodes[0].nodeValue,
					name.childNodes[0].nodeValue,
					productId.childNodes[0].nodeValue);
		}
	}
}

function parseManufacturer(responseXML) {

	var products = responseXML.getElementsByTagName("manufacturers")[0];

	if (products.childNodes.length > 0) {
		completeTable.setAttribute("bordercolor", "black");
		completeTable.setAttribute("border", "1");

		for (loop = 0; loop < products.childNodes.length; loop++) {
			var product = products.childNodes[loop];
			var name = product.getElementsByTagName("name")[0];
			var productId = product.getElementsByTagName("id")[0];
			appendManufacturer(name.childNodes[0].nodeValue,
					productId.childNodes[0].nodeValue);
		}
	}
}