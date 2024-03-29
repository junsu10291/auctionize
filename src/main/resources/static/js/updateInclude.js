var categories = {
    "YARD": true,
    "CLEAN": true,
    "COURIER": true, 
    "PAINT": true,
    "ACT": true, 
    "MOVE": true,
    "CONSTRUCT": true,
    "HANDY": true, 
    "DRIVE": true, 
    "PET": true, 
    "ASSEMBLE": true, 
    "MISC": true
}

function updateInclude() {
  clearDirections();
  resetInclude();
  var startTime = getStartTime();
  var endTime = getEndTime();
  
  for (var key in include) {
    var job = jobs[key];
    if (jobWithinRange(job, startTime, endTime) && categories[job.category]) {
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

function toggleCategory(category) {
  categories[category] = !categories[category];
  var button = $("#" + category);
  button.toggleClass("btn-success");
  button.toggleClass("btn-secondary");
  updateInclude();
}
