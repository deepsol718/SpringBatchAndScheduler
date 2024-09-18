package com.americanexpress.epen.EpenScheduler.batchitems;

import com.americanexpress.epen.EpenScheduler.model.IndianName;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
@StepScope
public class BatchItemReaderNames implements ItemReader<IndianName>, StepExecutionListener {

    private StepExecution stepExecution;

    List<IndianName> indianNameList;

    private int next;

    private String fileName = "D:\\Project 2.0\\EpenScheduler\\names";

    @Override
    public IndianName read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        System.out.println("read execution start");
        StepSynchronizationManager.register(stepExecution);
        StepContext stepContext = StepSynchronizationManager.getContext();
        stepContext.setAttribute("intr_case_id", 1);
        if(CollectionUtils.isEmpty(indianNameList) || this.next >= indianNameList.size()){
            return null;
        }

        IndianName indianName = indianNameList.get(next++);
        System.out.println("read execution end");
        return indianName;

    }

    @Override
    public void beforeStep(StepExecution stepExecution){
        System.out.println("read beforestep execution start");
        this.next = 0;
        this.stepExecution = stepExecution;
        List<String> allNames = this.getAllNamesFromFile();
        indianNameList = convertToNameEnity(allNames);
        System.out.println("read beforestep execution end");
    }

    private List<IndianName> convertToNameEnity(List<String> allNames) {
        List<IndianName> nameList = new ArrayList();
        for (String name: allNames){
            IndianName indianName = new IndianName();
            indianName.setName(name);
            nameList.add(indianName);
        }
        return nameList;
    }

    private List<String> getAllNamesFromFile() {
        List<String> allNames = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(this.fileName))){
            String line;
            while ((line = br.readLine()) != null){
//                System.out.println(line);
                allNames.add(line);
            }
        }
        catch (IOException e){
            System.out.println("oh shit: "+ e.getMessage());
        }
        return allNames;
    }
}
