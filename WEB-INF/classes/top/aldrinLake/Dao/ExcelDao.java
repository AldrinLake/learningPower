package top.aldrinLake.Dao;

public interface ExcelDao {
	/**
	 * @view ��ҳ��ȡ��ȡexcel�����������
	 */
	public String getExcelInfo(int page);
	/**
	 * @view ���ش�����һ
	 * ����:id �ļ���id
	 */
	public void  addDownloadNum(int id);
	/**
	 * @view ���ļ����������ݿ�
	 */
	public void storeFilePath(String filePath);
}
