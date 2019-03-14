package com.wudi.model.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

/**
 * 
 * @author XIAO
 *
 */
public class CustomerModel extends Model<CustomerModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "customer";
	public static final CustomerModel dao = new CustomerModel();

	public String getId() {
		return get("id");
	}

	public void setId(String id) {
		set("id", id);
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name", name);
	}

	public int getSex() {
		return get("sex");
	}

	public void setSex(int sex) {
		set("sex", sex);
	}

	public void setAge(int age) {
		set("age", age);
	}

	public int getAge() {
		return get("age");
	}

	public void setTel_no(String tel_no) {
		set("tel_no", tel_no);
	}

	public String getTel_no() {
		return get("tel_no");
	}

	public void setDisclose(int disclose) {
		set("disclose", disclose);
	}

	public int getDisclose() {
		return get("disclose");
	}

	public String getnNation() {
		return get("nation");
	}

	public void setNation(String nation) {
		set("nation", nation);
	}

	public void setWork_address(String work_address) {
		set("work_address", work_address);
	}

	public String getWork_address() {
		return get("work_address");
	}

	public void setComments(String comments) {
		set("comments", comments);
	}

	public String getComments() {
		return get("comments");
	}

	public void setPhone_no(String phone_no) {
		set("phone_no", phone_no);
	}

	public String getPhone_no() {
		return get("phone_no");
	}

	public void setcreate_time(Date create_time) {
		set("create_time", create_time);
	}

	public Date getcreate_time() {
		return get("create_time");
	}

	/**
	 * 成交时间
	 * 
	 * @param finish_time
	 */
	public void setfinish_time(Date finish_time) {
		set("finish_time", finish_time);
	}

	public Date getfinish_time() {
		return get("finish_time");
	}

	/**
	 * 1：未处理，首次录入信息 2：已经跟进，已经修改备注 3：待处理，已经跟进，还未成交（显示的按钮就是“处理”） 6：已成交，没有按钮
	 * 
	 * @param status
	 */
	public void setstatus(int status) {
		set("status", status);
	}

	public int getstatus() {
		return get("status");
	}

	public void settype(String type) {
		set("type", type);
	}

	public String gettype() {
		return get("type");
	}

	/**
	 * 分页查询显示，就是查找
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param key
	 * @return
	 */
	public static Page<CustomerModel> getList(int pageNumber, int pageSize, String key, String type) {
		String sele_sql = "select * ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName).append(" where type='").append(type).append(" ' ");
		if (!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("and name like '%" + key + "%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	
	public static Page<CustomerModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select * ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName).append(" ");
		if (!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where  name like '%" + key + "%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}

	/**
	 * 根据id查找
	 * 
	 * @param no
	 * @return
	 */
	public static CustomerModel getById(String id) {

		return dao.findFirst("select * from " + tableName + " where id = ? ", id);
	}

	/**
	 * 保存
	 */
	public static boolean save(String name, int sex, String tel_no, int disclose, int age, String work_address,
			String comments, String phone_no, String nation, String type, int status) {
		CustomerModel model = new CustomerModel();
		model.setId(StringUtil.getId());
		model.setName(name);
		model.setAge(age);
		model.setNation(nation);
		model.setComments(comments);
		model.setDisclose(disclose);
		model.setSex(sex);
		model.setTel_no(tel_no);
		model.setWork_address(work_address);
		model.setPhone_no(phone_no);
		model.settype(type);
		model.setstatus(status);
		model.setcreate_time(new Date());
		return model.save();
	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @return
	 */
	public static boolean save(CustomerModel model) {
		return model.save();
	}

	/**
	 * 更新
	 */
	public static boolean update(String id, String name, int sex, String tel_no, int disclose, int age,
			String work_address, String comments, String phone_no, String nation, String type, int status,
			Date create_time) {
		CustomerModel model = CustomerModel.getById(id);
		boolean result = false;
		if (model == null) {
			return result;
		} else {
			model.setName(name);
			model.setAge(age);
			model.setNation(nation);
			model.setComments(comments);
			model.setDisclose(disclose);
			model.setSex(sex);
			model.setTel_no(tel_no);
			model.setWork_address(work_address);
			model.setPhone_no(phone_no);
			model.settype(type);
			model.setstatus(status);
			try {
				result = model.update();
			} catch (Exception e) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 */
	public static boolean update(String id, CustomerModel model) {
		CustomerModel m = CustomerModel.getById(id);
		boolean result = false;
		if (m == null) {
			return result;
		} else {
			m.setName(model.getName());
			m.setAge(model.getAge());
			m.setNation(model.getnNation());
			m.setComments(model.getComments());
			m.setDisclose(model.getDisclose());
			m.setSex(model.getSex());
			m.setTel_no(model.getTel_no());
			m.setWork_address(model.getWork_address());
			m.setPhone_no(model.getPhone_no());
			m.settype(model.gettype());
			m.setstatus(model.getstatus());
			try {
				result = m.update();
			} catch (Exception e) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 更新或保持
	 * 
	 * @param id
	 * @param name
	 * @param sex
	 * @param tel_no
	 * @param disclose
	 * @param age
	 * @param work_address
	 * @param comments
	 * @param phone_no
	 * @param nation
	 * @return
	 */
	public static boolean saveOrUpate(String id, String name, int sex, String tel_no, int disclose, int age,
			String work_address, String comments, String phone_no, String nation, String type, int status) {
		boolean result = false;
		CustomerModel model = findModel(tel_no, type);
		if (StringUtil.isBlankOrEmpty(id)) {// 为保存
			if (model == null) {
				result = save(name, sex, tel_no, disclose, age, work_address, comments, phone_no, nation, type, status);
			}
		} else {// 更新
			if(model!=null&&model.getId().equals(id)) {
				model.setName(name);
				model.setAge(age);
				model.setNation(nation);
				model.setComments(comments);
				model.setDisclose(disclose);
				model.setSex(sex);
				model.setTel_no(tel_no);
				model.setWork_address(work_address);
				model.setPhone_no(phone_no);
				model.settype(type);
				/*
				 * 1：未处理，首次录入信息 2：已经跟进，已经修改备注 3：待处理，已经跟进，还未成交 6：已成交
				 */
				if (!StringUtil.isBlankOrEmpty(comments)) {
					if (status == 1) {// 只有未处理状态的时候才可以修改
						status = 2;
					}
				}
				model.setstatus(status);
				model.setcreate_time(new Date());
			}
			result = update(id, model);
		}
		return result;
	}

	/**
	 * 根据ID删除数据
	 * 
	 * @param no
	 * @return
	 */
	public static boolean delById(String id) {
		try {
			String delsql = "DELETE FROM " + tableName + " WHERE id=?";
			int iRet = Db.update(delsql, id);
			if (iRet > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<CustomerModel> findModelbyPhone_no(String phone_no, int type) {
		List<CustomerModel> list = null;
		// 要先到admininfo表里查看phone_no是不是一样，如果是，就说明是管理员
		UserInfoModel admin = UserInfoModel.getModeByAdminlogin(phone_no);
		if (admin != null) {// 说明是管理员
			String sql = "select * from " + tableName + " where type = ?";
			list = dao.find(sql, type);
		} else {
			String sql = "select * from " + tableName + " where phone_no=? and type = ?";
			list = dao.find(sql, phone_no, type);
		}

		return list;
	}

	public static List<CustomerModel> getListAll() {
		StringBuffer sql = new StringBuffer();
		sql.append("select *  from ").append(tableName);
		return dao.find(sql.toString());
	}

	public static List<CustomerModel> getCustomerNum(String type) {
		String sql = "select * from " + tableName + " where  type = ?";
		List<CustomerModel> list = dao.find(sql, type);
		return list;
	}

	public static List<CustomerModel> findListByPhone_no(String phone_no) {
		String sql = "select * from " + tableName + " where phone_no=?";
		List<CustomerModel> list = dao.find(sql, phone_no);
		return list;
	}

	public static List<CustomerModel> findListByStatus(int status, String phone_no) {
		String sql = "select * from " + tableName + " where status=? and phone_no =?";
		List<CustomerModel> list = dao.find(sql, status, phone_no);
		return list;
	}

	public static CustomerModel Byphone_no(String phone_no) {
		String sql = "select * from " + tableName + " where phone_no =?";
		return dao.findFirst(sql, phone_no);
	}

	public static boolean updateCustomerStatus(String id, int status) {
		CustomerModel m = getById(id);
		boolean result = false;
		if (m != null) {
			m.setstatus(status);
			result = m.update();
		}
		return result;
	}

	/**
	 * 更改客户信息状态，交易成功
	 * 
	 * @param id
	 * @param status
	 * @return 1：未处理，首次录入信息 2：已经跟进，已经修改备注 3：待处理，已经跟进，还未成交 6：已成交
	 */
	public static boolean completeCustomer(String id) {
		CustomerModel m = getById(id);
		boolean result = false;
		if (m != null) {
			m.setstatus(6);
			m.setfinish_time(new Date());
			result = m.update();// 更新状态为已成交

			// 将这个客户统计到用户账号上，并且判断是否可以升级为二级用户
			List<CustomerModel> list = findListByPhone_no(m.getPhone_no(), 6);
			if (list.size() > 10) {// 如果本用户的客户的成交量大于10人，那么就升级为2级
				// 找到这个客户的用户
				UserInfoModel u = UserInfoModel.findByPhone_no(m.getPhone_no());
				if (u != null) {
					// 0，初级，1为2级
					u.setVip_grade("1");
					u.update();
				}
			}
		}
		return result;
	}

	/**
	 * 处理 @Title: dealCustomer @Description:??? @param @param id @param @return
	 * 参数 @return boolean 返回类型 @throws
	 */
	public static boolean dealCustomer(String id) {
		CustomerModel m = getById(id);
		boolean result = false;
		if (m != null) {
			m.setstatus(3);
			result = m.update();// 更新状态为已成交
		}
		return result;
	}

	public static List<CustomerModel> findListByPhone_no(String phone_no, int status) {
		String sql = "select * from " + tableName + " where phone_no=? and status=?";
		List<CustomerModel> list = dao.find(sql, phone_no);
		return list;
	}
	public static List<CustomerModel> TeamfindListByPhone_no(String phone_no) {
		String sql = "select * from " + tableName + " where phone_no=? and status between 1 and 2";
		List<CustomerModel> list = dao.find(sql, phone_no);
		return list;
	}
	public static List<CustomerModel> AdminGetInfoByType(String type){
		String sql = "select * from " + tableName + " where type=?";
		List<CustomerModel> list = dao.find(sql, type);
		return list;
	}

	/**
	 * 找出本年的数据
	 * 
	 * @return
	 */
	public static List<CustomerModel> getListByCYeanMonth(int month) {
		StringBuffer sql = new StringBuffer();
		Calendar now = Calendar.getInstance();
		int yean = now.get(Calendar.YEAR);
		// 构造类似2019-01的格式（SELECT * from customer where create_time like '%2019-01%'）
		StringBuffer m = new StringBuffer();
		m.append(yean).append("-");
		if (month < 10) {
			m.append("0").append(month);
		} else {
			m.append(month);
		}
		sql.append("select *  from ").append(tableName);
		sql.append(" where create_time like '%" + m + "%'");

		return dao.find(sql.toString());
	}

	public static CustomerModel findModel(String tel_no, String type) {
		String sql = "select * from " + tableName + " where tel_no=? and type=?";
		CustomerModel m = dao.findFirst(sql, tel_no, type);
		return m;
	}
	
	public List<CustomerModel>getXls(String type){
		
		return dao.find("select * from "+tableName+" where type=?",type);

	}
	
}
