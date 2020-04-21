/*
Ajax 三级省市联动 [ {"city":[{"cityCode":"110100","cityName":"北京市","dist":[{"distCode":"110101","distName":"东城区"}]}],"provCode":"110000","provName":"北京"}]
http://code.ciaoca.cn/
日期：2012-7-18

settings 参数说明 
-----
url:省市数据josn文件路径
prov:默认省份
city:默认城市
dist:默认地区（县）
nodata:无数据状态
required:必选项
------------------------------ */
(function($){
	$.fn.areaSelect=function(settings){
		if(this.length<1){return;};
		// 默认值
		settings=$.extend({
			url:"",
			provCode:null,
			cityCode:null,
			distCode:null,
			nodata:null,
			required:false
		},settings);

		var box_obj=this;
		var prov_obj=box_obj.find("#province");
		var city_obj=box_obj.find("#city");
		var dist_obj=box_obj.find("#dist");
		var select_prehtml=(settings.required) ? "" : "<option value=''>请选择</option>";
		var city_json;
		
		// 赋值市级函数
		var cityStart=function(){
			var prov_id=prov_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
			};
			city_obj.empty().attr("disabled",true);
			dist_obj.empty().attr("disabled",true);

			var jsonSize = findJSONSize(city_json[prov_id].city); 
			if(prov_id<0 || jsonSize<=0){
				if(settings.nodata=="none"){
					city_obj.css("display","none");
					dist_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					city_obj.css("visibility","hidden");
					dist_obj.css("visibility","hidden");
				};
				return;
			};
			// 遍历赋值市级下拉列表
			var temp_html = "";
			$.each(city_json[prov_id].city,function(i,city){
				temp_html+="<option value='"+city.cityCode+"'>"+city.cityName+"</option>";
			});
			city_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
			distStart();
		};

		// 赋值地区（县）函数
		var distStart=function(){
			var prov_id=prov_obj.get(0).selectedIndex;
			var city_id=city_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
			};
			dist_obj.empty().attr("disabled",true);
			
			var jsonSize = findJSONSize(city_json[prov_id].city[city_id].dist); 
			if(prov_id<0 || city_id<0 || jsonSize <= 0){
				if(settings.nodata=="none"){
					dist_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					dist_obj.css("visibility","hidden");
				};
				return;
			};
			
			// 遍历赋值市级下拉列表
			var temp_html = "";
			$.each(city_json[prov_id].city[city_id].dist,function(i,dist){
				temp_html+="<option value='"+dist.distCode+"'>"+dist.distName+"</option>";
			});
			dist_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
		};

		var init=function(){
			// 遍历赋值省份下拉列表
			temp_html=select_prehtml;
			$.each(city_json,function(i,area){
				temp_html+="<option value='"+area.provCode+"'>"+area.provName+"</option>";
			});
			prov_obj.html(temp_html);
			
			// 若有传入省份与市级的值，则选中。（setTimeout为兼容IE6而设置）
			setTimeout(function(){
				if(settings.provCode!=null && settings.provCode!=""){
					prov_obj.val(settings.provCode);
					cityStart();
					setTimeout(function(){
						if(settings.cityCode!=null && settings.cityCode!=""){
							city_obj.val(settings.cityCode);
							distStart();
							setTimeout(function(){
								if(settings.distCode!=null && settings.distCode!=""){
									dist_obj.val(settings.distCode);
								};
							},1);
						};
					},1);
				};
			},1);

			// 选择省份时发生事件
			prov_obj.bind("change",function(){
				cityStart();
			});

			// 选择市级时发生事件
			city_obj.bind("change",function(){
				distStart();
			});
		};

		// 设置省市json数据
		if(typeof(settings.url)=="string"){
			$.getJSON(settings.url,function(json){
				city_json=json;
				init();
			});
		}else{
			city_json=settings.url;
			init();
		};
	};
})(jQuery);

//获取json的元素数量
function findJSONSize(json){
	var size = 0;
	for (key in json) {
		if (json.hasOwnProperty(key)) size++;
	}
	
	return size;
}