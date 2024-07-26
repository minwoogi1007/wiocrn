let tabCounter = 0;

function loadContent(url) {
    $.ajax({
        url: url,
        method: 'GET',
        success: function(response) {
            $('#main-content').html(response);
        }
    });
}

function openNewTab(url, title) {
    tabCounter++;
    const tabId = 'tab-' + tabCounter;

    $('#myTab').append(`
        <li class="nav-item">
            <a class="nav-link" id="${tabId}-tab" data-toggle="tab" href="#${tabId}" role="tab"
               aria-controls="${tabId}" aria-selected="false">${title} <span class="close-tab">&times;</span></a>
        </li>
    `);

    $('#myTabContent').append(`
        <div class="tab-pane fade" id="${tabId}" role="tabpanel" aria-labelledby="${tabId}-tab">
            <!-- 탭 컨텐츠가 여기에 로드됩니다 -->
        </div>
    `);

    $(`#${tabId}-tab`).tab('show');

    $.ajax({
        url: url,
        method: 'GET',
        success: function(response) {
            $(`#${tabId}`).html(response);
        }
    });
}

function createMenu(menuData) {
    const $menu = $('#dynamic-menu');
    $menu.empty();

    function createMenuItem(item) {
        const $li = $('<li>');
        const $a = $('<a>')
            .attr('href', item.MENU_URL)
            .text(item.MENU_NAME);

        if (item.children && item.children.length > 0) {
            $a.addClass('dropdown-toggle')
                .attr('data-toggle', 'collapse')
                .attr('aria-expanded', 'false');

            const $subMenu = $('<ul>')
                .addClass('collapse list-unstyled')
                .attr('id', 'submenu-' + item.MENU_ID);

            item.children.forEach(child => {
                $subMenu.append(createMenuItem(child));
            });

            $li.append($a).append($subMenu);
        } else {
            $li.append($a);
        }

        return $li;
    }

    JSON.parse(menuData).forEach(item => {
        $menu.append(createMenuItem(item));
    });
}

$(document).ready(function() {
    createMenu(menuData);

    $(document).on('click', '#dynamic-menu a', function(e) {
        e.preventDefault();
        const url = $(this).attr('href');
        if (url && url !== '#') {
            loadContent(url);
        }
    });

    $('#callModal .btn-answer').on('click', function() {
        const consultUrl = '/consult/register';
        openNewTab(consultUrl, '상담 등록');
        $('#callModal').modal('hide');
    });

    $(document).on('click', '.close-tab', function() {
        const tabId = $(this).closest('a').attr('href');
        $(this).closest('li').remove();
        $(tabId).remove();
        $('#myTab li:first-child a').tab('show');
    });

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });

    setupDcsApiListeners();
});