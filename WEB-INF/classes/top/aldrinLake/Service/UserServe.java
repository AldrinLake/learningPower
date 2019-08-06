package top.aldrinLake.Service;

import top.aldrinLake.Model.user;

public interface UserServe {
	//登录检测
	public user checkLogin(user u);
	//学习强国分数更新
	public void setScore(user u);
	//更新用户 未提交/不达标 的次数
	public void setNumberOfFailed();
	//根据前端的id搜索用户信息
	public user selectUserInfo(user u);
	//每晚更新提交状态（把新时间放在旧时间，把新成绩放在旧成绩，并改变提交状态为0，改变当日合格状态为0）
	public void updateUserState();
	//每晚更新状态之前检索一遍数据库，根据支部查找前一天不合格的人 返回这些人的名单
	public String selectPeopleUnQualified(String belong);
	//每晚更新状态之前检索一遍数据库，把当天不合格的人记录在一张记录表上
	public void putPeopleUnQualifiedInTable();
	//分页搜索未达标人员的信息名单,以json字符串的格式返回
	public String getUnqualifedPeopleList(int page);
	//根据用户的ID，分页查找用户的缺失记录，以json字符串的格式返回
	public String getMyUnQualifiedList(user user, int page);
	//获取所有人的成绩信息（用户表），以json字符串的格式返回
	public String getHonorGradesList();
	//修改密码，修改之前先判断原始密码是否符合
	public boolean modifyThePwd(user u,String oldPwd,String newPwd);
	//发送邮件给指定联系邮箱belong是“支部”
	public void sendEmail_menUnqualified(String email,String belong);
	//获取所有人当天的成绩信息（姓名，上次分数，这次分数）并且把情况单独存进一张excel表
	public String getAllPeopleTodayGrades();
	//把用户当天的成绩存入一张excel表，每天存一张，放在服务器上（管理员要定期下载，汇总表格）
	public void putTodayInfoInExcel();
}
