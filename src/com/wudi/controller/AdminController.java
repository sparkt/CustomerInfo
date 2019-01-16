package com.wudi.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.interceptor.AdminInterceptor;
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
import com.wudi.util.StringUtil;
import com.wudi.util.Util;

/**
 * 
 * @ClassName: AdminController
 * @Description: TODO 后台管理页面跳转管理类
 * @author 李金鹏
 * @date 2019年1月12日11:01
 *
 */
@Before(AdminInterceptor.class)
public class AdminController extends Controller {
	@Clear(AdminInterceptor.class)
	public void login() {
		String username=getPara("username");
		String password=getPara("password");
		//如果不正确，就提示什么不正确？
		//如果正确，就正常显示系统页面
		UserModel m=UserModel.getModeByUsername(username);
		//判断用户名和密码是否正确
		if(m!=null) {
			if(m.getpassword().equals(password)) {
				setAttr("result", 0);//可以登录
				setCookie(Util.Cookie_NAME,username,36000);
				setSessionAttr("user", m);
			}else {
				setAttr("result", 1);//密码错误
			}
		}else {
			setAttr("result", 2);//用户名不存在
		}
		renderJson();
	}
	@Clear(AdminInterceptor.class)
	public void outLogin() {
		removeCookie(Util.Cookie_NAME);
		removeSessionAttr("user");
		redirect("/admin");
	}
	/**
	 * 
	 * @Title: index @Description:后台管理默认到达页面 @param 参数 @return void 返回类型 @throws
	 */
	public void index() {
		render("index.html");
	}
	public void main() {
		render("main.html");
	}

	/**
	 * 
	 * @Title: getnavs @Description: 获取主页面左侧菜单数据 @param 参数 @return void 返回类型 @throws
	 */
	public void getnavs() {
		Object object = NavsModel.getListForLeft();
		renderJson(object);
	}

	/**
	 * 显示菜单列表
	 */
	public void navsinfo() {
		render("sys/navsinfo.html");
	}

	/**
	 * 
	 * @Title: getnavs @Description: 获取侧菜单数据列表 @param 参数 @return void 返回类型 @throws
	 */
	public void getNavsList() {
		// 获取页面查询的关键字
		String key = getPara("key");
		int limit=getParaToInt("limit");
		int page=getParaToInt("page");
		Page<NavsModel> list = NavsModel.getList(page, limit, key);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", list.getTotalRow());
		setAttr("data", list.getList());
		renderJson();
	}

	/**
	 * 打开菜单添加页面
	 */
	public void openNavsAdd() {
		render("sys/navsAdd.html");
	}

	/**
	 * 添加保存菜单信息
	 */
	public void saveNavs() {
		String title = getPara("title");
		String href = getPara("href");
		String icon = "&#xe630;";
		String fid = getPara("fid");
		boolean result = NavsModel.saveModel(title, href, icon, fid);
		setAttr("result", result);
		renderJson();

	}

	/**
	 * TODO:根据id查找信息数据
	 */
	public void getModeListById() {
		String id = getPara("id");
		NavsModel m = NavsModel.getModeById(id);
		List<NavsModel> ml = NavsModel.getModeListByFid("-1");
		setAttr("m", m);// 找数据去更新
		setAttr("ml", ml);// 父节点列表
		renderJson();
	}

	/**
	 * TODO:根据fid查找信息数据
	 */
	public void getModeByFId() {
		List<NavsModel> ml = NavsModel.getModeListByFid("-1");
		setAttr("ml", ml);// 找数据去更新
		renderJson();
	}

	/**
	 * 打开菜单修改页面
	 */
	public void openNavsEdit() {
		// 接收页面数据
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("sys/navsEdit.html");
	}

	/**
	 * 更新保存菜单信息
	 */
	public void updatenavs() {
		String id = getPara("id");
		String title = getPara("title");
		String href = getPara("href");
		String icon = "&#xe630;";
		String fid = getPara("fid");
		boolean result = NavsModel.updateModel(id, title, href, icon, fid);
		setAttr("result", result);
		renderJson();

	}

	//**********************************用户信息表操作开始****张志强*******************************************//
	
		/*
		//打开用户信息界面
		 * */
		public void userinfo() {
			render("userinfo/userinfoInfo.html");
		}
		
		/*
		 * 打开添加用户信息界面
		 * */
		public void openUserinfoAdd() {
			render("userinfo/userinfoAdd.html");
		}
		
		/*
		 * 打开修改用户信息页面
		 * 
		 * */
		public void openUserinfoEdit() {
			// 接收页面数据
				String phone_no = getPara("phone_no");
				setAttr("phone_no", phone_no);
				renderFreeMarker("userinfo/userinfoEdit.html");
		}
		
		
		
