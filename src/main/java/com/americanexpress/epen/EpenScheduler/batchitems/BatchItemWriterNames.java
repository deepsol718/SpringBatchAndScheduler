package com.americanexpress.epen.EpenScheduler.batchitems;

import com.americanexpress.epen.EpenScheduler.dao.IndianNamesDAO;
import com.americanexpress.epen.EpenScheduler.model.IndianName;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BatchItemWriterNames implements ItemWriter<IndianName> {

    @Autowired
    IndianNamesDAO indianNamesDAO;

    @Override
    public void write(Chunk<? extends IndianName> chunk) throws Exception {
        System.out.println("inside writer method:");
        List<IndianName> indianNames = new ArrayList<>();
        for (IndianName indianName: chunk){
            indianNames.add(indianName);
        }

        indianNamesDAO.saveAll(indianNames);
    }

}
