package com.wudi.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.wudi.model.admin.AccountingModel;
import com.wudi.model.admin.CourtClerkModel;
import com.wudi.model.admin.MandarinModel;
import com.wudi.model.admin.PartTimePostgraduateModel;
import com.wudi.model.admin.SpecialPromotiomModel;
import com.wudi.model.admin.TeachercertificationModel;
import com.wudi.model.admin.*;


/**
 * 微信小程序数据访问
 * @author 李金鹏
 * 2018年10月26日11:10:01
 */
public class WeixinController extends Controller {
	/**
	 * 默认获取数据的地方
	 */
	public void index() {
		setAttr("result", "你好，无敌小团队微信小程序路径！");
		renderJson();
	}
	
	
	
	
	/**
	 * 微信端注册入口
	 * @author 张志强
	 * @Description: TODO 录入用户注册信息
	 * 给微信端发送提示信息
	 */
	public void saveUserinfo() {
		
		String user_name = getPara("user_name");
		String user_password = getPara("user_password");
		String user_sex = getPara("user_sex");
		String phone_no = getPara("phone_no");
		if(user_sex.equals("0")) {
			user_sex="男";
		}else {
			user_sex="女";
		}
		int code =0; //注册不成功
		String info ="注册不成功";
		UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
		if(m!=null) {
			code =1;//用户已经存在
			info = "用户已经存在";
		}else {

			boolean result = new UserInfoModel().saveUserinfo(user_name, user_password, user_sex, phone_no,"0","0","0");
			if(result) {
				code=2;//注册成功
				info = "注册成功";
			}
		}
		setAttr("code", code);
		setAttr("info", info);
		renderJson();
		}
	
	/**
	 * 微信登录入口
	 * @author 张志强
	 * 
	 * GET phone_no &user_password
	 * 
	 * */
	
	public void userLogin() {
		String phone_no =getPara("phone_no");
		String user_password = getPara("user_password");
		int code=0;
		int type =0;//1普通用户，2管理员
		String info = "用户不存在";
		//查询用户表phone_no
		UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
		//查询管理员表admin_phone_no
		AdminInfoModel n = new AdminInfoModel().getphone_no(phone_no);
		if (n!=null||m!=null) {
			if (n!=null) {
				if(n.getAdmin_password().equals(user_password)) {
					type =1;//
					code=1;//密码正确
					info ="密码正确";
				}else {
					//密码错误
					type=0;
					code=0;
					info = "密码错误";
				}
				
			}else {
				if(m.getUser_password().equals(user_password)) {
					type =2;//
					code=1;//密码正确
					info ="密码正确";
				}else {
					//密码错误
					type=0;
					code=0;
					info = "密码错误";
				}
				
			}
			
		}else {
			//不存在
			code=2;
			info = "用户不存在";
		}
		setAttr("code", code);
		setAttr("info", info);
		setAttr("type", type);
		renderJson();
	}
	
	
	
	
	
	
	/**
	 * DOTO：微信端获取并删除高升专客户信息
	 * @author 李金鹏
	 */
	public void getSpecialPromotiom() {
		String phone_no=getPara("phone_no");		
        List<SpecialPromotiomModel> result = SpecialPromotiomModel.findModelbyPhone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
		SpecialPromotiomModel.delSpecialPromotiomId(id);
		
		}
	
	/**
	 * DOTO：微信端获取并删除财会经济客户信息
	 * @author 李金鹏
	 */
	public void getAccounting() {
		String phone_no=getPara("phone_no");		
        List<AccountingModel> result = AccountingModel.findModelbyPhone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
        AccountingModel.delAccountingById(id);
//	    AccountingModel.delAccountingById(id);

		}
	
	/**
	 * DOTO：微信端获取并删除财会经济客户信息
	 * @author 李金鹏
	 */
	public void getCourtClerk() {
		String phone_no=getPara("phone_no");		
        List<CourtClerkModel> result = CourtClerkModel.findModelbyPhone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
        CourtClerkModel.delCourtClerkById(id);

		}
	/**
	 * DOTO：微信端获取并删除普通话培训客户信息
	 * @author 李金鹏
	 */
	public void getMandarin() {
		String phone_no=getPara("phone_no");		
        List<MandarinModel> result = MandarinModel.findModelbyPhone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
        MandarinModel.delMandarinById(id);

		}
	
	/**
	 * DOTO：微信端获取并删除非全日制研究生训客户信息
	 * @author 李金鹏
	 */
	public void getPartTimePostgraduate() {
		String phone_no=getPara("phone_no");		
        List<PartTimePostgraduateModel> result = PartTimePostgraduateModel.findModelbyPhone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
	    PartTimePostgraduateModel.delPartTimePostgraduateById(id);

		}
	
	/**
	 * DOTO：微信端获取并删除非全日制研究生训客户信息
	 * @author 李金鹏
	 */
	public void getTeachercertification() {
		String phone_no=getPara("phone_no");		
        List<TeachercertificationModel> result =TeachercertificationModel.findModelbyphone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
	    TeachercertificationModel.delTeachercertificationById(id);

		}
	/**
	 * DOTO：微信端获取并删除专升本客户信息
	 * @author 王驰
	 */
	public void getUndergraduate() {
		String phone_no=getPara("phone_no");		
        List<UndergraduateModel> result = UndergraduateModel.findModelbyPhone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
		UndergraduateModel.delUndergraduateById(id);
		
		}
	
	/**
	 * DOTO：微信端获取并删除建筑工程客户信息
	 * @author 王驰
	 */
	public void getArchitect() {
		String phone_no=getPara("phone_no");		
        List<ArchitectModel> result = ArchitectModel.findModelbyPhone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
	    ArchitectModel.delArchitectById(id);
//	    AccountingModel.delAccountingById(id);

		}
	
	/**
	 * DOTO：微信端获取并删除职业资格客户信息
	 * @author 王驰
	 */
	public void getProfessional() {
		String phone_no=getPara("phone_no");		
        List<ProfessionalModel> result = ProfessionalModel.findModelbyPhone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
		ProfessionalModel.delProfessionalById(id);
		
		}
	
	/**
	 * DOTO：微信端获取并删除外语少儿客户信息
	 * @author 王驰
	 */
	public void getForeignLanguage() {
		String phone_no=getPara("phone_no");		
        List<ForeignLanguageModel> result = ForeignLanguageModel.findModelbyPhone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
		ForeignLanguageModel.delForeignLanguageById(id);
		
		}
	
	/**
	 * DOTO：微信端获取并删除医药卫生客户信息
	 * @author 王驰
	 */
	public void getMedicalScience() {
		String phone_no=getPara("phone_no");		
        List<MedicalScienceModel> result = MedicalScienceModel.findModelbyPhone_no(phone_no);
		setAttr("data",result);
		renderJson();
		String id=getPara("id");
		MedicalScienceModel.delMedicalScienceById(id);
		
		}

	}
