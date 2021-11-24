layui.use(['form', 'layer', 'table', 'laytpl'], function() {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //部门列表
    var tableIns = table.render({
        elem: '#jobList',
        url: '/job/list.do',
        cellMinWidth: 95,
        page: true,
        // height : "full-120",
        limits: [2, 4, 6, 8],
        limit: 2,
        id: "jobListTable",
        cols: [
            [{
                type: "checkbox",
                fixed: "left"
            },
                {
                    field: 'name',
                    title: '姓名',
                    align: "center"
                },{
                field: 'remark',
                title: '详细信息',
                align: 'center',
                templet: function(d) {
                    return '<span class="layui-blue"">' + d.remark + '</span>';
                }
            },
                {
                    title: '操作',
                    minWidth: 200,
                    templet: '#jobListBar',
                    fixed: "right",
                    align: "center"
                }
            ]
        ],
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function() {
        let Reload = $(".searchVal").val();
        // if ($(".searchVal").val() != '') {
            table.reload("jobListTable", {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    //搜索的关键字
                    name:Reload
                }
            })
        // } else {
        //     // location.reload()
        //     layer.msg("请输入搜索的内容")
        // }
    });

    //批量删除
    $(".delAll_btn").click(function() {
        var checkStatus = table.checkStatus('jobListTable'),
            data = checkStatus.data,
            newsId = [];
        // console.log(data)
        if (data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].id);
            }
            layer.confirm('确定删除选中的用户？', {
                icon: 3,
                title: '提示信息'
            }, function(index) {
                $.get("job/removes.do",{
                    newsId : newsId, //将需要删除的newsId作为参数传入
                },function(data){
                    data = JSON.parse(data);
                    if(data.code==0){
                        tableIns.reload();
                        layer.close(index);
                        layer.msg("删除成功！");
                    }else {
                        layer.msg("删除失败！")
                    }
                })
            })
        } else {
            layer.msg("请选择需要删除的部门！");
        }
    })


    //修改用户
    function updateJob(edit) {
        var index = layui.layer.open({
            title: "修改职位",
            type: 2,
            area: ['90%', '90%'],
            fixed: false, //不固定
            maxmin: true,
            content: "jobUpdate.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    body.find(".usedname").val(edit.name);
                    body.find(".name").val(edit.name);
                    body.find(".remark").val(edit.remark); //
                    form.render();
                }
                setTimeout(function() {
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        })
        //layui.layer.full(index);
        window.sessionStorage.setItem("index", index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）

    }
    // $(".addNews_btn").click(function() {
    // 	addUser();
    // })
    //列表操作
    table.on('tool(newList)', function(obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            updateJob(data);
        } else if (layEvent === 'usable') { //启用禁用
            var _this = $(this),
                usableText = "是否确定禁用此用户？",
                btnText = "已禁用";
            if (_this.text() == "已禁用") {
                usableText = "是否确定启用此用户？",
                    btnText = "已启用";
            }
            layer.confirm(usableText, {
                icon: 3,
                title: '系统提示',
                cancel: function(index) {
                    layer.close(index);
                }
            }, function(index) {
                _this.text(btnText);
                layer.close(index);
            }, function(index) {
                layer.close(index);
            });
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此用户？', {
                icon: 3,
                title: '提示信息'
            }, function(index) {
                $.get("job/remove.do",{
                    newsId : data.id  //将需要删除的newsId作为参数传入
                },function(data){
                    data = JSON.parse(data);
                    if(data.code==0){
                        tableIns.reload();
                        layer.close(index);
                        layer.msg("删除成功！");
                    }else {
                        layer.msg("删除失败！")
                    }
                })
            });
        }
    });

})
