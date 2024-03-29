function drawChart() {
    var container = document.getElementById('timeline');
    chart = new google.visualization.Timeline(container);
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn({
        type: 'string',
        id: 'Position'
    });
    dataTable.addColumn({
        type: 'string',
        id: 'Name'
    });
    dataTable.addColumn({type: 'string', role: 'tooltip', p: {'html': true}});
    dataTable.addColumn({
        type: 'date',
        id: 'Start'
    });
    dataTable.addColumn({
        type: 'date',
        id: 'End'
    });
    var rows = [];
    var label = "Time";
    if (path.length == 0) {
        var start = new Date(0, 0, 0, 8, 0, 0);
        var end = new Date(0, 0, 0, 24, 0, 0);
        var row = [label, "Your jobs will appear here",
                   "Click 'Day of Jobs' to create a schedule!", start, end];
        rows.push(row);
    } else {
        for (var i = 0; i < path.length; i++) {
            var jobId = path[i];
            var job = jobs[jobId];
            var row = [label, job.title, jobInfoHTML(job), toDate(job.start), toDate(job.end)];
            rows.push(row);
        }
    }
    var fixedRow = ["Fixed", "Available Hours", "", new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 24, 0, 0)];
    rows.push(fixedRow);
    dataTable.addRows(rows);
    var options = {
        timeline: {showRowLabels: false},
        tooltip: {isHtml: true}
    };
    chart.draw(dataTable, options);
    //  google.visualization.events.addListener(chart, 'select', function(e) {
    //    var row = chart.getSelection()[0].row;
    //    document.getElementById("sidebar").innerHTML = jobs[ids[row]];
    //  });
}

function toDate(localTime) {
    return new Date(0, 0, 0, localTime.hour, localTime.minute, 0);
}

