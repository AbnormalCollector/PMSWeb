layui.use(['form', 'layer', 'upload'], function () {
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    upload = layui.upload;
        /*let formData = new FormData();
        formData.append("title",$(".title").val());
        formData.append("remark",$(".remark").val())*/
    let uid = window.localStorage.getItem("sessionUserid");
        upload.render({
            elem: '#pic',
            url: '/file/add.do',//此处配置你自己的上传接口即可
            accept: 'file',
            auto: false,
            data: {"title":function (){
                return $(".title").val();
                },"remark":function (){
                    return $(".remark").val();
                },"uid":function (){
                return uid;
                }}
            //,multiple: true
            , bindAction: '#addFile'
            , done: function (result) {
                if(result.code=="0"){
                    layer.msg("上传成功！");
                    location.href = "fileList.html"
                }else {
                    layer.msg("上传失败！")
                }

                // console.log(res)
            },
            error:function () {
                // console.log({title: $(".title").val(), remark: $(".remark").val()})
            }
        });

    // form.on("submit(addJob)",function(data){
    // //弹出loading
    //         var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
    //         let file = new FormData($(".layui-form"));
    //         // 实际使用时的提交信息
    //         console.log($(".title").val())
    //         $.post("/file/add.do",{
    //             title : $(".title").val(),  //登录名
    //             remark : $(".remark").val(),
    //             pic : $(".pic").val(),
    //         },function(res){
    //             if(res.code==0){
    //                 setTimeout(function(){
    //                     top.layer.close(index);
    //                     top.layer.msg("职位添加成功！");
    //                     layer.closeAll("iframe");
    //                     //刷新父页面
    //                     location.href = "jobList.html"
    //                 },1000);
    //             }else {
    //                 layer.msg("职位添加失败！")
    //             }
    //         })
    //         return false;
    // })

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