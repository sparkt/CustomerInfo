package com.wudi.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.interceptor.AdminInterceptor;
import com.wudi.model.NavsModel;
import com.wudi.model.UserModel;
import com.wudi.model.admin.AdminInfoModel;
import com.wudi.model.admin.CustomerModel;
import com.wudi.model.admin.GroupInfoModel;
import com.wudi.model.admin.UserInfoModel;
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
		 * 审核用户
		 */
		public void check() {
			String phone_no = getPara("phone_no");
			UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
			m.setStatus("1");
			boolean  result = m.update();
			setAttr("result", result);
			renderJson();
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

				result = new UserInfoModel().saveUserinfo(user_name, user_password, user_sex, phone_no,"0","0");
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
		
		/**
		 * 
		 * @Title: delUserInfo @Description:删除信息，这个我们是根据唯一主键admin_phone_no来删除的。 @param 参数 @return
		 *         void 返回类型 @throws
		 */
		public void delUserInfo() {
			String admin_phone_no = getPara("admin_phone_no");
			// 删除
			boolean result = new AdminInfoModel().deleteAdminInfo(admin_phone_no);
			// 返回结果
			setAttr("result", result);
			renderJson();
		}
		
		
		
		

		
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
		
	public void openCuStomers() {
		//要传一个类型给它,???
		String type=getPara("type");
		setAttr("type", type);
		renderFreeMarker("customer/customerInfo.html");
	}
	public void queryCustomers() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        String type=getPara("type"); 
        Page<CustomerModel> list = CustomerModel.getList(page, limit, key,type);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}
	public void getCustomer() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		CustomerModel m = CustomerModel.getById(id);
		// *放到编辑页面上去*
		setAttr("d", m);
		// 返回格式是json
		renderJson();

	}
	public void delCustomerModel() {
		String id = getPara("id");
		// 删除
		boolean result = CustomerModel.delById(id);
		// 返回结果
		setAttr("result", result);	
		renderJson();
	}
	public void getCustomerNum() {
		String type = getPara("type");
		List<CustomerModel> list=CustomerModel.getCustomerNum(type);
		setAttr("row", list.size());
		renderJson();
	}
	
	
	//------------------团队管理开始 梁老师----------------
	/*
	 * @Descripion: 打开团队管理信息界面
	 * @author 梁老师
	 */
	public void groupinfo() {
		render("groupinfo/groupinfoInfo.html");
	}
	/**
	 * 
	 * @Title: getGrouplist
	 * @Description: 获取团队管理信息界面数据列表
	 * @param 参数
	 * @return void 返回类型
	 * @throws
	 * @author 梁老师
	 */
	public void getGrouplist() {
		// 获取页面查询的关键字
		String key = getPara("key");
		int limit=getParaToInt("limit");
		int page=getParaToInt("page");
		Page<GroupInfoModel> list = new GroupInfoModel().getList(page, limit, key);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", list.getTotalRow());
		setAttr("data", list.getList());
		renderJson();
	}

	/**
	 * 打开团队管理添加页面
	 *@author 梁老师
	 */
	public void openGroupinfoAdd() {
		render("groupinfo/groupinfoAdd.html");
	}

	/*
	 * @Title: saveGroupinfo
	 * @Description: 保存添加团队管理信息界面数据
	 * @param 参数
	 * @return void 返回类型
	 * @throws
	 *  @author 梁老师
	 * **/
	public void saveGroupInfo() {
		String captain_name = getPara("captain_name");
		String captain_phone = getPara("captain_phone");
		String group_info = getPara("group_info");
		String group_name = getPara("group_name");
		
		boolean result =false;
		GroupInfoModel m = new GroupInfoModel().getisGroup(captain_phone);
		if(m==null) {//如果团队不存在，即m为空，则新建团队。
			result = new GroupInfoModel().saveGroupinfo(captain_name, captain_phone, group_info, group_name);
		}else {//团队已存在
			return;
		}
		setAttr("result", result);
		renderJson();
		
		
	}
	/**
	 * 打开管理员修改页面
	 * 
	 */
	public void openGroupinfoEdit() {
		// 接收页面数据
		String captain_phone = getPara("captain_phone");
		setAttr("captain_phone", captain_phone);
		renderFreeMarker("groupinfo/groupinfoEdit.html");
	}
	/* @updateGroupinfo
	 * @Description: 更新修改团队管理信息界面数据到数据库
	 * @param 参数
	 * @return void 返回类型
	 * @throws
	 *  @author 梁老师
	 * **/
	public void updateGroupinfo() {
		String captain_name = getPara("captain_name");
		String captain_phone = getPara("captain_phone");
		String group_info = getPara("group_info");
		String group_headcount = getPara("group_headcount");
		String group_name = getPara("group_name");
		
		boolean result = new GroupInfoModel().updateGroupinfo(captain_name, group_info, group_headcount, group_name,captain_phone);
		setAttr("result", result);
		renderJson();
	}
	
	/**
	 * 
	 * @Title: delUserInfo @Description:删除信息，这个我们是根据唯一主键admin_phone_no来删除的。 @param 参数 @return
	 *         void 返回类型 @throws
	 */
	public void delGroupInfo() {
		String id = getPara("id");
		// 删除
		boolean result = new GroupInfoModel().deleteGroupinfo(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	
	/*
	 * 返回团队所有队员信息员信息
	 * @author 张志强
	 * phone_no //用户号码
	 * */
	
	public void getGroupMemberAllInfo() {
		String captain_phone = getPara("captain_phone");
		List<?> list = new UserInfoModel().getGroupMemberAllInfo(captain_phone);
		setAttr("data", list);
		renderJson();
	}
	
	
	
	//------------------团队管理结束 梁老师----------------

}
