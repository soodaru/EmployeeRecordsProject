/**
 * Ayah Mahmoud & Arushi Sood
 * 991647463 & 991644338
 * Final Project
 * April 17, 2022
 */
package content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class EmployeeFile {
    public static ArrayList<Employee> getEmployees(File file) throws FileNotFoundException, IOException {
        ArrayList<Employee> employeeList = new ArrayList();
        
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        
        // While loop used to loop through every record in Employee.dat
        String line = br.readLine();
        while (line != null) {
            // Use StringTokenizer to read each field for every record in Employee.dat
            StringTokenizer stEmployee = new StringTokenizer(line, ",");
            int number = Integer.parseInt(stEmployee.nextToken());
            String name = stEmployee.nextToken();
            String city = stEmployee.nextToken();
            String position = stEmployee.nextToken();
            
            // Construct Employee object using variables set using StringTokenizer
            Employee one = new Employee(number);
            one.setName(name);
            one.setCity(city);
            one.setPosition(position);
            employeeList.add(one);
            
            //Read next line
            line = br.readLine();
        }
        
        return employeeList;
    }
    
    public static void updateFile(File file, ArrayList<Employee> employeeList) throws IOException {
        FileWriter fw = new FileWriter(file, false);
        BufferedWriter bw = new BufferedWriter(fw);
        
        for (Employee employee : employeeList) {
            bw.write(employee.getIdNumber() + "," + employee.getName() + "," +
                    employee.getCity() + "," + employee.getPosition());           
            bw.newLine();         
        }
        
        bw.close();
        fw.close();
    }
}
