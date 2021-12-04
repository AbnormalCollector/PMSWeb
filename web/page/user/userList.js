layui.use(['form', 'layer', 'table', 'laytpl','dropdown'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		laytpl = layui.laytpl,
		table = layui.table,
		dropdown = layui.dropdown;

		//用户列表
		var tableIns = table.render({
			elem: '#userList',
			url: '/user/list.do',
			cellMinWidth: 95,
			page: true,
			// height : "full-120",
			limits: [2, 4, 6, 8],
			limit: 2,
			id: "userListTable",
			cols: [
				[{
					type: "checkbox",
					fixed: "left"
				},
					{
						field: 'username',
						title: '用户名',
						align: "center"
					},
					{
						field: 'password',
						title: '密码',
						align: 'center',
						templet: function (d) {
							return '<span class="layui-blue"">' + d.password + '</span>';
						}
					},
					{
						field: 'loginname',
						title: '登录名',
						align: 'center',
						width: 100
					},
					{
						field: 'status',
						title: '状态',
						align: 'center',
						width: 100,
						templet: function (d) {
							return d.status == "0" ? "正常使用" : "限制使用";
						}
					},
					{
						field: 'createDate',
						title: '创建时间',
						align: 'center',
					},
					{
						title: '操作',
						minWidth: 200,
						templet: '#userListBar',
						fixed: "right",
						align: "center"
					}
				]
			],
		});
	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function() {
		let username = $(".searchVal-username").val();
		let status = $(".searchVal-status").val()
		// if ($(".searchVal").val() != '') {
			table.reload("userListTable", {
				page: {
					curr: 1 //重新从第 1 页开始
				},
				where: {
					 //搜索的关键字
					username:username,
					status:status
				}
			})
		// } else {
		// 	// location.reload()
		// 	layer.msg("请输入搜索的内容")
		// }
	});
	//批量删除
	$(".delAll_btn").click(function() {
		var checkStatus = table.checkStatus('userListTable'),
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
				$.get("user/removes.do",{
				    newsId : newsId, //将需要删除的newsId作为参数传入
				},function(data){
				tableIns.reload();
				layer.close(index);
				layer.msg(data.msg);
				})
			})
		} else {
			layer.msg("请选择需要删除的用户");
		}
	})


	//修改用户
	function updateUser(edit) {
		var index = layui.layer.open({
			title: "修改用户",
			type: 2,
			area: ['90%', '90%'],
			fixed: false, //不固定
			maxmin: true,
			content: "userUpdate.html",
			success: function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				if (edit) {
					body.find(".usedname").val(edit.username);
					body.find(".userName").val(edit.username); //登录名
					body.find(".password").val(edit.password); //密码
					body.find(".loginname").val(edit.loginname); //会员等级
					body.find(".status").val(edit.status); //用户状态
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
	table.on('tool(userList)', function(obj) {
		var layEvent = obj.event,
			data = obj.data;
		if (layEvent === 'edit') { //编辑
			updateUser(data);
		} else if (layEvent === 'usable') { //启用禁用
			let flag = $(this).text();
			let enable;
			if($.trim(flag) == "已启用"){
				enable = "您确定禁用吗？";
			}else {
				enable = "您确定启用吗？";
			}
			layer.confirm(enable, {
				icon: 3,
				title: '系统提示',
				cancel: function(index) {
					layer.close(index);
				}
			}, function(index) {
				layer.close(index);
				$.ajax({
					url: "/user/status.do",
					type:"post",
					data:{username:data.username,status:data.status},
					dataType:"json",
					success:function (result){
						if(result.code=="0"){
							location.href = "userList.html"
						}
					},error:function (){
						layer.msg("修改失败")
					}
				})
			}, function(index) {
				layer.close(index);
			});

		} else if (layEvent === 'del') { //删除
			layer.confirm('确定删除此用户？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				$.get("user/remove.do",{
				     newsId : data.id  //将需要删除的newsId作为参数传入
				 },function(data){
				tableIns.reload();
				layer.close(index);
				layer.msg(data.msg);
				})
			});
		}
	});

})
