$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	
		$.ajax({
			type : 'POST',
			data : {
				"startDate":$("#startDate").val(),
		   		"endDate" :$("#endDate").val(),
			},
			url : '/studentReport/baogaojyjimg',
			success : function(result) {
				console.info(result);
				if(result.code == -1){
					alert("该检查时间内没有数据");
				}else{
					
					// 基于准备好的dom，初始化echarts实例
					var gejinshi = echarts.init(document.getElementById('gejinshi'));
					var option = {
						 animation:false,
						 xAxis: {
							 type: 'category',
						     data: ["幼儿园","1年级","2年级","3年级","4年级","5年级","6年级","初一","初二","初三","高一","高二","高三"]
						  },
						  yAxis: {
						      type: 'value'
						  },
						  series: [{
						      /* itemStyle: {
					                normal: {
				                     // color: '#6cb041',
			                            lineStyle:{
			                             width:5//设置线条粗细
							            }
						            }
						        }, */
						        label: {
						            normal: {
						            	show: true,
						            	position: "top",
						            	formatter:'{c}%'			                                
						            },
						         },
						 		data: result.jinshi,
						 		 type: 'line'
						 }] 
						 			         
					}
					gejinshi.setOption(option);

						 	
					var gebuliang = echarts.init(document.getElementById('gebuliang'));
					var option = {
						 	animation:false,
						 	xAxis: {
						 	type: 'category',
						 		 data: ["幼儿园","1年级","2年级","3年级","4年级","5年级","6年级","初一","初二","初三","高一","高二","高三"]
						 	},
						 	yAxis: {
						 		type: 'value'
						 	},
						 	series: [{
						 		/* itemStyle: {
						             normal: {
						                // color: '#6cb041',
						               	lineStyle:{
						                    width:5//设置线条粗细
						                }
						              }
						     		}, */
						            label: {
						            	normal: {
						            		show: true,
						            		position: "top",
						            		formatter:'{c}%'			                                
						            	},
						            },
						 			data: result.buliang,
						 			 type: 'line'
						 	}] 
						 			         
					}
					gebuliang.setOption(option);
	
						 	
					var legendname = [];
					var data = [];
					for(var key in result.nianling){
						legendname.push(key);
						data.push({
							name: key,
							label: {
				            	normal: {
				            		show: true,
				            		position: "top",
				            		formatter:'{c}%'			                                

				            	},
				            },
							type: 'line',
							data:result.nianling[key]
				           });
					 }
							
					var nianling = echarts.init(document.getElementById('nianling'));
					var option = {
						 animation:false,
						 legend: {
					 		data:legendname,
					 		bottom : "2%",
					 	  },
						 xAxis: {
						 	type: 'category',
						 	data: ["幼儿园","小学","初中","高中"]
						 },
						  yAxis: {
						 	type: 'value'
						 },
						 series:data 
					} 
					nianling.setOption(option);
	
							
				    var xAxis = [];
					var nan = [];
					var nv = [];
					var nn = [];
					for(var key in result.nan){
						xAxis.push(key);
						nan.push(result.nan[key]);
					}
					for(var key in result.nv){
						nv.push(result.nv[key]);
					}
					nn.push({
						name: "男生",
						label: {
				        	normal: {
				        		show: true,
				        		position: "top",
				        		formatter:'{c}%'			                                
				        	},
				        },
						type: 'line',
						data:nan
				     });
					nn.push({
						name: "女生",
						label: {
				        	normal: {
				        		show: true,
				        		position: "top",
				        		formatter:'{c}%'			                                
				        	},
				        },
						type: 'line',
						data:nv
				     });
					var nannv = echarts.init(document.getElementById('nannv'));
					var option = {
						animation:false,
						legend: {
					 		data:["男生","女生"],
					 		bottom : "2%",
					 	},
					 	xAxis: {
							type: 'category',
							data: xAxis
						},
					 	yAxis: {
							type: 'value'
						},
						series:nn
				}
				nannv.setOption(option);
				
					
					$.ajax({
		 				type : 'POST',
		 				data : {
		 					"startDate":$("#startDate").val(),
		 			   		"endDate" :$("#endDate").val(),
		 					"gejinshi" : gejinshi.getDataURL(),
		 					"gebuliang" : gebuliang.getDataURL(),
		 					"nianling" : nianling.getDataURL(),
		 					"nannv" : nannv.getDataURL(),
		 				},
		 				url : '/studentReport/jiaoyujutu',
		 				success : function(result) {
		 					console.info(result);
		 					window.location.href= "/studentReport/dengdaijiaoyuju?startDate="+$("#startDate").val()
																				+"&endDate="+$("#endDate").val()
																				+"&date="+result;
		 			        
		 				}
		 			})
					/*$.ajax({
						type : 'POST',
						data : {
							"startDate":$("#startDate").val(),
					   		"endDate" :$("#endDate").val(),
						},
						url : '/studentReport/baogaojiaoyuju',
						success : function(result) {
							console.info(result);
							
						}
					});*/
						
					
				}
			}
		});
			
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			/*school : {
				required : true
			},*/
			startDate : {
				required : true
			},
			endDate : {
				required : true
			},
		},
		messages : {
			/*school : {
				required : icon + "选择学校"
			},*/
			startDate : {
				required : icon + "选择开始时间"
			},
			endDate : {
				required : icon + "选择结束时间"
			},
		}
	})
}