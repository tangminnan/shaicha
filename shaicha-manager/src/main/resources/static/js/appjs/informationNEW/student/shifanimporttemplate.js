$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		daoru();
	}
});
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
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			activityId : {
				required : true
			},
			schoolId : {
				required : true
			},
			file : {
				required : true
			},
		},
		messages : {
			activityId : {
				required : icon + "请选择活动名称"
			},
			schoolId : {
				required : icon + "请选择学校"
			},
			file : {
				required : icon + "请选择文件"
			},
		}
	})
}