		/*
		 * 加载用户信息
		 * @author Zhangzhiqiang
		 * */
		public void getUserInfoList() {
			String key = getPara("key");
			int limit=getParaToInt("limit");
			int page=getParaToInt("page");
			Page<UserInfoModel> list = new UserInfoModel().getList(page, limit, key);
			setAttr("code", 0);
			setAttr("msg", "你好！");
			setAttr("count", list.getTotalRow());
			setAttr("data", list.getList());
			renderJson();
		}
		
		
		/**
		 * 添加用户信息
		 * @author Zhangzhiqiang
		 * @Description: TODO 录入用户注册信息
		 * 
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
			boolean result =true;
			UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
			if(m!=null) {
				result=false;
			}else {

				result = new UserInfoModel().saveUserinfo(user_name, user_password, user_sex, phone_no,"0","0","0");
			}
			setAttr("result", result);
			renderJson();
			}
		
		
		/* 修改用户信息
		 * @Title: updataUserinfo
		 * @Description: 更新修改用户信息界面数据到数据库
		 * @param 参数
		 * @return void 返回类型
		 * @throws
		 *  @author zhangzhiqiang
		 * **/
		public void updataUserinfo() {
			String user_name = getPara("user_name");
			String user_password = getPara("user_password");
			String user_sex = getPara("user_sex");
			String phone_no = getPara("phone_no");
			
			if(user_sex.equals("0")) {
				user_sex="男";
			}else {
				user_sex="女";
			}
			boolean result = new UserInfoModel().updataUserinfo(user_name, user_password, user_sex, phone_no, "1", "0");
			
			setAttr("result", result);
			renderJson();
		}
		
		
		
		
		
		/*****************************************添加用户信息结束*******张志强*******************************************/

		
		//***********************************{管理员信息表操作开始}张志强********************************************************************************//
		/*
		 * @Descripion: 打开管理员信息界面
		 * @author zhangzhiqiang
		 */
		public void admininfo() {
			render("admininfo/admininfoInfo.html");
		}
		/**
		 * 
		 * @Title: getAdmininfolist
		 * @Description: 获取管理员信息界面数据列表
		 * @param 参数
		 * @return void 返回类型
		 * @throws
		 * @author zhangzhiqiang
		 */
		public void getAdminlist() {
			// 获取页面查询的关键字
			String key = getPara("key");
			int limit=getParaToInt("limit");
			int page=getParaToInt("page");
			Page<AdminInfoModel> list = new AdminInfoModel().getList(page, limit, key);
			setAttr("code", 0);
			setAttr("msg", "你好！");
			setAttr("count", list.getTotalRow());
			setAttr("data", list.getList());
			renderJson();
		}

		/**
		 * 打开管理员添加页面
		 *@author zhangzhiqiang
		 */
		public void openAdminAdd() {
			render("admininfo/admininfoAdd.html");
		}

		/*
		 * @Title: saveAdmininfo
		 * @Description: 保存添加理员信息界面数据
		 * @param 参数
		 * @return void 返回类型
		 * @throws
		 *  @author zhangzhiqiang
		 * **/
		public void saveAdmininfo() {
			String admin_name = getPara("admin_name");
			String admin_password = getPara("admin_password");
			String admin_sex = getPara("admin_sex");
			String admin_phone_no = getPara("admin_phone_no");
			if(admin_sex.equals("0")) {
				admin_sex="男";
			}else {
				admin_sex="女";
			}
			boolean result =false;
			AdminInfoModel m = new AdminInfoModel().getphone_no(admin_phone_no);
			if(m==null) {
				result = new AdminInfoModel().saveAdminInfo(admin_name, admin_password, admin_sex, admin_phone_no, "1", "0");
			}
			setAttr("result", result);
			renderJson();
		}
		/**
		 * 打开管理员修改页面
		 * 
		 */
		public void openAdmininfoEdit() {
			// 接收页面数据
			String admin_phone_no = getPara("admin_phone_no");
			setAttr("admin_phone_no", admin_phone_no);
			renderFreeMarker("admininfo/admininfoEdit.html");
		}
		/* @updataAdmininfo
		 * @Title: saveAdmininfo
		 * @Description: 更新修改理员信息界面数据到数据库
		 * @param 参数
		 * @return void 返回类型
		 * @throws
		 *  @author zhangzhiqiang
		 * **/
		public void updataAdmininfo() {
			String admin_name = getPara("admin_name");
			String admin_password = getPara("admin_password");
			String admin_sex = getPara("admin_sex");
			String admin_phone_no = getPara("admin_phone_no");
			
			if(admin_sex.equals("0")) {
				admin_sex="男";
			}else {
				admin_sex="女";
			}
			boolean result = new AdminInfoModel().updataAdminInfo(admin_name, admin_password, admin_sex, admin_phone_no, "1", "0");
			setAttr("result", result);
			renderJson();
		}
		
	//****************************************************{管理员信息表操作结束}***张志强******************************************************************************//
	
	
	
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
	public void getSpecialPromotiom() {
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
		SpecialPromotiomModel data=SpecialPromotiomModel.getById(id);
		setAttr("data", data);
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
	public void getPartTimePostgraduate() {
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
	public void getMandarin() {
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
	public void getTeachercertification() {
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
	public void getCourtClerk() {
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
	public void getAccounting() {
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
	public void getArchitect() {
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
	public void getUndergraduate() {
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
	public void getProfessional() {
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
	public void getForeignLanguage() {
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
	public void getMedicalScience() {
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
