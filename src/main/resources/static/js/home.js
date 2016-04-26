var collapsed = false;

$( document ).ready(function() {
    $(".chzn-select").chosen({
            width: "230px",
            placeholder_text_multiple: "Select Category"});

    $( document ).click(function() {
        if (!collapsed) {
            $("#sidebar").hide();
            $("#map").width("100%");
            collapsed = true;
        } else {
            $("#sidebar").show();
            $("#map").width("80%");
            collapsed = false;
        }
        
    //     $( "#sidebar" ).animate({
    //     "margin-left": "-=300",
    // }, 500, function() {
        
    // });
});
});

$("#profile").on('click', function(){
	if(connected == 1){
		console.log("go to profile");
	}else{
		console.log("login!");
	}
});

