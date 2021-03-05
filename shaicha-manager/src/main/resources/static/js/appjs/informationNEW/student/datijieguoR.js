
var prefix = "/informationNEW/student"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/listDati", // 服务器数据的加载地址
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
								identityCard:$("#identityCard").val(),
								addTime:$("#addTime").val()
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
									field : 'identityCard', 
									title : '身份证号' 
								},
																{
									field : 'status', 
									title : '处理状态' 
								},
								{
									field : 'fileName', 
									title : '文件名' 
								},
																{
									field : 'addTime', 
									title : '添加时间' 
								},
																{
									field : 'answerResult1', 
									title : '1题' 
								},
								{
									field : 'answerResult2', 
									title : '2题' 
								},
								{
									field : 'answerResult3', 
									title : '3题' 
								},
								{
									field : 'answerResult4', 
									title : '4题' 
								},
								{
									field : 'answerResult5', 
									title : '5题' 
								},
								{
									field : 'answerResult6', 
									title : '6题' 
								},
								{
									field : 'answerResult7', 
									title : '7题' 
								},
								{
									field : 'answerResult8', 
									title : '8题' 
								},
								{
									field : 'answerResult9', 
									title : '9题' 
								},
								{
									field : 'answerResult10', 
									title : '10题' 
								},
								{
									field : 'answerResult11', 
									title : '11题' 
								},
								{
									field : 'answerResult12', 
									title : '12题' 
								},
								{
									field : 'answerResult13', 
									title : '13题' 
								},
								{
									field : 'answerResult14', 
									title : '14题' 
								},
								{
									field : 'answerResult15', 
									title : '15题' 
								},
								{
									field : 'answerResult16', 
									title : '16题' 
								},
								{
									field : 'answerResult17', 
									title : '17题' 
								},
								{
									field : 'answerResult18', 
									title : '18题' 
								},
								{
									field : 'answerResult19', 
									title : '19题' 
								},
								{
									field : 'answerResult20', 
									title : '20题' 
								},
								{
									field : 'answerResult21', 
									title : '21题' 
								},
								{
									field : 'answerResult22', 
									title : '22题' 
								},
								{
									field : 'answerResult23', 
									title : '23题' 
								},
								{
									field : 'answerResult24', 
									title : '24题' 
								},
								{
									field : 'answerResult25', 
									title : '25题' 
								},
								{
									field : 'answerResult26', 
									title : '26题' 
								},
								{
									field : 'answerResult27', 
									title : '27题' 
								},
								{
									field : 'answerResult28', 
									title : '28题' 
								},
								{
									field : 'answerResult29', 
									title : '29题' 
								},
								{
									field : 'answerResult30', 
									title : '30题' 
								},
								{
									field : 'answerResult31', 
									title : '31题' 
								},
								{
									field : 'answerResult32', 
									title : '32题' 
								},
								{
									field : 'answerResult33', 
									title : '33题' 
								},
								{
									field : 'answerResult34', 
									title : '34题' 
								},
								{
									field : 'answerResult35', 
									title : '35题' 
								},
								{
									field : 'answerResult36', 
									title : '36题' 
								},
								{
									field : 'answerResult37', 
									title : '37题' 
								},
								{
									field : 'answerResult38', 
									title : '38题' 
								},
								{
									field : 'answerResult39', 
									title : '39题' 
								},
								{
									field : 'answerResult40', 
									title : '40题' 
								},
								{
									field : 'answerResult41', 
									title : '41题' 
								},
								{
									field : 'answerResult42', 
									title : '42题' 
								},
								{
									field : 'answerResult43', 
									title : '43题' 
								},
								{
									field : 'answerResult44', 
									title : '44题' 
								},
								{
									field : 'answerResult45', 
									title : '45题' 
								},
								{
									field : 'answerResult46', 
									title : '46题' 
								},
								{
									field : 'answerResult47', 
									title : '47题' 
								},
								{
									field : 'answerResult48', 
									title : '48题' 
								},
								{
									field : 'answerResult49', 
									title : '49题' 
								},
								{
									field : 'answerResult50', 
									title : '50题' 
								},
								{
									field : 'answerResult51', 
									title : '51题' 
								},
								{
									field : 'answerResult52', 
									title : '52题' 
								},
								
								{
									field : 'answerResult53', 
									title : '53题' 
								},
								{
									field : 'answerResult54', 
									title : '54题' 
								},
								{
									field : 'answerResult55', 
									title : '55题' 
								},
								{
									field : 'answerResult56', 
									title : '56题' 
								},
								{
									field : 'answerResult57', 
									title : '57题' 
								},
								{
									field : 'answerResult58', 
									title : '58题' 
								},
								{
									field : 'answerResult59', 
									title : '59题' 
								},
								{
									field : 'answerResult60', 
									title : '60题' 
								},
								{
									field : 'answerResult61', 
									title : '61题' 
								},
								{
									field : 'answerResult62', 
									title : '62题' 
								},
								{
									field : 'answerResult63', 
									title : '63题' 
								},
								{
									field : 'answerResult64', 
									title : '64题' 
								},
								{
									field : 'answerResult65', 
									title : '65题' 
								},
								{
									field : 'answerResult66', 
									title : '66题' 
								},
								{
									field : 'answerResult67', 
									title : '67题' 
								},
								{
									field : 'answerResult68', 
									title : '68题' 
								},
								{
									field : 'answerResult69', 
									title : '69题' 
								},
								{
									field : 'answerResult70', 
									title : '70题' 
								},
								{
									field : 'answerResult71', 
									title : '71题' 
								},
								{
									field : 'answerResult72', 
									title : '72题' 
								},
								{
									field : 'answerResult73', 
									title : '73题' 
								},
								{
									field : 'answerResult74', 
									title : '74题' 
								},
								{
									field : 'answerResult75', 
									title : '75题' 
								},
																
								/*								{
									field : 'status', 
									title : '状态0：正常1：禁止' 
								},*/
																/*{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ row.id
												+ '\')"><i class="fa fa-remove"></i></a> ';
										
										return d;
									}
								}*/ ]
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
	layer.open({
		type : 2,
		title : '导入会员',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '300px' ],
		content : prefix + '/importtemplate' // iframe的url
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
		url : "/informationNEW/student/importMember",
		data : formData,// 你的formid
		processData:false,
		contentType:false,
		async : false,
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.alert(data.msg);
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
		url : "/informationNEW/student/daorudatijiguo",
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
	
	window.location.href="/informationNEW/student/downloadErweima?ids="+ids
}