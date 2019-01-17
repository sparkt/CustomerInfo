package com.wudi.controller;


import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.interceptor.WeixinIntercepter;
import com.wudi.model.admin.AccountingModel;
import com.wudi.model.admin.AdminInfoModel;
import com.wudi.model.admin.ArchitectModel;
import com.wudi.model.admin.CourtClerkModel;
import com.wudi.model.admin.ForeignLanguageModel;
import com.wudi.model.admin.GroupInfoModel;
import com.wudi.model.admin.MandarinModel;
import com.wudi.model.admin.MedicalScienceModel;
import com.wudi.model.admin.PartTimePostgraduateModel;
import com.wudi.model.admin.ProfessionalModel;
import com.wudi.model.admin.SpecialPromotiomModel;
import com.wudi.model.admin.TeachercertificationModel;
import com.wudi.model.admin.UndergraduateModel;
import com.wudi.model.admin.UserInfoModel;
import com.wudi.util.StringUtil;



/**
 * 微信小程序数据访问
 * 2018年10月26日11:10:01
 */
@Before(WeixinIntercepter.class)
public class WeixinController extends Controller {
	/**
	 * 默认获取数据的地方
	 */
	public void index() {
		setAttr("result", "你好，无敌小团队微信小程序路径！");
		renderJson();
	}
	
	
	
	
	/*
	 * 创建团队接口
	 * @author 张志强
	 * captain_phone //队长号码
	 * captain_name //队长名字
	 * group_info //团队简介
	 * group_name //团队名称
	 * */
	
	public void createGroupinfo(){
		String captain_phone  = getPara("captain_phone");
		String captain_name = getPara("captain_name");
		String group_info = getPara("group_info");
		String group_name = getPara("group_name");
		int code =0; //注册不成功
		String info ="注册不成功";
		UserInfoModel m = new UserInfoModel().getphone_no(captain_phone);
		if(m!=null) {
		//判断该用户是否满足建队条件
		if(m.getGroup("").equals("0")&&m.getVip_grade().equals("1")) {
			
			boolean result =new GroupInfoModel().saveGroupinfo(group_name, captain_name, captain_phone, group_info);

			if(result) {
				code =1;
				info ="注册成功";
			}
		}else {
			code =2;
			info ="你不满足创建团队条件";
		}
		}
			setAttr("code", code);
			setAttr("info", info);
			renderJson();
			
		}
		
		
	
	
	/*
	 * 
	 *加入团队接口 
	 *
	 *从微信端接收用户phone_no & 队长phone_no
	 * @author 
	 * */
	public void joinGroup() {
		String captain_phone  = getPara("captain_phone");
		String phone_no = getPara("phone_no");
		int code =0;
		String info="加入不成功";
		UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
		if(m.getGroup("").equals("0")) {
		boolean result = new UserInfoModel().userJoinGroup(captain_phone, phone_no);
		if(result) {
			code =1;
			info ="加入成功";	
		}
		}else {
			code =2;
			info ="你已有团队";	
		}
		setAttr("code", code);
		setAttr("info", info);
		renderJson();
		
	}
	/*
	 * 返回用户所在团队信息
	 * 
	 * phone_no// 用户号码
	 * */
	public void getGroupAllInfo() {
		String phone_no = getPara("phone_no");
		UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
		String captain_phone =m.getGroup("");
		List<?> list = m.getUserGrouAllInfo(phone_no, captain_phone);
		setAttr("infoList", list);
		renderJson();
	}
	/*
	 * 返回用户所在团队队员员信息
	 * 
	 * 
	 * */
	
	public void getGroupMemberAllInfo() {
		String phone_no = getPara("phone_no");
		UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
		String groups =m.getGroup("");
		List<?> list = new UserInfoModel().getGroupMemberAllInfo(groups);
		setAttr("infoList", list);
		renderJson();
	}
	
	/*
	 * 用户退团队接口
	 * 
	 * 
	 * */
	
	public void quitGroup() {
		String phone_no = getPara("phone_no");
		
	}
	
	
	
	/**
	 * 微信端用户注册入口
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
	 * 微信用户登录入口
	 * @author 张志强
	 *  GET phone_no & user_password
	 *  
	 * @Description: TODO 给微信端返回用户或管理员所有信息
	 * 
	 * */
	
