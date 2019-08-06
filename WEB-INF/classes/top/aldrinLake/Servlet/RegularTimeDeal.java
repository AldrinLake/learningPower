package top.aldrinLake.Servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import top.aldrinLake.Service.Impl.UserServeImpl;
/**
 * 
 * @author XuFankang
 * @Time:Jul 18, 2019
 * TODO定时执行任务（每天凌晨执行）
 *
 */
@WebServlet(value={"/RegularTimeDeal"},loadOnStartup=1)
public class RegularTimeDeal extends HttpServlet  {
	/**
	 * 
	 */
	public static HttpServletRequest request;//excel表上传到服务器得到路径所需的东西
	private static final long serialVersionUID = -617410660736717093L;
	//时间间隔(一天)  
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
	@Override
	public void init() throws ServletException {
		System.out.println("开始行动");
		Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.HOUR_OF_DAY, 0); //凌晨0点0分1秒执行  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 1);  
        Date date=calendar.getTime(); //第一次执行定时任务的时间  
        //如果第一次执行定时任务的时间 小于当前的时间  
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。  
        if (date.before(new Date())) {  
            date = this.addDay(date, 1);  
        }  
        Timer timer = new Timer();  
        MyTask task = new MyTask();  
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。  
        timer.schedule(task,date,PERIOD_DAY);   
}  
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse arg1) throws ServletException, IOException {
		System.out.println("===Servlet程序===");
	}
	// 增加或减少天数  
    public Date addDay(Date date, int num) {  
        Calendar startDT = Calendar.getInstance();  
        startDT.setTime(date);  
        startDT.add(Calendar.DAY_OF_MONTH, num);  
        return startDT.getTime();  
    }  

}
/**
 * 类描述：这个类代表一个定时任务
 * @author xfk
 * 自定义定时任务，继承TimerTask
 * 
 */
class MyTask extends TimerTask{
    //在run方法中的语句就是定时任务执行时运行的语句。
	public static Log  log = LogFactory.getLog(MyTask.class);

	
    public void run() {
        System.out.println("Hello!! 早日康复 现在是："+new Date());
        
        UserServeImpl usi = new UserServeImpl();
        /*****把前一天所有人情况存在excel**********/
	    usi.putTodayInfoInExcel();
		/******************************************/
        try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}//暂停几秒，给服务器休息一下
        /****把前一天未达标的人放进另一个数据表*****/
        usi.putPeopleUnQualifiedInTable();
        /*****开始发送邮件****/
        usi.sendEmail_menUnqualified("1471600276@qq.com","一支部");//发送邮件给（徐凡康）
        try {TimeUnit.SECONDS.sleep(8);} catch (InterruptedException e) {e.printStackTrace();}//暂停几秒，防止邮箱被封
        //usi.sendEmail_menUnqualified("1550872304@qq.com","二支部");//发送邮件给（朱伟）
        //try {TimeUnit.SECONDS.sleep(8);} catch (InterruptedException e) {e.printStackTrace();}//暂停几秒，防止邮箱被封
        usi.sendEmail_menUnqualified("1307278401@qq.com","一支部");//发送邮件给（涂佳芸）
        //try {TimeUnit.SECONDS.sleep(8);} catch (InterruptedException e) {e.printStackTrace();}//暂停几秒，防止邮箱被封
        //usi.sendEmail_menUnqualified("1577355947@qq.com","二支部");//发送邮件给（朱晨）
        /****发送邮件结束****/
        usi.updateUserState();//更新（刷新）用户状态
    }

}
