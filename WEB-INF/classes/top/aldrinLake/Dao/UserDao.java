/**
 * @author XuFankang
 * time:2019-7-5
 */
package top.aldrinLake.Dao;

import java.sql.SQLException;

import top.aldrinLake.Model.user;



public interface UserDao {
	/**
	 * @param User
	  *  根据用户名和密码查找用户,用于登陆检测
	 */
	public user findUser(user users) throws SQLException;
	/**
	 * @param
	 * 把学习强国分数存进数据库
	 */
	public void setScore(user users) throws SQLException;
	/**
	 * @param
	 * 更新用户  的未提交/不达标次数 ，每晚更新用户状态之前 把不合格人员的“不合格次数”加一(由数据库中控制代码完成)
	 */
	public void setNumberOfFailed() throws SQLException;
	/**
	 * @param
	 * 根据前端的id来查询用户的信息
	 */
	public user selectUserInfo(user users) throws SQLException;
	/**
	 * @param
	 * 每晚更新提交状态（把新时间放在旧时间，把新成绩放在旧成绩，并改变提交状态为0，改变当日合格状态为0）
	 */
	public void updateUserState() throws SQLException;
	/**
	 * @param
	 * 每晚更新状态之前检索一遍数据库，把当天不合格的人 返回这些人的名单,belong是支部参数
	 */
	public String selectPeopleUnQualified(String belong) throws SQLException;
	/**
	 * @param 
	 * 每晚更新用户状态之前 把不合格人员信息存进一张表
	 */
	public void putPeopleUnQualifiedInTable() throws SQLException;
	/**
	 * @param
	 * 分页搜索未达标人员的信息名单,以json字符串的格式返回
	 */
	public String getUnqualifedPeopleList(int page) throws SQLException;
	/**
	 * @param
	 * 根据用户的ID，分页查找用户的缺失记录，以json字符串的格式返回
	 */
	public String getMyUnQualifiedList(user u,int page) throws SQLException;
	/**
	 * 获取所有人的成绩信息（用户表），以json字符串的格式返回
	 */
	public String getHonorGradesList() throws SQLException;
	/**
	 * 修改密码
	 */
	public boolean modifyThePwd(user u,String newPwd) throws SQLException;
	/**
	 * 获取所有人当天的成绩等信息（所有的信息，比较全面，用于在前端页面展示）
	 */
	public String getAllPeopleTodayGrades() throws SQLException;
	/**
	 * 获取所有用户的部分信息（姓名，上次提交成绩，今日提交成绩），用来存进excel
	 */
	public String getAllPropleTodayInfoUsedToStore() throws SQLException;
}
