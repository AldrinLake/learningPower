import java.io.IOException;

import top.aldrinLake.Service.Impl.UserServeImpl;;
public class test {
	public String FilePath;
	public static void main(String[] args) throws IOException {
		 UserServeImpl usi = new UserServeImpl();
	     //usi.putPeopleUnQualifiedInTable();//把前一天未达标的人放进另一个数据表
	     //usi.updateUserState();//更新（刷新）用户状态
		 //usi.putTodayInfoInExcel();
		 new test().a();
	}
	public void a() {
		String str = getClass().getResource("/").getFile().toString();
		str = str.substring(0, str.length()-14);
		System.out.print(str);
	}
	
}
