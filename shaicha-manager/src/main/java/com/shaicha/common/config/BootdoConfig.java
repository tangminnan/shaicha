package com.shaicha.common.config;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="shaicha")
public class BootdoConfig {
	//上传路径
	private String uploadPath;
	//poi导出word文件暂存路径
	private String poiword;

	public String getPoiword() {
		File file =new File(poiword);
		if(!file.exists())
			file.mkdirs();
		return poiword;
	}

	public void setPoiword(String poiword) {
		this.poiword = poiword;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
}
