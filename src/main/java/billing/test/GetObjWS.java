package billing.test;


import billing.test.dao.DBConnection;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebService()
public class GetObjWS {

    @WebMethod(operationName = "GetObj")
    public String GetObj(String s) throws SQLException{
        Connection con = DBConnection.getDBConnection();
        StringBuilder res = new StringBuilder("<Attributes>");
        try {

            OracleCallableStatement statement = (OracleCallableStatement) con.prepareCall("{? = call JAVATEST.TESTPKG.GETOBJ(?,?)}");

        ArrayDescriptor arrayDesc = ArrayDescriptor.createDescriptor("TYPE_OBJ_TBL", con);
        List arrayList = ParseString(s);
        ARRAY array = new ARRAY(arrayDesc, con, arrayList.toArray());
        statement.registerOutParameter(1, OracleTypes.NVARCHAR);
        statement.setArray(2, array);
        statement.registerOutParameter(3, OracleTypes.ARRAY, "TYPE_OBJ_TBL");
        statement.execute();
        array = statement.getARRAY(3);
        Object[] objects = (Object[]) array.getArray();
       // ArrayList res = new ArrayList();

        //StructDescriptor structDesc = StructDescriptor.createDescriptor("TYPE_OBJ", con);

        Map map = (((STRUCT) objects[0]).getMap());
        for (Object obj : objects) {
            //record = ((STRUCT) obj).getAttributes();
            //System.out.println(record[0] + " | " + record[1] + " | " + record[2]);
           // res.add((Object [])((STRUCT) obj).getAttributes());
           //res.append(((STRUCT) obj).get)
        }
            System.out.println(map.size());
        } catch (SQLException e){

        }finally {

            if (con != null) {
                con.close();
            }

        }
        return res.toString();


    }

    private List ParseString(String s){
        List res = new ArrayList<String>();
        res.add(s.split("\\;",3));
        return res;
    }

}
