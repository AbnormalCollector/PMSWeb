layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //部门列表
    var tableIns = table.render({
        elem: '#fileList',
        url: '/file/list.do',
        cellMinWidth: 95,
        page: true,
        // height : "full-120",
        limits: [2, 4, 6, 8],
        limit: 2,
        id: "fileListTable",
        cols: [
            [{
                type: "checkbox",
                fixed: "left"
            },
                {
                    field: 'title',
                    title: '标题',
                    align: "center"
                }, {
                field: 'createDate',
                title: '创建时间',
                align: 'center',

            }, {
                field: 'uid',
                title: '创建人',
                align: 'center',

            }, {
                field: 'remark',
                title: '详细信息',
                align: 'center',

            },
                {
                    title: '操作',
                    minWidth: 200,
                    templet: '#fileListBar',
                    fixed: "right",
                    align: "center"
                }, {
                title: '下载',
                align: 'center',
                fixed: "right",
                minWidth: 100,
                templet: function (d) {
                    return '<div><a href="\\upload\\'+d.fileName+'" class="layui-btn layui-btn-primary" download="'+ d.title + '.' +d.fileName.split(".")[1] +'">下载</a></div>';

                }
            }
            ]
        ],
        // done:function (res) {
        //     console.log(res);
        // }
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        let title = $(".searchVal").val();
        // if ($(".searchVal").val() != '') {
            table.reload("fileListTable", {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    //搜索的关键字
                    title: title
                }
            })
        // } else {
        //     // location.reload()
        //     layer.msg("请输入搜索的内容")
        // }
    });

    //批量删除
    $(".delAll_btn").click(function () {
        var checkStatus = table.checkStatus('fileListTable'),
            data = checkStatus.data,
            newsId = [];
            let newName = [];
        // console.log(data)
        if (data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].id);
                newName.push(data[i].fileName);
            }
            layer.confirm('确定删除选中的用户？', {
                icon: 3,
                title: '提示信息'
            }, function (index) {
                $.get("file/removes.do", {
                    newsId: newsId, //将需要删除的newsId作为参数传入
                    newName:newName
                }, function (data) {
                    data = JSON.parse(data);
                    if (data.code == 0) {
                        tableIns.reload();
                        layer.close(index);
                        layer.msg("删除成功！");
                    } else {
                        layer.msg("删除失败！")
                    }
                })
            })
        } else {
            layer.msg("请选择需要删除的用户");
        }
    })




    // $(".addNews_btn").click(function() {
    // 	addUser();
    // })
    //列表操作
    table.on('tool(newList)', function (obj) {
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
                cancel: function (index) {
                    layer.close(index);
                }
            }, function (index) {
                _this.text(btnText);
                layer.close(index);
            }, function (index) {
                layer.close(index);
            });
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此用户？', {
                icon: 3,
                title: '提示信息'
            }, function (index) {
                $.get("file/remove.do", {
                    newsId: data.id, //将需要删除的newsId作为参数传入
                    newName:data.fileName
                }, function (data) {
                    data = JSON.parse(data);
                    if (data.code == 0) {
                        tableIns.reload();
                        layer.close(index);
                        layer.msg("删除成功！");
                    } else {
                        layer.msg("删除失败！")
                    }
                })
            });
        }
    });
    $("#docDownload").on("click", function () {
        alert(1)
    })
})
