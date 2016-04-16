var map;
var jobs;
var markers = [];
var time = 8*60;

function initMap() {
  var myLatLng = {lat: 41.826130, lng: -71.403};

  map = new google.maps.Map(document.getElementById('map'), {
    zoom: 17,
    center: myLatLng
  });
  
  $.post("/jobs", {}, function(responseJSON) {
    jobs = JSON.parse(responseJSON);
    for (var i = 0; i < jobs.length; i++) {
      jobs[i].profit = Math.round(jobs[i].profit * 100) / 100;
      makeJobMarker(jobs[i]);
    }
  });
}

function makeJobMarker(job) {
  var jobTime = job.start.hours*60 + job.start.minutes;
  var diff = jobTime - time;
  var opacity = 1 - (1/12)*diff;
  var marker = new google.maps.Marker({
    position: {lat: job.lat, lng: job.lng},
    map: map,
//    icon: {
//      url: "https://lh4.googleusercontent.com/-Dr4TCurbw-Q/AAAAAAAAAAI/AAAAAAAAC9U/t_1ZEww4REQ/photo.jpg",
//      size: new google.maps.Size(job.profit*2, job.profit*2),
//      origin: new google.maps.Point(0, 0),
//      anchor: new google.maps.Point(0, 0),
//      scaledSize: new google.maps.Size(job.profit*2, job.profit*2),
//    },
    icon: {
      path: google.maps.SymbolPath.CIRCLE,
      scale: job.profit/2,
      fillColor: "red",
      fillOpacity: opacity,
      strokeOpacity: opacity,
      strokeWeight: 1
    },
    title: "job"
  });
  markers.push(marker);
  marker.setAnimation(google.maps.Animation.DROP);
  var info = new google.maps.InfoWindow({
    content: "<style>p{text-align: center}"
            + "p.title{font-weight: bold;}</style>"
            + "<p class=\"title\">" + job.title + "</p>"
            + "<p>" + job.description + "</p>"
            + "<p>Profit: $" + job.profit + "</p>"
  });
  marker.addListener('click', function() {
    info.open(map, marker);
  });
}

function directions() {
  var directionsService = new google.maps.DirectionsService();
  directionsDisplay = new google.maps.DirectionsRenderer();
  directionsDisplay.setMap(map);
  var directionsRequest = {
      origin: jobs[0],
      destination: jobs[4],
      waypoints: [
        {
          location: jobs[1],
          stopover: true
        },{
          location: jobs[2],
          stopover: true
        },{
          location: jobs[3],
          stopover: true
        }],
      provideRouteAlternatives: true,
      travelMode: google.maps.TravelMode.WALKING,
      unitSystem: google.maps.UnitSystem.IMPERIAL
    }
  directionsService.route(directionsRequest, function(result, status) {
    if (status == google.maps.DirectionsStatus.OK) {
      directionsDisplay.setDirections(result);
    }
  });
  for (var i = 5; i < jobs.length; i++) {
    var oldMarker = markers[i];
    oldMarker.setMap(null);
    var job = jobs[i];
    var newMarker = new google.maps.Marker({
      position: {lat: job.lat, lng: job.lng},
      map: map,
      icon: {
        path: google.maps.SymbolPath.CIRCLE,
        scale: job.profit/2,
        fillColor: "red",
        fillOpacity: 0.2,
        strokeOpacity: 0.2,
        strokeWeight: 1
      },
      title: "job"
    });
    var info = new google.maps.InfoWindow({
      content: "<style>p{text-align: center}"
              + "p.title{font-weight: bold;}</style>"
              + "<p class=\"title\">" + job.title + "</p>"
              + "<p>" + job.description + "</p>"
              + "<p>Profit: $" + job.profit + "</p>"
    });
    newMarker.addListener('click', function() {
      info.open(map, newMarker);
    });
    markers[i] = newMarker;
  }
}
