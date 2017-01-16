package billing.test.dao;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Init {
    public static List initArray(Connection con){
        List arrayList = new ArrayList();

        try {
            StructDescriptor structDesc = StructDescriptor.createDescriptor("TYPE_OBJ", con);
            Object[] record = new Object[3];
            //Определение значений и создание объекта STRUCT./*
            record[0] = 11;
            record[1] = "о";
            record[2] = "один";
            arrayList.add(new STRUCT(structDesc, con, record));

            record[0] = 2;
            record[1] = 2;
            record[2] = 2;
            arrayList.add(new STRUCT(structDesc, con, record));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //   arrayList.add((Object)new TypeObj(1,"11","11"));
        //              arrayList.add((Object)new TypeObj(2,"22","22"));
        return arrayList;
}

}
