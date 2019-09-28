package com.shaicha.common.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/validate")
public class UploadUtils {
   
	@RequestMapping("/repeat")
	public R test(String method,String property) throws Exception, Exception, Exception{
		Class<?> cla  = Class.forName(method);
		Object obj = cla.newInstance();
		Method list=cla.getMethod("list",Map.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(property,property);
		Object o = list.invoke(obj, map);
		return R.ok();
	}
}
