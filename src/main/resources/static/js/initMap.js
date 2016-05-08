var map;

// map of id to job
var jobs = {};

// map of id to marker
var markers = {};
var colors = ["blue", "red", "yellow", "green", "purple"];
var circles = [];

// map of id to boolean (whether or not the given job should be displayed
//   on the map and included in the day of jobs algorithm
//   based on categories and time sliders)
var include = {};

// an array of job ids that represent the path for the day of jobs
var path = [];

var profitBox;

var loc = {
    lat: 41.826130,
    lng: -71.403
};

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
var logos = 
{
    ASSEMBLE : "http://i.imgur.com/vgycjR0.png",
    CLEAN : "http://i.imgur.com/aOr8D3s.png",
    CONSTRUCT : "http://i.imgur.com/9jpCbQp.png",
    COURIER : "http://i.imgur.com/u4glEXX.png",
    DRIVE : "http://i.imgur.com/FFV0Qk0.png",
    HANDY : "http://i.imgur.com/HXY6Ygz.png",
    MISC : "http://i.imgur.com/tGKgs5a.png",
    MOVE : "http://i.imgur.com/VIAXfH8.png",
    PAINT : "http://i.imgur.com/PNMjfkT.png",
    PET : "http://i.imgur.com/aKEengg.png",
    ACT : "http://i.imgur.com/9XPdGLa.png",
    YARD : "http://i.imgur.com/tRrZ0hx.png"
}

function initMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            loc = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            map = new google.maps.Map(document.getElementById('map'), {
                zoom: 15,
                center: loc
            });

            

            homeMarker = new google.maps.Marker({
                position: loc,
                map: map,
                icon: FLAG,
                draggable: true,
                title: "HOME"
            });
            var info = new google.maps.InfoWindow({
                content: "<style>/*p{text-align: center}*/" + "p.title{font-weight: bold;}</style>" + "<p class=\"title\">HOME</p>"
            });
            homeMarker.addListener('click', function () {
                info.open(map, homeMarker);
            });
            $.post("/jobs", {}, function (responseJSON) {
                jobs = JSON.parse(responseJSON);
                for (var key in jobs) {
                    newMarker(jobs[key], OPAQUE, false);
                    include[key] = true;
                }
                google.charts.load("current", {
                    packages: ["timeline"]
                });
                google.charts.setOnLoadCallback(drawChart);
            });
        }, function () {});
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
    $.each(jobs, function (index, value) {
        if (value.category == category) {
            includeJob(index);
        }
    });
}

function removeCategory(category) {
    $.each(jobs, function (index, value) {
        if (value.category == category) {
             removeJob(index);
        }
    });
}

//display the given job on the map, and set its inlude value to true
//(so that it will be included in path alorithm)
function inlcudeJob(id) {
    markers[id].setMap(map);
    include[id] = true;
}

//opposite of include
function removeJob(id) {
    markers[id].setMap(null);
    include[id] = false;
}



function newMarker(job, opacity, drop) {
    var myURL = logos[job.category];
    var oldMarker = markers[job.id];
    if (oldMarker != undefined) {
        oldMarker.setMap(null);
    }
    var size = (5 / 7) * job.profit + 50;
    var marker = new google.maps.Marker({
        position: {
            lat: job.lat,
            lng: job.lng
        },
        map: map,
           icon: {
             url: myURL,
             size: new google.maps.Size(25, 25),
             origin: new google.maps.Point(0, 0),
             anchor: new google.maps.Point(0, 0),
             scaledSize: new google.maps.Size(25, 25),
           }
    });
    markers[job.id] = marker;
    if (drop) {
        marker.setAnimation(google.maps.Animation.DROP);
    }
    var startTimeString = localtimeToString(job.start);
    var endTimeString = localtimeToString(job.end);
    var info = new google.maps.InfoWindow({
        content: jobInfoHTML(job)
    });
    marker.addListener('click', function () {
        info.open(map, marker);
    });
}



function timeFromVal(value) {
    var rawTime = (value / 100) * 16 + 8;
    var rawMinutes = rawTime % 1;
    var hour;
    var minutes = 0;
    if (rawMinutes >= 0.25 && rawMinutes <= 0.75) {
        minutes = 30;
        hour = Math.floor(rawTime);
    } else if (rawMinutes < 0.25) {
        hour = Math.floor(rawTime);
    } else {
        hour = Math.ceil(rawTime);
    }
    return {
        hour: hour,
        minute: minutes
    };
}

function getPath() {
    clearDirections();
    var sliderVals = $("#slider").slider("getValue");
    var startTime = getStartTime();
    var endTime = getEndTime();
    var home = getHomeLatLng();
    var params = {
        startHours: startTime.hour,
        startMinutes: startTime.minute,
        endHours: endTime.hour,
        endMinutes: endTime.minute,
        homeLat: home.lat,
        homeLng: home.lng,
        included: JSON.stringify(getIncluded())
    };
    if (params.endHours === 24){
        params.endHours = 23;
        params.endMinutes = 59;
    }
    console.log(params);
    console.log(getIncluded());
    if (getIncluded().length == 0) {
      alert("There are no profitable jobs! Please select a different time range or different categories.");
    } else {
      $.post("/path", params, function (responseJSON) {
          path = JSON.parse(responseJSON);
          if (path.length == 0) {
            alert("There are no profitable jobs! Please select a different time range or different categories.");
          } else {
            directions();
            drawChart();
            drawProfitBox();
          }
      });
    }
}

