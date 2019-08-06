package top.aldrinLake.Dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import top.aldrinLake.Dao.ExcelDao;
import utils.ResultSetToString;
import utils.conJDBC;

public class ExcelDaoImpl implements ExcelDao{

	@Override
	public String getExcelInfo(int page) {
		// TODO Auto-generated method stub
		//这段代码仅供小程序端调用，web端就直接用jsp代码嵌入了
		String str="";
		try {
			Connection conn = conJDBC.getcon();
			String SQL = "SELECT * FROM excel  ORDER BY id DESC limit ?,20";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, page*20);
			ResultSet res = pstmt.executeQuery();
			ResultSetToString rsts = new ResultSetToString(res);
			str = rsts.toString(); 	
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			
		}
		return str;
	}

	@Override
	public void addDownloadNum(int id) {
		// TODO Auto-generated method stub
		try {
			Connection conn = conJDBC.getcon();
			String SQL = "UPDATE excel SET amountOfDownloads=amountOfDownloads+1 WHERE id=?";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			
		}
		
	}

	@Override
	public void storeFilePath(String filePath) {
		// TODO Auto-generated method stub
		//把文件路径存入数据库
		String SQL = "INSERT INTO excel (fileName) values (?)";
		System.out.println(SQL);
		Connection conn = conJDBC.getcon();
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, filePath);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
