$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
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
		url : "/carousel/banner/update",
		data : formData,// 你的formid
		processData:false,
		contentType:false,
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
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入名字"
			}
		}
	})
}

function dateParse(dateString){  
    var SEPARATOR_BAR = "-";  
    var SEPARATOR_SLASH = "/";  
    var SEPARATOR_DOT = ".";  
    var dateArray;  
    if(dateString.indexOf(SEPARATOR_BAR) > -1){  
        dateArray = dateString.split(SEPARATOR_BAR);    
    }else if(dateString.indexOf(SEPARATOR_SLASH) > -1){  
        dateArray = dateString.split(SEPARATOR_SLASH);  
    }else{  
        dateArray = dateString.split(SEPARATOR_DOT);  
    }  
    return new Date(dateArray[0], dateArray[1]-1, dateArray[2]);   
};  