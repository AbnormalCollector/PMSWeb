layui.use(['form', 'layer'], function () {


    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】

    $(function () {
        let date = new Date()
        date.setDate(date.getDate() - 7);
        table(date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate());
        $("#getchart").on("click", function () {
            let start = $(".start").val();
            console.log(start)
            let date = new Date(start)
            table(date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate());
        });
    })
})

function table(start) {
    $.ajax({
        url: "/main/charts.do",
        type: "post",
        data: {start: start},
        dataType: "json",
        success: function (result) {
            let data = result.data;
            let signDate = [];
            let signUp = [];
            let signDown = [];
            for (var index in data) {
                let sign = data[index];
                signDate.push(sign.createtime.split("-")[1] + "月" + sign.createtime.split("-")[2] + "日");
                signUp.push(sign.start.split(":")[0])
                signDown.push(sign.end.split(":")[0])
            }
            // Step:3 conifg ECharts's path, link to echarts.js from current page.
            // Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
            require.config({
                paths: {
                    echarts: '/js'
                }
            });

            // Step:4 require echarts and use it in the callback.
            // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
            require(
                [
                    'echarts',
                    'echarts/chart/bar',
                    'echarts/chart/line'
                ],
                function (ec) {
                    //--- 折柱 ---
                    var myChart = ec.init(document.getElementById('main'));

                    myChart.setOption({
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['上班', '下班']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                mark: {show: true},
                                dataView: {show: true, readOnly: false},
                                magicType: {show: true, type: ['line', 'bar']},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        calculable: true,
                        xAxis: [
                            {
                                type: 'category',
                                data: signDate
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                splitArea: {show: true}
                            }
                        ],
                        series: [
                            {
                                name: '上班',
                                type: 'bar',
                                data: signUp
                            },
                            {
                                name: '下班',
                                type: 'bar',
                                data: signDown
                            }
                        ]
                    });
                });


        },
        error: function () {

        }
    })
}
