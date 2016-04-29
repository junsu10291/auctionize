
<!DOCTYPE html>
<html>
  <head>
    <title>Job Finder</title>
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/chosen.min.css">
  </head>
  <body>

    <div id="tutorialCover"></div>
    <div id="tutorialIntroWrapper">
      <div id="tutorialIntro">
          <a href="#" class="tutButton" onclick="beginTut()">Start Tutorial</a>
          <a href="#" id="skipTut" class="tutButton" onclick="skipTut()">Skip Tutorial</a>
      </div>
    </div>
    <div class="timelineBubble upBubble bubble"> This is the timeline blahblahblah woohoo!!!! </div>
    <div class="categoriesBubble upBubble bubble"> This is the categories bubble :-) ㅗㅗㅗㅗㅗ </div>
    <div class="dayofJobsBubble downBubble bubble">Day of Jobs Description</div>

    <div id="pageContainer">
    	<div id="navBar">
    		<div class="navButton"><a class="active" href="#home">Home</a></div>
	        <div class="navButton" id="post"><a href="/post">Post</a></div>
	        <div class="logout"> 
	          <fb:login-button scope="public_profile,email" onlogin="checkLoginState();" data-auto-logout-link="true" data-size="xlarge"></fb:login-button>
	        </div>
    	</div>
    	<div class="container">
    	    <div id ="sidebar"> 
            <img id="profilePicture">
            <div id="userName"></div>
            <div id="jobCategory">
              <select class="chzn-select" multiple="true" name="faculty">
                <option value="YARD" selected>YARD</option>
                <option value="CLEAN" selected>CLEAN</option>
                <option value="COURIER" selected>COURIER</option>
                <option value="PAINT" selected>PAINT</option>
                <option value="ACT" selected>ACT</option>
                <option value="MOVE" selected>MOVE</option>
                <option value="CONSTRUCT" selected>CONSTRUCT</option>
                <option value="HANDY" selected> HANDY</option>
                <option value="DRIVE" selected> DRIVE </option>
                <option value="PET" selected>PET</option>
                <option value="BABY" selected>BABY</option>
                <option value="ASSEMBLE" selected>ASSEMBLE</option>
                <option value="MISC" selected>MISC</option>
              </select>
            </div>  

            <div id="dayofjobsButton">
              <a href="#" class="myButton" onclick="getPath()">Day of Jobs</a>
            </div>
            
          </div>
          <div id ="togglebar"></div>
        <div id="mapContainer">
          <div id="floatingPanel">
            <input onclick="removeRegion();" type=button value="Remove Region">
          </div>
          <div id="timeline"></div>
          <div id="map"></div>
        </div>
	   </div>
   </div>


    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/initMap.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAP_cgI8zBhAlb5qicByyn9vNjHzU0puYY&libraries=drawing&callback=initMap"
    async defer></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="js/timeline.js"></script>
    <script src="js/login.js"></script>
    <script type="text/javascript" src="js/chosen.jquery.min.js"></script>
    <script src="js/home.js"></script>
  </body>
</html>


