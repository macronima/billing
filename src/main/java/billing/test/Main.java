package billing.test;

import billing.test.dao.DBConnection;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {






       public static void main(String[] args) throws SQLException  {

            Connection con = null;

            try {
                con = DBConnection.getDBConnection();
                s2(con);

            } catch (SQLException e) {

                System.out.println(e.getMessage());

            } finally {

                    if (con != null) {
                        con.close();
                    }

            }

        }

    private static int GetObj(String s) throws SQLException {
        billing.test.GetObjWS service = new billing.test.GetObjWS();
        billing.test.GetObjWS port = service.GetObj(s);
        return port.add(i, j);
    }

        private static void s2(Connection con) throws SQLException {


            Scanner sc = new Scanner(System.in);
            OracleCallableStatement statement = (OracleCallableStatement) con.prepareCall("{call JAVATEST.TESTPKG.CALC(?,?,?)}");
            int x = sc.nextInt();
            int y = sc.nextInt();
            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.registerOutParameter(3, Types.TINYINT);
            statement.execute();
            System.out.println(statement.getByte(3));

        }

        private static void s3(Connection con) throws SQLException {
            Scanner sc = new Scanner(System.in);
            OracleCallableStatement statement = (OracleCallableStatement)con.prepareCall("{? = call JAVATEST.TESTPKG.getInfoCity(?,?,?)}");

                System.out.println("Введите название города: ");
                String cityName = sc.nextLine();
                System.out.println("Добавьте отметку о городе: ");
                String addText = sc.nextLine();

                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.setString(2, cityName);
                statement.setString(3, addText);
                statement.registerOutParameter(4, Types.CLOB);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                StringBuilder sb = new StringBuilder();
                rs.next();
                    sb      .append("id: ")
                            .append(rs.getString(1))
                            .append("\nНазвание: ")
                            .append(rs.getString(2))
                            .append("\nКод города: ")
                            .append(rs.getString(3));

                System.out.println (sb.toString());
                System.out.println("Описание: ");
                Clob clob =  statement.getClob(4);
                System.out.println(clob.getSubString(1,(int)clob.length()));
        }



         public static ArrayList s4(Connection con,ArrayList arrayList) throws SQLException {
             OracleCallableStatement statement = (OracleCallableStatement) con.prepareCall("{? = call JAVATEST.TESTPKG.GETOBJ(?,?)}");
             ArrayDescriptor arrayDesc = ArrayDescriptor.createDescriptor("TYPE_OBJ_TBL", con);
             ARRAY array = new ARRAY(arrayDesc, con, arrayList.toArray());
             statement.registerOutParameter(1, OracleTypes.NVARCHAR);
             statement.setArray(2, array);
             statement.registerOutParameter(3, OracleTypes.ARRAY, "TYPE_OBJ_TBL");
             statement.execute();
             array = statement.getARRAY(3);
             Object[] objects = (Object[]) array.getArray();
             ArrayList res = new ArrayList();
             StructDescriptor structDesc = StructDescriptor.createDescriptor("TYPE_OBJ", con);
             for (Object obj : objects) {
                 //record = ((STRUCT) obj).getAttributes();
                 //System.out.println(record[0] + " | " + record[1] + " | " + record[2]);
                 res.add((Object [])((STRUCT) obj).getAttributes());
             }
             return res;

         }








    }
