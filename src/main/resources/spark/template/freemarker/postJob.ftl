
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
        height: 90%;
        background-color: #1a1a1a;
        color: white;
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
      #postForm{
        width: 50%;
        height: 75%;
        padding-left: 25%;
        padding-right: 25%;
        padding-top: 12.5%; 
        padding-bottom: 12.5%;
      }
      #postForm > *{
        text-align: center;
      }
    </style>
  </head>
  <body>
    <ul id="navBar">
      <li class="navButton"><a class="active" href="/map">Home</a></li>
    <li class="navButton"><a href="#about">About</a></li>
    <li class="navButton"><a href="/post">Post</a></li>
    <li class="navButton"> 
      <fb:login-button scope="public_profile,email" onlogin="checkLoginState();" data-auto-logout-link="true">
        </fb:login-button>
    </li>
    <li class="navButton"><div id="status"></div></li>
    </ul>
    <div class="container">
      <div id="postForm">
        <h1>Post your job!</h2>
        <div id="jobTitle">
          <span>Job Description:</span>
          <input id="title" type="text"></input>
        </div>
        <div id="categoriesDiv">
          <span>Categories:</span>
          <select id="categories" class="chzn-select" name="faculty">
            <option value="" disabled selected>Select job type</option>
            <option value="YARD">Landscaping</option>
            <option value="CLEAN">Cleaner</option>
            <option value="COURIER">Courier</option>
            <option value="PAINT">Painting</option>
            <option value="ACT">Modeling</option>
            <option value="MOVE">Moving</option>
            <option value="CONSTRUCT">Construction</option>
            <option value="PET">Pet</option>
            <option value="BABY">Babysitting</option>
            <option value="HANDY">Handywork</option>
            <option value="DRIVE">Driving</option>
            <option value="ASSEMBLE">Assember</option>
            <option value="MISC">Miscellaneous</option>
          </select>
        </div>
        <div id="location">
          <span>Latitude: </span>
          <span id="lat"></span>
          <span> | Longitude: </span>
          <span id="lon"></span>
        </div>
        <div id="times">
          <select id="startTime">
            <option value="" disabled selected>Start</option>
            <option value="9:00">9:00</option>
            <option value="9:30">9:30</option>
            <option value="10:00">10:00</option>
            <option value="10:30">10:30</option>
            <option value="11:00">11:00</option>
            <option value="11:30">11:30</option>
            <option value="12:00">12:00</option>
            <option value="12:30">12:30</option>
            <option value="1:00">1:00</option>
            <option value="1:30">1:30</option>
            <option value="2:00">2:00</option>
            <option value="2:30">2:30</option>
            <option value="3:00">3:00</option>
            <option value="3:30">3:30</option>
            <option value="4:00">4:00</option>
            <option value="4:30">4:30</option>
            <option value="5:00">5:00</option>
            <option value="5:30">5:30</option>
            <option value="6:00">6:00</option>
            <option value="6:30">6:30</option>
            <option value="7:00">7:00</option>
          </select>
          <select id="endTime">
            <option value="" disabled selected>End</option>
            <option value="10:00">10:00</option>
            <option value="10:30">10:30</option>
            <option value="11:00">11:00</option>
            <option value="11:30">11:30</option>
            <option value="12:00">12:00</option>
            <option value="12:30">12:30</option>
            <option value="1:00">1:00</option>
            <option value="1:30">1:30</option>
            <option value="2:00">2:00</option>
            <option value="2:30">2:30</option>
            <option value="3:00">3:00</option>
            <option value="3:30">3:30</option>
            <option value="4:00">4:00</option>
            <option value="4:30">4:30</option>
            <option value="5:00">5:00</option>
            <option value="5:30">5:30</option>
            <option value="6:00">6:00</option>
            <option value="6:30">6:30</option>
            <option value="7:00">7:00</option>
            <option value="7:30">7:30</option>
            <option value="8:00">8:00</option>
          </select>
        </div>
        <div id="payDiv">
          <span>Pay($):</span><input id="pay" type="number" min="0.01" step="0.01" max="10000">
        </div>
        <input id="jobSubmit" type="button" value="Submit job!"><br>
        <span id="error_msg"></span>
      </div>
    </div>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/login.js"></script>
    <script src="js/postJob.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAP_cgI8zBhAlb5qicByyn9vNjHzU0puYY&callback=mylocation"
    async defer></script>
  </body>
</html>


