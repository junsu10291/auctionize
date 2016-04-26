
<!DOCTYPE html>
<html>
  <head>
    <title>Simple Map</title>
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/chosen.min.css">
    <style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      .container { 
        position:absolute; 
        width: 100%;
        height: 90%;
      }
      #navBar {
        list-style-type: none;
      margin: 0;
      padding: 0;
      overflow: hidden;
      background-color: #333;
      float: top;
      height: 10%;
      }
      .navButton {
      float: left;
      height: 100%;
      width: 150px;
    }
    .navButton a {
      display: block;
      color: white;
      text-align: center;
      padding: 14px 16px;
      text-decoration: none;
      width: 100%;
      height: 100%;
      font-size: 175%
    }
    .navButton a:hover:not(.active) {
      background-color: #111;
    }
    .active {
      background-color: #4CAF50;
    }
      #sidebar {
        position:absolute;
        top:0; bottom:0; left:0;
        width:20%;
        background:#000;
        color:white;
      }  
      #timeline {
    width:80%;
    height: 25%;
    float: right;
      }
      #map {
    width:80%;
    height: 75%;
    float: right;
      }
    </style>
  </head>
  <body>
  	<ul id="navBar">
  		<li class="navButton"><a class="active" href="#home">Home</a></li>
		<li class="navButton"><a href="#about">About</a></li>
		<li id="profile" class="navButton"><a href="#profile">MyProfile</a></li>
		<li class="navButton"> 
			<fb:login-button scope="public_profile,email" onlogin="checkLoginState();" data-auto-logout-link="true" data-size="xlarge">
		    </fb:login-button>
		</li>
		<li class="navButton"><div id="status"></div></li>
  	</ul>
  	<div class="container">

  	    <div id ="sidebar">
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

	     <div id="timeline"></div>
  	   <div id="map"></div>

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


