$(document).ready(function() {
    $("#resetForm").validate({
        rules: {
            email: {
                required: true,
                email: true
            }
        },

        messages: {
            email: {
                required: "Please enter email",
                email: "Please enter a valid email address"
            }
        }
    });
});
$(function () {
    $("#resetBtn").button({});
});