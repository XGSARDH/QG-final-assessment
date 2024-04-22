<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<body>

<h2>Login</h2>

<form id="loginForm">
    userId:<input type="text" name="userId" id="userId"><br>
    password:<input type="password" name="password" id="password"><br>
    <button type="submit">Login</button>
    <!-- 新增的注册按钮 -->
    <button type="button" onclick="window.location.href='register.jsp'">Register</button>
    <button type="button" onclick="window.location.href='retrievePassword.jsp'">Retrieve Password</button>
</form>

<div id="responseMsg"></div>

<script>
    $(document).ready(function() {
        $('#loginForm').submit(function(event) {
            event.preventDefault();

            var userId = $('#userId').val();
            var password = $('#password').val();

            $.ajax({
                dataType: "json",
                async: true,
                type: "POST",
                url: "http://localhost:8080/QG/LoginAndRegisterServlet",
                data: {
                    method: 'login',
                    userId: userId,
                    password: password
                },
                beforeSend: function(xhr) {
                    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
                },
                success: function(response) {
                    if(response.status === 200) {
                        $('#responseMsg').html('<p style="color: green;">Success to login: ' + '</p>');
                    } else {
                        $('#responseMsg').html('<p style="color: red;">Failed to login: ' + '</p>');
                    }
                },
                error: function() {
                    $('#responseMsg').html('<p style="color: red;">Error</p>');
                }
            });
        });
    });
</script>

</body>
</html>
