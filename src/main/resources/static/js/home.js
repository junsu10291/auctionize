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
    });

    $('.chzn-select').on('change', function(evt, params) {
        if (params.deselected != null) {
            removeCategory(params.deselected);
        }

        if (params.selected != null) {
            addCategory(params.selected);
        }
    });

    categoriesFilterSet.add("ASSEMBLE").add("YARD").add("MISC").add("CLEAN").add("CONSTRUCT").add("MOVE")
    .add("HANDY").add("DRIVE").add("PAINT").add("ACT").add("COURIER").add("PET").add("BABY");
});

$("#profile").on('click', function(){
	if(connected == 1){
		console.log("go to profile");
	}else{
		console.log("login!");
	}
});

