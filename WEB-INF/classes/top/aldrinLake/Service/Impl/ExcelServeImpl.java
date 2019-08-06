package top.aldrinLake.Service.Impl;

import top.aldrinLake.Dao.Impl.ExcelDaoImpl;
import top.aldrinLake.Service.ExcelServe;
public class ExcelServeImpl implements ExcelServe {

	@Override
	public void addDownloadNum(int id) {
		// TODO Auto-generated method stub
		ExcelDaoImpl edi = new ExcelDaoImpl();
		edi.addDownloadNum(id);
	}

	@Override
	public void storeFilePath(String filePath) {
		// TODO Auto-generated method stub
		ExcelDaoImpl edi = new ExcelDaoImpl();
		edi.storeFilePath(filePath);
	}

	@Override
	public String getExcelInfo(int page) {
		// TODO Auto-generated method stub
		ExcelDaoImpl edi = new ExcelDaoImpl();
		String jsonStr = edi.getExcelInfo(page);
		return jsonStr;
	}

}
