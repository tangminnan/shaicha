$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	
	var type = $("input[name='type']:checked").val();
	if(type == 1){//学校
		xuexiaobaogao();
	}
	if(type == 2){
		jiaoyujubaogao();
	}	
	if(type == 3){
		var activityId = $('#school').val();
		var school = $('#school option:selected').text();
		var grade = $('#grade option:selected').val();
		if(grade == null || grade == ""){
			alert("请选择年级！！！");
			return false;
		}
		window.location.href= "/studentReportNew/dengdaigrade?activityId="+activityId
														+"&school="+school
														+"&grade="+grade
														+"&type="+type
	}
	if(type == 4){
		var activityId = $('#school').val();
		var school = $('#school option:selected').text();
		var grade = $('#grade option:selected').val();
		var stuclass = $('#stuclass option:selected').val();
		
		if(stuclass == null || stuclass == ""){
			alert("请选择班级！！！");
			return false;
		}
		window.location.href= "/studentReportNew/dengdaigrade?activityId="+activityId
															+"&school="+school
															+"&grade="+grade
															+"&stuclass="+stuclass
															+"&type="+type
	}
	if(type == 5){
		var activityId = $('#activityId option:selected').val();
        var school = $('#school option:selected').text();
        var Stype = $('#Stype option:selected').val();

        window.location.href= "/studentReportNew/dengdaigrade?activityId="+activityId
															+"&type="+type
                                                             +"&school="+school
                                                             +"&Stype="+Stype
	}
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			type : {
				required : true
			},
			school : {
				required : true
			},
			activityId : {
				required : true
			},
		},
		messages : {
			type : {
				required : icon + "选择类型"
			},
			school : {
				required : icon + "选择学校"
			},
			activityId : {
				required : icon + "请先选择活动"
			},
		}
	})
}




function xuexiaobaogao(){
	
	$.ajax({
		type : 'POST',
		data : {
			"activityId" : $('#school option:selected').val(),
			"school" :$('#school option:selected').text()
			//"checkDate" :$("#checkDate").val(),
			//"schoolNum":$("#schoolNum").val(),
		},
		url : '/studentReportNew/baogaoimg',
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
                    data: result.year
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter:'{value}%'
                    }
                },
                series: [{
                    data: result.overYearMyopia,
                    type: 'line',
                    symbol: 'triangle',
                    symbolSize: 20,
                    lineStyle: {
                        color: 'green',
                        width: 4,
                        type: 'dashed'
                    },
                    itemStyle: {
                        borderWidth: 3,
                        // borderColor: 'yellow',
                        color: 'red'
                    },
                    label: {
                        normal: {
                            show: true,
                            position: "top",
                            formatter:'{c}%'
                        },
                    }
                }]


		 	  }
			  overYear.setOption(option);



			var overYearGradeMyopia = echarts.init(document.getElementById('overYearGradeMyopia'));

		 			var option = {
		 			animation:false,
		 				    legend: {
		 				    	data : result.year,
		 				    	bottom : "1%"
		 				    	},
		 				    //tooltip: {},
		 				    xAxis: {
		 				    	type: 'category',
		 				    	data: result.grade,
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
		 				                 name:result.year[0],
		 				                 type:'bar',
		 				                 data:result.seventeen
		 				                 //data:[26.7,25.5,30.2,33.4,45.6,38.3]
		 				             },
		 				             {
		 				            	label: {
		 				        			normal: {
		 				        				show: true,
		 				        				position: "top",
		 				        				formatter:'{c}%'

		 				        			},
		 				        		},
		 				                 name:result.year[1],
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
		 				                 name:result.year[2],
		 				                 type:'bar',
		 				                 data:result.nineteen
		 				                //data:[23.6,22.3,26.5,26.7,33.2,25.7]
		 				             },

		 				    ]
		 				};

		 			overYearGradeMyopia.setOption(option);




				// 基于准备好的dom，初始化echarts实例
			var overYearSexNv = echarts.init(document.getElementById('overYearSexNv'));
		 			var option = {
                        animation:false,
                        legend: {
                            data : result.year,
                            bottom : "1%"
                        },
                        //tooltip: {},
                        xAxis: {
                            type: 'category',
                            data: ['男生','女生'],
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
                                name:result.year[0],
                                type:'bar',
                                data:[result.studentSexNanMyopia[0],result.studentSexNvMyopia[0]]
                                //data:[26.7,25.5,30.2,33.4,45.6,38.3]
                            },
                            {
                                label: {
                                    normal: {
                                        show: true,
                                        position: "top",
                                        formatter:'{c}%'

                                    },
                                },
                                name:result.year[1],
                                type:'bar',
                                data:[result.studentSexNanMyopia[1],result.studentSexNvMyopia[1]]
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
                                name:result.year[2],
                                type:'bar',
                                data:[result.studentSexNanMyopia[2],result.studentSexNvMyopia[2]]
                                //data:[23.6,22.3,26.5,26.7,33.2,25.7]
                            },

                        ]

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
		 				    	data: result.grade,
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

		 			var shangcibulingjinshi = echarts.init(document.getElementById('shangcibulingjinshi'));

		 			var option = {
                        animation:false,
                        legend: {
                            data : result.year,
                            bottom : "1%"
                        },
                        //tooltip: {},
                        xAxis: {
                            type: 'category',
                            data: result.grade,
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
                                name:result.year[0],
                                type:'bar',
                                data:result.qu
                                //data:[26.7,25.5,30.2,33.4,45.6,38.3]
                            },
                            {
                                label: {
                                    normal: {
                                        show: true,
                                        position: "top",
                                        formatter:'{c}%'

                                    },
                                },
                                name:result.year[1],
                                type:'bar',
                                data:result.jin
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
                                name:result.year[2],
                                type:'bar',
                                data:result.ming
                                //data:[23.6,22.3,26.5,26.7,33.2,25.7]
                            },

                        ]
				 				};

		 			shangcibulingjinshi.setOption(option);


                $.ajax({
                    type : 'POST',
                    data : {
                        "activityId" : $('#school option:selected').val(),
                        "school" :$('#school option:selected').text(),
                        //"checkDate" :$("#checkDate").val(),
                        //"schoolNum":$("#schoolNum").val(),
                        "overYear" : overYear.getDataURL(),
                        // "gradeMyopia" : gradeMyopia.getDataURL(),
                        "overYearGradeMyopia" : overYearGradeMyopia.getDataURL(),
                        // "studentSexMyopia" : studentSexMyopia.getDataURL(),
                        // "overYearSexNan" : overYearSexNan.getDataURL(),
                        "overYearSexNv" : overYearSexNv.getDataURL(),
                        "overYearGradeSex" : overYearGradeSex.getDataURL(),
                        "shangcibulingjinshi" : shangcibulingjinshi.getDataURL(),
                    },
                    url : '/studentReportNew/xuexiaotu',
                    success : function(result) {

                        window.location.href= "/studentReportNew/dengdaixuexiao?school="+$('#school option:selected').text()
                            +"&activityId="+$('#school option:selected').val()
                            // +"&checkDate="+$("#checkDate").val()
                            // +"&schoolNum="+$("#schoolNum").val()
                            +"&date="+result;

                    }
                })

			}		
		}
	});
	
}






