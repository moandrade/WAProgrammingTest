package test.biz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author moandrade
 * This function is not using any external CSVReader so no external libraries are required.
 * I divided the solution in 3 parts: 1 - select min and max; 2 - mark to delete; 3 - delete.
 * TODO: use a proper CSV reader
 * TODO: include validations, at this time an invalid file would blow up the code
 */
public class ClearBadRows {

	public static void main(String[] args) {
		
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		String csvFile = "/Users/IBM_ADMIN/Google Drive/Docs Mauricio/CV/WA Senior Developer Test v1/Part 1 - Coding/exampleC_input.csv";

        try {
        	// read the header row to find out how many columns are present in the CSV file
        	br = new BufferedReader(new FileReader(csvFile));
        	line = br.readLine();
            String[] record = line.split(cvsSplitBy);
            
            // total number of columns minus columns id and decision
            int numColumns = record.length - 2;
            
            // These lists are going to hold the minimum, maximum for each variable column.
            ArrayList<Integer> minList = new ArrayList<Integer>(numColumns); // we already know how many columns we have
            ArrayList<Integer> maxList = new ArrayList<Integer>(numColumns); // same here
            
            // This list will hold the decisions for each record
            ArrayList<Integer> decisionList = new ArrayList<Integer>();      // but we don't know how many records we'll read
            
            // list of lists, holding all the values in the CSV file (only variable values, not ID or decision).
            // each List<Integer> represents the values for a given variable (column)
            ArrayList<ArrayList<Integer>> columns = new ArrayList<ArrayList<Integer>>(numColumns);
            // initializing the above list with empty lists
            for (int i = 0; i < numColumns; i++) {
            	columns.add(i, new ArrayList<Integer>());       // each of these lists represent a column of values
            	minList.add(i, new Integer(Integer.MAX_VALUE)); // initialized with the maximum, so it will be updated at the first comparison
            	maxList.add(i, new Integer(Integer.MIN_VALUE)); // similar thing done here.
            }
            
            /*
             * PART 1 - read the file, filling up each column list with all the values.
             *          as we are sweeping the entire list, it is also selecting the minimum and maximum for each column.
             */
            Integer decision = null;
            int count = 0;
            for (; (line = br.readLine()) != null; count++) {
            	record = line.split(cvsSplitBy);
            	decision = new Integer(record[record.length-1]); // decision is always at the last column in the CSV
            	
            	// iterate through the variable columns, 
            	// skipping the "id column" (j starts with 1) and the "decision column" (length - 1)
                for (int i = 0, j = 1; j < (record.length-1); i++, j++) {
                	Integer val = new Integer(record[j]);
                	// update min value for this column
                	if ((decision.intValue() == 1) && (val.intValue() < minList.get(i).intValue())) {
                		minList.set(i, val);
                	}
                	// update max value for this column
                	if ((decision.intValue() == 1) && (val.intValue() > maxList.get(i).intValue())) {
                		maxList.set(i, val);
                	}
                	// add the value to this column
                	columns.get(i).add(val);
                }
                decisionList.add(decision);
            }
            boolean[] removalList = new boolean[count];
            
            
            /*
             * PART 2 - go through the entire list of columns, comparing if the records (across the lists) all fit the specification.
             */
            for(int c = 0; c < count; c++) {
            	// this variable will tell, at the end of the first loop, if all variables in that record passed the specification.
            	boolean remove = true;  
	            for(int i = 0; i < columns.size(); i++) {
	            	
	            	ArrayList<Integer> iList = columns.get(i);
	            	
	            	int cInt = iList.get(c).intValue();
	            	int cDecision = decisionList.get(c).intValue();
	            	int iMin = minList.get(i).intValue();
	            	int iMax = maxList.get(i).intValue();
	            	
	            	// if it finds any value that is out of the criteria mentioned by the exercise
	            	// it will keep the record
	            	if ((cDecision != 0) || ((cInt >= iMin) && (cInt <= iMax))) {
	            		remove = false;
	            	}
	            }
        		removalList[c] = remove;
            }
            
            /*
             * PART 3 - remove elements from the lists 
             */
            for(int i = 0; i < columns.size(); i++) {
            	ArrayList<Integer> iList = columns.get(i);
            	
            	for (int j = 0; j < iList.size(); j++) {
            		if (removalList[j]) {
            			iList.set(j, null);
            		}
            	}
            }
            
            // prints all lists
            System.out.println(Arrays.toString(columns.toArray()));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
