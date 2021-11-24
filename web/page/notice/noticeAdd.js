layui.use(['form','layer'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    $(".layui-btn").on("click",function (){
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url:"/notice/add.do",
            type:"post",
            data:{title:$(".title").val(),content:$(".content").val(),uid:$(".uid").val()},
            dataType:"json",
            success:function (result) {
                if(result.code=="0"){
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg("公告添加成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        location.href = "noticeList.html"
                    },1000);
                }else {
                    layer.msg("公告添加失败！")
                }
            }
        })
    })
    /*form.on("submit(addNotice)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        console.log($(".title").val())
        $.post("/notice/add.do",{
            title : $(".title").val(),
            content : $(".content").val(),
            uid:$(".uid").val()
        },function(res){
            res=JSON.parse(res);
            if(res.code==0){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg("公告添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    location.href = "noticeList.html"
                },1000);
            }else {
                layer.msg("公告添加失败！")
            }
        })
        return false;
    })*/

    //格式化时间
    // function filterTime(val){
    //     if(val < 10){
    //         return "0" + val;
    //     }else{
    //         return val;
    //     }
    // }
    //定时发布
    // var time = new Date();
    // var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());

})