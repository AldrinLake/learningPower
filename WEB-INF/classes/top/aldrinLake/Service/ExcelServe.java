package top.aldrinLake.Service;

public interface ExcelServe {
	/**
	 * �ļ����ش�������
	 */
	public void addDownloadNum(int id);
	/**
	 * @view ���ļ����������ݿ�
	 */
	public void storeFilePath(String filePath);
	/**
	 * @view ��ȡexcel�����������
	 */
	public String getExcelInfo(int page);
}
