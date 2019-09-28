$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	/*var phonePattern = /^1\d{10}$/; //手机号 /^1[3|5|7|8]\d{9}$/
*/	if( $('#name').val()==null || $('#name').val()=="" ){
		layer.msg("请输入小区名称"); return;
	}
	if( $('#userName').val()==null || $('#userName').val()=="" ){
		layer.msg("请选择负责人"); return;
	}
	/*if( $('#phone').val()==null || $('#phone').val()=="" ){
		layer.msg("请输入联系人电话"); return;
	}
	if(phonePattern.test($('#phone').val())==false){
		layer.msg("请输入正确手机号");
		return false;
	}*/
	if( $('#province').val()==null || $('#province').val()=="" ||$('#city').val()==null || $('#city').val()==""||$('#detailedAddress').val()==null || $('#detailedAddress').val()=="" ){
		layer.msg("请输入小区地址"); return;
	}
	if( $('#towerNum').val()==null || $('#towerNum').val()=="" ){
		layer.msg("请输入小区楼数"); return;
	}
	if( $('#unitNum').val()==null || $('#unitNum').val()=="" ){
		layer.msg("请输入小区单元数"); return;
	}
	if( $('#tierNum').val()==null || $('#tierNum').val()=="" ){
		layer.msg("请输入小区层数"); return;
	}
//	if( $('#propertyFee').val()==null || $('#propertyFee').val()=="" ){
//		layer.msg("请输入小区物业费"); return;
//	}
	$.ajax({
		cache : true,
		type : "POST",
		url : "/plot/save",
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
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}