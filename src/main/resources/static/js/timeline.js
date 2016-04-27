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
  var pathTitle = "Most Profitable Path"
  for (var i = 0; i < path.length; i++) {
    var jobId = path[i];
    var job = jobs[jobId];
    var row = [pathTitle, job.title, toDate(job.start), toDate(job.end)];
    rows.push(row);
    ids.push(jobId);
  }
  dataTable.addRows(rows);

  chart.draw(dataTable);
//  google.visualization.events.addListener(chart, 'select', function(e) {
//    var row = chart.getSelection()[0].row;
//    document.getElementById("sidebar").innerHTML = jobs[ids[row]];
//  });
}

function toDate(localTime) {
  return new Date(0, 0, 0, localTime.hour, localTime.minute, 0);
}