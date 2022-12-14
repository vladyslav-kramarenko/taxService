function sortTable(column, header) {
    var table, rows, switching, i, x, y, shouldSwitch;
    table = document.getElementById("reportTable");
    switching = true;
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[column];
            y = rows[i + 1].getElementsByTagName("TD")[column];
            // Check if the two rows should switch place:
            if (header.value == 'asc') {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
    if (header.value == 'asc') {
        header.value = 'desc';
    } else {
        header.value = 'asc';
    }
}
$(function () {
    $(".reportActionEditButton").button({
        text: false,
        icons: {
            primary: "ui-icon-document"
        }
    });
    $(".reportActionCancelButton").button({
        text: false,
        icons: {
            primary: "ui-icon-closethick"
        }
    });
    $(".reportActionTrashButton").button({
        text: false,
        icons: {
            primary: "ui-icon-trash"
        }
    });
    $(".reportActionButton").button({
        text: true,
        icons: {
            primary: "ui-icon-gear"
        }
    });
    $("#newReportBtn").button({});
    $("#newReportType").selectmenu({});
});

