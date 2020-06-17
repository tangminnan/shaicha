$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	
	 var provincename = $("#eprovinceName option:selected").val();
     var provincecode=$("#eprovinceName").find("option:selected").attr("data-code");
	 var cityname = $("#ecityName option:selected").val();
	 var citycode=$("#ecityName").find("option:selected").attr("data-code");
	 var areaname = $("#edistrictName option:selected").val();
	 var areacode=$("#edistrictName").find("option:selected").attr("data-code");
	 $("input[name='provincename']").val(provincename);
	 $("input[name='provincecode']").val(provincecode);
	 $("input[name='cityname']").val(cityname);
	 $("input[name='citycode']").val(citycode);
	 $("input[name='areaname']").val(areaname);
	 $("input[name='areacode']").val(areacode);
	
	$.ajax({
		cache : true,
		type : "POST",
		url : "/information/school/update",
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
			/*orgcode : {
				required : true
			},*/
			orgname : {
				required : true
			},
			provincename : {
				required : true
			},
			cityname : {
				required : true
			},
			areaname : {
				required : true
			},
			xuebu : {
				required : true
			}
		},
		messages : {
			orgcode : {
				required : icon + "请输入学校编码"
			},
			orgname : {
				required : icon + "请输入学校名称"
			},
			provincename : {
				required : icon + "请选择省份"
			},
			cityname : {
				required : icon + "请选择城市"
			},
			areaname : {
				required : icon + "请选择市区县"
			},
			xuebu : {
				required : icon + "请输入学部"
			},
		}
	})
}