layui.use(['form','layer'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(addJob)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        $.post("/emp/add.do",{
            name : $(".name").val(),  //登录名
            cardid:$(".cardid").val(),
            sex:$(".sex").val(),
            job:$(".job").val(),
            education:$(".education").val(),
            email:$(".email").val(),
            tel:$(".tel").val(),
            party:$(".party").val(),
            address:$(".address").val(),
            birthday:$(".birthday").val(),
            department:$(".department").val(),
            remark : $(".remark").val(),  //密码
        },function(res){
            res=JSON.parse(res);
            if(res.code==0){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg("员工添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    location.href = "empList.html";
                },1000);
            }else {
                layer.msg("员工添加失败！")
            }
        })
        return false;
    })

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