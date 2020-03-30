$(function () {
    $("#sidebar_society_management").addClass("active");
});
var ctx = 'society_chart';
var backgroundColorsSample = [
    'rgba(255, 99, 132, 0.2)',
    'rgba(54, 162, 235, 0.2)',
    'rgba(255, 206, 86, 0.2)',
    'rgba(75, 192, 192, 0.2)',
    'rgba(153, 102, 255, 0.2)',
    'rgba(255, 159, 64, 0.2)'
];
var borderColorSample = [
    'rgba(255, 99, 132, 1)',
    'rgba(54, 162, 235, 1)',
    'rgba(255, 206, 86, 1)',
    'rgba(75, 192, 192, 1)',
    'rgba(153, 102, 255, 1)',
    'rgba(255, 159, 64, 1)'
];
var firstLoad = true;
var chart;

$(function () {
    loadChart("society/countByCollege", "学院分布统计");
});

function loadChart(url, chartName) {
    url = "/" + url + "/";
    $.ajax({
        url: url + $("#society_id").val(),
        success: function (data) {
            var currentBackgroundColors = [];
            var currentBorderColors = [];
            for (var i = 0; i < data.names.length; i++) {
                currentBackgroundColors.push(backgroundColorsSample[i % 6]);
                currentBorderColors.push(borderColorSample[i % 6])
            }
            if (firstLoad) {
                chart = new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: data.names,
                        datasets: [{
                            data: data.counts,
                            backgroundColor: currentBackgroundColors,
                            borderColor: currentBorderColors,
                            borderWidth: 1
                        }]
                    },
                    options: {
                        title: {
                            text: chartName,
                            display: true,
                            fontSize: 18,
                            padding: 20
                        }
                    }
                });
                firstLoad = false;
            } else {
                chart.data = {
                    labels: data.names,
                    datasets: [{
                        data: data.counts,
                        backgroundColor: currentBackgroundColors,
                        borderColor: currentBorderColors
                    }]
                };
                chart.options.title.text = chartName;
                chart.update();
            }
        }
    });
}

function gotoTable() {
    window.location.href = "/society/queryUserBySocietyId/" + $("#society_id").val();
}