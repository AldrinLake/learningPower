package top.aldrinLake.Service;

public interface ExcelServe {
	/**
	 * 文件下载次数自增
	 */
	public void addDownloadNum(int id);
	/**
	 * @view 把文件名存入数据库
	 */
	public void storeFilePath(String filePath);
	/**
	 * @view 获取excel表的所有数据
	 */
	public String getExcelInfo(int page);
}
