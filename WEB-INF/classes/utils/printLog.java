package utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @version日志测试（可以连续输入不覆盖）
 * @author XuFankang
 * @Time:Jul 18, 2019
 * TODO
 *
 */
public class printLog {
	public static Log  log = LogFactory.getLog(printLog.class);
	public static void store(String str) {
		log.info(str);
	}
	
	public static void main(String[] args) {
	    store("测试1");       
	}

}  

