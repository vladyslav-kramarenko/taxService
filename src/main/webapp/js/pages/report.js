const showComment = function (element) {
    if (element.value == 4) {
        document.getElementById("comment").style.display = "inline-block";
    } else {
        document.getElementById("comment").style.display = "none";
    }
};
$(function () {
    $("#reportButtons").buttonset();
    $("#updateStatusBtn").button({});
    $("#loadBtn").button({});
    $("#saveReportBtn").button({});
    $("#sendReportBtn").button({});
    $("#downloadXnlBtn").button({});
    // $("#statusSelect").selectmenu({});
});