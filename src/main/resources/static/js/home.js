var collapsed = false;
var begin = false;
var tutStep = 1;

// array of tutorial buubles
var tutBubbles = [$( "#tutorialIntroWrapper" ),
                  $( ".timelineBubble" ),
                  $( ".categoriesBubble" ),
                  $( ".dayofJobsBubble" )];

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
    
    // get tutorialStatus
    $.post("/getTutorialStatus", {}, function(responseJSON) {
      var response = JSON.parse(responseJSON);
      console.log(response);
      if (!response) {
        $( "#tutorialCover" ).fadeOut("fast");
        $( "#tutorialIntroWrapper" ).fadeOut("fast");
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
    $("#tutorialCover").css({"cursor":"pointer"});
    begin = true;
    tutNext();
}

function tutNext() {
   if (tutStep < tutBubbles.length) {
     tutBubbles[tutStep - 1].fadeOut( "slow", function() {});
     tutBubbles[tutStep].fadeIn( "slow", function() {});
     tutStep += 1;
   } else {
     console.log("done");
     tutBubbles[tutBubbles.length - 1].fadeOut( "slow", function() {});
     skipTut();
   }
}


