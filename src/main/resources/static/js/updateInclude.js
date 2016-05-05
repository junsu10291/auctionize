$("#startTime").on("input change", function() {
  updateInclude();
});

$("#endTime").on("input change", function() {
  updateInclude();
});

function updateInclude() {
  clearDirections();
  resetInclude();
  var startTime = timeFromVal($("#startTime").val());
  var endTime = timeFromVal($("#endTime").val());
  var selectedCategories = $(".chzn-select").children("input:checked").map(function(){return this.value;});
  for (var key in include) {
    var job = jobs[key];
    if (jobWithinRange(job, startTime, endTime) && inArray(job.category, selectedCategories)) {
      include[key] = true;
    }
  }
  updateMarkers();
}

function resetInclude() {
  for (var key in include) {
    include[key] = false;
  }
}

function jobWithinRange (job, startTime, endTime) {
  return (compareTimes(job.start, startTime) >= 0) &&
      (compareTimes(job.end, endTime) <= 0);
}

function compareTimes(time1, time2) {
  if (time1.hour < time2.hour) {
    return -1;
  } else if (time1.hour > time2.hour) {
    return 1;
  } else {
    // hours are equal: compare minutes
    if (time1.minute < time2.minute) {
      return -1;
    } else if (time1.minute > time2.minute) {
      return 1;
    } else {
      return 0;
    }
  }
}