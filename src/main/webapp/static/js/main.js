/**
 * Za svaki dokument s tooltipovima
 */
$( document ).ready(function() {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    })
});

/**
 * Puni delete modale
 */
function popuniModal() {
    let id = this.id.substring(0, this.id.indexOf('_'));
    let title = this.id.substring(this.id.indexOf('_') + 1, this.id.lastIndexOf('_'));
    document.getElementById('delete_id').value = id;
    document.getElementById('delete_text').innerText = title;
}

/**
 * Dodaje predloške u text area
 * @param textToInsert
 * @returns {*|jQuery}
 */
$.fn.insertIntoTextArea = function(textToInsert) {
    return this.each(function () {
        var txt = $(this);
        var cursorPosStart = txt.prop('selectionStart');
        var cursorPosEnd = txt.prop('selectionEnd');
        var v = txt.val();
        var textBefore = v.substring(0,  cursorPosStart);
        var textAfter  = v.substring(cursorPosEnd, v.length);
        txt.val(textBefore + textToInsert + textAfter);
        txt.prop('selectionStart', cursorPosStart);
        txt.prop('selectionEnd', cursorPosStart + textToInsert.length);
        txt.focus();
    });
}

/**
 * Provjerava je li pravilan autor upisan pri dodavanju izvještaja
 */
function check() {
    let options = document.getElementById('autorOptions').options;
    let input = document.getElementById('autorDatalist').value;
    let alert = document.getElementById('wrongUser');
    let dodajButton = document.getElementById('dodajButton');

    dodajButton.disabled = true;
    alert.hidden = false;
    for(let option of options) {
        if(option.value === input) {
            alert.hidden = true;
            dodajButton.disabled = false;
        }
    }
}

/**
 * Pretrazuje korisinike
 */
function pretrazi() {
    let input, filter, trs, txt, table;
    input = document.getElementById('traziKorisnika');
    filter = input.value.toUpperCase();
    table = document.getElementsByClassName('table')[0];
    trs = table.getElementsByTagName('tr');

    const searchStart = 0;
    const searchEnd = 4;

    for(let i = 1; i < trs.length; i++) {
        trs[i].style.display = "none";
        for(let j = searchStart; j < searchEnd; j++) {
            txt = trs[i].getElementsByTagName('td')[j].textContent;
            if(txt.toUpperCase().indexOf(filter) > -1) {
                trs[i].style.display = "";
                break;
            }
        }
    }
}

/**
 * Kod zaduživanja opreme puni modal
 */
function updateOpremaModal() {
    let alpinist = document.getElementById('alpinist');
    alpinist.innerText = document.getElementById('alpinistDataList').value;

    let listItem = document.getElementById('komadOpreme');
    let pill = document.getElementById('pill');

    let list = document.getElementById('listaZaduzenja');
    list.innerHTML = '';
    list.appendChild(alpinist)

    let table = document.getElementById('oprema_body');
    let trs = table.getElementsByTagName('tr');
    for(let i = 0; i < trs.length; i++) {
        if(trs[i].firstElementChild.firstElementChild.firstElementChild.checked) {
            let newListItem = listItem.cloneNode(true);
            let tds = trs[i].getElementsByTagName('td');
            newListItem.innerText = tds[1].innerText + ' ' + tds[2].innerText + ' ' + tds[3].innerText + ' ' + tds[4].innerText;
            let newPill = pill.cloneNode(true);
            newPill.innerText = trs[i].lastElementChild.firstElementChild.value;
            newListItem.appendChild(newPill);

            list.appendChild(newListItem);
        }
    }

    let dateInput = document.getElementById('povratDatum');
    dateInput.valueAsDate = new Date(Date.now() + (31*24*60*60*1000));
}

/**
 * Pretrazuje opremu
 */
function pretraziOpremu() {
    let input, filter, trs, txt, table;
    input = document.getElementById('traziOpremu');
    filter = input.value.toUpperCase();
    table = document.getElementById('oprema_body');
    trs = table.getElementsByTagName('tr');
    const searchStart = 1;
    const searchEnd = 4;

    for(let i = 0; i < trs.length; i++) {

        trs[i].style.display = "none";
        for(let j = searchStart; j < searchEnd; j++) {
            txt = trs[i].getElementsByTagName('td')[j].textContent;
            if(txt.toUpperCase().indexOf(filter) > -1) {
                trs[i].style.display = "";
                break;
            }
        }
    }
}

/**
 * When given value is selected, element with textInputId is enabled. Otherwise, its disabled
 * @param onValue
 * @param textInputId
 */
function enableTextInput() {
    let textInput = document.getElementById('newType');
    if(this.value == 'none') {
        textInput.disabled = false;
        textInput.required = true;
    }
    else {
        textInput.disabled = true;
        textInput.required = false;
    }
}