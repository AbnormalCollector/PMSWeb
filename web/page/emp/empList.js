layui.use(['form', 'layer', 'table', 'laytpl'], function() {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //部门列表
    var tableIns = table.render({
        elem: '#empList',
        url: '/emp/list.do',
        cellMinWidth: 95,
        page: true,
        // height : "full-120",
        limits: [2, 4, 6, 8],
        limit: 2,
        id: "empListTable",
        cols: [
            [{
                type: "checkbox",
                fixed: "left"
            },
                {
                    field: 'name',
                    title: '姓名',
                    align: "center"
                },
                {
                    field: 'sex',
                    title: '性别',
                    align: "center",
                    templet: function(d) {
                        return d.sex == "1"?"男":"女";
                    }
                },
                {
                    field: 'tel',
                    title: '手机号码',
                    align: "center"
                },
                {
                    field: 'email',
                    title: '邮箱',
                    align: "center"
                },
                {
                    field: 'jid',
                    title: '职位',
                    align: "center",
                    templet: function(d) {
                        let job;
                        if (d.jid == "1"){
                            job = "java工程师";
                        }else if(d.jid == "2"){
                            job = "前端工程师";
                        }else if(d.jid == "3"){
                            job = "销售员"
                        }
                        return job;
                    }
                },
                {
                    field: 'education',
                    title: '学历',
                    align: "center"
                },
                {
                    field: 'cardid',
                    title: '身份证号码',
                    align: "center"
                },
                {
                    field: 'did',
                    title: '部门',
                    align: "center",
                    templet: function(d) {
                       let department;
                        if(d.did == "1"){
                            department = "技术部";
                        }else if(d.did == "2"){
                            department = "财务部";
                        }else if(d.did == "3"){
                            department = "销售部";
                        }else if(d.did == "4"){
                            department = "主管部";
                        }
                        return department;
                    }
                },
                {
                    field: 'address',
                    title: '联系地址',
                    align: "center"
                },
                {
                    field: 'createDate',
                    title: '建档日期',
                    align: "center"
                },
                {
                    title: '操作',
                    minWidth: 200,
                    templet: '#empListBar',
                    fixed: "right",
                    align: "center"
                }
            ]
        ],
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function() {
        let name = $(".searchVal-name").val();
        let tel = $(".searchVal-tel").val();
        let job = $(".searchVal-job").val();
        let sex = $(".searchVal-sex").val();
        let cardid = $(".searchVal-card").val();
        let De = $(".searchVal-De").val();
        // if ($(".searchVal").val() != '') {
            table.reload("empListTable", {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    //搜索的关键字
                    name:name,
                    tel:tel,
                    job:job,
                    sex:sex,
                    cardid:cardid,
                    De:De
                }
            })
        // } else {
        //     // location.reload()
        //     layer.msg("请输入搜索的内容")
        // }
    });

    //批量删除
    $(".delAll_btn").click(function() {
        var checkStatus = table.checkStatus('empListTable'),
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
                $.get("emp/removes.do",{
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
            layer.msg("请选择需要删除的用户");
        }
    })


    //修改用户
    function updateEmp(edit) {
        var index = layui.layer.open({
            title: "修改部门",
            type: 2,
            area: ['90%', '90%'],
            fixed: false, //不固定
            maxmin: true,
            content: "empUpdate.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    body.find(".usedname").val(edit.name);
                    body.find(".name").val(edit.name); //登录名
                    body.find(".sex").val(edit.sex);
                    body.find(".tel").val(edit.tel);
                    body.find(".email").val(edit.email);
                    body.find(".job").val(edit.jid);
                    body.find(".education").val(edit.education);
                    body.find(".cardid").val(edit.cardid);
                    body.find(".department").val(edit.did);
                    body.find(".address").val(edit.address);
                    body.find(".createDate").val(edit.createDate);//密码
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
            updateEmp(data);
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
                $.get("emp/remove.do",{
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
