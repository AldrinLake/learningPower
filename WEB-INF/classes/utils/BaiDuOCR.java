package utils;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;


public class BaiDuOCR {
	 //设置APPID/AK/SK
    public static final String APP_ID = "16730582";
    public static final String API_KEY = "I6Z5VNyvKVgXGGpOCsYtRM1P";
    public static final String SECRET_KEY = "pRUMiBV1K6YjtQjMBKiQkcRbeqmu4t8Z";
    /**
     * @category通用文字识别（普通版，50000次/天 免费）
     * @param PicPath
     * @return
     */
    public static String startOCR(String PicPath) {
    	// 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String path = PicPath;
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        String result = res.getJSONArray("words_result").toString();
        //System.out.println(result);
        return result;
    }
    /**
     * @category通用文字识别高精度版（500次/天 免费）
     * @param args
     */
    public static String startOCR_Heigh(String PicPath) {
    	// 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("probability", "true");
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        
        // 参数为本地图片路径
        String image = PicPath;
        JSONObject res = client.basicAccurateGeneral(image, options);
        String text = res.getJSONArray("words_result").toString();
		return text;
    }
    
	public static void main(String[] args) {
    	String text = startOCR_Heigh("C:\\Users\\lenovo\\Desktop\\ab\\1526.png");
        System.out.print(text);
    }
}
