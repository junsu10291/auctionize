var chart;
//jobs = {job1: {id: job1, title: "Job 1"}, job2: {id: job2, title: "Job 2"}};
//var rows = [
//  [ 'Path 1', 'Job 1.1', new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 10, 0, 0)],
//  [ 'Path 1', 'Job 1.2', new Date(0, 0, 0, 10, 30, 0), new Date(0, 0, 0, 13, 0, 0)],
//  [ 'Path 2', 'Job 2.1', new Date(0, 0, 0, 9, 30, 0), new Date(0, 0, 0, 12, 30, 0)],
//  [ 'Path 2', 'Job 2.2', new Date(0, 0, 0, 13, 0, 0), new Date(0, 0, 0, 15, 0, 0)],
//  [ 'Path 3', 'Job 3.1', new Date(0, 0, 0, 9, 0, 0), new Date(0, 0, 0, 12, 0, 0)],
//  [ 'Path 3', 'Job 3.2', new Date(0, 0, 0, 12, 15, 0), new Date(0, 0, 0, 23, 0, 0)]
//];

google.charts.load("current", {packages:["timeline"]});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
  var container = document.getElementById('timeline');
  chart = new google.visualization.Timeline(container);
  var dataTable = new google.visualization.DataTable();
  dataTable.addColumn({ type: 'string', id: 'Position' });
  dataTable.addColumn({ type: 'string', id: 'Name' });
  dataTable.addColumn({ type: 'date', id: 'Start' });
  dataTable.addColumn({ type: 'date', id: 'End' });
  var rows = [];
  var ids = [];
  for (var i = 0; i < paths.length; i++) {
    var path = paths[i];
    var pathTitle = 'Path ' + i;
    for (var j = 0; j < path.length; j++) {
      var jobId = path[j];
      var job = jobs[jobId];
      var row = [pathTitle, job.title, toDate(job.start), toDate(job.end)];
      rows.push(row);
      ids.push(jobId);
    }
  }
  dataTable.addRows(rows);

  chart.draw(dataTable);
  google.visualization.events.addListener(chart, 'select', function(e) {
    var row = chart.getSelection()[0].row;
    console.log(row);
    document.getElementById("sidebar").innerHTML = jobs[ids[row]];
  });
}

function toDate(localTime) {
  return new Date(0, 0, 0, localTime.hours, localTime.minutes, 0);
}
