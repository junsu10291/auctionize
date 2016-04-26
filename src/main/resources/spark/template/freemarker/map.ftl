
<!DOCTYPE html>
<html>
  <head>
    <title>Simple Map</title>
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/chosen.min.css">
  </head>
  <body>
    <div id="pageContainer">
    	<div id="navBar">
    		<div class="navButton"><a class="active" href="#home">Home</a></div>
    	</div>
    	<div class="container">
    	    <div id ="sidebar">
            <fb:login-button scope="public_profile,email" onlogin="checkLoginState();" data-auto-logout-link="true" data-size="xlarge"></fb:login-button>
            <img id="profilePicture">
            <div id="userName"></div>
            <div id="jobCategory">
              <select class="chzn-select" multiple="true" name="faculty">
                <option value="landscaping">Landscaping</option>
                <option value="cleaner">Cleaner</option>
                <option value="courier">Courier</option>
                <option value="painting">Painting</option>
                <option value="modeling">Modeling</option>
                <option value="moving">Moving</option>
                <option value="construction">Construction</option>
                <option value="pet">Pet</option>
                <option value="babysitting">Babysitting</option>
                <option value="assembler">Assember</option>
                <option value="misc">Miscellaneous</option>
              </select>
            </div>  
          </div>
          <div id ="togglebar"></div>
        <div id="mapContainer">
          <div id="timeline"></div>
          <div id="map"></div>
        </div>
	   </div>

    <script src="js/jquery-2.1.1.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="js/timeline.js"></script>
    <script src="js/initMap.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAP_cgI8zBhAlb5qicByyn9vNjHzU0puYY&callback=initMap"
    async defer></script>
    <script src="js/login.js"></script>
    <script type="text/javascript" src="js/chosen.jquery.min.js"></script>
    <script src="js/home.js"></script>

  </body>
</html>


