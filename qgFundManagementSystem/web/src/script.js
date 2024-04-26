$(document).ready(function() {
    $('.menu-item > a').click(function() {
        $(this).next('.submenu').slideToggle();
    });

    $('.submenu a').click(function(event) {
        event.preventDefault(); // 阻止链接跳转
        var content = $(this).data('content');
        var url = content + '.html'; // 构造要加载的 HTML 文件的 URL
        $('#main-content').load(url, function(response, status, xhr) {
            if (status == "error") {
                var msg = "抱歉，发生了错误: ";
                $('#main-content').html(msg + xhr.status + " " + xhr.statusText);
            }
        });
    });
});

// id 动态获取
$(document).ready(function() {
    var newUserId = null;
    const token = localStorage.getItem('jwtToken');
    if(token === null) {
        window.location.href = 'login.html';
    }
    else {
        $.ajax({
            url: 'http://localhost:8080/QG/UserServlet', // 请求的URL
            type: 'POST',
            headers: {'Authorization': `Bearer ${token}`},
            data: {"method": "viewUser"},
            success: function(response) {
                var data = response.data;
                newUserId = data.user_id;
                document.getElementById('userid').textContent = newUserId; // 更新页面上的 userid
            },
            error: function() {
                document.getElementById('userid').textContent = null; // 更新页面上的 userid
            }
        });
    }
});

// id 动态获取
setInterval(function() {
    var newUserId = null;
    const token = localStorage.getItem('jwtToken');
    if(token === null) {
        window.location.href = 'login.html';
    }
    else {
        $.ajax({
            url: 'http://localhost:8080/QG/UserServlet', // 请求的URL
            type: 'POST',
            headers: {'Authorization': `Bearer ${token}`},
            data: {"method": "viewUser"},
            success: function(response) {
                var data = response.data;
                newUserId = data.user_id;
                document.getElementById('userid').textContent = newUserId; // 更新页面上的 userid
            },
            error: function() {
                document.getElementById('userid').textContent = null; // 更新页面上的 userid
            }
        });
    }
}, 30*1000); // 时间单位是ms
