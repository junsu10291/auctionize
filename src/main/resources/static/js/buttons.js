var categories = ["YARD","CLEAN","COURIER","PAINT","ACT","MOVE",
                  "CONSTRUCT","HANDY","DRIVE","PET","BABY","ASSEMBLE","MISC"];

$( document ).ready(function() {
    $("#slider").slider({ id: "slider-id", min: 96, max: 288, range: true, value: [96, 288] });
});

// trying to change the time displayed on the day of jobs button
// when the slider is changed, not working
$("#slider").on("slide", function(slideEvent) {
  alert("akksjfkld");
});

function sliderValToTime(sliderVal) {
  var hour = Math.floor(sliderVal / 12);
  var minute = (sliderVal % 12) * 5;
  return {hour: hour, minute: minute};
}