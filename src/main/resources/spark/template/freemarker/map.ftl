
<!DOCTYPE html>
<html>
  <head>
    <title>Simple Map</title>
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
    <style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      .container { 
        position:absolute; 
        width: 100%;
        height: 100%;
      }
      #sidebar {
        position:absolute;
        top:0; bottom:0; left:0;
        width:20%;
        background:#000;
      }  
      #map {
		width:80%;
		height:100%;
		float: right;
      }
    </style>
  </head>
  <body>
  	<div class="container">
  	    <div id ="sidebar"></div>
    	<div id="map"></div>
    </div>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/initMap.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAP_cgI8zBhAlb5qicByyn9vNjHzU0puYY&callback=initMap"
    async defer></script>
  </body>
</html>


