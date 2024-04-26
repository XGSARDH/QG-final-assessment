$(document).ready(function() {
    $("#sidebar ul li").click(function() {
        var target = $(this).attr("data-target");
        $(".content-section").hide();
        $("#" + target).show();
    });
});
$(document).ready(function() {
    const token = localStorage.getItem('jwtToken'); // 假设你已经有了Token
    $.ajax({
        url: 'http://localhost:8080/QG/UserServlet', // 请求的URL
        type: 'POST', // 请求方法
        dataType: 'json', // 预期服务器返回的数据类型
        headers: {
            'Authorization': 'Bearer ' + token // 携带的认证Token
        },
        data: {
            "method": "testConn" // 发送到服务器的数据
        },
        success: function(data) {
            console.log(data); // 成功时的回调函数
        },
        error: function(xhr, status, error) {
            console.error('Error:', error); // 错误时的回调函数
            window.location.href = 'login.html';
        }
    });
});
$(document).ready(function() {
    // 绑定按钮的点击事件
    $('#loadData_OneGroup').click(function() {
        var groupId = $('#groupId').val();

        // 获取存储在LocalStorage中的JWT
        const token = localStorage.getItem('jwt');

        // 使用AJAX发送请求，附带JWT
        $.ajax({
            dataType: "json",
            url: "http://localhost:8080/QG/LoginAndRegisterServlet",
            async: true,
            type: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: {
                "method": "findPublicOneGroup",
                "groupId": groupId
            },
            success: function(response) {
                groups = response.groups

                // 清空现有的内容
                $('#OneGroup').empty();
                var index = 1;  // 初始化计数器，用于递增ID
                // 解析返回的JSON对象数组并显示
                groups.forEach(group => {
                    const groupDiv = $('<div class="group-container"></div>');
                    groupDiv.append(`<h2 class="group-header">组名: ${group.group_name}</h2>`);
                    groupDiv.append(`<p id="groupId${index}">组ID: ${group.group_id}</p>`);  // 使用计数器为ID添加后缀
                    groupDiv.append(`<p id="groupCreateBy${index}">创建者ID: ${group.create_by}</p>`);
                    groupDiv.append(`<p id="description${index}">描述: ${group.description}</p>`);
                    index++;
                    $('#OneGroup').append(groupDiv);
                });
            },
            error: function(xhr, status, error) {
                console.error("Error fetching data: " + error);
            }
        });
    });
});
$(document).ready(function() {
    $('#loadData_AllGroups').click(function() {
        const token = localStorage.getItem('jwtToken');
        $.ajax({
            dataType: "json",
            url: "http://localhost:8080/QG/LoginAndRegisterServlet",
            type: 'POST',
            headers: {'Authorization': `Bearer ${token}`},
            data: {"method": "findPublicAllGroup"},
            success: function(response) {
                $('#AllGroups').empty();
                var index = 1;
                response.groups.forEach(group => {
                    const groupDiv = $('<div class="group-container"></div>');
                    const detailsDiv = $(`<div class="details" id="details${index}"></div>`);
                    const adminDiv = $(`<div class="admin-panel" id="adminPanel${index}"></div>`);

                    // 将详情放入隐藏的div中
                    detailsDiv.append(`<p>组ID: ${group.group_id}</p>`);
                    detailsDiv.append(`<p>创建者ID: ${group.create_by}</p>`);
                    detailsDiv.append(`<p>描述: ${group.description}</p>`);

                    groupDiv.append(`<h2 class="group-header">组名: ${group.group_name}</h2>`);
                    groupDiv.append(`<button onclick="$('#details${index}').slideToggle()">展开详情</button>`);
                    groupDiv.append(detailsDiv);
                    groupDiv.append(`<button onclick="openAdminPanel(${group.group_id}, ${index})">管理群组</button>`);
                    groupDiv.append(adminDiv);
                    $('#AllGroups').append(groupDiv);
                    index++;
                });
            },
            error: function(xhr, status, error) {
                alert("error: " + error);
                console.error("Error fetching data: " + error);
            }
        });
    });

    // 打开管理面板的函数，发送AJAX请求并展示结果
    window.openAdminPanel = function(groupId, index) {
        $.ajax({
            dataType: "json",
            url: "http://localhost:8080/QG/AdminServlet",
            type: 'POST',
            data: { "group_id": groupId },
            success: function(response) {
                $(`#adminPanel${index}`).html(`<p>管理内容: ${response.adminData}</p>`);
                $(`#adminPanel${index}`).slideToggle();
            },
            error: function(xhr, status, error) {
                console.error("Error fetching admin data: " + error);
                $(`#adminPanel${index}`).html(`<p>Error loading data.</p>`);
                $(`#adminPanel${index}`).slideToggle();
            }
        });
    }
});
