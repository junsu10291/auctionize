<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/main.css">
    
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/buttons.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAP_cgI8zBhAlb5qicByyn9vNjHzU0puYY&libraries=drawing&callback=initMap" async defer></script>
    <script src="js/initMap.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="js/timeline.js"></script>
    <script src="js/updateInclude.js"></script>
    
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
            <li><a href="/about">About</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
    <div class="container" id="content-container">
    	
		<div class="row" id="timeline-row">
    		<div id="timeline"></div>
    	</div>
    	<div class="row" id="slider-row">
    		<input id="slider" type="text" data-slider-tooltip="hide"/><br>
    	</div>
    	<div class="row" id="dayofjobs-row">
    		<button type="button" class="btn btn-primary btn-lg btn-block" id="dayofjobs-btn" onclick="this.blur();getPath();">Day of Jobs from 8:00 AM to 12:00 AM</button>
    	</div>
    	<div class="row" id="map-row">
    		<div id="map"></div>
    	</div>
    	
    	<div class="row" id="categories-row">
    		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 btn-col" id="btn-col1"> 
    			<button type="button" class="btn btn-success btn-block category-btn active" id="ACT" onclick="this.blur();" data-toggle="button" aria-pressed="true">ACT</button>
    			<button type="button" class="btn btn-success btn-block category-btn active" id="DRIVE" onclick="this.blur();" data-toggle="button" aria-pressed="true">DRIVE</button>
    			<button type="button" class="btn btn-success btn-block category-btn active" id="YARD" onclick="this.blur();" data-toggle="button" aria-pressed="true">YARD</button>
    		</div>
    		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 btn-col" id="btn-col2"> 
    			<button type="button" class="btn btn-success btn-block category-btn active" id="ASSEMBLE" onclick="this.blur();" data-toggle="button" aria-pressed="true">ASSEMBLE</button>
    			<button type="button" class="btn btn-success btn-block category-btn active" id="HANDY" onclick="this.blur();" data-toggle="button" aria-pressed="true">HANDY</button>
    		</div>
    		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 btn-col" id="btn-col3"> 
    			<button type="button" class="btn btn-success btn-block category-btn active" id="BABY" onclick="this.blur();" data-toggle="button" aria-pressed="true">BABY</button>
    			<button type="button" class="btn btn-success btn-block category-btn active" id="MISC" onclick="this.blur();" data-toggle="button" aria-pressed="true">MISC</button>
    		</div>
    		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 btn-col" id="btn-col4"> 
    			<button type="button" class="btn btn-success btn-block category-btn active" id="CLEAN" onclick="this.blur();" data-toggle="button" aria-pressed="true">CLEAN</button>
    			<button type="button" class="btn btn-success btn-block category-btn active" id="MOVE" onclick="this.blur();" data-toggle="button" aria-pressed="true">MOVE</button>
    		</div>
    		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 btn-col" id="btn-col5"> 
    			<button type="button" class="btn btn-success btn-block category-btn active" id="CONSTRUCT" onclick="this.blur();" data-toggle="button" aria-pressed="true">CONSTRUCT</button>
    			<button type="button" class="btn btn-success btn-block category-btn active" id="PAINT" onclick="this.blur();" data-toggle="button" aria-pressed="true">PAINT</button>
    		</div>
    		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 btn-col" id="btn-col6"> 
    			<button type="button" class="btn btn-success btn-block category-btn active" id="COURIER" onclick="this.blur();" data-toggle="button" aria-pressed="true">COURIER</button>
    			<button type="button" class="btn btn-success btn-block category-btn active" id="PET" onclick="this.blur();" data-toggle="button" aria-pressed="true">PET</button>
    		</div>
    	</div>
    	
    </div>
	    
	    
  </body>
</html>



