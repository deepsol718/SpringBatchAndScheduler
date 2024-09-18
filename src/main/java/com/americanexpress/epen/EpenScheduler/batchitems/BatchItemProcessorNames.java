package com.americanexpress.epen.EpenScheduler.batchitems;

import com.americanexpress.epen.EpenScheduler.dao.TableADAO;
import com.americanexpress.epen.EpenScheduler.model.IndianName;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

public class BatchItemProcessorNames implements ItemProcessor<IndianName, IndianName>, StepExecutionListener {

    @Autowired
    TableADAO tableADAO;

    @Override
    public IndianName process(IndianName indianName) throws Exception {
//        multithreadingChecker(indianName);
        return indianName;
    }


    private void multithreadingChecker(IndianName indianName){
        synchronized (this) {
            tableADAO.updateData(indianName.getName());
            System.out.println("updating table_a  "+ indianName.getName());
        }
//        tableADAO.updateData(indianName.getName());
//        System.out.println("updating table_a  "+ indianName.getName());
    }

}
