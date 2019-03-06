package com.wudi.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.wudi.interceptor.WeixinIntercepter;
import com.wudi.model.admin.AdminInfoModel;
import com.wudi.model.admin.CustomerModel;
import com.wudi.model.admin.GroupInfoModel;
import com.wudi.model.admin.InformModel;
import com.wudi.model.admin.UserInfoModel;
import com.wudi.util.Util;

/**
 * 微信小程序数据访问 2018年10月26日11:10:01
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
	 * 
	 * @author 张志强 captain_phone 队长号码 captain_name 队长名字 group_info 团队简介 group_name
	 * 团队名称
	 */

	public void createGroupinfo() {
		String captain_phone = getPara("captain_phone");
		String captain_name = getPara("captain_name");
		String group_info = getPara("group_info");
		String group_name = getPara("group_name");
		int code = 0; // 注册不成功
		String info = "注册不成功";
		UserInfoModel m = new UserInfoModel().getphone_no(captain_phone);
		if (m != null) {
					// 判断该用户是否满足建队条件
					if (m.getGroup().equals("0") && m.getVip_grade().equals("1")) {
						m.setGroup(captain_phone);
						m.update();
						boolean result = new GroupInfoModel().saveGroupinfo(group_name, captain_name, captain_phone,
								group_info);
						if (result) {
							code = 1;
							info = "注册成功";
						}
					} else {
						code = 2;
						info = "你不满足创建团队条件";
					}
			}
		
		
		setAttr("code", code);
		setAttr("info", info);
		renderJson();
}
		


	/*
	 * 
	 * 拉入团队接口
	 *
	 * 从微信端接收用户 phone_no & 队长phone_no
	 * 
	 * @author 张志强
	 */
	public void joinGroup() {
		String captain_phone = getPara("captain_phone");
		String phone_no = getPara("phone_no");
		int code = 0;
		String info = "加入不成功";
		UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
		if (m != null) {
			if (m.getGroup().equals("0")) {
				boolean result = new UserInfoModel().userJoinGroup(captain_phone, phone_no);
				if (result) {
					boolean temp = new InformModel().circularize(
							"你已于" + Util.getCurrentTime() + "加入" + captain_phone + "团队", phone_no,
							Util.getCurrentTime());
					code = 1;
					info = "加入成功";
				}
			} else {
				code = 2;
				info = "已有团队";
			}
		} else {
			code = 0;
			info = "该用户不存在";

		}

		setAttr("code", code);
		setAttr("info", info);
		renderJson();

	}

	/*
	 * 个人中心页面 返回用户所在团队信息
	 * 
	 * @author 张志强 phone_no用户号码
	 */
	public void getGroupAllInfo() {
		String phone_no = getPara("phone_no");
		UserInfoModel user = new UserInfoModel().getphone_no(phone_no);
		GroupInfoModel groups = GroupInfoModel.getGroupAllInfo(user.getGroup());
		List<CustomerModel> customers = CustomerModel.findListByPhone_no(phone_no);
		setAttr("user", user);
		setAttr("customers", customers);
		setAttr("groups", groups);
		renderJson();
	}

	/*
	 * 获取通知信息
	 */
	public void circularize() {
		String phone_no = getPara("phone_no");
		String info = "暂无信息！";
		InformModel m = new InformModel().getphone_no(phone_no);
		if (m != null) {
			info = m.getinfo();
		}
		setAttr("info", info);
		renderJson();
	}

	/*
	 * 返回用户所在团队队员员信息
	 * 
	 * @author 张志强 phone_no 用户号码
	 */

	public void getGroupMemberAllInfo() {
		String phone_no = getPara("phone_no");
		UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
		List<?> list = new UserInfoModel().getGroupMemberAllInfo(m.getGroup());
		setAttr("data", list);
		renderJson();
	}

	/*
	 * 用户退团队接口
	 * 
	 * @author 张志强 phone_no 用户号码
	 */

	public void quitGroup() {
		String phone_no = getPara("phone_no");
		boolean result = new UserInfoModel().userQuitGroup(phone_no);
		int code = 0; // 退队不成功
		String info = "退队不成功";
		if (result) {
			boolean temp = new InformModel().circularize("你已于" + Util.getCurrentTime() + "退出团队", phone_no,
					Util.getCurrentTime());
			code = 0;
			info = "退队成功";
		}
		setAttr("code", code);
		setAttr("info", info);
		renderJson();

	}

	/*
	 * 队长删除队员接口 captain_phone 队长号码 phone_no 要删除团员号码
	 */
	public void deleteMember() {
		String captain_phone = getPara("captain_phone");
		String phone_no = getPara("phone_no");
		boolean result = new UserInfoModel().deleteMember(captain_phone, phone_no);
		int code = 0; // 删除不成功
		String info = "删除不成功";
		if (result) {
			code = 0;
			info = "删除成功";
		}
		setAttr("code", code);
		setAttr("info", info);
		renderJson();

	}

	/**
	 * 微信端用户注册入口
	 * 
	 * @author 张志强
	 * @Description: TODO 录入用户注册信息 给微信端发送提示信息 user_name user_password user_sex
	 *               phone_no
	 */
	public void saveUserinfo() {

		String user_name = getPara("user_name");
		String user_password = getPara("user_password");
		String user_sex = getPara("user_sex");
		String phone_no = getPara("phone_no");
		if (user_sex.equals("0")) {
			user_sex = "男";
		} else {
			user_sex = "女";
		}
		int code = 0; // 注册不成功
		String info = "注册不成功";
		UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
		if (m != null) {
			code = 1;// 用户已经存在
			info = "用户已经存在";
		} else {

			boolean result = new UserInfoModel().saveUserinfo(user_name, user_password, user_sex, phone_no, "0", "0");
			if (result) {
				code = 2;// 注册成功
				info = "注册成功";
			}
		}
		setAttr("code", code);
		setAttr("info", info);
		renderJson();
	}

	/**
	 * 微信用户登录入口
	 * 
	 * GET phone_no & user_password
	 * 
	 * @Description: TODO 给微信端返回用户或管理员所有信息 phone_no user_password
	 * 
	 * @author 张志强
	 */

	public void userLogin() {
		String phone_no = getPara("phone_no");
		String user_password = getPara("user_password");
		int code = 0; // 返回给前端做判断登录是否成功 0 密码错误 1密码正确 2 用户不存在，3表示用户未通过审核
		int type = 0;// 用户类型 1普通用户，2管理员
		String info = "用户不存在";
		List<?> list = null;
		// 查询用户表phone_no字段
		UserInfoModel m = new UserInfoModel().getphone_no(phone_no);
		// 查询管理员表admin_phone_no字段
		AdminInfoModel n = new AdminInfoModel().getphone_no(phone_no);
		if (n != null || m != null) {

			if (n != null) {

				if (n.getAdmin_password().equals(user_password)) {
					list = new AdminInfoModel().getAdminAllInfo(phone_no);
					type = 1;//
					code = 1;// 密码正确
					info = "密码正确";
				} else {
					// 密码错误
					type = 0;
					code = 0;
					info = "密码错误";
				}

			} else {
				if (m.getStatus().equals("0")) {
					code = 3;// 未审核用户
					info = "未审核用户";
				} else if (m.getUser_password().equals(user_password)) {
					list = new UserInfoModel().getUserAllInfo(phone_no);
					type = 2;//
					code = 1;// 密码正确
					info = "密码正确";
				} else {
					// 密码错误
					type = 0;
					code = 0;
					info = "密码错误";
				}

			}

		} else {
			// 不存在
			type = 0;
			code = 2;
			info = "用户不存在";
		}
		setAttr("code", code);
		setAttr("info", info);
		setAttr("type", type);
		setAttr("data", list);
		renderJson();
	}

	/**
	 * 保存客户信息 xiao
	 */
	public void saveOrUpdateCustomer() {
		String id = getPara("id");
		String name = getPara("name");
		int sex = getParaToInt("sex");
		String tel_no = getPara("tel_no");
		int disclose = getParaToInt("disclose");
		int age = getParaToInt("age");
		String work_address = getPara("work_address");
		String comments = getPara("comments");
		String phone_no = getPara("phone_no");
		String nation = getPara("nation");
		String type = getPara("type");
		int status = getParaToInt("status");
		// 保存数据
		boolean result = CustomerModel.saveOrUpate(id, name, sex, tel_no, disclose, age, work_address, comments,
				phone_no, nation, type, status);
		setAttr("result", result);
		renderJson();
	}

	/*
	 * 根据id查找客户信息
	 */
	public void getCustomerById() {
		// 接收页面数据
		String id = getPara("id");
		// 根据条件查询数据库的数据
		CustomerModel data = CustomerModel.getById(id);

		// *放到编辑页面上去*
		setAttr("data", data);
		// 返回格式是json
		renderJson();
	}

	/**
	 * 根据电话号码查询客户信息
	 */
	public void getCustomerByPhoneNo() {
		String phone_no = getPara("phone_no");
		int type = getParaToInt("type");
		List<CustomerModel> result = CustomerModel.findModelbyPhone_no(phone_no, type);
		setAttr("data", result);
		renderJson();
	}

	/**
	 * 根据id删除客户信息
	 */
	public void delCustomerById() {
		String id = getPara("id");
		boolean result = CustomerModel.delById(id);
		setAttr("data", result);
		renderJson();
	}

	/**
	 * @TODO 已跟进信息,待处理信息，已成交信息的接口。
	 * @author lijinpeng 根据status数值进行判断
	 */
	public void getCustomerInfo() {
		String phone_no = getPara("phone_no");
		int status = getParaToInt("status");
		CustomerModel p = CustomerModel.Byphone_no(phone_no);
		if (p.getPhone_no().equals(phone_no)) {
			if (status == 2) {
				// 2为已跟进
				List<CustomerModel> had = CustomerModel.findListByStatus(status, phone_no);
				setAttr("data", had);
				renderJson();
			} else if (status == 3) {
				// 3为待处理
				List<CustomerModel> ing = CustomerModel.findListByStatus(status, phone_no);
				setAttr("data", ing);
				renderJson();
			} else if (status == 6) {
				// 6为已成交
				List<CustomerModel> done = CustomerModel.findListByStatus(status, phone_no);
				setAttr("data", done);
				renderJson();
			} else {
				setAttr("data", "出错！");
				renderJson();
			}
		}
	}
	/*
	 * 
	 * 管理员获取客户信息接口
	 * lijinpeng
	 * 
	 */
	
	 public void getByType() {
		 String type = getPara("type");
		 List <CustomerModel> list = CustomerModel.getCustomerNum(type);
		 setAttr("data", list);
		 renderJson();
	 }
}
