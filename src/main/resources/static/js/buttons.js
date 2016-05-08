

$(document).ready(function () {
    $("#slider").slider({
        id: "slider-id",
        min: 96,
        max: 288,
        range: true,
        value: [96, 288]
    });

    $("#slider").slider().on("change", function (slideEvent) {
        var sliderVals = $("#slider").slider("getValue");
        var startTime = timeToString(getStartTime());
        var endTime = timeToString(getEndTime());
        $("#dayofjobs-btn").text("Day of Jobs from " + startTime + " to " + endTime);
        
        
    });
    
    $("#slider").slider().on("slideStop", function (slideEvent) {
      updateInclude();
    });
});

function sliderValToTime(sliderVal) {
    var hour = Math.floor(sliderVal / 12);
    var minute = (sliderVal % 12) * 5;
    return {
        hour: hour,
        minute: minute
    };
}

function timeToString(hourMinuteObject) {
    var hour = hourMinuteObject.hour;
    var minute = hourMinuteObject.minute;
    var toReturn = "";
    if (hour > 12) {
        toReturn += (hour - 12);
    } else {
        toReturn += hour;
    }
    if (minute < 10) {
        toReturn += ":0";
    } else {
        toReturn += ":";
    }
    toReturn += minute;
    if (hour >= 12 && hour != 24){
        toReturn += " PM";
    } else {
        toReturn += " AM";
    }
    return toReturn;
}

function getStartTime() {
  var sliderVals = $("#slider").slider("getValue");
  return sliderValToTime(sliderVals[0]);
}

function getEndTime() {
  var sliderVals = $("#slider").slider("getValue");
  return sliderValToTime(sliderVals[1]);
}