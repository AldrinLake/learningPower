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
	  *  �����û�������������û�,���ڵ�½���
	 */
	public user findUser(user users) throws SQLException;
	/**
	 * @param
	 * ��ѧϰǿ������������ݿ�
	 */
	public void setScore(user users) throws SQLException;
	/**
	 * @param
	 * �����û�  ��δ�ύ/�������� ��ÿ������û�״̬֮ǰ �Ѳ��ϸ���Ա�ġ����ϸ��������һ(�����ݿ��п��ƴ������)
	 */
	public void setNumberOfFailed() throws SQLException;
	/**
	 * @param
	 * ����ǰ�˵�id����ѯ�û�����Ϣ
	 */
	public user selectUserInfo(user users) throws SQLException;
	/**
	 * @param
	 * ÿ������ύ״̬������ʱ����ھ�ʱ�䣬���³ɼ����ھɳɼ������ı��ύ״̬Ϊ0���ı䵱�պϸ�״̬Ϊ0��
	 */
	public void updateUserState() throws SQLException;
	/**
	 * @param
	 * ÿ�����״̬֮ǰ����һ�����ݿ⣬�ѵ��첻�ϸ���� ������Щ�˵�����,belong��֧������
	 */
	public String selectPeopleUnQualified(String belong) throws SQLException;
	/**
	 * @param 
	 * ÿ������û�״̬֮ǰ �Ѳ��ϸ���Ա��Ϣ���һ�ű�
	 */
	public void putPeopleUnQualifiedInTable() throws SQLException;
	/**
	 * @param
	 * ��ҳ����δ�����Ա����Ϣ����,��json�ַ����ĸ�ʽ����
	 */
	public String getUnqualifedPeopleList(int page) throws SQLException;
	/**
	 * @param
	 * �����û���ID����ҳ�����û���ȱʧ��¼����json�ַ����ĸ�ʽ����
	 */
	public String getMyUnQualifiedList(user u,int page) throws SQLException;
	/**
	 * ��ȡ�����˵ĳɼ���Ϣ���û�������json�ַ����ĸ�ʽ����
	 */
	public String getHonorGradesList() throws SQLException;
	/**
	 * �޸�����
	 */
	public boolean modifyThePwd(user u,String newPwd) throws SQLException;
	/**
	 * ��ȡ�����˵���ĳɼ�����Ϣ�����е���Ϣ���Ƚ�ȫ�棬������ǰ��ҳ��չʾ��
	 */
	public String getAllPeopleTodayGrades() throws SQLException;
	/**
	 * ��ȡ�����û��Ĳ�����Ϣ���������ϴ��ύ�ɼ��������ύ�ɼ������������excel
	 */
	public String getAllPropleTodayInfoUsedToStore() throws SQLException;
}
