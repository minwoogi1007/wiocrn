var TabManager = (function() {
    function initTabManager() {
        console.log('TabManager initialized');
    }

    function openTab(url, title) {
        console.log('openTab call');
        var tabId = 'tab-' + url.replace(/[^\w-]/g, '');
        if ($('#' + tabId).length) {
            $('#myTabs a[href="#' + tabId + '"]').trigger('click');
        } else {
            var tabTemplate = '<li class="nav-item"><a class="nav-link" id="' + tabId + '-tab" href="#' + tabId + '" role="tab" aria-controls="' + tabId + '" aria-selected="true">' + title + '</a><button class="close ml-2" type="button" title="Remove this page">×</button></li>';
            $('#myTabs').append(tabTemplate);
            var tabContentTemplate = '<div class="tab-pane fade show active" id="' + tabId + '" role="tabpanel" aria-labelledby="' + tabId + '-tab"><iframe src="' + url + '" style="width: 100%; height: calc(100vh - 200px); border: none;"></iframe></div>';
            $('#myTabContent').append(tabContentTemplate);
            $('#myTabs a[href="#' + tabId + '"]').trigger('click');
        }
    }

    function answerCall() {
        console.log('Answering call');
        var pNo = "1234"; // 실제 데이터로 교체해야 합니다.
        var cNo = "5678"; // 실제 데이터로 교체해야 합니다.
        var projectName = "프로젝트명"; // 실제 데이터로 교체해야 합니다.
        var personName = "고객명"; // 실제 데이터로 교체해야 합니다.
        var url = '/consultation-registration?pNo=' + pNo + '&cNo=' + cNo + '&projectName=' + projectName + '&personName=' + personName;
        openTab(url, '상담 등록');
    }

    return {
        initTabManager: initTabManager,
        answerCall: answerCall,
        openTab: openTab
    };
})();

$(document).ready(function() {
    TabManager.initTabManager();

    // 모달창에서 전화받기 버튼 클릭 이벤트 추가
    $(document).on('click', '#answerCallButton', function() {
        console.log('전화 받기 버튼 클릭');
        TabManager.answerCall();
        $('#callModal').hide();
        $('.modal-backdrop').remove(); // 모달 백드롭도 제거
    });
});
