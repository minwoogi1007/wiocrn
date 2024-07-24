window.TabManager = window.TabManager || {};

TabManager.currentTabs = [];
TabManager.MAX_TABS = 8;

TabManager.createTabContainer = function() {
    var contentPlaceholder = document.getElementById('content-placeholder');
    if (!contentPlaceholder) {
        console.error('content-placeholder를 찾을 수 없습니다.');
        return;
    } else {
        console.log('content-placeholder 요소가 존재합니다.');
    }

    var existingTabContainer = document.getElementById('tab-container');
    if (existingTabContainer) {
        console.log('탭 컨테이너가 이미 존재합니다.');
        return;
    }

    var tabContainer = document.createElement('div');
    tabContainer.id = 'tab-container';

    var myTabs = document.createElement('ul');
    myTabs.id = 'myTabs';
    myTabs.className = 'nav nav-tabs';
    myTabs.setAttribute('role', 'tablist');

    var myTabContent = document.createElement('div');
    myTabContent.id = 'myTabContent';
    myTabContent.className = 'tab-content';

    tabContainer.appendChild(myTabs);
    tabContainer.appendChild(myTabContent);
    contentPlaceholder.appendChild(tabContainer);

    console.log('탭 컨테이너를 동적으로 생성했습니다.');
};

TabManager.initTabManager = function() {
    console.log('TabManager 초기화 시작');
    this.createTabContainer();
    console.log('TabManager 초기화 완료');
};

TabManager.answerCall = function() {
    console.log('answerCall 함수 호출됨');
    var pNo = document.getElementById('modalPNo').innerText;
    var cNo = document.getElementById('modalCNo').innerText;
    var projectName = document.getElementById('modalProjectName').innerText;
    var personName = document.getElementById('modalPersonName').innerText;

    console.log('전화 정보:', pNo, cNo, projectName, personName);

    // 탭 컨테이너 확인 후 탭 생성
    if (!document.getElementById('tab-container')) {
        console.error('탭 컨테이너가 존재하지 않습니다. 탭 컨테이너를 다시 생성합니다.');
        TabManager.createTabContainer();
    }

    this.createNewTab(pNo, cNo, projectName, personName);
    this.hideModal();
};

TabManager.hideModal = function() {
    var modal = document.getElementById('callModal');
    if (modal) {
        modal.style.display = 'none';
    }
    document.body.className = document.body.className.replace('modal-open', '');
    var backdrop = document.querySelector('.modal-backdrop');
    if (backdrop && backdrop.parentNode) {
        backdrop.parentNode.removeChild(backdrop);
    }
};

TabManager.createNewTab = function(pNo, cNo, projectName, personName) {
    console.log('createNewTab 함수 호출됨');

    var tabContainer = document.getElementById('tab-container');
    console.log('tabContainer:', tabContainer);

    if (!tabContainer) {
        console.error('tab-container를 찾을 수 없습니다.');
        return;
    }

    var tabList = tabContainer.querySelector('#myTabs');
    var tabContent = tabContainer.querySelector('#myTabContent');

    console.log('tabList:', tabList);
    console.log('tabContent:', tabContent);

    if (!tabList || !tabContent) {
        console.error('탭 컨테이너 내부 요소를 찾을 수 없습니다.');

        // 요소가 없다면 새로 생성
        if (!tabList) {
            tabList = document.createElement('ul');
            tabList.id = 'myTabs';
            tabList.className = 'nav nav-tabs';
            tabList.setAttribute('role', 'tablist');
            tabContainer.appendChild(tabList);
            console.log('myTabs 요소를 새로 생성했습니다.');
        }

        if (!tabContent) {
            tabContent = document.createElement('div');
            tabContent.id = 'myTabContent';
            tabContent.className = 'tab-content';
            tabContainer.appendChild(tabContent);
            console.log('myTabContent 요소를 새로 생성했습니다.');
        }
    }

    // 여기서부터는 기존 코드와 동일
    if (this.currentTabs.length >= this.MAX_TABS) {
        alert('최대 ' + this.MAX_TABS + '개의 탭만 열 수 있습니다.');
        return;
    }

    var tabId = 'tab-' + Date.now();
    var tabTitle = personName + ' (' + cNo + ')';

    console.log('새 탭 생성:', tabId, tabTitle);

    // 탭 헤더 추가
    var tabHeader = document.createElement('li');
    tabHeader.className = 'nav-item';
    tabHeader.innerHTML = '<a class="nav-link" id="' + tabId + '-tab" href="#' + tabId + '" role="tab">' +
        tabTitle +
        '<button class="close" type="button" onclick="TabManager.closeTab(\'' + tabId + '\')">&times;</button>' +
        '</a>';
    tabList.appendChild(tabHeader);

    // 탭 내용 추가
    var newTabPane = document.createElement('div');
    newTabPane.className = 'tab-pane fade';
    newTabPane.id = tabId;
    newTabPane.setAttribute('role', 'tabpanel');
    newTabPane.innerHTML = '<div class="container-fluid">' +
        '<div class="row">' +
        '<div class="col-md-6">' +
        '<h3>상담 정보</h3>' +
        '<p><strong>내선 번호:</strong> ' + pNo + '</p>' +
        '<p><strong>전화 번호:</strong> ' + cNo + '</p>' +
        '<p><strong>프로젝트:</strong> ' + projectName + '</p>' +
        '<p><strong>고객 이름:</strong> ' + personName + '</p>' +
        '</div>' +
        '<div class="col-md-6">' +
        '<h3>상담 내용</h3>' +
        '<textarea class="form-control" rows="5" placeholder="상담 내용을 입력하세요..."></textarea>' +
        '</div>' +
        '</div>' +
        '<div class="row mt-3">' +
        '<div class="col-md-12">' +
        '<button class="btn btn-primary" onclick="TabManager.saveTab(\'' + tabId + '\')">저장</button>' +
        '</div>' +
        '</div>' +
        '</div>';
    tabContent.appendChild(newTabPane);

    console.log('새 탭 콘텐츠 추가:', tabId);

    // 새 탭 활성화
    this.activateTab(tabId);

    // 탭 정보 저장
    this.currentTabs.push({id: tabId, pNo: pNo, cNo: cNo, projectName: projectName, personName: personName});

    console.log('새 탭이 생성되었습니다:', tabId);
};

