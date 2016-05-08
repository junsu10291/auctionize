function checkAndPost(){
  if($("#title").val() == ""){
    $("#error_msg").text("Enter a job title");
    $("#error_msg").show();
    return;
  }
  
  var selected = false;
  for(var i=1; i < document.getElementById("categories").options.length; i++){
    if (document.getElementById("categories").options[i].selected){
      selected = true;
    }
  }
  if(selected == false){
    $("#error_msg").text("Select a job category");
    $("#error_msg").show();
    return;
  }
  
  var start = false;
  for(var i=1; i < document.getElementById("startTime").options.length; i++){
    if (document.getElementById("startTime").options[i].selected){
      start= true;
    }
  }
  if(start == false){
    $("#error_msg").text("Select a start time");
    $("#error_msg").show();
    return;
  }

  var end = false;
  for(var i=1; i < document.getElementById("endTime").options.length; i++){
    if (document.getElementById("endTime").options[i].selected){
      end = true;
    }
  }
  if(end == false){
    $("#error_msg").text("Select an end time");
    $("#error_msg").show();
    return;
  }

  if($("#pay").val() == ""){
    $("#error_msg").text("Enter a pay");
    $("#error_msg").show();
    return;
  }
  var postParams = {
    title : $("#title").val(),
    type : $('select[id=categories]').val(),
    lat : $("#lat").val(),
    lon : $("#lon").val(),
    start : $('select[id=startTime]').val(),
    end : $('select[id=endTime]').val(),
    pay : $('#pay').val()
  }
  console.log(postParams);
  
  $.post("/postJob",postParams,function(responseObject){
    var responseJSON = JSON.parse(responseObject);
    if(responseJSON=="success"){
      console.log("reload now");
      location.reload();
    }
  });
}

function mylocation() {
  var loc = {lat: 41.826130, lng: -71.403};
  
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      loc = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };
      $("#lat").val(loc.lat);
      $("#lon").val(loc.lng);
    }, function() {});
  }
}

$("#jobSubmit").on('click', function(){
  console.log("submitted!");
  $("#error_msg").hide();
  $("#error_msg").text("");
  checkAndPost();
  console.log($("#error_msg").text());
});


$(document).ready(function(){
   console.log("ready!!!!!!!");
   $("#error_msg").hide();   
});