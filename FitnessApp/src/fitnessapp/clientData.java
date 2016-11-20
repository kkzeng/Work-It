/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnessapp;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author tetrocs
 */
public class clientData {

    public String focus;
    public String user;
    public int fitnessLevel;
    public double squatmax, benchmax, dlmax, pressmax;
    public String gdrivePath;
    public String fullName;
    public HSSFWorkbook exerciseData;

    public clientData(String id, String filePath) throws FileNotFoundException, IOException {
        
        gdrivePath = filePath;
        user = id;
        exerciseData = new HSSFWorkbook(new FileInputStream(new File(gdrivePath + "/Java Files/Custom Workout/exercisedatabase.xls")));
        HSSFWorkbook clientData = new HSSFWorkbook(new FileInputStream(new File(gdrivePath + "/Java Files/Client Data/" + id + "/"+id+"clientdata.xls")));
        HSSFSheet clientSheet = clientData.getSheet("Client Data");
        HSSFRow clientRow = clientSheet.getRow(1);
        HSSFCell clientFocus = clientRow.getCell(3);
        HSSFCell clientFitness = clientRow.getCell(1);
        HSSFCell squat = clientRow.getCell(4);
        HSSFCell bench = clientRow.getCell(5);
        HSSFCell deadlift = clientRow.getCell(6);
        HSSFCell press = clientRow.getCell(7);
        focus = clientFocus.getStringCellValue();
        fitnessLevel = (int) clientFitness.getNumericCellValue();
        squatmax = squat.getNumericCellValue();
        benchmax = bench.getNumericCellValue();
        dlmax = deadlift.getNumericCellValue();
        pressmax = press.getNumericCellValue();
        
        HSSFSheet clientName = clientData.getSheet("Name");
        fullName = clientName.getRow(0).getCell(1).getStringCellValue(); //retrieves the client's full name
        
        //bug fixing prints
        System.out.println(fullName);
        if (clientName.getRow(0).getCell(0).getStringCellValue().equalsIgnoreCase(user))
        {
            System.out.println("Everything is alright");
        }
        System.out.println(squatmax); 
        System.out.println(benchmax);
        System.out.println(dlmax);
        System.out.println(pressmax);

        
    }
    
    public clientData(){ //empty constructor so that object can be intialized without any information
         
        }

    public String[] randomize(String bodypart) throws IOException {

        int max = 0, min = 0;
        HSSFSheet sheet = exerciseData.getSheet("Hypertrophy"); //can be extended to generate others

        if (bodypart.equalsIgnoreCase("Legs")) {
            min = 1;
            max = 4;
        } else if (bodypart.equalsIgnoreCase("Push")) {
            min = 5;
            max = 8;
        } else if (bodypart.equalsIgnoreCase("Pull")) {
            min = 9;
            max = 12;
        }

        String[] exercises = new String[5]; //main exercises 0,1,2 - accessories 3 and 4

        Random generator = new Random();
        int rand = generator.nextInt(max - min + 1) + min;
        HSSFRow row = sheet.getRow(rand);
        HSSFCell exercise1 = row.getCell(1);
        exercises[0] = exercise1.getStringCellValue();
        HSSFCell accessory1 = row.getCell(2);
        exercises[4] = accessory1.getStringCellValue();

        int newRand = generator.nextInt(max - min + 1) + min;
        while (newRand == rand) {
            newRand = generator.nextInt(max - min + 1) + min;
        }
        rand = newRand;

        row = sheet.getRow(newRand);
        HSSFCell exercise2 = row.getCell(1);
        exercises[1] = exercise2.getStringCellValue();
        HSSFCell accessory2 = row.getCell(2);
        exercises[3] = accessory2.getStringCellValue();

        newRand = generator.nextInt(max - min + 1) + min;
        while (newRand == rand) {
            newRand = generator.nextInt(max - min + 1) + min;
        }
        row = sheet.getRow(newRand);
        HSSFCell exercise3 = row.getCell(1);
        exercises[2] = exercise3.getStringCellValue();
        return exercises;

    }
    
