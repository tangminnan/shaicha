package com.shaicha.system.config;

import com.google.common.collect.Maps;
import com.shaicha.system.filter.CorsFilter;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 跨域过滤拦截器
 */
@Configuration
public class CorsConfig {
	@Bean
	public CorsFilter remoteIpFilter() {
		return new CorsFilter();
	}

	/**
	 * 跨域过滤拦截器
	 *//*
	@Bean
	public FilterRegistrationBean xssFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new CorsFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		Map<String, String> initParameters = Maps.newHashMap();
		initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
		filterRegistrationBean.setInitParameters(initParameters);
		return filterRegistrationBean;
	}*/
}  
