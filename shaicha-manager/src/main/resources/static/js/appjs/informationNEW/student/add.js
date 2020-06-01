$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	
	var ideentityType = $("input[name='ideentityType']:checked").val();
	var identityCard = $("input[name='identityCard']").val();
	if(ideentityType=="身份证"){
		var date = new Date();
		var nowyear = date.getFullYear();
		var year = identityCard.substring(6, 10);
		var month = identityCard.substring(10, 12);
		var day = identityCard.substring(12, 14);
		if(year>=nowyear){
			alert("出生年份应当小于当前年份！！！");
			return false;
		}
		if(month>=13){
			alert("出生月份应当小于13！！！");
			return false;
		}
		if(day>=32){
			alert("出生天数应当小于32！！！");
			return false;
		}
		//var birthday = year+"-"+month+"-"+day
		//$("input[name='birthday']").val(birthday);
	}
	
	$.ajax({
		cache : true,
		type : "POST",
		url : "/informationNEW/student/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			activityId : {
				required : true
			},
			studentName : {
				required : true
			},
			studentSex : {
				required : true
			},
			nation : {
				required : true
			},
			ideentityType : {
				required : true
			},
			identityCard : {
				required : true
			},
			schoolId : {
				required : true
			},
			grade : {
				required : true
			},
			studentClass : {
				required : true
			},
			phone : {
				required : true
			},
			
		},
		messages : {
			activityId : {
				required : icon + "请选择活动名称"
			},
			studentName : {
				required : icon + "请输入姓名"
			},
			studentSex : {
				required : icon + "请选择性别"
			},
			nation : {
				required : icon + "请输入民族"
			},
			ideentityType : {
				required : icon + "请选择证件类型"
			},
			identityCard : {
				required : icon + "请输入身份证号"
			},
			schoolId : {
				required : icon + "请选择学校"
			},
			grade : {
				required : icon + "请输入年级"
			},
			studentClass : {
				required : icon + "请输入班级"
			},
			phone : {
				required : icon + "请输入联系方式"
			},
			
		}
	})
}