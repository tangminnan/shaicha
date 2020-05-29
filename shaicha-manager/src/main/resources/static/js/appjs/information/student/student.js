
var prefix = "/information/student"
$(function() {
	load();
});

function load() {
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
								limit: params.limit,
								offset:params.offset,
								studentName:$("#studentName").val(),
								phone:$("#phone").val(),
								school:$("#school option:selected").val(),
								studentSex:$("#studentSex option:selected").val(),
								lastCheckTime:$("#lastCheckTime").val()
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
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
									title : 'id' 
								},
																{
									field : 'studentName', 
									title : '学生姓名' 
								},
																{
									field : 'studentSex', 
									title : '性别 ' ,
									formatter : function(value, row, index) {
										if(value == '1'){
									   		return '<span class="label">男</span>';
									   	}else if(value == '2'){
									   		return '<span class="label">女</span>';
									   	}else if(value == '0'){
									   		return '<span class="label">未知</span>';
									   	}
									}
								},
																{
									field : 'nation', 
									title : '民族' 
								},
																{
									field : 'birthday', 
									title : '出生年月' 
								},
																{
									field : 'identityCard', 
									title : '身份证号' 
								},
																{
									field : 'school', 
									title : '学校' 
								},
																{
									field : 'grade', 
									title : '年级' 
								},
																{
									field : 'studentClass', 
									title : '班级' 
								},
																{
									field : 'phone', 
									title : '联系电话' 
								},
//																{
//									field : 'address', 
//									title : '联系地址' 
//								},
//																{
//									field : 'height', 
//									title : '身高' 
//								},
//																{
//									field : 'weight', 
//									title : '体重' 
//								},
																{
									field : 'addTime', 
									title : '添加时间' 
								},
								/*								{
									field : 'status', 
									title : '状态0：正常1：禁止' 
								},*/
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ row.id
												+ '\')"><i class="fa fa-remove"></i></a> ';
										var f = '<a class="btn btn-success btn-xs" href="#" title="二维码"  mce_href="#" onclick="code(\''
												+ row.id
												+ '\')" style="text-decoration: none;">二维码</a>';
										var g = '<a class="btn btn-primary btn-xs" href="#" title="详情"  mce_href="#" onclick="detail(\''
												+ row.id
												+ '\')" style="text-decoration: none;">详情</a>';
										
										
										var h = '<a class="btn btn-primary btn-xs" href="#" title="电脑验光数据"  mce_href="#" onclick="optometry(\''
												+ row.id
												+ '\')" style="text-decoration: none;">验光数据</a>';
										var p = '<a class="btn btn-primary btn-xs" href="#" title=""  mce_href="#" onclick="datijieguoR(\''
											+ row.identityCard
											+ '\')" style="text-decoration: none;">答题结果</a>';
										var n = '<a class="btn btn-primary btn-xs" href="#" title="二维码下载"  mce_href="#" onclick="erweimaxiazaibyliulanqi(\''
											+ row.id
											+ '\')" style="text-decoration: none;">二维码打印</a>';
										var q = '<a class="btn btn-primary btn-xs" href="#" title="筛查打印"  mce_href="#" onclick="putongshaichadayin(\''
											+ row.id
											+ '\',\''+row.lastCheckTime+'\')" style="text-decoration: none;">筛查打印</a>';
										return g + h;
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
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
function code(id) {
	layer.open({
		type : 2,
		title : '二维码',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '450px', '520px' ],
		content : prefix + '/code/' + id // iframe的url
	});
}

function detail(id) {
	var page = layer.open({
		type : 2,
		title : '详情',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '450px', '520px' ],
		content : prefix + '/detail/' + id // iframe的url
	});
	layer.full(page);
}
function optometry(id) {
	var page = layer.open({
		type : 2,
		title : '电脑验光数据',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '450px', '520px' ],
		content : prefix + '/optometry/' + id // iframe的url
	});
	layer.full(page);
}

function datijieguoR(identityCard){
	window.location.href=prefix + '/datijieguoR/' + identityCard
}


/**
 * 模板导入会员
 */
function importtemplate(){
	var checkType='PU_TONG';
	layer.open({
		type : 2,
		title : '导入会员',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '300px' ],
		content : prefix + '/importtemplate/'+checkType // iframe的url
	});
}

/**
 * 答题结果导入
 */
function datidaoru(){
	layer.open({
		type : 2,
		title : '导入答题结果',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '300px' ],
		content : prefix + '/datidaoru' // iframe的url
	});
}

/**
 * 模板导入
 */
function daoru(){
	var formData = new FormData(document.getElementById("signupForm"));
	$.ajax({
		cache : true,
		type : "POST",
		url : "/information/student/importMember",
		data : formData,// 你的formid
		processData:false,
		contentType:false,
		async : false,
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});
}


/**
 * 模板导入
 */
function daorudatijiguo(){
	var formData = new FormData(document.getElementById("signupForm"));
	$.ajax({
		cache : true,
		type : "POST",
		url : "/information/student/daorudatijiguo",
		data : formData,// 你的formid
		processData:false,
		contentType:false,
		async : false,
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});
}


function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}

function resetPwd(id) {
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
	}, function() {

	});
}

function erweimaxiazai(){
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("选择要下载的二维码");
		return;
	}
	var ids=[];
	$.each(rows, function(i, row) {
		ids[i] = row['id'];
	});
	
	window.location.href="/information/student/downloadErweima?ids="+ids
}

/**
 * 普通筛查结果导出
 */
function shaichajieguodaochu(){
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("选择要导出结果的学生");
		return;
	}
	var ids=[];
	$.each(rows, function(i, row) {
		if(row['lastCheckTime']!=null)
			ids[i] = row['id'];
	});
	
	if(ids.length==0){alert("所选没有进行筛查检查，无法提供下载");return ;}
	window.location.href="/information/student/shaichajieguodaochu?ids="+ids
}

function erweimaxiazaibyliulanqi(id){
	//window.location.href="/information/student/downLoadErWeiMaByBrower?id="+id
	window.open("/information/student/downLoadErWeiMaByBrower?id="+id);
}
//普通筛查打印
function putongshaichadayin(id,lastCheckTime){
	if(lastCheckTime=='null'){
		alert("没有进行过筛查检查...");
		return;
	}
	window.open("/information/student/putongshaichadayin?id="+id);
}