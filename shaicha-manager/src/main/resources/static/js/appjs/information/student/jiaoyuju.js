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
				"startDate":$("#startDate").val(),
		   		"endDate " :("#endDate").val(),
			},
			url : '/studentReport/baogao',
			success : function(result) {
				console.info(result);
				if(result.code == -1.0){
					alert("该学校在该检查时间没有数据");
				}else{
					
				}
			}
		});
			
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			school : {
				required : true
			},
			startDate : {
				required : true
			},
			endDate : {
				required : true
			},
		},
		messages : {
			school : {
				required : icon + "选择学校"
			},
			startDate : {
				required : icon + "选择开始时间"
			},
			endDate : {
				required : icon + "选择结束时间"
			},
		}
	})
}