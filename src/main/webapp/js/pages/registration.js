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
function openTab(tabName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName+"Btn").className += " active";
    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(tabName).style.display = "inline-block";
}