/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnessapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/** need to change the way total progress calculates and the way custom workouts
 * are generated - the weights need to be user-defined. need to exclude prebuilt templates
 * and add progress sheet to prebuilts, need to incorporate other focus - only have hypertrophy now, need cardio and other sutff need to modify workout generation code*/
 
public class Summary {
    //arraylists preserve order of items, thus many can be used to store the names and progress and they will be in corresponding
    public ArrayList results = new ArrayList(); //holds the filepaths of all for extraction of data later on
    public ArrayList totalProgress = new ArrayList(); //holds total lift progress values 
    public ArrayList mainProgress = new ArrayList(); //holds main lift progress values
    public ArrayList accProgress = new ArrayList(); //holds accessory lift progress
    public ArrayList usernames = new ArrayList(); //holds all the usernames of clients
    public ArrayList names = new ArrayList(); //holds the names of the clients
    public Double[] totalArr,mainArr,accArr; //the static data structure of all of them, Double class is used rather than primitive
    public String[] clientNameArr,clientUsernameArr;//holds the name and username of all the clients
    public String gdrivePath;
    
    public Summary (String filePath) {
    gdrivePath = filePath;
    listFiles(new File(gdrivePath + "/Java Files/Custom Workout"));
    listFiles(new File(gdrivePath + "/Java Files/Muscle Prebuilts")); 
//cardio doesn't require any progress checks since it's not a progression, just a maintenance type of exercise
    }
    
    
//lists all the files in the folder specified, needs to exclude default files
public final void listFiles(File folder) { 
    for (File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            
            listFiles(fileEntry);
            
        } 
        else {
            
            results.add(fileEntry.getAbsolutePath());
            
         
        }
    }
    // all default program files are removed
    
    for (final Iterator iterator = results.iterator(); iterator.hasNext(); ) { //removes all .DS_Store files created by OS 
    String search = (String) iterator.next();
    if(search.contains(".DS_Store")){
    iterator.remove();
    }
    
    }
   
    results.remove(gdrivePath + "Java Files/Custom Workout/exercisedatabase.xls");
    results.remove(gdrivePath + "Java Files/Custom Workout/extremecardio.xls");
    results.remove(gdrivePath + "Java Files/Custom Workout/relaxedcardio.xls");
    results.remove(gdrivePath + "Java Files/Muscle Prebuilts/lowerbody copy.xls");
    results.remove(gdrivePath + "Java Files/Muscle Prebuilts/lowerbody.xls");
    results.remove(gdrivePath + "Java Files/Muscle Prebuilts/totalbody copy.xls");
    results.remove(gdrivePath + "Java Files/Muscle Prebuilts/totalbody.xls");
    results.remove(gdrivePath + "Java Files/Muscle Prebuilts/upperbody copy.xls");
    results.remove(gdrivePath + "Java Files/Muscle Prebuilts/upperbody.xls");
}

public String[][] fillTable () throws FileNotFoundException, IOException{ //retrieves all the client data as well as client progress from their workout files in preparation for the trainer menu to display
   
    String[][] allData; 
    for(Object file: results){ //loops through all the files and retrieves this information, storing it into a collection
        
        String filePath = (String) file;
        
        System.out.println(filePath); //error checking
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
        HSSFSheet progress = workbook.getSheet("Progress"); //retrieves info in sheet and put into collection, table
        //retrieves all the total lift progress values
        totalProgress.add((progress.getRow(3).getCell(3).getNumericCellValue()));//retrieves total values
        mainProgress.add((progress.getRow(7).getCell(3).getNumericCellValue())); //retrieves main values
        accProgress.add((progress.getRow(11).getCell(3).getNumericCellValue())); //retrieves accessory values
        
        HSSFSheet info = workbook.getSheet("Name"); //retrieves the sheet with all the names on it
        usernames.add(info.getRow(0).getCell(0).getStringCellValue()); 
        names.add(info.getRow(0).getCell(1).getStringCellValue());
        
        }
//converting arraylist to array
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
percentFormat.setMaximumFractionDigits(1);
totalArr =  new Double[totalProgress.size()]; 
totalProgress.toArray(totalArr);
mainArr = new Double[mainProgress.size()];
mainProgress.toArray(mainArr);
accArr = new Double[accProgress.size()]; 
accProgress.toArray(accArr);
clientNameArr = new String[names.size()];
names.toArray(clientNameArr);
clientUsernameArr = new String[usernames.size()];
usernames.toArray(clientUsernameArr);
allData = new String[clientUsernameArr.length][5];
    for(int g = 0; g < clientUsernameArr.length;g++ ){
        System.out.println(clientUsernameArr[g]+","+clientNameArr[g]+","+mainArr[g]+","+accArr[g]+","+totalArr[g]);
        allData[g] = new String[] {clientUsernameArr[g],clientNameArr[g],percentFormat.format(mainArr[g]),percentFormat.format(accArr[g]),percentFormat.format(totalArr[g])}; //sets all the double to strings and formats them in percentage form
      }
   
    return allData;
}

}


