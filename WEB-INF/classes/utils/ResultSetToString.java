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
	//把数据库搜索得到的res转化为String格式返回
	ResultSet res;
	
	public  ResultSetToString(ResultSet res) {
		this.res =res;
	}
	
	public String toString() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {	
			ResultSetMetaData md = (ResultSetMetaData) res.getMetaData(); //获得结果集结构信息,元数据
			int columnCount = md.getColumnCount();   //获得列数 

			while(res.next()) {
				Map<String,Object> rowData = new HashMap<String,Object>();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), res.getObject(i));
				}
				list.add(rowData);
			}
		} catch (SQLException e) {
			System.out.println("ResultSet转str失败");
		}
		//创建JSON集合
        JSONArray jsonarray = JSONArray.fromObject(list);
        //把JSON集合转出String字符串输出
        String str = jsonarray.toString();
		return str;
	}
	
}
