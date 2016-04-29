var collapsed = false;
var begin = false;
var tut = 0;

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

$("#tutorialCover").on('click', function(){
    if (begin) {
        tutNext();
    }
});

function skipTut() {
    $( "#tutorialCover" ).fadeOut( "slow", function() {});
    $( "#tutorialIntroWrapper" ).fadeOut( "slow", function() {});
}

function beginTut() {
    $( "#tutorialIntroWrapper" ).fadeOut( "slow", function() {});
    $( ".timelineBubble" ).fadeIn( "slow", function() {});
    $("#tutorialCover").css({"cursor":"pointer"});
    begin = true;
    tut = 1;
}

function tutNext() {
    if (tut == 1) {
        $( ".timelineBubble" ).fadeOut( "slow", function() {});
        $( ".categoriesBubble" ).fadeIn( "slow", function() {});
        tut = 2;
    } else if (tut == 2) {
        $( ".categoriesBubble" ).fadeOut( "slow", function() {});
        $( ".dayofJobsBubble" ).fadeIn( "slow", function() {});
        tut = 3;
    } else if (tut == 3) {
        $( ".dayofJobsBubble" ).fadeOut( "slow", function() {});
        skipTut();
    }
}

