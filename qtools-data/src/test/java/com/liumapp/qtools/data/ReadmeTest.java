package com.liumapp.qtools.data;

import com.liumapp.qtools.data.core.Model;
import com.liumapp.qtools.data.core.Row;
import com.liumapp.qtools.data.csv.CSVWriter;
import com.liumapp.qtools.data.jdbc.JDBCReader;
import com.liumapp.qtools.data.jdbc.JDBCWriter;
import com.liumapp.qtools.data.service.Reader;
import com.liumapp.qtools.data.service.Transformer;
import com.liumapp.qtools.data.service.Writer;
import org.apache.commons.csv.CSVFormat;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * file ReadmeTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/8
 */
public class ReadmeTest {

    private boolean debug = true;

    /**
     * todo
     */
    @Test
    public void testReaderAndWriteIntoCSVFromJDBC () throws Exception {

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/sdk_emergency";
        String username = "root";
        String password = "adminadmin";
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url,username,password);

        Reader reader = new JDBCReader()
                .setConnection(connection) // jdbc connection
                .setSQL("select * from emergency_app"); // any sql

        Transformer transformer = new Transformer<Row>() {
            @Override
            public void onEvent(Row event, long sequence, boolean endOfBatch) throws Exception {
                //deal each row here
                System.out.println("event is :");
                System.out.println(event.toString());
                System.out.println("sequence is :");
                System.out.println(sequence);
                System.out.println("endofbatch is :");
                System.out.println(endOfBatch);
            }
        };

//        Writer[] writers = new CSVWriter();

        Model model = new Model()
                .setReader(reader)
                .setTransformer(transformer);

        model.start();
    }


}
