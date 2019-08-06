package top.aldrinLake.Dao;

public interface ExcelDao {
	/**
	 * @view 分页提取获取excel表的所有数据
	 */
	public String getExcelInfo(int page);
	/**
	 * @view 下载次数加一
	 * 参数:id 文件的id
	 */
	public void  addDownloadNum(int id);
	/**
	 * @view 把文件名存入数据库
	 */
	public void storeFilePath(String filePath);
}
