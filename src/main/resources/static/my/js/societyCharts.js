$(function () {
    $("#sidebar_society_management").addClass("active");
});
var ctx = 'society_chart';
var lineCtx = "lineChart";
var polarCtx = "polarArea";
var bgColorSample = [
    'rgba(255, 99, 132, 1)',
    'rgba(54, 162, 235, 1)',
    'rgba(255, 206, 86, 1)',
    'rgba(75, 192, 192, 1)',
    'rgba(153, 102, 255, 1)',
    'rgba(255, 159, 64, 1)'
];
var firstLoad = true;
var chart;
var lineChart;
var polarChart;

$(function () {
    loadChart("society/countByCollege", "学 院 分 布 统 计");
});

function loadChart(url, chartName) {
    $("#chart_name").text(chartName);
    url = "/" + url + "/";
    $.ajax({
        url: url + $("#society_id").val(),
        success: function (data) {
            var currentBackgroundColors = [];
            var currentBgColors = [];
            for (var i = 0; i < data.names.length; i++) {
                currentBgColors.push(bgColorSample[i % 6])
            }
            if (firstLoad) {
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
                polarChart = new Chart(polarCtx,{
                    type: 'polarArea',
                    data: {
                        labels: data.names,
                        datasets: [{
                            data: data.counts,
                            backgroundColor: currentBgColors
                        }]
                    }
                });
                lineChart = new Chart(lineCtx, {
                    type: 'bar',
                    data: {
                        labels: data.names,
                        datasets: [{
                            label: '折线图',
                            data: data.counts,
                            type: "line",
                            fill: false,
                            lineTension: 0,
                            backgroundColor: 'rgba(153, 102, 255, 1)',
                            borderColor: 'rgba(153, 102, 255, 1)',
                            borderWidth: 2
                        }, {
                            label: '条形图',
                            data: data.counts,
                            backgroundColor: currentBgColors
                        }]
                    },
                    options: {
                        scales: {
                            yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                        }
                    }
                })
                firstLoad = false;
            } else {
                chart.data = {
                    labels: data.names,
                    datasets: [{
                        data: data.counts,
                        backgroundColor: currentBgColors
                    }]
                };
                lineChart.data = {
                    labels: data.names,
                    datasets: [{
                        label: '折线图',
                        data: data.counts,
                        type: "line",
                        fill: false,
                        lineTension: 0,
                        backgroundColor: 'rgba(153, 102, 255, 1)',
                        borderColor: 'rgba(153, 102, 255, 1)',
                        borderWidth: 2
                    }, {
                        label: '条形图',
                        data: data.counts,
                        backgroundColor: currentBgColors
                    }]
                };
                polarChart.data = {
                    labels: data.names,
                    datasets: [{
                        data: data.counts,
                        backgroundColor: currentBgColors
                    }]
                };
                chart.update();
                polarChart.update();
                lineChart.update();
            }
        }
    });
}

function gotoTable() {
    window.location.href = "/society/queryUserBySocietyId/" + $("#society_id").val();
}