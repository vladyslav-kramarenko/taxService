const checkBoxFunction = function (className) {
    var checkboxes = document.getElementsByClassName(className);
    console.log("here")
    for (let i = 0; i < checkboxes.length; i++) {
        checkboxes[i].onclick = function () {
            if (document.getElementById(this.id).checked == true) {
                for (let j = 0; j < checkboxes.length; j++) {
                    checkboxes[j].checked = false;
                }
                document.getElementById(this.id).checked = true;
            }
        }
    }
}
const getNumber = function (name) {
    return parseFloat(document.getElementById(name).value)
}

const getSum = function (variables) {
    var res = 0;
    for (i = 0; i < variables.length; i++) {
        if (!isNaN(variables[i])) {
            res += variables[i];
        }
    }
    return res.toFixed(2);
}

// const submitForm = function (command) {
//     document.getElementById("formCommand").value = command;
//     document.report_form.submit();
// }