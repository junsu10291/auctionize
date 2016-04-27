var collapsed = false;

$( document ).ready(function() {
    $(".chzn-select").chosen({
            width: "250px",
            placeholder_text_multiple: "Select Category"});

    $("#togglebar").click(function() {
        if (!collapsed) {
            $("#sidebar").hide();
            $("#mapContainer").width("99%");
            collapsed = true;
            google.maps.event.trigger(map, 'resize');
            drawChart();
        } else {
            $("#sidebar").show();
            $("#mapContainer").width("76.5%");
            collapsed = false;
            drawChart();
        }
    });

    $('.chzn-select').on('change', function(evt, params) {
        if (params.deselected != null) {
            removeCategory(params.deselected);
        }

        if (params.selected != null) {
            addCategory(params.selected);
        }
    });
});

$("#profile").on('click', function(){
	if(connected == 1){
		console.log("go to profile");
	}else{
		console.log("login!");
	}
});