function directions() {
    if (directionsDisplay != undefined) {
      // if directions are already being displayed, get rid of them
      directionsDisplay.setMap(null);
    }
    var directionsService = new google.maps.DirectionsService();
    var waypoints = [];
    for (var i = 0; i < path.length; i++) {
        waypoints.push({
            location: jobs[path[i]],
            stopover: true
        });
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

    directionsService.route(directionsRequest, function (result, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay = new google.maps.DirectionsRenderer({
              map: map,
              directions: result,
              draggable: true,
              suppressMarkers: true
            });
        }
    });

    showMarkers(path);
    circlePathMarkers(path);
}

function circlePathMarkers() {
    for (i = 0; i < path.length; i++) {
        var circle = new google.maps.Circle({
          strokeColor: colors[i],
          strokeOpacity: 0.8,
          strokeWeight: 2,
          fillColor: colors[i],
          map: map,
          center: {lat: jobs[path[i]].lat, lng: jobs[path[i]].lng},
          radius: 2000
        });

        circles.push(circle);
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

function clearDirections() {
    clearTimeline();

    for(i = 0; i < circles.length; i++) {
        circles[i].setMap(null);
    }

    if ((profitBox != undefined) && (profitBox.parentNode != null)) {
      profitBox.parentNode.removeChild(profitBox);
    }
    if (directionsDisplay != undefined) {
      // if directions are already being displayed, get rid of them
      directionsDisplay.setMap(null);
    }
    
    showMarkers(getIncluded());
}

function hideAllMarkers() {
    for (var key in markers) {
        markers[key].setMap(null);
    }
}

function showMarkers(idArray) {
    hideAllMarkers();
    for(var i = 0; i < idArray.length; i++) {
        var id = idArray[i];
        markers[id].setMap(map);
    }
}

function updateMarkers() {
  for (var key in markers) {
    var included = include[key];
    var marker = markers[key];
    var markerMap = marker.getMap();
    if (included && markerMap == null) {
      // if the marker should be displayed, but it isn't
      marker.setMap(map); //display the marker
    } else if (!included && markerMap == map) {
      // if the marker shouldn't be displayed, but it is
      marker.setMap(null); // remove the marker
    }
  }
}


function getHomeLatLng() {
    return {
        lat: homeMarker.getPosition().lat(),
        lng: homeMarker.getPosition().lng()
    };
}

function getIncluded() {
    var included = [];
    for (var key in include) {
        if (include[key]) {
          included.push(key);
        }
    }
    return included;
}

function localtimeToString(localTime) {
  var str = toDate(localTime).toLocaleTimeString();
  var len = str.length;
  last3chars = str.substring(len - 3, len);
  str = str.substring(0, len - 6);
  str = str + last3chars;
  return str;
}

function jobInfoHTML(job) {
  var style = "p.title {font-weight: bold;}"
  + "p {"
  + "  margin: 10px 10px;"
  + "  text-align: center;"
  + "  font-family: Calibri, Candara, Segoe, 'Segoe UI', Optima, Arial, sans-serif;"
  + "  font-size: 14px;"
  + "  font-style: normal;"
  + "  font-variant: normal;"
  + "  font-weight: 400;"
  + "  line-height: 23px;"
  + "}";
  var startTimeString = localtimeToString(job.start);
  var endTimeString = localtimeToString(job.end);
  
  return "<style>" + style + "</style>"
      + "<p class=\"title\">" + job.title + "</p>"
      + "<p>Category: " + job.category + "</p>"
      + "<p>Start: " + startTimeString + "</p>"
      + "<p>End: " + endTimeString + "</p>"
      + "<p>Profit: $" + job.profit + "</p>"
      + "<p>Contact: " + generateRandomPhoneNumber() + "</p>";
}

function generateRandomPhoneNumber(){
    var number = "(401) ";
    number += pad((Math.floor(Math.random() * 999) + 1), 3);
    number += "-";
    number += pad((Math.floor(Math.random() * 9999) + 1), 4);
    return number;
}

function pad(num, size) {
    var s = num+"";
    while (s.length < size) s = "0" + s;
    return s;
}

function drawProfitBox() {
  var span = document.createElement('span');
  span.className = "label label-success";
  span.innerHTML = "Profit: $" + getProfit();
  
  var h2 = document.createElement('h2');
  h2.appendChild(span);
  
  profitBox = document.createElement('div');
  profitBox.appendChild(h2);

  map.controls[google.maps.ControlPosition.TOP_CENTER].push(profitBox);
}

function getProfit() {
  var profit = 0;
  for (var i = 0; i < path.length; i++) {
    profit += jobs[path[i]].profit;
  }
  return profit;
}

function clearTimeline() {
  path = [];
  drawChart();
}