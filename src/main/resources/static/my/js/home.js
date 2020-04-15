var societyCtx = 'society_chart';
var userCtx = "user_chart";
var activityCtx = "activity_chart";
var bgColorSample = [
    'rgba(255, 99, 132, 1)',
    'rgba(54, 162, 235, 1)',
    'rgba(255, 206, 86, 1)',
    'rgba(75, 192, 192, 1)',
    'rgba(153, 102, 255, 1)',
    'rgba(255, 159, 64, 1)'
];

var userChart;
var societyChart;
var activityChart;

$(function () {
    $("#sidebar_home").addClass("active");
    loadChart("/home/societyChart",societyCtx,societyChart);
    loadChart("/home/userChart",userCtx,userChart);
    loadChart("/home/activityChart",activityCtx,activityChart);
});


function loadChart(url,ctx,chart) {
    $.ajax({
        url:url,
        success:function (data) {
            var currentBgColors = [];
            for (var i = 0; i < data.names.length; i++) {
                currentBgColors.push(bgColorSample[i % 6])
            }
            chart = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: data.names,
                    datasets: [{
                        data: data.counts,
                        backgroundColor: currentBgColors
                    }]
                }
            });
        }
    })
}