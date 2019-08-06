package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import net.sf.json.JSONArray;

public class ResultSetToString {
	//�����ݿ������õ���resת��ΪString��ʽ����
	ResultSet res;
	
	public  ResultSetToString(ResultSet res) {
		this.res =res;
	}
	
	public String toString() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {	
			ResultSetMetaData md = (ResultSetMetaData) res.getMetaData(); //��ý�����ṹ��Ϣ,Ԫ����
			int columnCount = md.getColumnCount();   //������� 

			while(res.next()) {
				Map<String,Object> rowData = new HashMap<String,Object>();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), res.getObject(i));
				}
				list.add(rowData);
			}
		} catch (SQLException e) {
			System.out.println("ResultSetתstrʧ��");
		}
		//����JSON����
        JSONArray jsonarray = JSONArray.fromObject(list);
        //��JSON����ת��String�ַ������
        String str = jsonarray.toString();
		return str;
	}
	
}
