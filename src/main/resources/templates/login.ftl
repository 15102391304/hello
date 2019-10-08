<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Bootstrap模态登录框 - 源码之家</title>
</head>

<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />

<body>

<a style="display:block;width:200px;height:42px;line-height:42px;font-size:18px;text-align:center;margin:40px auto;font-weight:800;" href="#" class="tbox">点击弹出</a>

<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            </div>
            <div class="modal-body">
                <h4 class="modal-title" align="center">登录框</h4>
                <br/>
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="name" class="col-sm-offset-2 col-sm-2 control-label">账号</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="name" placeholder="请输您的入账号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-offset-2 col-sm-2 control-label">密码</label>
                        <div class="col-sm-5">
                            <input type="password" class="form-control" id="password" placeholder="请输入您的密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-5">
                            <button id="submitBtn" type="submit" class="btn btn-default btn-block btn-primary">登录</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
            </div>
        </div>
        <!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
    $(function(){

        $(".tbox").click(function(){
            $('#myModal').modal('show') //显示模态框
        })

    });
</script>
更多模板：<a href="http://www.mycodes.net/" target="_blank">源码之家</a>
</body>
</html>