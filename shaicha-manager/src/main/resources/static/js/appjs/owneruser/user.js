var prefix = "/owner/user"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				// showRefresh : true,
				// showToggle : true,
				// showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						name : $('#searchName').val(),
						addTime:$('#addTime').val(),
						updateTime:$('#updateTime').val()
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : [
					{
						checkbox : true
					},
					{
						field : 'nickname',
						title : '会员昵称'
					},
					{
						field : 'relation',
						title : '身份'
					},
					{
						field : 'name',
						title : '业主姓名'
					},
					
					{
						field : 'phone',
						title : '手机号'
					},
					{
						field : 'address',
						title : '小区'
					},
					{
						field : 'mianji',
						title : '面积'
					},
					{
						field : 'wuyefei',
						title : '物业费(元/每平)'
					},
					{
						field : 'registerTime',
						title : '加入时间'
					},
					

//					{
//						field : 'balance',
//						title : '余额'
//					},
					
					
					{
						field : 'deleteFlag',
						title : '状态',
						formatter : function(value, row, index) {
							var str = '';
							
							str +=' <div class="switch onoffswitch col-sm-1"> ';
								str +=' <div class="onoffswitch"> ';
									str +=' <input name="allowComment" '; 
									//启用状态 0：是；1：否
									if(row.deleteFlag == 0)
										str += ' checked="" ';
										
									str +=' type="checkbox" onchange="updateEnable(' +row.id+ ',this)" value="' +row.id+ '" class="onoffswitch-checkbox" id="example1' +row.id+ '">  ';
									str +=' <label class="onoffswitch-label" for="example1' +row.id+ '">  ';
										str +=' <span class="onoffswitch-inner"></span> ';
										str +=' <span class="onoffswitch-switch"></span> ';
											str +=' </label> ';
								str +=' </div>';
							str +=' </div>';
							return str;
						}
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var e = '<a  class="btn btn-primary btn-sm ' + s_chakan_h + '" href="#" mce_href="#" title="查看" onclick="show(\''
								+ row.id
								+ '\',\''
								+ row.address
								+ '\')"><i class="fa fa-rocket "></i></a> ';
							var d = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="修改" onclick="edit(\''
							+ row.id
							+ '\',\''
							+ row.address
							+ '\')"><i class="fa fa-edit "></i></a> ';
							var f = '<a class="btn btn-success btn-sm ' + s_cuikuan_h + '" href="#" title="催款"  mce_href="#" onclick="cuikuan(\''
								+ row.id
								+ '\')"><i class="fa fa-bell-o"></i></a> ';
							return e +d +f;
						}
					} ]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function updateEnable(id,enable){
	var isEnable = 1;
	if($(enable).prop("checked")){
		isEnable = 0;
	}
	
	$.ajax({
		url : prefix + "/updateEnable",
		type : "post",
		data : {
			'id' : id,
			'enable' : isEnable
		},
		dataType: 'JSON',
		async : false,
		success : function(r) {
			if (r.code == 0) {
				layer.msg(r.msg);
				reLoad();
			} else {
				layer.msg(r.msg);
			}
		}
	});
}
function cuikuan(id){
	$.ajax({
		url : prefix + "/cuikuan",
		type : "post",
		data : {
			'id' : id,
		},
		dataType: 'JSON',
		async : false,
		success : function(r) {
			if (r.code == 0) {
				layer.msg("催款成功");
				reLoad();
			} else {
				layer.msg(r.msg);
			}
		}
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : "/sys/user/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}
function show(id,address) {
	
	layer.open({
		type : 2,
		title : '业主详情',
		maxmin : true,
		shadeClose : false,
		area : [ '1100px', '700px' ],
		content : prefix + '/show/' + id +'/'+address// iframe的url
	});
}
function edit(id,address) {
	
	layer.open({
		type : 2,
		title : '业主详情',
		maxmin : true,
		shadeClose : false,
		area : [ '1100px', '700px' ],
		content : prefix + '/edit/' + id +'/'+address// iframe的url
	});
}
function resetPwd(id) {
	layer.open({
		type : 2,
		title : '重置密码',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '400px', '260px' ],
		content : prefix + '/resetPwd/' + id // iframe的url
	});
}

function loadTree(tree) {
	$('#jstree').jstree({
		'core' : {
			'data' : tree
		},
		"plugins" : [ "search" ]
	});
	$('#jstree').jstree().open_all();
}
$('#jstree').on("changed.jstree", function(e, data) {
	if (data.selected == -1) {
		var opt = {
			query : {
				deptId : '',
			}
		}
		$('#exampleTable').bootstrapTable('refresh', opt);
	} else {
		var opt = {
			query : {
				deptId : data.selected[0],
			}
		}
		$('#exampleTable').bootstrapTable('refresh',opt);
	}

});
