package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 如果程序启动报数据源相关的错误.则springboot程序启动
 * 时会根据pom.xml文件中的数据源的jar包加载相关配置
 * 但是jt-web项目只引用jar包,但是不需要链接数据源
 * 所以yml配置中没有该配置.导致报错.
 * 解决策略:
 * 	1.在yml文件中添加数据源配置
 * 	2.springboot容器启动时不加载数据源配置
 * @author ta
 *
 */
@SpringBootApplication
(exclude=DataSourceAutoConfiguration.class)
public class SpringBootRun {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRun.class, args);
	}
}
