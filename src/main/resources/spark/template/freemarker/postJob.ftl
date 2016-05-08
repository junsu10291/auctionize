<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>Post a Job!</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/main.css">
    <script src="js/jquery-2.1.1.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/7.0.2/bootstrap-slider.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/7.0.2/css/bootstrap-slider.min.css"></link>
  
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/">Working Weekend</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="/post">Post a Job</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
    <div class="container" id="content-container">
        <h1 class="text-center">Post your job!</h2>
        <div id="postForm">
          <div class="form-group" id="jobTitle">
            <label for="title">Job Description</label>
            <input id="title" type="text" class="form-control" placeholder="e.g. assemble my IKEA desk">
          </div>

        <div class="form-group"  id="categoriesDiv">
          
          <label for="categories">Categories</label>
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
        <div class="form-group" id="location">
          <label for="lat">Latitude</label>
          <input id="lat" type="text" class="form-control">
          <label for="lon">Longitude</label>
          <input id="lon" type="text" class="form-control">
        </div>
        <div class="form-group" id="times">
          <label for="startTime">Start</label>
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
          </select><br>
          <label for="endTime">End</label>
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
        <div class="form-group" id="payDiv">
          <label for="pay">Pay</label>
          <form class="form-inline">
            <div class="form-group">
              <label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
              <div class="input-group">
                <div class="input-group-addon">$</div>
                <input id="pay" type="number" min="0.01" step="0.01" max="10000" class="form-control" placeholder="Amount">
              </div>
            </div>
          </form>
        </div>
        <div class="form-group" id="submission">
          <button id="jobSubmit" type="submit" class="btn btn-default">Submit</button><br>
          <p class="help-block" id="error_msg"></p>
        </div>
      </div>
    </div>
    <script src="js/postJob.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAP_cgI8zBhAlb5qicByyn9vNjHzU0puYY&callback=mylocation"
    async defer></script>
  </body>
</html>



