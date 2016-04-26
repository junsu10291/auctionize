
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
        margin: 0 auto; 
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
       <input type="text" placeholder="Enter job title"> </input>
       <select class="chzn-select" name="faculty">
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
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/login.js"></script>
  </body>
</html>