	public void userLogin() {
		String phone_no =getPara("phone_no");
		String user_password = getPara("user_password");
		int code=0;	// 返回给前端做判断登录是否成功 0 密码错误 1密码正确 2 用户不存在
		int type =0;// 用户类型  1普通用户，2管理员
		String info = "用户不存在";
		List<?> list =null;
		//查询用户表phone_no字段
		UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
		//查询管理员表admin_phone_no字段
		AdminInfoModel n = new AdminInfoModel().getphone_no(phone_no);
		if (n!=null||m!=null) {
			if (n!=null) {
				if(n.getAdmin_password().equals(user_password)) {
					list= new AdminInfoModel().getAdminAllInfo(phone_no);
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
					list= new UserInfoModel().getUserAllInfo(phone_no);
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
			type=0;
			code=2;
			info = "用户不存在";
		}
		setAttr("code", code);
		setAttr("info", info);
		setAttr("type", type);
		setAttr("userAllInfo",list);
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
//		String id=getPara("id");
//		SpecialPromotiomModel.delSpecialPromotiomId(id);
		
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
	
	
	/**
	 * 
	 * TODO:高升专
	 * @author李金鹏
	 */
	
	/**
	 * @Title: SpecialPromotiom @Description: 打开客户信息列表页面 @param 参数 @return void 返回类型 @throws
	 */
	public void SpecialPromotiom() {
		render("SpecialPromotiom/SpecialPromotiomInfo.html");
	}

	/**
	 * @Title: openSpecialPromotiomAdd @Description:打开添加信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 *         暂时不用
	 */
//	public void openSpecialPromotiomAdd() {
//		render("SpecialPromotiom/SpecialPromotiomAdd.html");
//	}

	/**
	 * @Title: openSpecialPromotiomEdit @Description:打开修改信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void openSpecialPromotiomEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("SpecialPromotiom/SpecialPromotiomEdit.html");
	}

	/**
	 * @Title: querySpecialPromotiom @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 @param 参数 @return void 返回类型 @throws
	 */
	public void querySpecialPromotiom() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<SpecialPromotiomModel> list = SpecialPromotiomModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getSpecialPromotiom @Description:获取需要修改的客户信息 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void getupdateSpecialPromotiom() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		SpecialPromotiomModel date = SpecialPromotiomModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	/**
	 * @Title: saveSpecialPromotiom @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void saveSpecialPromotiom() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = "";
		age = getPara("age");
		String work_address="";
		work_address=getPara("work_address");
		String comments="";
		comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation="";
		nation=getPara("nation");
		// 保存数据
		boolean result = SpecialPromotiomModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatSpecialPromotiom @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void updateSpecialPromotiom() {
		
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = getPara("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation=getPara("nation");
		// 更新数据
		boolean result = SpecialPromotiomModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delUserInfo @Description:删除信息，这个我们是根据唯一主键id来删除的。 @param 参数 @return
	 *         void 返回类型 @throws
	 */
	public void delSpecialPromotiom() {
		String id = getPara("id");
		// 删除
		boolean result = SpecialPromotiomModel.delSpecialPromotiomId(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	
	public void getsizeS() {
		List<SpecialPromotiomModel> list=SpecialPromotiomModel.getListAll();
		list.size();
		setAttr("row", list.size());
		renderJson();
	}
	
	
	/**
	 * 
	 * TODO:非全日制研究生
	 * @author李金鹏
	 */
	
	/**
	 * @Title: PartTimePostgraduate @Description: 打开客户信息列表页面 @param 参数 @return void 返回类型 @throws
	 */
	public void PartTimePostgraduate() {
		render("PartTimePostgraduate/PartTimePostgraduateInfo.html");
	}

	/**
	 * @Title: openPartTimePostgraduateAdd @Description:打开添加信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 *         暂时不用
	 */
//	public void openPartTimePostgraduateAdd() {
//		render("PartTimePostgraduate/PartTimePostgraduateAdd.html");
//	}

	/**
	 * @Title: openPartTimePostgraduateEdit @Description:打开修改信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void openPartTimePostgraduateEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("PartTimePostgraduate/PartTimePostgraduateEdit.html");
	}

	/**
	 * @Title: queryPartTimePostgraduate @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 @param 参数 @return void 返回类型 @throws
	 */
	public void queryPartTimePostgraduate() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<PartTimePostgraduateModel> list = PartTimePostgraduateModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getPartTimePostgraduate @Description:获取需要修改的客户信息 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void getupdatePartTimePostgraduate() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		PartTimePostgraduateModel date = PartTimePostgraduateModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	/**
	 * @Title: savePartTimePostgraduate @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void savePartTimePostgraduate() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = "";
		age = getPara("age");
		String work_address="";
		work_address=getPara("work_address");
		String comments="";
		comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation="";
		nation=getPara("nation");
		// 保存数据
		boolean result = PartTimePostgraduateModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);

		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatPartTimePostgraduate @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void updatePartTimePostgraduate() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = getPara("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation=getPara("nation");
		// 更新数据
		boolean result = PartTimePostgraduateModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delPartTimePostgraduate @Description:删除信息，这个我们是根据唯一主键id来删除的。 @param 参数 @return
	 *         void 返回类型 @throws
	 */
	public void delPartTimePostgraduate() {
		String id = getPara("id");
		// 删除
		boolean result = PartTimePostgraduateModel.delPartTimePostgraduateById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	
	public void getsizeP() {
		List<PartTimePostgraduateModel> list=PartTimePostgraduateModel.getListAll();
		list.size();
		setAttr("row", list.size());
		renderJson();
	}
	
	
	/**
	 * 
	 * TODO:普通话
	 * @author李金鹏
	 */
	
	/**
	 * @Title: Mandarin @Description: 打开客户信息列表页面 @param 参数 @return void 返回类型 @throws
	 */
	public void Mandarin() {
		render("Mandarin/MandarinInfo.html");
	}

	/**
	 * @Title: openMandarinAdd @Description:打开添加信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 *         暂时不用
	 */
//	public void openMandarineAdd() {
//		render("Mandarin/PMandarinAdd.html");
//	}

	/**
	 * @Title: openMandarinEdit @Description:打开修改信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void openMandarinEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("Mandarin/MandarinEdit.html");
	}

	/**
	 * @Title: queryMandarin @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 @param 参数 @return void 返回类型 @throws
	 */
	public void queryMandarin() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<MandarinModel> list =MandarinModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getMandarin @Description:获取需要修改的客户信息 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void getupdateMandarin() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		MandarinModel date = MandarinModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	/**
	 * @Title: saveMandarin @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void saveMandarin() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		int age = getParaToInt("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation=getPara("nation");
		// 保存数据
		boolean result = MandarinModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);

		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatMandarin @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void updateMandarin() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		int age = getParaToInt("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");	
		String nation=getPara("nation");
		// 更新数据
		boolean result = MandarinModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delMandarin @Description:删除信息，这个我们是根据唯一主键id来删除的。 @param 参数 @return
	 *         void 返回类型 @throws
	 */
	public void delMandarin() {
		String id = getPara("id");
		// 删除
		boolean result = MandarinModel.delMandarinById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	
	public void getsizeM() {
		List<MandarinModel> list=MandarinModel.getListAll();
		list.size();
		setAttr("row", list.size());
		renderJson();
	}
	
	
	/**
	 * 
	 * TODO:教师资格证
	 * @author李金鹏
	 */
	
	/**
	 * @Title: Teachercertification @Description: 打开客户信息列表页面 @param 参数 @return void 返回类型 @throws
	 */
	public void Teachercertification() {
		render("Teachercertification/TeachercertificationInfo.html");
	}

	/**
	 * @Title: openTeachercertificationAdd @Description:打开添加信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 *         暂时不用
	 */
//	public void openTeachercertificationAdd() {
//		render("Teachercertification/TeachercertificationAdd.html");
//	}

	/**
	 * @Title: openTeachercertificationEdit @Description:打开修改信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void openTeachercertificationEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("Teachercertification/TeachercertificationEdit.html");
	}

	/**
	 * @Title: queryTeachercertification @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 @param 参数 @return void 返回类型 @throws
	 */
	public void queryTeachercertification() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<TeachercertificationModel> list =TeachercertificationModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getTeachercertification @Description:获取需要修改的客户信息 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void getupdateTeachercertification() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		TeachercertificationModel date = TeachercertificationModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	/**
	 * @Title: saveTeachercertification @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void saveTeachercertification() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = "";
		age = getPara("age");
		String work_address="";
		work_address=getPara("work_address");
		String comments="";
		comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation="";
		nation=getPara("nation");
		// 保存数据
		boolean result = TeachercertificationModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);

		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatTeachercertification @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void updatTeachercertification() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = getPara("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");	
		String nation=getPara("nation");
		// 更新数据
		boolean result = TeachercertificationModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delTeachercertification @Description:删除信息，这个我们是根据唯一主键id来删除的。 @param 参数 @return
	 *         void 返回类型 @throws
	 */
	public void delTeachercertification() {
		String id = getPara("id");
		// 删除
		boolean result = TeachercertificationModel.delTeachercertificationById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	public void getsizeT() {
		List<TeachercertificationModel> list=TeachercertificationModel.getListAll();
		list.size();
		setAttr("row", list.size());
		renderJson();
	}
	
	
	/**
	 * 
	 * TODO:法院书记员
	 * @author李金鹏
	 */
	
	/**
	 * @Title: CourtClerk @Description: 打开客户信息列表页面 @param 参数 @return void 返回类型 @throws
	 */
	public void CourtClerk() {
		render("CourtClerk/CourtClerkInfo.html");
	}

	/**
	 * @Title: openCourtClerkAdd @Description:打开添加信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 *         暂时不用
	 */
//	public void openCourtClerkAdd() {
//		render("CourtClerk/CourtClerkAdd.html");
//	}

	/**
	 * @Title: openCourtClerkEdit @Description:打开修改信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void openCourtClerkEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("CourtClerk/CourtClerkEdit.html");
	}

	/**
	 * @Title: queryCourtClerk @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 @param 参数 @return void 返回类型 @throws
	 */
	public void queryCourtClerk() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<CourtClerkModel> list =CourtClerkModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getCourtClerk @Description:获取需要修改的客户信息 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void getupdateCourtClerk() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		CourtClerkModel date = CourtClerkModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	/**
	 * @Title: saveCourtClerk @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void saveCourtClerk() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = "";
		age = getPara("age");
		String work_address="";
		work_address=getPara("work_address");
		String comments="";
		comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation="";
		nation=getPara("nation");
		// 保存数据
		boolean result = CourtClerkModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);

		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatCourtClerk @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void updatCourtClerk() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = getPara("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");	
		String nation=getPara("nation");
		// 更新数据
		boolean result = CourtClerkModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delCourtClerk @Description:删除信息，这个我们是根据唯一主键id来删除的。 @param 参数 @return
	 *         void 返回类型 @throws
	 */
	public void delCourtClerk() {
		String id = getPara("id");
		// 删除
		boolean result = CourtClerkModel.delCourtClerkById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	
	public void getsizeC() {
		List<CourtClerkModel> list=CourtClerkModel.getListAll();
		list.size();
		setAttr("row", list.size());
		renderJson();
	}
	
	/**
	 * 
	 * TODO:财会经济
	 * @author李金鹏
	 */
	
	/**
	 * @Title: Accounting @Description: 打开客户信息列表页面 @param 参数 @return void 返回类型 @throws
	 */
	public void Accounting() {
		render("Accounting/AccountingInfo.html");
	}

	/**
	 * @Title: openAccountingkAdd @Description:打开添加信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 *         暂时不用
	 */
//	public void openAccountingAdd() {
//		render("Accounting/AccountingAdd.html");
//	}

	/**
	 * @Title: openAccountingEdit @Description:打开修改信息页面 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void openAccountingEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("Accounting/AccountingEdit.html");
	}

	/**
	 * @Title: queryAccounting @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 @param 参数 @return void 返回类型 @throws
	 */
	public void queryAccounting() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<AccountingModel> list =AccountingModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getAccounting @Description:获取需要修改的客户信息 @param 参数 @return void
	 *         返回类型 @throws
	 */
	public void getupdateAccounting() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		AccountingModel date = AccountingModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	
	/**
	 * @Title: saveAccounting @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void saveAccounting() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = "";
		age = getPara("age");
		String work_address="";
		work_address=getPara("work_address");
		String comments="";
		comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation="";
		nation=getPara("nation");
		// 保存数据
		boolean result = AccountingModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);

		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatAccounting @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 @param
	 *         参数 @return void 返回类型 @throws
	 */
	public void updatAccounting() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = getPara("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");	
		String nation=getPara("nation");
		// 更新数据
		boolean result = AccountingModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delAccounting @Description:删除信息，这个我们是根据唯一主键id来删除的。 @param 参数 @return
	 *         void 返回类型 @throws
	 */
	public void delAccounting() {
		String id = getPara("id");
		// 删除
		boolean result = AccountingModel.delAccountingById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	
	public void getsizeA() {
		List<AccountingModel> list=AccountingModel.getListAll();
		list.size();
		setAttr("row", list.size());
		renderJson();
	}
	/**
	 * TODO:建筑工程
	 * @author 王驰
	 * @Title: Architect 
	 * @Description: 打开客户信息列表页面 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void Architect() {
		render("Architect/ArchitectInfo.html");
	}

	/**
	 * @Title: openArchitectEdit
	 * @Description:打开添加信息页面 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void openArchitectEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("Architect/ArchitectEdit.html");
	}

	/**
	 * @Title: queryArchitect 
	 * @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void queryArchitect() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<ArchitectModel> list =ArchitectModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getArchitect
	 * @Description:获取需要修改的客户信息 
	 * @param 参数
	 * @return void 返回类型 
	 * @throws
	 */
	public void getupdateArchitect() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		ArchitectModel date = ArchitectModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	/**
	 * @Title: saveArchitect
	 * @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void saveArchitect() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = "";
		age = getPara("age");
		String work_address="";
		work_address=getPara("work_address");
		String comments="";
		comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation="";
		nation=getPara("nation");
		// 保存数据
		boolean result = ArchitectModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);

		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatArchitect
	 * @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void updateArchitect() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = getPara("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");	
		String nation=getPara("nation");
		// 更新数据
		boolean result = ArchitectModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delArchitect
	 * @Description:删除信息，这个我们是根据唯一主键id来删除的。 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void delArchitect() {
		String id = getPara("id");
		// 删除
		boolean result =ArchitectModel.delArchitectById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	

	/**
	 * TODO:专升本
	 * @author 王驰
	 * @Title: Undergraduate 
	 * @Description: 打开客户信息列表页面 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void Undergraduate() {
		render("Undergraduate/UndergraduateInfo.html");
	}

	/**
	 * @Title: openUndergraduateEdit
	 * @Description:打开添加信息页面 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void openUndergraduateEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("Undergraduate/UndergraduateEdit.html");
	}

	/**
	 * @Title: queryUndergraduate
	 * @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void queryUndergraduate() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<UndergraduateModel> list =UndergraduateModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getUndergraduate 
	 * @Description:获取需要修改的客户信息 
	 * @param 参数
	 * @return void 返回类型 
	 * @throws
	 */
	public void getupdateUndergraduate() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		UndergraduateModel date = UndergraduateModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	/**
	 * @Title: saveUndergraduate 
	 * @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void saveUndergraduate() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = "";
		age = getPara("age");
		String work_address="";
		work_address=getPara("work_address");
		String comments="";
		comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation="";
		nation=getPara("nation");
		// 保存数据
		boolean result = UndergraduateModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);

		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatUndergraduate 
	 * @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void updateUndergraduate() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = getPara("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");	
		String nation=getPara("nation");
		// 更新数据
		boolean result = UndergraduateModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delUndergraduate 
	 * @Description:删除信息，这个我们是根据唯一主键id来删除的。 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void delUndergraduate () {
		String id = getPara("id");
		// 删除
		boolean result = UndergraduateModel.delUndergraduateById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}

	/**
	 * TODO:职业资格
	 * @author 王驰
	 * @Title: Professional 
	 * @Description: 打开客户信息列表页面 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void Professional() {
		render("Professional/ProfessionalInfo.html");
	}

	/**
	 * @Title: openProfessionalEdit
	 * @Description:打开添加信息页面 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void openProfessionalEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("Professional/ProfessionalEdit.html");
	}

	/**
	 * @Title: queryProfessional
	 * @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void queryProfessional() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<ProfessionalModel> list =ProfessionalModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getProfessional
	 * @Description:获取需要修改的客户信息 
	 * @param 参数
	 * @return void 返回类型 
	 * @throws
	 */
	public void getupdateProfessional() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		ProfessionalModel date = ProfessionalModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	/**
	 * @Title: saveProfessional
	 * @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void saveProfessional() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = "";
		age = getPara("age");
		String work_address="";
		work_address=getPara("work_address");
		String comments="";
		comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation="";
		nation=getPara("nation");
		// 保存数据
		boolean result = ProfessionalModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);

		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatProfessional
	 * @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void updateProfessional() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = getPara("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");	
		String nation=getPara("nation");
		// 更新数据
		boolean result = ProfessionalModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delProfessional
	 * @Description:删除信息，这个我们是根据唯一主键id来删除的。 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void delProfessional() {
		String id = getPara("id");
		// 删除
		boolean result =ProfessionalModel.delProfessionalById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	
	/**
	 * TODO:外语少儿
	 * @author 王驰
	 * @Title: ForeignLanguage
	 * @Description: 打开客户信息列表页面 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void ForeignLanguage() {
		render("ForeignLanguage/ForeignLanguageInfo.html");
	}

	/**
	 * @Title: openForeignLanguageEdit
	 * @Description:打开添加信息页面 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void openForeignLanguageEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("ForeignLanguage/ForeignLanguageEdit.html");
	}

	/**
	 * @Title: queryForeignLanguage
	 * @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void queryForeignLanguage() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<ForeignLanguageModel> list =ForeignLanguageModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getForeignLanguage
	 * @Description:获取需要修改的客户信息 
	 * @param 参数
	 * @return void 返回类型 
	 * @throws
	 */
	public void getupdateForeignLanguage() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		ForeignLanguageModel date = ForeignLanguageModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	/**
	 * @Title: saveForeignLanguage
	 * @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void saveForeignLanguage() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = "";
		age = getPara("age");
		String work_address="";
		work_address=getPara("work_address");
		String comments="";
		comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation="";
		nation=getPara("nation");
		// 保存数据
		boolean result = ForeignLanguageModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);

		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatForeignLanguage
	 * @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void updateForeignLanguage() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = getPara("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");	
		String nation=getPara("nation");
		// 更新数据
		boolean result = ForeignLanguageModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delForeignLanguage
	 * @Description:删除信息，这个我们是根据唯一主键id来删除的。 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void delForeignLanguage () {
		String id = getPara("id");
		// 删除
		boolean result = ForeignLanguageModel.delForeignLanguageById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	
	
	/**
	 * TODO:医药卫生
	 * @author 王驰
	 * @Title: MedicalScience
	 * @Description: 打开客户信息列表页面 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void MedicalScience() {
		render("MedicalScience/MedicalScienceInfo.html");
	}

	/**
	 * @Title: openMedicalScienceEdit
	 * @Description:打开添加信息页面 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void openMedicalScienceEdit() {
		// 接收页面数据
		String id = getPara("id");

		setAttr("id", id);
		renderFreeMarker("MedicalScience/MedicalScienceEdit.html");
	}

	/**
	 * @Title: queryMedicalScience
	 * @Description: 获取客户信息列表信息（查询），在这里，是用异步加载方式，
	 *         就是说，页面先打开了，然后在用js向后台获取数据，这个就是。 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void queryMedicalScience() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<MedicalScienceModel> list =MedicalScienceModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}

	/**
	 * @Title: getMedicalScience
	 * @Description:获取需要修改的客户信息 
	 * @param 参数
	 * @return void 返回类型 
	 * @throws
	 */
	public void getupdateMedicalScience() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		MedicalScienceModel date = MedicalScienceModel.getById(id);
		
		// *放到编辑页面上去*
		setAttr("d", date);
		// 返回格式是json
		renderJson();

	}
	
	/**
	 * @Title: saveMedicalScience
	 * @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void saveMedicalScience() {

		String id = StringUtil.getId();
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = "";
		age = getPara("age");
		String work_address="";
		work_address=getPara("work_address");
		String comments="";
		comments=getPara("comments");
		String phone_no=getPara("phone_no");
		String nation="";
		nation=getPara("nation");
		// 保存数据
		boolean result = MedicalScienceModel.save(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);

		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: updatMedicalScience
	 * @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void updateMedicalScience() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String tel_no = getPara("tel_no");
		String disclose = getPara("disclose");
		String age = getPara("age");
		String work_address=getPara("work_address");
		String comments=getPara("comments");
		String phone_no=getPara("phone_no");	
		String nation=getPara("nation");
		// 更新数据
		boolean result = MedicalScienceModel.update(id, name, sex, tel_no, disclose,age,work_address,comments,phone_no,nation);
		setAttr("result", result);
		renderJson();
	}

	/**
	 * 
	 * @Title: delMedicalScience
	 * @Description:删除信息，这个我们是根据唯一主键id来删除的。 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public void delMedicalScience() {
		String id = getPara("id");
		// 删除
		boolean result =MedicalScienceModel.delMedicalScienceById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}

	}
