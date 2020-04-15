var id = $("#id").val();

$(function() {
	load();
});

function load() {
	
	$('#exampleTable1')
	.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : "/information/resultOptometry/getUserDetail/"+id, // 服务器数据的加载地址
			//	showRefresh : true,
			//	showToggle : true,
			//	showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
	//			pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				/*pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
*/				//search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
				queryParams : function(params) {
					return {
						//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit: params.limit,
						offset:params.offset,
						checkDate : $("#checkDate").find("option:selected").text()
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
							field : 'firstCheckVd', 
							title : '第一次验光VD' 
						},
														{
							field : 'firstCheckRps', 
							title : '第一次检查右眼ps' 
						},
														{
							field : 'firstCheckLps', 
							title : '第一次检查左眼ps' 
						},
														{
							field : 'firstCheckRcs', 
							title : '第一次检查右眼cs' 
						},
														{
							field : 'firstCheckLcs', 
							title : '第一次检查左眼cs' 
						},
						{
							field : 'firstCheckPd', 
							title : '第一次验光PD' 
						},							
														{
							field : 'secondCheckRps', 
							title : '第二次检查右眼ps' 
						},
														{
							field : 'secondCheckLps', 
							title : '第二次检查左眼ps' 
						},
														{
							field : 'secondCheckRcs', 
							title : '第二次检查右眼cs' 
						},
														{
							field : 'secondCheckLcs', 
							title : '第二次检查左眼cs' 
						},
														{
							field : 'checkDate', 
							title : '检查日期' 
						},
														
						{
							field : 'secondCheckVd', 
							title : '第二次验光VD' 
						},
														{
							field : 'secondCheckPd', 
							title : '第二次验光PD' 
						},
						/*								{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.tOptometryId
										+ '\')"><i class="fa fa-edit"></i></a> ';
								var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.tOptometryId
										+ '\')"><i class="fa fa-remove"></i></a> ';
								var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
										+ row.tOptometryId
										+ '\')"><i class="fa fa-key"></i></a> ';
								return e + d ;
							}
						} */]
			});
	
	
	$('#exampleTable2')
	.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : "/information/resultCorneal/getUserDetail/"+id, // 服务器数据的加载地址
			//	showRefresh : true,
			//	showToggle : true,
			//	showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
			//	pagination : true, // 设置为true会在底部显示分页条
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
						tOptometryId : $('#tOptometryId').val()
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
					/*	{
							checkbox : true
						},
														{
							field : 'tCornealId', 
							title : 'id' 
						},*/
														/*{
							field : 'tOptometryId', 
							title : 'id' 
						},*/
						{
							field : 'type', 
							title : '' 
							//	R1=最大曲率值  R2=最小曲率值  AVG=平均取滤纸 CYL=曲率差值
						},
														{
							field : 'cornealMm', 
							title : '曲率' 
						},
														{
							field : 'cornealD', 
							title : '屈光度' 
						},
														{
							field : 'cornealDeg', 
							title : '角膜柱镜轴向' 
						},
														
														{
							field : 'ifrl', 
							title : '左/右眼',
							formatter(value, row, index){
								if(value == 'L')
									return "左眼"
								else if(value == 'R') 
									return "右眼"
							}
							
						},
						{
							field : 'checkDate', 
							title : '检查时间',	
						},
														{
							field : 'firstSecond', 
							title : '',
							//  FIRST_CHECK=第一次检查   SECOND_CHECK=第二次检查
							formatter(value, row, index){
								if(value == 'FIRST_CHECK')
									return "第一次检查"
								else if(value == 'SECOND_CHECK') 
									return "第二次检查"
							}
						},
						/*								{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.tCornealId
										+ '\')"><i class="fa fa-edit"></i></a> ';
								var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.tCornealId
										+ '\')"><i class="fa fa-remove"></i></a> ';
								var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
										+ row.tCornealId
										+ '\')"><i class="fa fa-key"></i></a> ';
								return e + d ;
							}
						}*/ ]
			});
	
	
	$('#exampleTable3')
	.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : "/information/resultDiopter/getUserDetail/"+id, // 服务器数据的加载地址
			//	showRefresh : true,
			//	showToggle : true,
			//	showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				//pagination : true, // 设置为true会在底部显示分页条
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
						tOptometryId : $('#tOptometryId').val()
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
						/*{
							checkbox : true
						},
														{
							field : 'tDiopterId', 
							title : 'id' 
						},*/
														/*{
							field : 'tOptometryId', 
							title : 'id' 
						},*/
														{
							field : 'diopterS', 
							title : '球镜' 
						},
														{
							field : 'diopterC', 
							title : '柱镜' 
						},
														{
							field : 'diopterA', 
							title : '轴位' 
						},
														/*{
							field : 'believe', 
							title : '可信度' 
						},*/
						/*								{
							field : 'num', 
							title : 'SCA多次普通顺序 1 2  3...' 
						},*/
														{
							field : 'type', 
							title : '(AVG)',
							formatter(value, row, index){
								if(value == 'AVG')
									return "<  >";
								if(value=='L_DATA')
									return "L.DATA";
								if(value != 'AVG') 
									return ""
							}
						},
														{
							field : 'ifrl', 
							title : '左/右眼',
							formatter(value, row, index){
								if(value == 'L')
									return "左眼"
								else if(value == 'R') 
									return "右眼"
							}
						},
						{
							field : 'checkDate', 
							title : '检查时间',	
						},
														{
							field : 'firstSecond', 
							title : '',
							//  FIRST_CHECK=第一次检查   SECOND_CHECK=第二次检查
							formatter(value, row, index){
								if(value == 'FIRST_CHECK')
									return "第一次检查"
								else if(value == 'SECOND_CHECK') 
									return "第二次检查"
							}
						},
						/*								{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.tDiopterId
										+ '\')"><i class="fa fa-edit"></i></a> ';
								var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.tDiopterId
										+ '\')"><i class="fa fa-remove"></i></a> ';
								var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
										+ row.tDiopterId
										+ '\')"><i class="fa fa-key"></i></a> ';
								return e + d ;
							}
						}*/ ]
			});
	
}

function reLoad() {
	$('#exampleTable1').bootstrapTable('refresh');
	$('#exampleTable2').bootstrapTable('refresh');
	$('#exampleTable3').bootstrapTable('refresh');

}