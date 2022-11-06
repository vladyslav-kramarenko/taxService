$("#login_form").validate({
    messages: {
        email: {
            required: "Please enter 1 email",
            email: "Please enter a valid email address"
        },
        password: {
            required: "Please enter password"
        }
    }
});
$(function () {
    $("#log").buttonset();
    $("#loginBtn").button({});
    $("#registerBtn").button({});
});