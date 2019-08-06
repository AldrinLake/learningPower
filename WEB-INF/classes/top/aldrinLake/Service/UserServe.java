package top.aldrinLake.Service;

import top.aldrinLake.Model.user;

public interface UserServe {
	//��¼���
	public user checkLogin(user u);
	//ѧϰǿ����������
	public void setScore(user u);
	//�����û� δ�ύ/����� �Ĵ���
	public void setNumberOfFailed();
	//����ǰ�˵�id�����û���Ϣ
	public user selectUserInfo(user u);
	//ÿ������ύ״̬������ʱ����ھ�ʱ�䣬���³ɼ����ھɳɼ������ı��ύ״̬Ϊ0���ı䵱�պϸ�״̬Ϊ0��
	public void updateUserState();
	//ÿ�����״̬֮ǰ����һ�����ݿ⣬����֧������ǰһ�첻�ϸ���� ������Щ�˵�����
	public String selectPeopleUnQualified(String belong);
	//ÿ�����״̬֮ǰ����һ�����ݿ⣬�ѵ��첻�ϸ���˼�¼��һ�ż�¼����
	public void putPeopleUnQualifiedInTable();
	//��ҳ����δ�����Ա����Ϣ����,��json�ַ����ĸ�ʽ����
	public String getUnqualifedPeopleList(int page);
	//�����û���ID����ҳ�����û���ȱʧ��¼����json�ַ����ĸ�ʽ����
	public String getMyUnQualifiedList(user user, int page);
	//��ȡ�����˵ĳɼ���Ϣ���û�������json�ַ����ĸ�ʽ����
	public String getHonorGradesList();
	//�޸����룬�޸�֮ǰ���ж�ԭʼ�����Ƿ����
	public boolean modifyThePwd(user u,String oldPwd,String newPwd);
	//�����ʼ���ָ����ϵ����belong�ǡ�֧����
	public void sendEmail_menUnqualified(String email,String belong);
	//��ȡ�����˵���ĳɼ���Ϣ���������ϴη�������η��������Ұ�����������һ��excel��
	public String getAllPeopleTodayGrades();
	//���û�����ĳɼ�����һ��excel��ÿ���һ�ţ����ڷ������ϣ�����ԱҪ�������أ����ܱ��
	public void putTodayInfoInExcel();
}
