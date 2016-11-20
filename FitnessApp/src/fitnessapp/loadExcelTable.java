/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnessapp;

import java.io.File;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jxl.Cell; //uses Jxl API, some other classes use Apache POI
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

//extracts table from Excel and formats it into JTable
//handles data extraction from Excel
public class loadExcelTable {

public Workbook plan;
public File workbookPath;
public Sheet week1;
public Object[] titles = new Object[5];
public DefaultTableModel model;
public Label[] cells = new Label[5];
public WritableWorkbook copy;
public String gdrivePath;
    
public loadExcelTable (File path, JTable displayTable) throws IOException, BiffException{

plan  = Workbook.getWorkbook(path);
week1 = plan.getSheet(0);
model = (DefaultTableModel) displayTable.getModel();


}

public loadExcelTable (File path, JTable displayTable, int sheetSelection) throws IOException, BiffException{

plan  = Workbook.getWorkbook(path);
week1 = plan.getSheet(sheetSelection);
model = (DefaultTableModel) displayTable.getModel();


}

public void display(){


for (int g = 0; g < 5; g++){
        titles[g] = week1.getCell(g,0).getContents();
        }
        model.setColumnIdentifiers(titles); //loading column headings
    for(int z = 1; z < 6; z++){
    model.addRow(new Object[]{week1.getCell(0,z).getContents(),week1.getCell(1,z).getContents(),week1.getCell(2,z).getContents(),week1.getCell(3,z).getContents(),week1.getCell(4,z).getContents()});
    } //loading data in each row
 

}

public void startWorkout(String[] add, int col) throws IOException, WriteException{

    copy = Workbook.createWorkbook(new File(gdrivePath + "/Java Files/Muscle Prebuilts/currentworkout.xls"),plan);
    WritableSheet weekEdit = copy.getSheet(0);
    for(int row = 0; row < 5; row++){
    cells[row] = (Label) weekEdit.getWritableCell(col,(row+1));
    }
    
    for(int g = 0; g < 5; g++){
    cells[g].setString(add[g]);
    }
    
    copy.write();
    plan.close();
}

public void updateProgressMuscle(int previousweek) throws IOException, WriteException { //previousweek starts at 1
    
    String weekname = "Week "+(previousweek+1);
    WritableSheet progressNew = copy.createSheet(weekname, previousweek);
    WritableSheet progress = copy.getSheet(previousweek-1);
    
  
    
    for(int row = 0; row <5; row++){
    cells[row] = (Label) progressNew.getWritableCell(3,(row+1)); //only taking the weight column of cells
   }
    
    for(int row = 0; row < 5; row++){
    String weight = progress.getCell(3,(row+1)).getContents(); //old spreadsheet
    int numberweight = Integer.parseInt(weight);
    if(row < 3){
    numberweight = (numberweight+5);
    }
    else
    {
    numberweight = (numberweight+2);
    }
    weight = Integer.toString(numberweight);
    cells[row].setString(weight); //writing to cells on new spreadsheet
    }
    
    copy.write();
    copy.close();
}
}
