var map;

// map of id to job
var jobs = {};

// map of id to marker
var markers = {};

// map of id to boolean (whether or not the given job should be displayed
//   on the map and included in the day of jobs algorithm
//   based on categories and time sliders)
var include = {};

// an array of job ids that represent the path for the day of jobs
var path = [];

var loc = {lat: 41.826130, lng: -71.403};

var homeMarker;
var directionsDisplay;
var FLAG = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
var OPAQUE = 1;
var TRANSPARENT = 0;

var drawingManager;
var region;
var regionDrawable = true;
var regionNorthWestBound = [];
var regionSouthEastBound = [];
var markerJobDict = {};

function initMap() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      loc = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };
      map = new google.maps.Map(document.getElementById('map'), {
        zoom: 15,
        center: loc
      });

      drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: null,
        drawingControl: true,
        drawingControlOptions: {
          position: google.maps.ControlPosition.TOP_CENTER,
          drawingModes: [google.maps.drawing.OverlayType.RECTANGLE]
        },
      });
      drawingManager.setMap(map);

      google.maps.event.addListener(drawingManager, 'overlaycomplete', function(event) {
        regionDrawable = false;
        $("#floatingPanel").show();
        region = event.overlay;
        drawingManager.setOptions({
          drawingControl: false,
          drawingMode: null
        });

        if (event.type == google.maps.drawing.OverlayType.RECTANGLE) {
          var bounds = event.overlay.getBounds();
          regionNorthWestBound = [bounds.R.j, bounds.j.j];
          regionSouthEastBound = [bounds.R.R, bounds.j.R];
        }
      });

      homeMarker = new google.maps.Marker({
        position: loc,
        map: map,
        icon: FLAG,
        draggable: true,
        title: "HOME"
      });
      var info = new google.maps.InfoWindow({
        content: "<style>/*p{text-align: center}*/"
                + "p.title{font-weight: bold;}</style>"
                + "<p class=\"title\">HOME</p>"
      });
      homeMarker.addListener('click', function() {
        info.open(map, homeMarker);
      });
      $.post("/jobs", {}, function(responseJSON) {
        jobs = JSON.parse(responseJSON);
        for (var key in jobs) {
          newMarker(jobs[key], OPAQUE, false);
        }
        google.charts.load("current", {packages:["timeline"]});
        google.charts.setOnLoadCallback(drawChart);
      });
    }, function() {});
  }
}

function removeRegion() {
  region.setMap(null);
  regionDrawable = true;

  $("#floatingPanel").hide();

  drawingManager.setOptions({
    drawingControl: true
  });
}

function addCategory(category) {
  $.each(jobs, function(index, value){
    if (value.category == category) {
      var marker = markers[index];
      marker.setMap(map);
    }
  });  
}

function removeCategory(category) {
  $.each(jobs, function(index, value){
    if (value.category == category) {
      var marker = markers[index];
      marker.setMap(null);
    }
  });
}

//display the given job on the map, and set its inlude value to true
//(so that it will be included in path alorithm)
function inlcude(id) {
markers[id].setMap(map);
include[id] = true;
}

//opposite of include
function remove(id) {
markers[id].setMap(null);
include[id] = false;
}

function newMarker(job, opacity, drop) {
  var oldMarker = markers[job.id];
  if (oldMarker != undefined) {
    oldMarker.setMap(null);
  }
  var size = (5/7)*job.profit + 50;
  var marker = new google.maps.Marker({
    position: {lat: job.lat, lng: job.lng},
    map: map,
//    icon: {
//      url: "https://lh4.googleusercontent.com/-Dr4TCurbw-Q/AAAAAAAAAAI/AAAAAAAAC9U/t_1ZEww4REQ/photo.jpg",
//      size: new google.maps.Size(job.profit*2, job.profit*2),
//      origin: new google.maps.Point(0, 0),
//      anchor: new google.maps.Point(0, 0),
//      scaledSize: new google.maps.Size(job.profit*2, job.profit*2),
//    }
  });
  markers[job.id] = marker;
  if (drop) {
    marker.setAnimation(google.maps.Animation.DROP);
  }
  var startTimeString = localtimeToString(job.start);
  var endTimeString = localtimeToString(job.end);
  var info = new google.maps.InfoWindow({
    content: "<style>/*p{text-align: center}*/"
            + "p.title{font-weight: bold;}</style>"
            + "<p class=\"title\">" + job.title + "</p>"
            + "<p>Category: " + job.category + "</p>"
            + "<p>Start: " + startTimeString + "</p>"
            + "<p>End: " + endTimeString + "</p>"
            + "<p>Profit: $" + job.profit + "</p>"
  });
  marker.addListener('click', function() {
    info.open(map, marker);
  });
}



function timeFromVal(value){
  console.log(value);
  var rawTime = (value/100)*16 + 8;
  var rawMinutes = rawTime % 1;
  var hour;
  var minutes = 0;
  if(rawMinutes >= 0.25 && rawMinutes <= 0.75){
    minutes = 30;
    hour = Math.floor(rawTime);
  }else if(rawMinutes < 0.25){
    hour = Math.floor(rawTime);
  }else{
    hour = Math.ceil(rawTime);
  }
  return {hours : hour, mins : minutes};
}

function getPath() {
  var startTime = timeFromVal($("#startTime").val());
  var endTime = timeFromVal($("#endTime").val());
  var home = getHomeLatLng();
  var params = {
      startHours: startTime.hours, 
      startMinutes: startTime.mins, 
      endHours: endTime.hours, 
      endMinutes: endTime.mins,
      homeLat: home.lat, 
      homeLng: home.lng,
      included: getIncluded()
  };
  console.log(params);
  $.post("/path", params, function(responseJSON) {
    path = JSON.parse(responseJSON);
    directions();
    drawChart();
  });
  
//  directions();
//  drawChart();
}

function directions() {
  var directionsService = new google.maps.DirectionsService();
  directionsDisplay = new google.maps.DirectionsRenderer();
  directionsDisplay.setMap(map);
  var waypoints = [];
  for (var i = 0; i < path.length; i++) {
    waypoints.push({location: jobs[path[i]], stopover: true});
  }
  var home = getHomeLatLng();
  var directionsRequest = {
      origin: home,
      destination: home,
      waypoints: waypoints,
      provideRouteAlternatives: true,
      travelMode: google.maps.TravelMode.WALKING,
      unitSystem: google.maps.UnitSystem.IMPERIAL
  }
  directionsService.route(directionsRequest, function(result, status) {
    if (status == google.maps.DirectionsStatus.OK) {
      console.log(result);
      directionsDisplay.setDirections(result);
    }
  });
  setMarkerOpacity(TRANSPARENT);
}

function inArray(item, array) {
  for (var i = 0; i < array.length; i++) {
    if (item == array[i]) {
      return true;
    }
  }
  return false;
}

function clearDirections() {
  directionsDisplay.setMap(null);
  setMarkerOpacity(OPAQUE);
}

function setMarkerOpacity(opacity) {
  for (var key in markers) {
    markers[key].setOpacity(opacity);
  }
}

function getHomeLatLng() {
  return {lat: homeMarker.getPosition().lat(), lng: homeMarker.getPosition().lng()};
}

function getIncluded() {
  var included = [];
  for (var key in include) {
    included.push(key);
  }
  return included;
}
