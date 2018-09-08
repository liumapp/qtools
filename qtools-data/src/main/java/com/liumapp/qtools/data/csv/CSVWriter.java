package com.liumapp.qtools.data.csv;

import com.liumapp.qtools.data.core.Field;
import com.liumapp.qtools.data.core.Row;
import com.liumapp.qtools.data.service.Each;
import com.liumapp.qtools.data.service.Writer;
import com.lmax.disruptor.dsl.Disruptor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;

/**
 * file CSVWriter.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/8
 */
public class CSVWriter implements Writer<Row>, Each<Row> {

    private BufferedWriter ioWriter ;

    private Field field ;

    private boolean hasWriteHeader = false;

    private CSVPrinter csvPrinter;

    private CSVFormat csvFormat = CSVFormat.MYSQL;

    @Override
    public void setDisruptor(Disruptor<Row> disruptor) {

    }

    @Override
    public void before() throws Exception {
        csvPrinter = new CSVPrinter(ioWriter, csvFormat);
    }

    @Override
    public void after() throws Exception {
        ioWriter.close();
    }

    @Override
    public void onEvent(Row row) throws Exception {

        try {
            if(!row.isCanWrite()) return;
            dealEach(row);
            if(this.field==null) this.field = row.getField();

            int size= field.size();
            StringBuffer buffer = new StringBuffer();
            if(!hasWriteHeader) {
                //write header
                String ks [] =field.toStringArray();

                csvPrinter.printRecord(ks);
                hasWriteHeader=true;
            }

            csvPrinter.printRecord(row.toStringArray());

        }catch (Exception e){
            e.printStackTrace();
            csvPrinter.close();
            System.exit(-1);//emergency exit
//            disruptor.shutdown(3, TimeUnit.SECONDS);

            throw new Exception(e);
        }finally {
            if(!row.isCanRead()) row.setCanRead(true);
        }
    }

    public java.io.Writer getIoWriter() {
        return ioWriter;
    }

    public CSVWriter setIoWriter(java.io.Writer ioWriter) {
        this.ioWriter = new BufferedWriter(ioWriter);
        return this;
    }

    @Override
    public void dealEach(Row row) throws Exception {

    }


    public CSVFormat getCsvFormat() {
        return csvFormat;
    }

    public CSVWriter setCsvFormat(CSVFormat csvFormat) {
        this.csvFormat = csvFormat;
        return this;
    }
}
