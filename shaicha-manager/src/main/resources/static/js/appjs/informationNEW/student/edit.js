$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/informationNEW/student/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);
				//parent.layer.msg("操作成功");
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
			
			studentName : {
				required : true
			},
		
			nation : {
				required : true
			},
			birthday : {
				required : true
			},
			identityCard : {
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
			xueBu: {
				required : true
			},
		},
		messages : {
			
			studentName : {
				required : icon + "请输入姓名"
			},
			
			nation : {
				required : icon + "请输入民族"
			},
			birthday : {
				required : icon + "请输入出生年月"
			},
			identityCard : {
				required : icon + "请输入身份证号"
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
			xueBu : {
				required : icon + "请输入学部"
			},
		}
	})
}