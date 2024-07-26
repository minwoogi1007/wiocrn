function setupDcsApiListeners() {
    if (document.all.EventClientCtrl) {
        if (typeof document.all.EventClientCtrl.addEventListener === 'function') {
            document.all.EventClientCtrl.addEventListener("SendRingEvent", onRingEvent, false);
            document.all.EventClientCtrl.addEventListener("SendChannelListEvent", onChannelListEvent, false);
            document.all.EventClientCtrl.addEventListener("SendChannelOutEvent", onChannelOutEvent, false);
        } else if (typeof document.all.EventClientCtrl.attachEvent === 'function') {
            document.all.EventClientCtrl.attachEvent("onSendRingEvent", onRingEvent);
            document.all.EventClientCtrl.attachEvent("onSendChannelListEvent", onChannelListEvent);
            document.all.EventClientCtrl.attachEvent("onSendChannelOutEvent", onChannelOutEvent);
        }
    }
}

function onRingEvent(eventData) {
    showCallModal(parseEventData(eventData));
}

function onChannelListEvent(eventData) {
    console.log("Channel List Event:", parseEventData(eventData));
}

function onChannelOutEvent(eventData) {
    console.log("Channel Out Event:", parseEventData(eventData));
}

function parseEventData(eventData) {
    var data = {};
    var pairs = eventData.split('|');
    for (var i = 0; i < pairs.length; i++) {
        var pair = pairs[i].split(':');
        data[pair[0]] = pair[1];
    }
    return data;
}

function showCallModal(callData) {
    $('#modalPNo').text(callData.AGENT || '');
    $('#modalCNo').text(callData.CALLERID || '');
    $('#modalProjectName').text(callData.PROJECT || '');
    $('#modalPersonName').text(callData.PERSON || '');
    $('#callModal').modal('show');
}

// DCS API 함수들
function makeCall(number) {
    document.all.EventClientCtrl.Click2Call(number, "", "");
}

function answerCall() {
    document.all.EventClientCtrl.Answer();
}

function hangupCall() {
    document.all.EventClientCtrl.Hangup();
}

function holdCall() {
    document.all.EventClientCtrl.Hold();
}

function unholdCall() {
    document.all.EventClientCtrl.Unhold();
}

function transferCall(number) {
    document.all.EventClientCtrl.Transfer(number);
}

function pickupCall(number) {
    document.all.EventClientCtrl.Pickup(number);
}

$(document).ready(function() {
    $('#btnMakeCall').click(function() {
        var number = prompt("전화번호를 입력하세요:");
        if (number) makeCall(number);
    });

    $('#btnAnswer').click(answerCall);
    $('#btnHangup').click(hangupCall);
    $('#btnHold').click(holdCall);
    $('#btnUnhold').click(unholdCall);
    $('#btnTransfer').click(function() {
        var number = prompt("전환할 번호를 입력하세요:");
        if (number) transferCall(number);
    });
    $('#btnPickup').click(function() {
        var number = prompt("당겨받을 내선번호를 입력하세요:");
        if (number) pickupCall(number);
    });

    setupDcsApiListeners();
});