    public void increment (HSSFSheet a){
    for(int c = 2; c < 5; c++){
HSSFRow row = a.getRow(c);
HSSFCell cell = row.getCell(3); //weight column
cell.setCellValue(cell.getNumericCellValue()+5);
}

for(int c = 5; c < 7; c++){
HSSFRow row = a.getRow(c);
HSSFCell cell = row.getCell(3); //weight column
cell.setCellValue(cell.getNumericCellValue()+1.5);
}

for(int c = 10; c < 13; c++){
HSSFRow row = a.getRow(c);
HSSFCell cell = row.getCell(3); //weight column
cell.setCellValue(cell.getNumericCellValue()+5);
}

for(int c = 13; c < 15; c++){
HSSFRow row = a.getRow(c);
HSSFCell cell = row.getCell(3); //weight column
cell.setCellValue(cell.getNumericCellValue()+1.5);
}

for(int c = 18; c < 21; c++){
HSSFRow row = a.getRow(c);
HSSFCell cell = row.getCell(3); //weight column
cell.setCellValue(cell.getNumericCellValue()+5);
}

for(int c = 21; c < 23; c++){
HSSFRow row = a.getRow(c);
HSSFCell cell = row.getCell(3); //weight column
cell.setCellValue(cell.getNumericCellValue()+1.5);
}
    }
    
    public double determineWeight(boolean mainExercise, String bodypart) {
    double weight;
    if (mainExercise == true){
    switch (bodypart){
    case "Legs": weight = (fitnessLevel * 0.15 * squatmax);
        break;
    case "Push": weight = (fitnessLevel * 0.15 * ((pressmax + benchmax)/2));
        break;
    case "Pull": weight = (fitnessLevel * 0.15 * dlmax);
        break;
    default: weight = ((squatmax + dlmax + pressmax + benchmax)/4 * 0.15 * fitnessLevel);
            System.out.println("Error");
    }
    }
    else{
    switch (bodypart){
    case "Legs": weight = (fitnessLevel * 0.035 * squatmax);
        break;
    case "Push": weight = (fitnessLevel * 0.035 * ((pressmax + benchmax)/2));
        break;
    case "Pull": weight = (fitnessLevel * 0.036 * dlmax);
        break;
    default: weight = ((squatmax + dlmax + pressmax + benchmax)/4 * 0.035 * fitnessLevel);
    }
    }
    System.out.println(weight);
    return weight;
    }

