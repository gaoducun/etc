package com.platform.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.platform.index.IndexController;

public class JfinalConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		PropKit.use("config.properties");
		me.setDevMode(false);
	}

	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configHandler(Handlers arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configInterceptor(Interceptors arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		DruidPlugin dp = new DruidPlugin(PropKit.get("oracle.url").trim(),PropKit.get("username").trim(), PropKit.get("password").trim());
	    me.add(dp);
	    //配置Oracle驱动
	    dp.setDriverClass("oracle.jdbc.driver.OracleDriver");
	    
	    ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
	    
	    // 配置Oracle方言
	    arp.setDialect(new OracleDialect());
	    // 配置属性名(字段名)大小写不敏感容器工厂
	    arp.setContainerFactory(new CaseInsensitiveContainerFactory());
	    me.add(arp);
	    
	    
	    /**
	  		 * 配置电子影像系统的数据库连接
	  		 */
	  	    
	  	    //DruidPlugin apps = new DruidPlugin("jdbc:oracle:thin:@10.254.21.7:1521:evsjbo","apps", "apps");
	        DruidPlugin apps = new DruidPlugin("jdbc:oracle:thin:@10.254.25.48:1521:evsjboTest","apps", "apps");
	  	    me.add(apps);
	  	    //配置Oracle驱动
	  	    apps.setDriverClass("oracle.jdbc.driver.OracleDriver");
	  	    
	  	    ActiveRecordPlugin arps = new ActiveRecordPlugin("apps", apps);
	  	    
	  	    // 配置Oracle方言
	  	    arps.setDialect(new OracleDialect());
	  	 // 配置属性名(字段名)大小写不敏感容器工厂
	  	    arps.setContainerFactory(new CaseInsensitiveContainerFactory());
	  	    me.add(arps);
	  	    
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add("/", IndexController.class,"/index/");
	}

}
