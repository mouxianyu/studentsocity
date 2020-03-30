$(function () {
    $("#sidebar_society_management").addClass("active");
});
function gotoChart() {
    window.location.href="/society/chart/"+$("#society_id").val();
}
