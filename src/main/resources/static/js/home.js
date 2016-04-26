var collapsed = false;

$( document ).ready(function() {
    $(".chzn-select").chosen({
            width: "230px",
            placeholder_text_multiple: "Select Category"});

    $("#togglebar").click(function() {
        if (!collapsed) {
            $("#sidebar").hide();
            $("#mapContainer").width("99%");
            collapsed = true;
            google.maps.event.trigger(map, 'resize');
        } else {
            $("#sidebar").show();
            $("#mapContainer").width("79.5%");
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