TabManager.activateTab = function(tabId) {
    console.log('탭 활성화 시도:', tabId);

    var tabs = document.querySelectorAll('#myTabs .nav-link');
    var tabPanes = document.querySelectorAll('#myTabContent .tab-pane');

    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = tabs[i].className.replace('active', '');
    }
    for (var i = 0; i < tabPanes.length; i++) {
        tabPanes[i].className = tabPanes[i].className.replace('active show', '');
    }

    var selectedTab = document.getElementById(tabId + '-tab');
    var selectedPane = document.getElementById(tabId);
    if (selectedTab) {
        selectedTab.className += ' active';
        console.log('탭 활성화됨:', selectedTab);
    } else {
        console.error('선택한 탭을 찾을 수 없습니다:', tabId);
    }
    if (selectedPane) {
        selectedPane.className += ' active show';
        console.log('탭 콘텐츠 활성화됨:', selectedPane);
    } else {
        console.error('선택한 탭 콘텐츠를 찾을 수 없습니다:', tabId);
    }
};

TabManager.closeTab = function(tabId) {
    var tabElement = document.getElementById(tabId + '-tab').parentNode;
    var tabContentElement = document.getElementById(tabId);

    if (tabElement && tabElement.parentNode) {
        tabElement.parentNode.removeChild(tabElement);
    }
    if (tabContentElement && tabContentElement.parentNode) {
        tabContentElement.parentNode.removeChild(tabContentElement);
    }

    TabManager.currentTabs = TabManager.currentTabs.filter(function(tab) { return tab.id !== tabId; });

    if (TabManager.currentTabs.length > 0) {
        TabManager.activateTab(TabManager.currentTabs[0].id);
    }
};

TabManager.saveTab = function(tabId) {
    var content = document.querySelector('#' + tabId + ' textarea').value;
    var tabInfo = TabManager.currentTabs.filter(function(tab) { return tab.id === tabId; })[0];

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/save-consultation', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert('상담 내용이 저장되었습니다.');
                TabManager.closeTab(tabId);
            } else {
                alert('저장 중 오류가 발생했습니다.');
            }
        }
    };
    xhr.send(JSON.stringify({
        pNo: tabInfo.pNo,
        cNo: tabInfo.cNo,
        projectName: tabInfo.projectName,
        personName: tabInfo.personName,
        content: content
    }));
};

document.addEventListener("DOMContentLoaded", function() {
    console.log('DOM 완전히 로드됨');
    console.log(document.getElementById('content-placeholder'));
    console.log(document.getElementById('tab-container'));
    console.log(document.getElementById('myTabs'));
    console.log(document.getElementById('myTabContent'));
    TabManager.initTabManager();
});
