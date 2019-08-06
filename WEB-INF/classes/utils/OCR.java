package utils;
/**
 * @author XuFankang
 * @date 2019-7-7
 * ����ɨ�����֣���Ӣ�ģ�ֻ����ӡˢ��
 */
import com.java4less.ocr.tess3.OCRFacade;

public class OCR {

	public static String OCR_recognition(String picAddress) {
		OCRFacade facade=new OCRFacade();
		String text="";
		try {
			text=facade.recognizeFile(picAddress, "eng");
			System.out.println("Text in the image is: ");
			System.out.println(text);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//��������
		System.out.println(OCR_recognition("C:\\Users\\lenovo\\Desktop\\670.jpg"));
		
	}

}
