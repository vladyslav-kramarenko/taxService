$(document).ready(function () {
    $("#registration_form_individual").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            password: {
                required: true
            },
            code: {
                required: true,
                numeric: true
            },
            first_name: {
                required: true
            },
            last_name: {
                required: true
            },
            phone: {
                // tel: true
                numeric: true
            }
        },

        messages: {
            email: {
                required: "Please enter email",
                email: "Please enter a valid email address"
            }, company_name: {
                required: "Please enter official company name",
            }, email: {
                required: "Please enter email",
                email: "Please enter a valid email address"
            },
            password: {
                required: "Please enter password"
            }
        }
    });
    $("#registration_form_company").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            password: {
                required: true
            },
            code: {
                required: true,
                numeric: true
            },
            company_name: {
                required: true
            },
            phone: {
                required: true,
                // tel: true
                numeric: true
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
    $("#regBtnComp").button({});
    $("#regBtnInd").button({});
});