$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	var endTime = $("#endTime").val();
	var dateTime = new Date(endTime).getTime();  
	var compareDateTime = new Date().getTime();  
	if(endTime != null && compareDateTime > dateTime){  
		alert("结束时间应大于当前时间");
		return false;
	}
	var formData = new FormData(document.getElementById("signupForm"));
	$.ajax({
		cache : true,
		type : "POST",
		url : "/carousel/banner/save",
		data : formData,//$('#signupForm').serialize(), // 你的formid
		processData:false,
		contentType:false,
		async : false,
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name);
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
			name:{required : true}
		},
		messages : {
			name : {
				required : icon + "请输入名字"
			}
		}
	})
}