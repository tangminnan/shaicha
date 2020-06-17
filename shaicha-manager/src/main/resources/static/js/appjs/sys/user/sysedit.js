// 以下为官方示例
$().ready(function() {
	validateRule();
	// $("#signupForm").validate();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	
	var formData = new FormData(document.getElementById("signupForm"));
	$.ajax({
		cache : true,
		type : "POST",
		url : "/sys/user/sysupdate",
		data : formData,// 你的formid
		async : false,
		processData:false,
		contentType:false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				alert("操作成功！！！");
				//layer.msg(data.msg);
				//reLoad();
				//var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				//parent.layer.close(index);

			} else {
				alert("操作失败！！！");
				//parent.layer.msg(data.msg);
			}

		}
	});

}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			},
			username : {
				required : true,
				minlength : 2
			},
					},
		messages : {

			name : {
				required : icon + "请输入姓名"
			},
			username : {
				required : icon + "请输入您的用户名",
				minlength : icon + "用户名必须两个字符以上"
			},
			
		}
	})
}
