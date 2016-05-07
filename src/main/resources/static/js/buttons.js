var categories = ["YARD","CLEAN","COURIER","PAINT","ACT","MOVE",
                  "CONSTRUCT","HANDY","DRIVE","PET","BABY","ASSEMBLE","MISC"];

$( document ).ready(function() {
    $("#slider").slider({ id: "slider-id", min: 480, max: 1440, range: true, value: [480, 1440] });
});

// trying to change the time displayed on the day of jobs button
// when the slider is changed, not working
$("#slider").on("slide", function(slideEvent) {
  alert("akksjfkld");
});

function sliderValToTime(sliderVal) {
  var hour = Math.floor(sliderVal / 60);
  var minute = sliderVal % 60;
  return {hour: hour, minute: minute};
}