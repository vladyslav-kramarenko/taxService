$("#login_form").validate();
$(function () {
    $("#settings").button({
        text: true,
        icons: {
            primary: "ui-icon-gear"
        }
    });
    $("#users").button({
        text: true,
        icons: {
            primary: "ui-icon-person"
        }
    });
    $("#login").button({
        text: true,
        icons: {
            primary: "ui-icon-power"
        }
    });
    $("#logout").button({
        text: true,
        icons: {
            primary: "ui-icon-power"
        }
    });
    $("#register").button({
        text: true,
        icons: {
            primary: "ui-icon-key"
        }
    });
    $("#reports").button({
        text: true,
        icons: {
            primary: "ui-icon-script"
        }
    });
    $("#lang").buttonset();
    $("#reg").buttonset();
});