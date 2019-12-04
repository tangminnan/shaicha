var id = $("#id").val();
var identityCard = $("#identityCard").val();
$(function() {
	load();
});

function load() {
	//视力
	$('#exampleTable1')
	.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : "/information/resultEyesight/getUserDetail/"+identityCard, // 服务器数据的加载地址
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
						//checkDate:$("#checkDate").val()
						startTime:$('#startTime').val(),
						endTime:$('#endTime').val()
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
							field : 'tEyesightId', 
							title : 'id' 
						},*/
														{
							field : 'studentId', 
							title : '学生id' 
						},
														{
							field : 'checkorId', 
							title : '视力检查者id' 
						},
														{
							field : 'lifeFarvisionOd', 
							title : '右眼生活远视力' 
						},
														{
							field : 'lifeFarvisionOs', 
							title : '左眼生活远视力' 
						},
														{
							field : 'nakedFarvisionOd', 
							title : '右眼裸眼远视力' 
						},
														{
							field : 'nakedFarvisionOs', 
							title : '左眼裸眼远视力' 
						},
														{
							field : 'correctionFarvisionOd', 
							title : '右眼矫正远视力' 
						},
														{
							field : 'correctionFarvisionOs', 
							title : '左眼矫正远视力' 
						},
														{
							field : 'lifeNearvisionOd', 
							title : '右眼生活近视力' 
						},
														{
							field : 'lifeNearvisionOs', 
							title : '左眼生活近视力' 
						},
														{
							field : 'nakedNearvisionOd', 
							title : '右眼裸眼近视力' 
						},
														{
							field : 'nakedNearvisionOs', 
							title : '左眼裸眼近视力' 
						},
														{
							field : 'checkDate', 
							title : '检查日期' 
						},
						/*								{
							field : 'deleteFlag', 
							title : '状态0：正常1：禁止' 
						},
														{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.tEyesightId
										+ '\')"><i class="fa fa-edit"></i></a> ';
								var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.tEyesightId
										+ '\')"><i class="fa fa-remove"></i></a> ';
								var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
										+ row.tEyesightId
										+ '\')"><i class="fa fa-key"></i></a> ';
								return e + d ;
							}
						}*/ ]
			});
	
	//眼轴长度
	$('#exampleTable2')
	.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : "/information/resultEyeaxis/getUserDetail/"+identityCard, // 服务器数据的加载地址
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
						//checkDate:$("#checkDate").val()
						startTime:$('#startTime').val(),
						endTime:$('#endTime').val()
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
							field : 'tEyeaxisId', 
							title : 'id' 
						},*/
														{
							field : 'studentId', 
							title : '学生id' 
						},
														{
							field : 'checkorId', 
							title : '视力检查者id' 
						},
														{
							field : 'firstCheckOd', 
							title : '右眼第一次检查结果' 
						},
														{
							field : 'firstCheckOs', 
							title : '左眼第一次检查结果' 
						},
														{
							field : 'checkDate', 
							title : '检查日期' 
						},
														{
							field : 'secondCheckOd', 
							title : '右眼第二次检查结果' 
						},
														{
							field : 'secondCheckOs', 
							title : '左眼第二次检查结果' 
						},
						
						/*								{
							field : 'deleteFlag', 
							title : '状态0：正常1：禁止' 
						},
														{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.tEyeaxisId
										+ '\')"><i class="fa fa-edit"></i></a> ';
								var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.tEyeaxisId
										+ '\')"><i class="fa fa-remove"></i></a> ';
								var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
										+ row.tEyeaxisId
										+ '\')"><i class="fa fa-key"></i></a> ';
								return e + d ;
							}
						} */]
			});
	
	//眼内压
	$('#exampleTable3')
	.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : "/information/resultEyepressure/getUserDetail/"+identityCard, // 服务器数据的加载地址
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
						//checkDate:$("#checkDate").val()
						startTime:$('#startTime').val(),
						endTime:$('#endTime').val()
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
							field : 'tEyepressureId', 
							title : 'id' 
						},*/
														{
							field : 'studentId', 
							title : '学生id' 
						},
														{
							field : 'checkorId', 
							title : '眼内压检查者id' 
						},
														{
							field : 'eyePressureOd', 
							title : '右眼眼内压' 
						},
														{
							field : 'eyePressureOs', 
							title : '左眼眼内压' 
						},
														{
							field : 'checkDate', 
							title : '检查日期' 
						},
						/*								{
							field : 'deleteFlag', 
							title : '状态0：正常1：禁止' 
						},
														{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.tEyepressureId
										+ '\')"><i class="fa fa-edit"></i></a> ';
								var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.tEyepressureId
										+ '\')"><i class="fa fa-remove"></i></a> ';
								var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
										+ row.tEyepressureId
										+ '\')"><i class="fa fa-key"></i></a> ';
								return e + d ;
							}
						}*/ ]
			});
	
	//视功能,眼前后节
	$('#exampleTable4')
	.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : "/information/resultVisibility/getUserDetail/"+identityCard, // 服务器数据的加载地址
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
						//checkDate:$("#checkDate").val()
						startTime:$('#startTime').val(),
						endTime:$('#endTime').val()
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
							field : 'tVisibilityId', 
							title : 'id' 
						},*/
														{
							field : 'studentId', 
							title : '学生id' 
						},
														{
							field : 'checkorId', 
							title : '视功能检查者id' 
						},
														{
							field : 'stereoscopicViewingValue', 
							title : '立体视',
							//value 1=xx弧秒2=立体盲3=不配合4=其他
							formatter(value, row, index){
								if(value==1){
									return row.stereoscopicViewingDis+"弧秒";
								}else if(value==2){
									return "立体盲";
								}else if(value==3){
									return "不配合";
								}else if(value==4){
									return "其他";
								}
									
							}
						},
														{
							field : 'stereoscopicViewingDis', 
							title : '立体视备注' 
						},
														{
							field : 'adjustmentRangeOd', 
							title : '右眼调节幅度' 
						},
														{
							field : 'adjustmentRangeOc', 
							title : '左眼调节幅度' 
						},
														{
							field : 'adjustmentRangeOu', 
							title : '双眼调节幅度' 
						},
														{
							field : 'gatherNearOd', 
							title : '右眼集合近点' 
						},
														{
							field : 'gatherNearOc', 
							title : '左眼集合近点' 
						},
														{
							field : 'gatherNearOu', 
							title : '双眼集合近点' 
						},
														{
							field : 'obliqueValue', 
							title : '隐斜量' 
							//value   EXO  ECO
						},
														{
							field : 'obliqueDis', 
							title : '隐斜量值' 
						},
														{
							field : 'beforeAfterOdValue', 
							title : '右眼前后节' ,
							//value 0=正常   1=其他（备注）
							formatter(value, row, index){
								if(value == 0){
									return "正常" ;
								}else{
									return "其他" ;
								}
							}
						},
														{
							field : 'beforeAfterOdDis', 
							title : '右眼前后节text' 
						},
														{
							field : 'beforeAfterOsValue', 
							title : '左眼前后节',
							//value 0=正常   1=其他
							formatter(value, row, index){
								if(value == 0){
									return "正常" ;
								}else{
									return "其他" ;
								}
							}
						},
														{
							field : 'beforeAfterOsDis', 
							title : '左眼前后节text' 
						},
														{
							field : 'checkDate', 
							title : '检查日期' 
						},
						/*								{
							field : 'deleteFlag', 
							title : '状态0：正常1：禁止' 
						},
														{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.tVisibilityId
										+ '\')"><i class="fa fa-edit"></i></a> ';
								var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.tVisibilityId
										+ '\')"><i class="fa fa-remove"></i></a> ';
								var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
										+ row.tVisibilityId
										+ '\')"><i class="fa fa-key"></i></a> ';
								return e + d ;
							}
						} */]
			});

	//调节灵敏度
	$('#exampleTable5')
	.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : "/information/resultAdjusting/getUserDetail/"+identityCard, // 服务器数据的加载地址
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
						//checkDate:$("#checkDate").val()
						startTime:$('#startTime').val(),
						endTime:$('#endTime').val()
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
							field : 'tAdjustingId', 
							title : 'id' 
						},*/
														{
							field : 'studentId', 
							title : '学生id' 
						},
														{
							field : 'checkorId', 
							title : '调节灵敏度检查者id' 
						},
														{
							field : 'adjustingOd', 
							title : '右眼调节灵敏度' 
						},
														{
							field : 'adjustingOs', 
							title : '左眼调节灵敏度' 
						},
														{
							field : 'adjustingOu', 
							title : '双眼调节灵敏度' 
						},
														{
							field : 'jjOd', 
							title : '右眼调节灵敏度调整',
							// 0加  1减
							formatter(value, row, index){
								if(value == 0){
									return "加" ;
								}
								if(value == 1){
									return "减" ;
								}
							}
						},
														{
							field : 'jjOs', 
							title : '左眼调节灵敏度调整',
							// 0加  1减
							formatter(value, row, index){
								if(value == 0){
									return "加" ;
								}
								if(value == 1){
									return "减" ;
								}
							}
						},
														{
							field : 'jjOu', 
							title : '双眼调节灵敏度调整 ',
							// 0加  1减
							formatter(value, row, index){
								if(value == 0){
									return "加" ;
								}
								if(value == 1){
									return "减" ;
								}
							}
						},
														{
							field : 'checkDate', 
							title : '检查日期' 
						},
						/*								{
							field : 'deleteFlag', 
							title : '状态0：正常1：禁止' 
						},
														{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.tAdjustingId
										+ '\')"><i class="fa fa-edit"></i></a> ';
								var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.tAdjustingId
										+ '\')"><i class="fa fa-remove"></i></a> ';
								var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
										+ row.tAdjustingId
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
	$('#exampleTable4').bootstrapTable('refresh');
	$('#exampleTable5').bootstrapTable('refresh');
}