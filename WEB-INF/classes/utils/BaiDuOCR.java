package utils;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;


public class BaiDuOCR {
	 //����APPID/AK/SK
    public static final String APP_ID = "16730582";
    public static final String API_KEY = "I6Z5VNyvKVgXGGpOCsYtRM1P";
    public static final String SECRET_KEY = "pRUMiBV1K6YjtQjMBKiQkcRbeqmu4t8Z";
    /**
     * @categoryͨ������ʶ����ͨ�棬50000��/�� ��ѣ�
     * @param PicPath
     * @return
     */
    public static String startOCR(String PicPath) {
    	// ��ʼ��һ��AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // ��ѡ�������������Ӳ���
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // ��ѡ�����ô����������ַ, http��socket��ѡһ�����߾�������
        //client.setHttpProxy("proxy_host", proxy_port);  // ����http����
        //client.setSocketProxy("proxy_host", proxy_port);  // ����socket����

        // ��ѡ������log4j��־�����ʽ���������ã���ʹ��Ĭ������
        // Ҳ����ֱ��ͨ��jvm�����������ô˻�������
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // ���ýӿ�
        String path = PicPath;
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        String result = res.getJSONArray("words_result").toString();
        //System.out.println(result);
        return result;
    }
    /**
     * @categoryͨ������ʶ��߾��Ȱ棨500��/�� ��ѣ�
     * @param args
     */
    public static String startOCR_Heigh(String PicPath) {
    	// �����ѡ�������ýӿ�
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("probability", "true");
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        
        // ����Ϊ����ͼƬ·��
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
