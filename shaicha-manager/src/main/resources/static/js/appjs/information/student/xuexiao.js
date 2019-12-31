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
				"school" : $("#school").val(),
				"checkDate" :$("#checkDate").val(),
				"schoolNum":$("#schoolNum").val(),
			},
			url : '/studentReport/baogaoimg',
			success : function(result) {
				console.info(result);
				if(result.code == -1){
					alert("该学校在该检查时间没有数据");
				}else{
					
				// 基于准备好的dom，初始化echarts实例
				var overYear = echarts.init(document.getElementById('overYear'));
			 	var option = {
			 			 animation:false,
			 			 xAxis: {
			 				 type: 'category',
			 			     data: ["2017","2018","2019"]
			 			  },
			 			  yAxis: {
			 			      type: 'value'
			 			  },
			 			  series: [{
			 			      itemStyle: {
			                      normal: {
			                          // color: '#6cb041',
			                          lineStyle:{
			                                width:5//设置线条粗细
			                          }
			                       }
			                    },
			                    label: {
			            			normal: {
			            				show: true,
			            				position: "top",
			            				formatter:'{c}%'			                                

			            			},
			            		},
			 			        data: result.overYearMyopia,
			 			        //data: [79.3,61.1,33.5],
			 			        type: 'line'
			 			  }] 
			 			         
			 	  }
				  overYear.setOption(option);
				
				var gradeMyopia = echarts.init(document.getElementById('gradeMyopia'));	

			 			//console.info(result.gradeMyopia);
			 			var option = {
			 			animation:false,
			 				    color: ['#3398DB'],
			 				    legend: {
			 			            data:['系列一'],
			 			            bottom : "2%",
			 			        },
			 				    xAxis : [
			 				        {
			 				            type : 'category',
			 				            data : ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
			 				            axisTick: {
			 				                alignWithLabel: true
			 				            }
			 				        }
			 				    ],
			 				    yAxis : [
			 				        {
			 				            type : 'value'
			 				        }
			 				    ],
			 				    series : [{
			 				    	 label: {
			 							normal: {
			 								show: true,
			 								position: "top",
			 								formatter:'{c}%'			                                

			 							},
			 						},
			 				           // name:'系列一',
			 				            type:'bar',
			 				            barWidth: '30%',
			 				           data:result.gradeMyopia
			 				           //data: [0,0,33.2,0,0]
			 				        }]
			 				}
			 			
			 			gradeMyopia.setOption(option);

				
				var overYearGradeMyopia = echarts.init(document.getElementById('overYearGradeMyopia'));	

			 			var option = {
			 			animation:false,
			 				    legend: { 
			 				    	data : ['2017', '2018', '2019'],
			 				    	bottom : "1%"
			 				    	},
			 				    //tooltip: {},
			 				    xAxis: {
			 				    	type: 'category',
			 				    	data: ['一年级','二年级','三年级','四年级','五年级','六年级'],
			 				    	},
			 				    yAxis: {type: 'value'},
			 				    series: [
			 				             {
			 				            	label: {
			 				        			normal: {
			 				        				show: true,
			 				        				position: "top",
			 				        				formatter:'{c}%'			                                

			 				        			},
			 				        		},
			 				                 name:'2017',
			 				                 type:'bar',
			 				                 //data:result.seventeen
			 				                 data:[26.7,25.5,30.2,33.4,45.6,38.3]
			 				             },
			 				             {
			 				            	label: {
			 				        			normal: {
			 				        				show: true,
			 				        				position: "top",
			 				        				formatter:'{c}%'			                                

			 				        			},
			 				        		},
			 				                 name:'2018',
			 				                 type:'bar',
			 				                 data:result.eighteen
			 				                 //data:[23.5,24.7,28.2,30.8,40.1,30.7]
			 				             },
			 				             {
			 				            	 label: {
			 				        			normal: {
			 				        				show: true,
			 				        				position: "top",
			 				        				formatter:'{c}%'			                                

			 				        			},
			 				        		},
			 				                 name:'2019',
			 				                 type:'bar',
			 				                 data:result.nineteen
			 				                //data:[23.6,22.3,26.5,26.7,33.2,25.7]
			 				             },

			 				    ]
			 				};
			 			
			 			overYearGradeMyopia.setOption(option);

				
				var studentSexMyopia = echarts.init(document.getElementById('studentSexMyopia'));	
			 			var option = {
			 			animation:false,
			 				    color: ['#3398DB'],
			 				    legend: {
			 			            data:['男生','女生'],
			 			            bottom : "2%",
			 			        },
			 				    xAxis : [
			 				        {
			 				            type : 'category',
			 				            data : ['男生','女生'],
			 				            axisTick: {
			 				                alignWithLabel: true
			 				            }
			 				        }
			 				    ],
			 				    yAxis : [
			 				        {
			 				            type : 'value'
			 				        }
			 				    ],
			 				    series : [{
			 				    	 label: {
			 							normal: {
			 								show: true,
			 								position: "top",
			 								formatter:'{c}%'			                                

			 							},
			 						},
			 				    	   // name:'男生',
			 				            type:'bar',
			 				            barWidth: '50%',
			 				            data:result.studentSexMyopia
			 				           //data:[29.6,33.5]
			 				        }]
			 				}
			 			
			 			studentSexMyopia.setOption(option);

					// 基于准备好的dom，初始化echarts实例
				var overYearSexNan = echarts.init(document.getElementById('overYearSexNan'));
			 			var option = {
			 			animation:false,
			 					 xAxis: {
			 						 type: 'category',
			 			             data: ["2017","2018","2019"]
			 			         },
			 			         yAxis: {
			 			             type: 'value'
			 			         },
			 			         series: [{
			 			        	itemStyle: {
			                            normal: {
			                               // color: '#6cb041',
			                                lineStyle:{
			                                    width:5//设置线条粗细
			                                }
			                            }
			                         },
			                         label: {
			                 			normal: {
			                 				show: true,
			                 				position: "top",
			                 				formatter:'{c}%'			                                

			                 			},
			                 		},
			 			             data: result.studentSexNanMyopia,
			 			             type: 'line',
			 			            //data: [79.3,63.1,28.5],
			 			         }] 
			 			         
			 			}
				        
			 			overYearSexNan.setOption(option);
				
					// 基于准备好的dom，初始化echarts实例
				var overYearSexNv = echarts.init(document.getElementById('overYearSexNv'));
			 			var option = {
			 			animation:false,
			 					 xAxis: {
			 						 type: 'category',
			 			             data: ["2017","2018","2019"]
			 			         },
			 			         yAxis: {
			 			             type: 'value'
			 			         },
			 			         series: [{
			 			        	itemStyle: {
			                            normal: {
			                               // color: '#6cb041',
			                                lineStyle:{
			                                    width:5//设置线条粗细
			                                }
			                            }
			                         },
			                         label: {
			                 			normal: {
			                 				show: true,
			                 				position: "top",
			                 				formatter:'{c}%'			                                

			                 			},
			                 		},
			 			             data: result.studentSexNvMyopia,
			 			             type: 'line',
			 			            //data: [79.4,62.1,32.4],
			 			         }] 
			 			         
			 			}
				        
			 			overYearSexNv.setOption(option);

				var overYearGradeSex = echarts.init(document.getElementById('overYearGradeSex'));	
			 			var option = {
			 			animation:false,
			 				    legend: { 
			 				    	data : ['男生', '女生'],
			 				    	bottom : "1%"
			 				    	},
			 				    //tooltip: {},
			 				    xAxis: {
			 				    	type: 'category',
			 				    	data: ['一年级','二年级','三年级','四年级','五年级','六年级'],
			 				    	},
			 				    yAxis: {type: 'value'},
			 				    series: [
			 				             {
			 				            	 label: {
			 				        			normal: {
			 				        				show: true,
			 				        				position: "top",
			 				        				formatter:'{c}%'			                                

			 				        			},
			 				        		},
			 				                 name:'男生',
			 				                 type:'bar',
			 				                 data:result.overYearSexNan
			 				                //data:[0,0,33.2,0,0,0]
			 				             },
			 				             {
			 				            	 label: {
			 				        			normal: {
			 				        				show: true,
			 				        				position: "top",
			 				        				formatter:'{c}%'			                                

			 				        			},
			 				        		},
			 				                 name:'女生',
			 				                 type:'bar',
			 				                 data:result.overYearSexNv
			 				                //data:[0,0,32.5,0,0,0]
			 				             }
			 				    ]
			 				};
			 			
			 			overYearGradeSex.setOption(option);
						
			 			/*console.info(overYear.getDataURL()); 
						console.info(gradeMyopia.getDataURL()); 
						console.info(overYearGradeMyopia.getDataURL()); 
						console.info(studentSexMyopia.getDataURL()); 
						console.info(overYearSexNan.getDataURL()); 
						console.info(overYearSexNv.getDataURL()); 
						console.info(overYearGradeSex.getDataURL());*/
						
			 			$.ajax({
			 				type : 'POST',
			 				data : {
			 					"school" : $("#school").val(),
			 					"checkDate" :$("#checkDate").val(),
			 					"schoolNum":$("#schoolNum").val(),
			 					"overYear" : overYear.getDataURL(),
			 					"gradeMyopia" : gradeMyopia.getDataURL(),
			 					"overYearGradeMyopia" : overYearGradeMyopia.getDataURL(),
			 					"studentSexMyopia" : studentSexMyopia.getDataURL(),
			 					"overYearSexNan" : overYearSexNan.getDataURL(),
			 					"overYearSexNv" : overYearSexNv.getDataURL(),
			 					"overYearGradeSex" : overYearGradeSex.getDataURL(),
			 				},
			 				url : '/studentReport/xuexiaotu',
			 				success : function(result) {
			 					console.info(result);
			 					window.location.href="/studentReport/baogaoxuexiao?school="+$("#school").val()
																							+"&checkDate="+$("#checkDate").val()
																							+"&schoolNum="+$("#schoolNum").val()
																							+"&date="+result;
			 				}
			 			})
			 			
			 			/*parent.reLoad();
						var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
						parent.layer.close(index);*/
				}		
			}
		});
			
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			checkDate : {
				required : true
			},
			school : {
				required : true
			},
			schoolNum : {
				required : true
			},
		},
		messages : {
			checkDate : {
				required : icon + "选择检查时间"
			},
			school : {
				required : icon + "选择学校"
			},
			schoolNum : {
				required : icon + "请输入学校总人数"
			},
		}
	})
}