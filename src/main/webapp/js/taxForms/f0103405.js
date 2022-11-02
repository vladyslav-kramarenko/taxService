checkBoxFunction("formType");
checkBoxFunction("period");
checkBoxFunction("period1");

const calculate8 = function () {
    let variables = [];
    variables[0] = getNumber('R001G3');
    variables[1] = getNumber('R002G3');
    variables[2] = getNumber('R003G3');
    variables[3] = getNumber('R004G3');
    variables[4] = getNumber('R005G3');
    variables[5] = getNumber('R006G3');
    variables[6] = getNumber('R007G3');

    document.getElementById('R008G3').value = getSum(variables);
}

const calculate9 = function () {
    let variables = [];

    variables[0] = getNumber('R002G3');
    variables[1] = getNumber('R004G3');
    variables[2] = getNumber('R007G3');

    document.getElementById('R009G3').value = (getSum(variables) * 0.15).toFixed(2);
    calculate12();
}

const calculate10 = function () {
    document.getElementById('R010G3').value = getSum([getNumber('R005G3')]) * 0.03;
    calculate12();
}

const calculate11 = function () {
    document.getElementById('R011G3').value = (getSum([getNumber('R006G3')]) * 0.05).toFixed(2);
    calculate12();
}

const calculate12 = function () {
    let variables = [];
    variables[0] = getNumber('R009G3');
    variables[1] = getNumber('R010G3');
    variables[2] = getNumber('R011G3');
    document.getElementById('R012G3').value = getSum(variables);
    calculate14();
}

const calculate14 = function () {
    document.getElementById('R014G3').value = (getNumber('R012G3') - getNumber('R013G3')).toFixed(2);
}

const listener247 = function () {
    calculate8();
    calculate9();
}
const listener5 = function () {
    calculate8();
    calculate10();
}
const listener6 = function () {
    calculate8();
    calculate11();
}

const init = function () {
    console.log("init functions")
    document.getElementById('R001G3').addEventListener('input', calculate8);
    document.getElementById('R002G3').addEventListener('input', listener247);
    document.getElementById('R003G3').addEventListener('input', calculate8);
    document.getElementById('R004G3').addEventListener('input', listener247);
    document.getElementById('R005G3').addEventListener('input', listener5);
    document.getElementById('R006G3').addEventListener('input', listener6);
    document.getElementById('R007G3').addEventListener('input', listener247);
    document.getElementById('R013G3').addEventListener('input', calculate14);
}
init();
console.log("finish js")

// // onload = function () {
//     document.getElementById('R001G3').addEventListener('input', calculate8);
//     document.getElementById('R002G3').addEventListener('input', listener247);
//     document.getElementById('R003G3').addEventListener('input', calculate8);
//     document.getElementById('R004G3').addEventListener('input', listener247);
//     document.getElementById('R005G3').addEventListener('input', listener5);
//     document.getElementById('R006G3').addEventListener('input', listener6);
//     document.getElementById('R007G3').addEventListener('input', listener247);
//     document.getElementById('R013G3').addEventListener('input', calculate14);
// // }