package com.heisenberg.job;

import com.heisenberg.datamodel.DataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Heisenberg on 7/6/16.
 */
public class CollectData {

    private static Logger logger = LoggerFactory.getLogger(CollectData.class);

    private FileReader fr;
    private BufferedReader br;
    private FileWriter fw;
    private BufferedWriter bw;


    public List<String> getData(File originalData){

        List dataList = new ArrayList<String>();
        try {
            fr = new FileReader(originalData);
            br = new BufferedReader(fr);
            String data = null;
            while ((data = br.readLine()) != null){
                dataList.add(data);
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            closeStream();
        }

        return dataList;

    }


    public void writeTo(String path, List<DataModel> dataModels){

        try{
            fw = new FileWriter(path);
            bw = new BufferedWriter(fw);
            int count = 0;
            for (DataModel dataModel : dataModels){
                bw.write(dataModel.toString());
                bw.newLine();
                bw.flush();
                logger.info("写入数据{}: {}",+count,dataModel.toString());
            }


        }catch (IOException e){
            e.printStackTrace();
        }


    }


    public void closeStream(){

        try {
            if (fr != null && br != null){
                fr.close();
                br.close();
            }

            if (fw != null && bw != null){
                fw.close();
                bw.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
