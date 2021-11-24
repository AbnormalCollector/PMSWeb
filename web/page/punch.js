layui.use(['form', 'layer', 'table', 'laytpl'], function() {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    let name = window.localStorage.getItem("sessionUser");

    let nid;
    $.ajax({
        url:"/main/show.do",
        type:"post",
        data:{username:name},
        dataType:"json",
        success:function (result){
            if(result.code=="0"){
                datas = result.data;
                nid = datas.id;
                $(".name").html(name);
                $(".status").html(datas.status=="0"?"已审核":"未审核");
                $(".createDate").html(datas.createDate);
            }

        }
    });
     $(".flag").on("click",function (){
                    $.ajax({
                        url:"/main/add.do",
                        type:"post",
                        data:{uid:nid},
                        dataType:"json",
                        success:function (result){
                            console.log(result)
                            if(result.code=="0"){
                                layer.msg(result.msg);
                            }
                        }
                    })
                });
})