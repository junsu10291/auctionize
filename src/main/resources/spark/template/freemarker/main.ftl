<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/main.css">
  </head>
  <body>
    <div>
    <h1>Auctionize Login</h1>
    <fb:login-button scope="public_profile,email" onlogin="checkLoginState();" data-auto-logout-link="true">
    </fb:login-button>
    <div id="status"></div>

    <script src="js/main.js"></script>
    <script src="js/login.js"></script>
  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>