    public void generate() throws IOException {

        String[] legs = randomize("Legs");
        String[] push = randomize("Push");
        String[] pull = randomize("Pull");
        double sets = 5;
        double reps = 5;
        double rest = 10;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Week 1");
        
Map<String, Object[]> data = new HashMap<>();

double weight = determineWeight(true,"Legs");
data.put("1", new Object[] {"LEG DAY"});
data.put("2", new Object[] {"Exercise", "Sets", "Rep Range", "Weight(kg)", "Rest(seconds)"});
data.put("3", new Object[] {legs[0],sets,reps,weight,rest});
data.put("4", new Object[] {legs[1],sets,reps,weight,rest});
data.put("5", new Object[] {legs[2],sets,reps,weight,rest});
weight = determineWeight(false,"Legs");
data.put("6", new Object[] {legs[3],sets,reps,weight,rest});
data.put("7", new Object[] {legs[4],sets,reps,weight,rest});


Map<String, Object[]> data2 = new HashMap<>();
weight = determineWeight(true,"Push");
data2.put("1", new Object[] {"PUSH DAY"});
data2.put("2", new Object[] {"Exercise", "Sets", "Rep Range", "Weight(kg)", "Rest(seconds)"});
data2.put("3", new Object[] {push[0],sets,reps,weight,rest});
data2.put("4", new Object[] {push[1],sets,reps,weight,rest});
data2.put("5", new Object[] {push[2],sets,reps,weight,rest});
weight = determineWeight(false,"Push");
data2.put("6", new Object[] {push[3],sets,reps,weight,rest});
data2.put("7", new Object[] {push[4],sets,reps,weight,rest});

Map<String, Object[]> data3 = new HashMap<>();
weight = determineWeight(true,"Pull");
data3.put("1", new Object[] {"PULL DAY"});
data3.put("2", new Object[] {"Exercise", "Sets", "Rep Range", "Weight(kg)", "Rest(seconds)"});
data3.put("3", new Object[] {pull[0],sets,reps,weight,rest});
data3.put("4", new Object[] {pull[1],sets,reps,weight,rest});
data3.put("5", new Object[] {pull[2],sets,reps,weight,rest});
weight = determineWeight(false,"Pull");
data3.put("6", new Object[] {pull[3],sets,reps,weight,rest});
data3.put("7", new Object[] {pull[4],sets,reps,weight,rest});


Set<String> keyset = data.keySet();
int rownum = 0;
for (String key : keyset) {
    Row row = sheet.createRow(rownum++);
    Object [] objArr = data.get(key);
    int cellnum = 0;
    for (Object obj : objArr) {
        Cell cell = row.createCell(cellnum++);
         if(obj instanceof Boolean)
            cell.setCellValue((Boolean)obj);
        else if(obj instanceof String)
            cell.setCellValue((String)obj);
        else if(obj instanceof Double)
            cell.setCellValue((Double)obj);
        }
}
Set<String> keyset2 = data2.keySet();
    rownum = 8;
for (String key : keyset2) {
    Row row = sheet.createRow(rownum++);
    Object [] objArr = data2.get(key);
    int cellnum = 0;
    for (Object obj2 : objArr) {
        Cell cell = row.createCell(cellnum++);
         if(obj2 instanceof Boolean)
            cell.setCellValue((Boolean)obj2);
        else if(obj2 instanceof String)
            cell.setCellValue((String)obj2);
        else if(obj2 instanceof Double)
            cell.setCellValue((Double)obj2);
        }
}

Set<String> keyset3 = data3.keySet();
    rownum = 16;
for (String key : keyset3) {
    Row row = sheet.createRow(rownum++);
    Object [] objArr = data3.get(key);
    int cellnum = 0;
    for (Object obj3 : objArr) {
        Cell cell = row.createCell(cellnum++);
         if(obj3 instanceof Boolean)
            cell.setCellValue((Boolean)obj3);
        else if(obj3 instanceof String)
            cell.setCellValue((String)obj3);
        else if(obj3 instanceof Double)
            cell.setCellValue((Double)obj3);
        }
}

HSSFSheet sheet2 = workbook.cloneSheet(0);
workbook.setSheetName(workbook.getSheetIndex(sheet2), "Week 2");
increment(sheet2);

HSSFSheet sheet3 = workbook.cloneSheet(1);
workbook.setSheetName(workbook.getSheetIndex(sheet3), "Week 3");
increment(sheet3);

HSSFSheet sheet4 = workbook.cloneSheet(2);
workbook.setSheetName(workbook.getSheetIndex(sheet4), "Week 4"); 
increment(sheet4);

HSSFSheet progress = workbook.createSheet("Progress");
HSSFRow row = progress.createRow(0);
HSSFCell cell = row.createCell(0);
cell.setCellValue("PROGRESS");
row = progress.createRow(1);
row.createCell(0).setCellValue("Total Lifts Average Progress:");
row = progress.createRow(2);
row.createCell(0).setCellValue("Week 1 to Week 2");
row.createCell(1).setCellValue("Week 2 to Week 3");
row.createCell(2).setCellValue("Week 3 to Week 4");
row.createCell(3).setCellValue("Total Progress");
row = progress.createRow(3);
row.createCell(0).setCellFormula("((('Week 2'!D3-'Week 1'!D3)/'Week 1'!D3)+(('Week 2'!D4-'Week 1'!D4)/'Week 1'!D4)+(('Week 2'!D5-'Week 1'!D5)/'Week 1'!D5)+(('Week 2'!D6-'Week 1'!D6)/'Week 1'!D6)+(('Week 2'!D7-'Week 1'!D7)/'Week 1'!D7)+(('Week 2'!D11-'Week 1'!D11)/'Week 1'!D11)+(('Week 2'!D12-'Week 1'!D12)/'Week 1'!D12)+(('Week 2'!D13-'Week 1'!D13)/'Week 1'!D13)+(('Week 2'!D14-'Week 1'!D14)/'Week 1'!D14)+(('Week 2'!D15-'Week 1'!D15)/'Week 1'!D15)+(('Week 2'!D19-'Week 1'!D19)/'Week 1'!D19)+(('Week 2'!D20-'Week 1'!D20)/'Week 1'!D20)+(('Week 2'!D21-'Week 1'!D21)/'Week 1'!D21)+(('Week 2'!D22-'Week 1'!D22)/'Week 1'!D22)+(('Week 2'!D23-'Week 1'!D23)/'Week 1'!D23))/15"); //finds difference in weights used and then averages
row.createCell(1).setCellFormula("((('Week 3'!D3-'Week 2'!D3)/'Week 2'!D3)+(('Week 3'!D4-'Week 2'!D4)/'Week 2'!D4)+(('Week 3'!D5-'Week 2'!D5)/'Week 2'!D5)+(('Week 3'!D6-'Week 2'!D6)/'Week 2'!D6)+(('Week 3'!D7-'Week 2'!D7)/'Week 2'!D7)+(('Week 3'!D11-'Week 2'!D11)/'Week 2'!D11)+(('Week 3'!D12-'Week 2'!D12)/'Week 2'!D12)+(('Week 3'!D13-'Week 2'!D13)/'Week 2'!D13)+(('Week 3'!D14-'Week 2'!D14)/'Week 2'!D14)+(('Week 3'!D15-'Week 2'!D15)/'Week 2'!D15)+(('Week 3'!D19-'Week 2'!D19)/'Week 2'!D19)+(('Week 3'!D20-'Week 2'!D20)/'Week 2'!D20)+(('Week 3'!D21-'Week 2'!D21)/'Week 2'!D21)+(('Week 3'!D22-'Week 2'!D22)/'Week 2'!D22)+(('Week 3'!D23 -'Week 2'!D23)/'Week 2'!D23))/15"); //need to update these to account for all exercises
row.createCell(2).setCellFormula("((('Week 4'!D3-'Week 3'!D3)/'Week 3'!D3)+(('Week 4'!D4-'Week 3'!D4)/'Week 3'!D4)+(('Week 4'!D5-'Week 3'!D5)/'Week 3'!D5)+(('Week 4'!D6-'Week 3'!D6)/'Week 3'!D6)+(('Week 4'!D7-'Week 3'!D7)/'Week 3'!D7)+(('Week 4'!D11-'Week 3'!D11)/'Week 3'!D11)+(('Week 4'!D12-'Week 3'!D12)/'Week 3'!D12)+(('Week 4'!D13-'Week 3'!D13)/'Week 3'!D13)+(('Week 4'!D14-'Week 3'!D14)/'Week 3'!D14)+(('Week 4'!D15-'Week 3'!D15)/'Week 3'!D15)+(('Week 4'!D19-'Week 3'!D19)/'Week 3'!D19)+(('Week 4'!D20-'Week 3'!D20)/'Week 3'!D20)+(('Week 4'!D21-'Week 3'!D21)/'Week 3'!D21)+(('Week 4'!D22-'Week 3'!D22)/'Week 3'!D22)+(('Week 4'!D23-'Week 3'!D23)/'Week 3'!D23))/15");
row.createCell(3).setCellFormula("A4+B4+C4");
row = progress.createRow(5);
row.createCell(0).setCellValue("Main Lifts Average Progress:");
row = progress.createRow(6);
row.createCell(0).setCellValue("Week 1 to Week 2");
row.createCell(1).setCellValue("Week 2 to Week 3");
row.createCell(2).setCellValue("Week 3 to Week 4");
row.createCell(3).setCellValue("Total Progress");
row = progress.createRow(7);
row.createCell(0).setCellFormula("((('Week 2'!D3-'Week 1'!D3)/'Week 1'!D3)+(('Week 2'!D4-'Week 1'!D4)/'Week 1'!D4)+(('Week 2'!D5-'Week 1'!D5)/'Week 1'!D5)+(('Week 2'!D11-'Week 1'!D11)/'Week 1'!D11)+(('Week 2'!D12-'Week 1'!D12)/'Week 1'!D12)+(('Week 2'!D13-'Week 1'!D13)/'Week 1'!D13)+(('Week 2'!D19-'Week 1'!D19)/'Week 1'!D19)+(('Week 2'!D20-'Week 1'!D20)/'Week 1'!D20)+(('Week 2'!D21-'Week 1'!D21)/'Week 1'!D21))/9");
row.createCell(1).setCellFormula("((('Week 3'!D3-'Week 2'!D3)/'Week 2'!D3)+(('Week 3'!D4-'Week 2'!D4)/'Week 2'!D4)+(('Week 3'!D5-'Week 2'!D5)/'Week 2'!D5)+(('Week 3'!D11-'Week 2'!D11)/'Week 2'!D11)+(('Week 3'!D12-'Week 2'!D12)/'Week 2'!D12)+(('Week 3'!D13-'Week 2'!D13)/'Week 2'!D13)+(('Week 3'!D19-'Week 2'!D19)/'Week 2'!D19)+(('Week 3'!D20-'Week 2'!D20)/'Week 2'!D20)+(('Week 3'!D21-'Week 2'!D21)/'Week 2'!D21))/9");
row.createCell(2).setCellFormula("((('Week 4'!D3-'Week 3'!D3)/'Week 3'!D3)+(('Week 4'!D4-'Week 3'!D4)/'Week 3'!D4)+(('Week 4'!D5-'Week 3'!D5)/'Week 3'!D5)+(('Week 4'!D11-'Week 3'!D11)/'Week 3'!D11)+(('Week 4'!D12-'Week 3'!D12)/'Week 3'!D12)+(('Week 4'!D13-'Week 3'!D13)/'Week 3'!D13)+(('Week 4'!D19-'Week 3'!D19)/'Week 3'!D19)+(('Week 4'!D20-'Week 3'!D20)/'Week 3'!D20)+(('Week 4'!D21-'Week 3'!D21)/'Week 3'!D21))/9");
row.createCell(3).setCellFormula("A8+B8+C8");
row = progress.createRow(9);
row.createCell(0).setCellValue("Accessory Lifts Average Progress:");
row = progress.createRow(10);
row.createCell(0).setCellValue("Week 1 to Week 2");
row.createCell(1).setCellValue("Week 2 to Week 3");
row.createCell(2).setCellValue("Week 3 to Week 4");
row.createCell(3).setCellValue("Total Progress");
row = progress.createRow(11);
row.createCell(0).setCellFormula("((('Week 2'!D6-'Week 1'!D6)/'Week 1'!D6)+(('Week 2'!D7-'Week 1'!D7)/'Week 1'!D7)+(('Week 2'!D14-'Week 1'!D14)/'Week 1'!D14)+(('Week 2'!D15-'Week 1'!D15)/'Week 1'!D15)+(('Week 2'!D22-'Week 1'!D22)/'Week 1'!D22)+(('Week 2'!D23-'Week 1'!D23)/'Week 1'!D23))/6");
row.createCell(1).setCellFormula("((('Week 3'!D6-'Week 2'!D6)/'Week 2'!D6)+(('Week 3'!D7-'Week 2'!D7)/'Week 2'!D7)+(('Week 3'!D14-'Week 2'!D14)/'Week 2'!D14)+(('Week 3'!D15-'Week 2'!D15)/'Week 2'!D15)+(('Week 3'!D22-'Week 2'!D22)/'Week 2'!D22)+(('Week 3'!D23-'Week 2'!D23)/'Week 2'!D23))/6");
row.createCell(2).setCellFormula("((('Week 4'!D6-'Week 3'!D6)/'Week 3'!D6)+(('Week 4'!D7-'Week 3'!D7)/'Week 3'!D7)+(('Week 4'!D14-'Week 3'!D14)/'Week 3'!D14)+(('Week 4'!D15-'Week 3'!D15)/'Week 3'!D15)+(('Week 4'!D22-'Week 3'!D22)/'Week 3'!D22)+(('Week 4'!D23-'Week 3'!D23)/'Week 3'!D23))/6");
row.createCell(3).setCellFormula("A12+B12+C12");
 
//managing the name and username of client - used for summary class later
HSSFSheet name = workbook.createSheet("Name");
HSSFRow nameRow = name.createRow(0);
nameRow.createCell(0).setCellValue(user);
nameRow.createCell(1).setCellValue(fullName);


File myFile = new File(gdrivePath + "/Java Files/Custom Workout/"+user); //new directory named after user   
 myFile.mkdirs();
 myFile = new File(gdrivePath + "/Java Files/Custom Workout/"+user+"/"+user+"customworkout.xls"); //write to file itself
try (FileOutputStream out = new FileOutputStream(myFile)) {
                workbook.write(out); //weight increment. Then use formula to create statistic and Shah's menu to see summary.
                 System.out.println("Excel written successfully...");
            }
            if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(myFile);
            }
            else{
            System.out.println("You need Excel to open this");
            }
}
    
      public void open(String id){
      
          if (Desktop.isDesktopSupported()) {
    try {
         
            File myFile = new File(gdrivePath + "/Java Files/Client Data/"+id+"/"+id+"clientdata.xls");
            Desktop.getDesktop().open(myFile);
            
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
                        "The user you have entered does not exist!");
    }
}
      }
}
