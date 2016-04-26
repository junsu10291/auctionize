var map;
var jobs = {};
var markers = {};
var hours = new Date().getHours();
var minutes = new Date().getMinutes();

function initMap() {
  var loc = {lat: 41.826130, lng: -71.403};
  
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
      $.post("/jobs", {}, function(responseJSON) {
        jobs = JSON.parse(responseJSON);
        console.log(jobs);
        for (var key in jobs) {
          newMarker(jobs[key], 1, true);
        }
      });
    }, function() {});
  }
}

function newMarker(job, opacity, drop) {
  var oldMarker = markers[job.id];
  if (oldMarker != undefined) {
    oldMarker.setMap(null);
  }
  var size = (5/7)*job.profit + 50;
  var flag = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
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

function path() {
  $.post("/path", {}, function(responseJSON) {
    var path = JSON.parse(responseJSON);
    directions(path);
  });
}


function directions(path) {
  var directionsService = new google.maps.DirectionsService();
  directionsDisplay = new google.maps.DirectionsRenderer();
  directionsDisplay.setMap(map);
  var waypoints = [];
  for (var i = 1; i < path.length - 1; i++) {
    waypoints.push({location: path[i], stopover: true});
  }
  var directionsRequest = {
      origin: path[0],
      destination: path[path.length - 1],
      waypoints: waypoints,
      provideRouteAlternatives: true,
      travelMode: google.maps.TravelMode.WALKING,
      unitSystem: google.maps.UnitSystem.IMPERIAL
  }
  directionsService.route(directionsRequest, function(result, status) {
    if (status == google.maps.DirectionsStatus.OK) {
      directionsDisplay.setDirections(result);
    }
  });
  var ids = [];
  for (var i = 0; i < path.length; i++) {
    ids.push(path[i].id);
  }
  console.log(ids);
  for (var key in jobs) {
    console.log(key);
    console.log(!(inArray(key, ids)));
    if (!(inArray(key, ids))) {
      newMarker(jobs[key], 0.1, false);
    }
  }
}

function inArray(item, array) {
  for (var i = 0; i < array.length; i++) {
    if (item == array[i]) {
      return true;
    }
  }
  return false;
}

