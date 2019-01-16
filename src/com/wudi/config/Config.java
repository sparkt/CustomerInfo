package com.wudi.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.wudi.controller.AdminController;
import com.wudi.controller.WeixinController;
import com.wudi.interceptor.AdminInterceptor;
import com.wudi.interceptor.WeixinIntercepter;
import com.wudi.model.NavsModel;
import com.wudi.model.UserModel;
import com.wudi.model.admin.AccountingModel;
import com.wudi.model.admin.AdminInfoModel;
import com.wudi.model.admin.ArchitectModel;
import com.wudi.model.admin.CourtClerkModel;
import com.wudi.model.admin.ForeignLanguageModel;
import com.wudi.model.admin.MandarinModel;
import com.wudi.model.admin.MedicalScienceModel;
import com.wudi.model.admin.PartTimePostgraduateModel;
import com.wudi.model.admin.ProfessionalModel;
import com.wudi.model.admin.SpecialPromotiomModel;
import com.wudi.model.admin.TeachercertificationModel;
import com.wudi.model.admin.UndergraduateModel;
import com.wudi.model.admin.UserInfoModel;
/**
 * 	系统配置类
 *   @author XIAO
 *   2018年10月26日10:57:14
 */
public class Config extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// 加载配置文件，注意：配置文件必须放在src目录下,要不然找不到
		loadPropertyFile("config.properties");
		// 配置一些系统变量
		me.setDevMode(getPropertyToBoolean("DevMode", true));//设置为开发模式，方便查看日志
		
	}

	@Override
	public void configRoute(Routes me) {
		// 设置路由，客户端访问就是在这里设置的路径地址
		me.add("/admin", AdminController.class,"WEB-INF/admin");//后台数据管理访问路径：localhost:8086/admin
		me.add("/wudi", WeixinController.class);//微信小程序访问路径：localhost:8086/wudi
		
		
	}

	@Override
	public void configEngine(Engine me) {

	}

	@Override
	public void configPlugin(Plugins me) {
		// 插入其他插件，比如，连接数据库的mysql插件和连接多数据库插件
		DruidPlugin dsMysql = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"),
				getProperty("password").trim());
		{
			dsMysql.setTestOnBorrow(true);
			dsMysql.setTestOnReturn(true);
			dsMysql.setMaxWait(20000);
		}
		//mysql插件
		ActiveRecordPlugin arpMysql = new ActiveRecordPlugin("mysql", dsMysql);
		//从配置文件里查找出来，是否显示sql语句
		boolean showSql = getPropertyToBoolean("showSql", true);
		//系统设置是否显示sql
		arpMysql.setShowSql(showSql);
		{
			//将数据库表，绑定到这来来，注意，表名和类要相对应
			arpMysql.addMapping("navs", NavsModel.class);//主页面左侧菜单显示的表
			arpMysql.addMapping("SpecialPromotiom", SpecialPromotiomModel.class);//用户信息表（高升专）
			arpMysql.addMapping("PartTimePostgraduate", PartTimePostgraduateModel.class);//用户信息表（非全日制研究生）
			arpMysql.addMapping("Mandarin", MandarinModel.class);//用户信息表（普通话培训）
			arpMysql.addMapping("Teachercertification", TeachercertificationModel.class);//用户信息表（普通话培训）
			arpMysql.addMapping("CourtClerk", CourtClerkModel.class);//用户信息表（法院书记员）
			arpMysql.addMapping("Accounting", AccountingModel.class);//用户信息表（法院书记员）
			arpMysql.addMapping("userinfo", UserInfoModel.class);//用户信息表
			arpMysql.addMapping("AdminInfo", AdminInfoModel.class);//管理员信息表
			arpMysql.addMapping("Architect", ArchitectModel.class);//用户信息表（建筑工程）
			arpMysql.addMapping("Professional", ProfessionalModel.class);//用户信息表（职业资格）
			arpMysql.addMapping("MedicalScience", MedicalScienceModel.class);//用户信息表（医药卫生）
			arpMysql.addMapping("ForeignLanguage", ForeignLanguageModel.class);//用户信息表（外语少儿）
			arpMysql.addMapping("Undergraduate", UndergraduateModel.class);//用户信息表（专升本）
			arpMysql.addMapping("user", UserModel.class);
		}
		//添加插件
		me.add(dsMysql);
		me.add(arpMysql);
	
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
		//添加拦截器
		me.add(new AdminInterceptor());//添加后台数据管理访问拦截器
		me.add(new WeixinIntercepter());//添加微信小程序访问拦截器
	}

	@Override
	public void configHandler(Handlers me) {
		
	}

}
