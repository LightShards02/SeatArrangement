import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import javafx.util.Pair;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author night
 */
public class SeatChart {
    private static Student[][] seatChart;

    public static void getSeatChart(){
        boolean[][] chart= GUI.selectedButton;
        Collection<Student> ss=GUI.nameMap.values();
        ArrayList<Pair<Student,Student>> deskmates=GUI.deskMates;
        seatChart = new Student[chart.length][chart[0].length];
        int num = ss.size();
        ArrayList<Integer> row = new ArrayList<>();
        loop:
        for (int i = 0; i < chart.length; i++) {
            for (int j = 0; j < chart[i].length; j++) {
                if (chart[i][j]) {
                    row.add(i);
                    continue loop;
                }
            }
        }
        Queue<Student> frontss = new LinkedList<>();
        Queue<Student> backss = new LinkedList<>();
        for (Student student : ss) {
            if (student.isMustBack()) {
                backss.add(student);
            }
            if (student.isMustFront()) {
                frontss.add(student);
            }
        }
        for (int i = row.size() - 1; i >= 0; i--) {
            ArrayList<Integer> rowseat = new ArrayList<>();
            for (int j = 0; j < chart[row.get(i)].length; j++) {
                if (chart[row.get(i)][j]) {
                    rowseat.add(j);
                }
            }
            if (rowseat.isEmpty()) {
                continue;
            }
            int index = 0;
            while (!backss.isEmpty()) {
                int[] random = randomSort(rowseat.size());
                seatChart[i][rowseat.get(random[index])] = backss.poll();
                index++;
            }
        }
        for (int i = 0; i < row.size(); i++) {
            ArrayList<Integer> rowseat = new ArrayList<>();
            for (int j = 0; j < chart[row.get(i)].length; j++) {
                if (chart[row.get(i)][j]) {
                    rowseat.add(j);
                }
            }
            if (rowseat.isEmpty()) {
                continue;
            }
            int index = 0;
            while (!frontss.isEmpty()) {
                int[] random = randomSort(rowseat.size());
                seatChart[i][rowseat.get(random[index])] = frontss.poll();
                index++;
            }
        }
        ArrayList<Student> students = new ArrayList<>();
        for (Iterator<Student> iterator = ss.iterator();
                iterator.hasNext();) {
            Student next = iterator.next();
            if (!next.isMustBack() && !next.isMustFront()) {
                students.add(next);
            }
        }
        int[] other = randomSort(students.size());
        int index = 0;
        for (int i = 0; i < chart.length; i++) {
            for (int j = 0; j < chart[i].length; j++) {
                if (chart[i][j] && seatChart[i][j] == null) {
                    seatChart[i][j] = students.get(other[index]);
                    index++;
                }
            }
        }
        if (checkDeskMate(deskmates)) {
            printSeat();
        }
        else {
            getSeatChart();
        }
    }
    
    private static void printSeat() {
        String path = GUI.destfile;
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(path), "utf-8"));
            for (int i = 0; i < seatChart.length; i++) {
                for (int j = 0; j < seatChart[i].length; j++) {
                    if (seatChart[i][j] != null) {
                        pw.print(seatChart[i][j].getName());
                    }
                    pw.print(",");
                }
                pw.println();
            }
            pw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JPanel(), "printing error");
        }
    }
    
    public static int[] randomSort(int number) {
        int[] result = new int[number];
        for (int i = 0; i < number; i++) {
            result[i] = i;
        }
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int x = random.nextInt(number);
            int tmp = result[i];
            result[i] = result[x];
            result[x] = tmp;
        }
        return result;
    }
    
    private static boolean checkDeskMate(ArrayList<Pair<Student,Student>> deskmates) {
        ArrayList<Boolean> checks=new ArrayList<>();
        for (Iterator<Pair<Student, Student>> iterator = deskmates.iterator(); iterator.hasNext();) {
            Pair<Student, Student> next = iterator.next();
            Student s1=next.getKey(),s2=next.getValue();
            boolean check=false;
            for (int i=0;i<seatChart.length;i++){
                for (int j = 0; j < seatChart[i].length-1; j++) {
                    Student stu1=seatChart[i][j];
                    Student stu2=seatChart[i][j+1];
                    if (stu1!=null) {
                        if ((stu1==s1 && stu2==s2)||(stu1==s2 && stu2==s1)) {
                            check=true;
                            break;
                        }
                    }
                }
            }
            checks.add(check);
        }
        return !checks.contains(false);
    }
}
