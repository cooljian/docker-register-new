package com.dock.test;

import com.alibaba.fastjson.JSON;
import com.dock.core.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gaojian on 2016/4/22.
 */

@Service
public class testDb
{
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Connection connection = null;
        List<TestUseVO> testUseVOs = Lists.newArrayList();
        List<Map<String, Object>> dataList = Lists.newArrayList();
        try {
            connection = connectDB();
            String sql = "SELECT CONV(gpf.PERIOD_STATUS_01, 10, 2) hour08, CONV(gpf.PERIOD_STATUS_02,10,2) hour816, CONV(gpf.PERIOD_STATUS_03,10,2) hour1624, \n" +
                    "\n" +
                    "CASE DAYOFWEEK(gpf.PERIOD_DATE)  WHEN 1 THEN '星期日' WHEN 2 THEN '星期一' WHEN 3 THEN '星期二' WHEN 4 THEN '星期三' WHEN 5 THEN '星期四' WHEN 6 THEN '星期五' WHEN 7 THEN '星期六' end dayweek\n" +
                    "\n" +
                    ", gpf.PERIOD_DATE\n" +
                    "FROM GYM_PHYSIC_FIELD_RESOURCES gpf ,\n" +
                    "\t\t\t(\n" +
                    "\t\t\t\tSELECT gfc.PHYSIC_FIELD_ID pfid\n" +
                    "\t\t\t\tFROM GYM_FIELD_COMPOSE gfc,\n" +
                    "\t\t\t\t(\n" +
                    "\t\t\t\t\tSELECT FIELD_ID\n" +
                    "\t\t\t\t\tFROM GYM_FIELDS\n" +
                    "\t\t\t\t\tWHERE GYMNASIUM_ID = 3\n" +
                    "\t\t\t\t\tAND REMOVEMARK = 0\n" +
                    "\t\t\t\t)a\n" +
                    "\t\t\t\tWHERE a.FIELD_ID = gfc.FIELD_ID\n" +
                    "\t\t\t) b\n" +
                    "WHERE gpf.PHYSIC_FIELD_ID = b.pfid\n" +
                    "AND gpf.PERIOD_DATE >= '2014-01-01'\n" +
                    "AND gpf.PERIOD_DATE\t<= '2016-11-01'\n" +
                    "ORDER BY gpf.PERIOD_DATE DESC\n";

            dataList = handleData(connection, sql);
            DateTime dateTime = DateTime.parse("2016-11-01", DateTimeFormat.forPattern("yyyy-MM-dd"));
            Integer index = 0;

            do {
                List<Map<String, Object>> filterList = Lists.newArrayList();
                for(int i = index; i < dataList.size(); i++){
                    if(dataList.get(i).get("PERIOD_DATE").equals(dateTime.toString("yyyy-MM-dd"))){
                        filterList.add(dataList.get(i));
                        index++;
                    }else{
                        break;
                    }
                }
                dateTime = dateTime.minusDays(1);
                if(filterList.size() == 0 || filterList == null){
                    continue;
                }
                List<TestVO> testVOs = JSON.parseArray(JSON.toJSONString(filterList), TestVO.class);
                Integer numRate1 = 0;Integer numRate7 = 0;Integer numRate13 = 0;Integer numRate19 = 0;
                Integer numRate2 = 0;Integer numRate8 = 0;Integer numRate14 = 0;Integer numRate20 = 0;
                Integer numRate3 = 0;Integer numRate9 = 0;Integer numRate15 = 0;Integer numRate21 = 0;
                Integer numRate4 = 0;Integer numRate10 = 0;Integer numRate16 = 0;Integer numRate22 = 0;
                Integer numRate5 = 0;Integer numRate11 = 0;Integer numRate17 = 0;Integer numRate23 = 0;
                Integer numRate6 = 0;Integer numRate12 = 0;Integer numRate18 = 0;Integer numRate0 = 0;

                TestUseVO testUseVO = new TestUseVO();

                for(TestVO testVO : testVOs){
                    List<String> oneDay = Lists.newArrayList();
                    oneDay.addAll(test.transferTime(testVO.getHour08()));
                    oneDay.addAll(test.transferTime(testVO.getHour816()));
                    oneDay.addAll(test.transferTime(testVO.getHour1624()));
                    if(oneDay.get(0).contains("1")){
                        numRate0++;
                    }if(oneDay.get(1).contains("1")){
                        numRate1++;
                    }if(oneDay.get(2).contains("1")){
                        numRate2++;
                    }if(oneDay.get(3).contains("1")){
                        numRate3++;
                    }if(oneDay.get(4).contains("1")){
                        numRate4++;
                    }if(oneDay.get(5).contains("1")){
                        numRate5++;
                    }if(oneDay.get(6).contains("1")){
                        numRate6++;
                    }if(oneDay.get(7).contains("1")){
                        numRate7++;
                    }if(oneDay.get(8).contains("1")){
                        numRate8++;
                    }if(oneDay.get(9).contains("1")){
                        numRate9++;
                    }if(oneDay.get(10).contains("1")){
                        numRate10++;
                    }if(oneDay.get(11).contains("1")){
                        numRate11++;
                    }if(oneDay.get(12).contains("1")){
                        numRate12++;
                    }if(oneDay.get(13).contains("1")){
                        numRate13++;
                    }if(oneDay.get(14).contains("1")){
                        numRate14++;
                    }if(oneDay.get(15).contains("1")){
                        numRate15++;
                    }if(oneDay.get(16).contains("1")){
                        numRate16++;
                    }if(oneDay.get(17).contains("1")){
                        numRate17++;
                    }if(oneDay.get(18).contains("1")){
                        numRate18++;
                    }if(oneDay.get(19).contains("1")){
                        numRate19++;
                    }if(oneDay.get(20).contains("1")){
                        numRate20++;
                    }if(oneDay.get(21).contains("1")){
                        numRate21++;}
                    if(oneDay.get(22).contains("1")){
                        numRate22++;
                    }if(oneDay.get(23).contains("1")){
                        numRate23++;
                    }
                }

                System.out.println("time" + testVOs.get(0).getPERIOD_DATE());

                int size = testVOs.size();
                testUseVO.setDate(testVOs.get(0).getPERIOD_DATE());
                testUseVO.setDayweek(testVOs.get(0).getDayweek());
                testUseVO.setTime1(new BigDecimal(numRate0).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime2(new BigDecimal(numRate1).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime3(new BigDecimal(numRate2).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime4(new BigDecimal(numRate3).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime5(new BigDecimal(numRate4).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime6(new BigDecimal(numRate5).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime7(new BigDecimal(numRate6).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime8(new BigDecimal(numRate7).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime9(new BigDecimal(numRate8).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime10(new BigDecimal(numRate9).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime11(new BigDecimal(numRate10).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime12(new BigDecimal(numRate11).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime13(new BigDecimal(numRate12).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime14(new BigDecimal(numRate13).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime15(new BigDecimal(numRate14).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime16(new BigDecimal(numRate15).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime17(new BigDecimal(numRate16).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime18(new BigDecimal(numRate17).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime19(new BigDecimal(numRate18).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime20(new BigDecimal(numRate19).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime21(new BigDecimal(numRate20).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime22(new BigDecimal(numRate21).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime23(new BigDecimal(numRate22).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");
                testUseVO.setTime24(new BigDecimal(numRate23).divide(new BigDecimal(size+""), 2, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100"))+"%");

                testUseVOs.add(testUseVO);

            }while (dateTime.compareTo(DateTime.parse("2014-01-01", DateTimeFormat.forPattern("yyyy-MM-dd"))) != -1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connection.close();
        }
        List<Turple<String, String>> turples = Lists.newArrayList();
        turples.add(new Turple<>("date","日期"));turples.add(new Turple<>("dayweek","星期"));
        turples.add(new Turple<>("Time1","00:00-01:00"));turples.add(new Turple<>("Time2","01:00-02:00"));turples.add(new Turple<>("Time3","02:00-03:00"));
        turples.add(new Turple<>("Time4","03:00-04:00"));turples.add(new Turple<>("Time5","04:00-05:00"));turples.add(new Turple<>("Time6","05:00-06:00"));
        turples.add(new Turple<>("Time7","06:00-07:00"));turples.add(new Turple<>("Time8","07:00-08:00"));turples.add(new Turple<>("Time9","08:00-09:00"));
        turples.add(new Turple<>("Time10","09:00-10:00"));turples.add(new Turple<>("Time11","10:00-11:00"));turples.add(new Turple<>("Time12","11:00-12:00"));
        turples.add(new Turple<>("Time13","12:00-13:00"));turples.add(new Turple<>("Time14","13:00-14:00"));turples.add(new Turple<>("Time15","14:00-15:00"));
        turples.add(new Turple<>("Time16","15:00-16:00"));turples.add(new Turple<>("Time17","16:00-17:00"));turples.add(new Turple<>("Time18","17:00-18:00"));
        turples.add(new Turple<>("Time19","18:00-19:00"));turples.add(new Turple<>("Time20","19:00-20:00"));turples.add(new Turple<>("Time21","20:00-21:00"));
        turples.add(new Turple<>("Time22","21:00-22:00"));turples.add(new Turple<>("Time23","22:00-23:00"));turples.add(new Turple<>("Time24","23:00-24:00"));

        ExcelUtils.excelExport(testUseVOs, turples, "体院附中场馆利用率");

        System.out.println("over");
    }

    private static Connection connectDB() throws IOException, ClassNotFoundException, SQLException {
//        String driver = "com.mysql.jdbc.Driver";
//        String url = "jdbc:mysql://10.10.62.244:3306/tenant?useUnicode=true&characterEncoding=utf-8" ;
//        String user = "gym";
//        String password = "L4KjmrxYfQyMFaKB";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://xxxx:3306/tenant?useUnicode=true&characterEncoding=utf-8" ;
        String user = "xxxx";
        String password = "xxxx";
        Class.forName(driver) ;
        Connection Connection = (Connection) DriverManager.getConnection(url, user, password);
        return Connection;
    }

    public static List<String> getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> columnNames = new ArrayList<String>();
        int count = rsmd.getColumnCount();
        for (int i=1; i<=count; i++) {
            String columnName = rsmd.getColumnName(i);
            columnNames.add(columnName);
        }


        return columnNames;
    }

    public static List<Map<String, Object>> handleData(Connection connection, ResultSet rs, List<String> columnNames) throws SQLException {
        List<Map<String, Object>> dataList = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> obj = Maps.newHashMap();
            for (String columnName : columnNames) {
                String columnValue = StringUtils.msNull(rs.getString(columnName));
                obj.put(columnName, columnValue);
            }
            dataList.add(obj);
        }

        return dataList;
    }

    public long countRow(ResultSet rs) throws SQLException {
        long count = 0;
        while (rs.next()) {
            count = count + 1;
        }
        return count;
    }

    public static ResultSet getResultSet(Connection connection, String sql) throws SQLException {
        Statement statement = (Statement) connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        return rs;
    }

    public static List<Map<String, Object>> handleData(Connection connection, String sql) throws SQLException, IOException, ClassNotFoundException {
        ResultSet resultSet = getResultSet(connection, sql);
        List<String> columns = getColumnNames(getResultSet(connection, sql));

        List<Map<String, Object>> dataList = handleData(connection, resultSet, columns);

        return dataList;
    }
}
