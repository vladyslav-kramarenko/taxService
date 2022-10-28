checkBoxFunction("formType");
checkBoxFunction("period");
checkBoxFunction("period1");
checkBoxFunction("type");

const calculateTotalColumn1 = function () {
    let variables = [12];
    for (let i = 0; i < 12; i++) {
        variables[i] = getNumber('R09' + (i + 1) + 'G2');
    }

    document.getElementById('R09G2').value = getSum(variables);
}
const calculateTotalColumn3 = function () {
    let variables = [12];
    for (let i = 0; i < 12; i++) {
        variables[i] = getNumber('R09' + (i + 1) + 'G4');
    }

    document.getElementById('R09G4').value = getSum(variables);
}
const calculateColumn3Values = function () {
    for (let i = 1; i < 13; i++) {
        let column1Value = getNumber('R09' + i + 'G2');
        let column2Value = getNumber('R09' + i + 'G3');
        let res = 0;
        if (column1Value && column2Value) {
            res = column1Value / 100 * column2Value;
        }
        document.getElementById('R09' + i + 'G4').value = res;
    }
}

const init = function () {
    for (let i = 1; i < 13; i++) {
        let element = document.getElementById('R09' + (i) + 'G2');
        element.addEventListener('input', calculateTotalColumn1);
        element.addEventListener('input', calculateColumn3Values);
        element.addEventListener('input', calculateTotalColumn3);
        document.getElementById('R09' + (i) + 'G3').addEventListener('input', calculateColumn3Values);
    }
    // document.getElementById('R002G3').addEventListener('input', calculate4);
    // document.getElementById('R003G3').addEventListener('input', calculate4);
    // document.getElementById('R0031G3').addEventListener('input', calculate4);
    // document.getElementById('R0032G3').addEventListener('input', calculate4);
}
init();