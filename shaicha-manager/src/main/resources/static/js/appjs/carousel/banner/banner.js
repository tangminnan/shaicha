
var prefix = "/carousel/banner"
$(function() {
	
	//	var config = {
	//		'.chosen-select' : {},
	//		'.chosen-select-deselect' : {
	//			allow_single_deselect : true
	//		},
	//		'.chosen-select-no-single' : {
	//			disable_search_threshold : 10
	//		},
	//		'.chosen-select-no-results' : {
	//			no_results_text : '没有数据'
	//		},
	//		'.chosen-select-width' : {
	//			width : "95%"
	//		}
	//	}
	//	for (var selector in config) {
	//		$(selector).chosen(config[selector]);
	//	}
	load();
});
function selectLoad() {
	var html = "";
	$.ajax({
		url : '/common/dict/type',
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				html += '<option value="' + data[i].type + '">' + data[i].description + '</option>'
			}
			$(".chosen-select").append(html);
			$(".chosen-select").chosen({
				maxHeight : 200
			});
			//点击事件
			$('.chosen-select').on('change', function(e, params) {
				console.log(params.selected);
				var opt = {
					query : {
						type : params.selected,
					}
				}
				$('#exampleTable').bootstrapTable('refresh', opt);
			});
		}
	});
}
function load() {
	//selectLoad();
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				//	showRefresh : true,
				//	showToggle : true,
				//	showColumns : true,
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
				//search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
				queryParams : function(params) {
					return {
						//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						startTime:$('#startTime').val(),
						endTime:$('#endTime').val(),
						place : $('#place').val(),
						isEnable : $('#isEnable').val(),
						name : $('#name').val(),
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
						field : 'id',
						title : '编号'
					},
					{
						field : 'url',
						title : '图片',
						formatter : function(value, row, index) {
							var e = '<div class="image"><img width="90" height="100" alt="image" class="img-responsive" src="' + value + '"></div>'
							return e;
						}
					},
					{
						field : 'name',
						title : '广告名称'
					},
					{
						field : 'place',
						title : '位置',
						width : '100px',
						formatter : function(value, row, index) {
							var str = '';
							if(row.place == 1)
								str = '首页轮播';
							return str;
						}
					},
					{
						field : 'startTime',
						title : '开始时间'
					},
					{
						field : 'endTime',
						title : '结束时间'
					},
					{
						field : 'isEnable',
						title : '状态',
						formatter : function(value, row, index) {
							var str = '';
							
							str +=' <div class="switch onoffswitch col-sm-1"> ';
								str +=' <div class="onoffswitch"> ';
									str +=' <input name="allowComment" '; 
									//启用状态 0：是；1：否
									if(row.isEnable == 0)
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
							var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ row.id
								+ '\')"><i class="fa fa-edit"></i></a> ';
							var f = '<a class="btn btn-success btn-sm ' + s_add_h + '" href="#" title="查看"  mce_href="#" onclick="rocket(\''
								+ row.id 
								+ '\')"><i class="fa fa-rocket"></i></a> ';
							var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
							+ row.id
							+ '\')"><i class="fa fa-remove"></i></a> ';
							return e +f+d;
						}
					} ]
			});
}
function reLoad() {
	var opt = {
		query : {
			type : $('.chosen-select').val(),
		}
	}
	$('#exampleTable').bootstrapTable('refresh', opt);
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
				reLoad();
			}
		}
	});
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function rocket(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/rocket/' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/remove",
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

function addD(type,description) {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add/'+type+'/'+description // iframe的url
	});
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}