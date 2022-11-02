$(document).ready(function() {
    $("#login_form").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            password: {
                required: true
            }
        },

        messages: {
            email: {
                required: "Please enter email",
                email: "Please enter a valid email address"
            },
            password: {
                required: "Please enter password"
            }
        }
    });
});
$(function () {
    $("#log").buttonset();
    $("#loginBtn").button({});
    $("#registerBtn").button({});
});