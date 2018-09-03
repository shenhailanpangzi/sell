<html>
<head>
    <meta charset="utf-8">
    <title>登录页面</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h2>欢迎登录</h2>
                <form role="form" method="post" action="/sell/seller/userlogin">
                    <div class="form-group">
                        <label>账号：</label>                                        <#--处理对象空值时使用()!+''-->
                        <input name="username" type="text" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label>密码：</label>                                        <#--处理对象空值时使用()!+''-->
                        <input name="password" type="text" class="form-control"/>
                    </div>
                    <button type="submit" class="btn btn-default">提交</button>
                </form>
            </div>
        </div>
    </div>
</div>

</body>

</html>