
function filter(t){
var rows = document.getElementsByClassName("row");
for(i = 0; i< rows.length; i++){
	if(rows[i].innerText.indexOf(t) === -1){
		rows[i].style.display = "none";	
	}
}
}