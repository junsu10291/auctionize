var categories = ["YARD", "CLEAN", "COURIER", "PAINT", "ACT", "MOVE",
                  "CONSTRUCT", "HANDY", "DRIVE", "PET", "BABY", "ASSEMBLE", "MISC"];

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
        var startTime = timeToString(sliderValToTime(sliderVals[0]));
        var endTime = timeToString(sliderValToTime(sliderVals[1]));
        $("#dayofjobs-btn").text("Day of Jobs from " + startTime + " to " + endTime);
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