function jiaoyujubaogao(){
	
	var activityId = $('#activityId option:selected').val();
	 var choosess = [];
     $('#school option:selected').each(function(){
    	 choosess.push($(this).text());
     })
     console.log(choosess); 
     $.ajax({
		type : 'POST',
		dataType: 'json',
		data : {
			"activityId" : activityId,
			"school" :choosess
		},
		url : '/studentReportNew/baogaojyjimg',
		success : function(result) {
			console.info(result);
			if(result.code == -1){
				alert("该检查时间内没有数据");
			}else{
                if (1==1) {
                    var list = result.list;
                    for (var i = 0;i<list.length;i++){
                        var resulte = list[i];
                        // 基于准备好的dom，初始化echarts实例
                        var overYear = echarts.init(document.getElementById('overYear'));
                        var option = {
                            animation:false,
                            xAxis: {
                                type: 'category',
                                data: resulte.year
                            },
                            yAxis: {
                                type: 'value',
                                axisLabel: {
                                    formatter:'{value}%'
                                }
                            },
                            series: [{
                                data: resulte.overYearMyopia,
                                type: 'line',
                                symbol: 'triangle',
                                symbolSize: 20,
                                lineStyle: {
                                    color: 'green',
                                    width: 4,
                                    type: 'dashed'
                                },
                                itemStyle: {
                                    borderWidth: 3,
                                    // borderColor: 'yellow',
                                    color: 'red'
                                },
                                label: {
                                    normal: {
                                        show: true,
                                        position: "top",
                                        formatter:'{c}%'
                                    },
                                }
                            }]


                        }
                        overYear.setOption(option);

                        var overYearGradeMyopia = echarts.init(document.getElementById('overYearGradeMyopia'));

                        var option = {
                            animation:false,
                            legend: {
                                data : resulte.year,
                                bottom : "1%"
                            },
                            //tooltip: {},
                            xAxis: {
                                type: 'category',
                                data: result.grade,
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
                                    name:resulte.year[0],
                                    type:'bar',
                                    data:resulte.seventeen
                                    //data:[26.7,25.5,30.2,33.4,45.6,38.3]
                                },
                                {
                                    label: {
                                        normal: {
                                            show: true,
                                            position: "top",
                                            formatter:'{c}%'

                                        },
                                    },
                                    name:resulte.year[1],
                                    type:'bar',
                                    data:resulte.eighteen
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
                                    name:resulte.year[2],
                                    type:'bar',
                                    data:resulte.nineteen
                                    //data:[23.6,22.3,26.5,26.7,33.2,25.7]
                                },

                            ]
                        };

                        overYearGradeMyopia.setOption(option);

                        // 基于准备好的dom，初始化echarts实例
                        var overYearSexNv = echarts.init(document.getElementById('overYearSexNv'));
                        var option = {
                            animation:false,
                            legend: {
                                data : resulte.year,
                                bottom : "1%"
                            },
                            //tooltip: {},
                            xAxis: {
                                type: 'category',
                                data: ['男生','女生'],
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
                                    name:resulte.year[0],
                                    type:'bar',
                                    data:[resulte.studentSexNanMyopia[0],resulte.studentSexNvMyopia[0]]
                                    //data:[26.7,25.5,30.2,33.4,45.6,38.3]
                                },
                                {
                                    label: {
                                        normal: {
                                            show: true,
                                            position: "top",
                                            formatter:'{c}%'

                                        },
                                    },
                                    name:resulte.year[1],
                                    type:'bar',
                                    data:[resulte.studentSexNanMyopia[1],resulte.studentSexNvMyopia[1]]
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
                                    name:resulte.year[2],
                                    type:'bar',
                                    data:[resulte.studentSexNanMyopia[2],resulte.studentSexNvMyopia[2]]
                                    //data:[23.6,22.3,26.5,26.7,33.2,25.7]
                                },

                            ]

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
                                data: resulte.grade,
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
                                    data:resulte.overYearSexNan
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
                                    data:resulte.overYearSexNv
                                    //data:[0,0,32.5,0,0,0]
                                }
                            ]

                        };

                        overYearGradeSex.setOption(option);

                        var shangcibulingjinshi = echarts.init(document.getElementById('shangcibulingjinshi'));

                        var option = {
                            animation:false,
                            legend: {
                                data : resulte.year,
                                bottom : "1%"
                            },
                            //tooltip: {},
                            xAxis: {
                                type: 'category',
                                data: resulte.grade,
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
                                    name:resulte.year[0],
                                    type:'bar',
                                    data:resulte.qu
                                    //data:[26.7,25.5,30.2,33.4,45.6,38.3]
                                },
                                {
                                    label: {
                                        normal: {
                                            show: true,
                                            position: "top",
                                            formatter:'{c}%'

                                        },
                                    },
                                    name:resulte.year[1],
                                    type:'bar',
                                    data:resulte.jin
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
                                    name:resulte.year[2],
                                    type:'bar',
                                    data:resulte.ming
                                    //data:[23.6,22.3,26.5,26.7,33.2,25.7]
                                },

                            ]
                        };

                        shangcibulingjinshi.setOption(option);


                        $.ajax({
                            type : 'POST',
                            data : {
                                "activityId" : $('#school option:selected').val(),
                                "school" :resulte.school,
                                //"checkDate" :$("#checkDate").val(),
                                //"schoolNum":$("#schoolNum").val(),
                                "overYear" : overYear.getDataURL(),
                                // "gradeMyopia" : gradeMyopia.getDataURL(),
                                "overYearGradeMyopia" : overYearGradeMyopia.getDataURL(),
                                // "studentSexMyopia" : studentSexMyopia.getDataURL(),
                                // "overYearSexNan" : overYearSexNan.getDataURL(),
                                "overYearSexNv" : overYearSexNv.getDataURL(),
                                "overYearGradeSex" : overYearGradeSex.getDataURL(),
                                "shangcibulingjinshi" : shangcibulingjinshi.getDataURL(),
                            },
                            url : '/studentReportNew/xuexiaotu',
                            success : function(data) {

                                // window.location.href= "/studentReportNew/dengdaixuexiao?school="+$('#school option:selected').text()
                                //     +"&activityId="+$('#school option:selected').val()
                                //     // +"&checkDate="+$("#checkDate").val()
                                //     // +"&schoolNum="+$("#schoolNum").val()
                                //     +"&date="+result;

                            }
                        })
                    }
                }
                if (2==2) {
                    var gejinshi = echarts.init(document.getElementById('gejinshi'));
                    var option = {
                        animation: false,
                        xAxis: {
                            type: 'category',
                            data: ["幼儿园", "1年级", "2年级", "3年级", "4年级", "5年级", "6年级", "初一", "初二", "初三", "高一", "高二", "高三"]
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [{
                            label: {
                                normal: {
                                    show: true,
                                    position: "top",
                                    formatter: '{c}%'
                                },
                            },
                            data: result.jinshi,
                            type: 'line'
                        }]

                    }
                    gejinshi.setOption(option);


                    var gebuliang = echarts.init(document.getElementById('gebuliang'));
                    var option = {
                        animation: false,
                        xAxis: {
                            type: 'category',
                            data: ["幼儿园", "1年级", "2年级", "3年级", "4年级", "5年级", "6年级", "初一", "初二", "初三", "高一", "高二", "高三"]
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
                                    formatter: '{c}%'
                                },
                            },
                            data: result.buliang,
                            type: 'line'
                        }]

                    }
                    gebuliang.setOption(option);


                    var legendname = [];
                    var data = [];
                    for (var key in result.nianling) {
                        legendname.push(key);
                        data.push({
                            name: key,
                            label: {
                                normal: {
                                    show: true,
                                    position: "top",
                                    formatter: '{c}%'

                                },
                            },
                            type: 'line',
                            data: result.nianling[key]
                        });
                    }

                    // var nianling = echarts.init(document.getElementById('nianling'));
                    // var option = {
                    //     animation: false,
                    //     legend: {
                    //         data: legendname,
                    //         bottom: "2%",
                    //     },
                    //     xAxis: {
                    //         type: 'category',
                    //         data: ["幼儿园", "小学", "初中", "高中"]
                    //     },
                    //     yAxis: {
                    //         type: 'value'
                    //     },
                    //     series: data
                    // }
                    var nianling = echarts.init(document.getElementById('nianling'));
                    var option = {
                        animation: false,
                        title: {
                            text: '各学部历年近视率'
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['幼儿园', '小学', '初中', '高中']
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        // toolbox: {
                        //     feature: {
                        //         saveAsImage: {}
                        //     }
                        // },
                        xAxis: {
                            type: 'category',
                            data: result.nianling.checkyear
                        },
                        yAxis: {
                            axisLine: {
                                show: true
                            },
                            type: 'value'
                        },
                        series: [
                            {
                                name: '幼儿园',
                                type: 'line',
                                label: {
                                    normal: {
                                        show: true,
                                        position: "top",
                                        formatter:'{c}%'
                                    },
                                },
                                data: result.nianling.youer
                            },
                            {
                                name: '小学',
                                type: 'line',
                                label: {
                                    normal: {
                                        show: true,
                                        position: "top",
                                        formatter:'{c}%'
                                    },
                                },
                                data: result.nianling.xiaoxue
                            },
                            {
                                name: '初中',
                                type: 'line',
                                label: {
                                    normal: {
                                        show: true,
                                        position: "top",
                                        formatter:'{c}%'
                                    },
                                },
                                data: result.nianling.chuzhong
                            },
                            {
                                name: '高中',
                                type: 'line',
                                label: {
                                    normal: {
                                        show: true,
                                        position: "top",
                                        formatter:'{c}%'
                                    },
                                },
                                data: result.nianling.gaozhong
                            }
                        ]
                    };
                    nianling.setOption(option);


                    // var xAxis = [];
                    // var nan = [];
                    // var nv = [];
                    // var nn = [];
                    // for (var key in result.nan) {
                    //     xAxis.push(key);
                    //     nan.push(result.nan[key]);
                    // }
                    // for (var key in result.nv) {
                    //     nv.push(result.nv[key]);
                    // }
                    // nn.push({
                    //     name: "男生",
                    //     label: {
                    //         normal: {
                    //             show: true,
                    //             position: "top",
                    //             formatter: '{c}%'
                    //         },
                    //     },
                    //     type: 'line',
                    //     data: nan
                    // });
                    // nn.push({
                    //     name: "女生",
                    //     label: {
                    //         normal: {
                    //             show: true,
                    //             position: "top",
                    //             formatter: '{c}%'
                    //         },
                    //     },
                    //     type: 'line',
                    //     data: nv
                    // });
                    // var nannv = echarts.init(document.getElementById('nannv'));
                    // var option = {
                    //     animation: false,
                    //     legend: {
                    //         data: ["男生", "女生"],
                    //         bottom: "2%",
                    //     },
                    //     xAxis: {
                    //         type: 'category',
                    //         data: xAxis
                    //     },
                    //     yAxis: {
                    //         type: 'value'
                    //     },
                    //     series: nn
                    // }
                    // nannv.setOption(option);

                    $.ajax({
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            "activityId": activityId,
                            "school": choosess,
                            "gejinshi": gejinshi.getDataURL(),
                            "gebuliang": gebuliang.getDataURL(),
                            "nianling": nianling.getDataURL(),
                            // "nannv": nannv.getDataURL(),
                        },
                        url: '/studentReportNew/jiaoyujutu',
                        success: function (result) {
                            console.info(result);
                            window.location.href = "/studentReportNew/dengdaijiaoyuju?activityId=" + activityId
                                + "&school=" + choosess
                                + "&date=" + result;
                        }
                    })
                }
			}
		}
     })
}
