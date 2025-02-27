package org.example;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient{
    private Connection connection;

    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

    public void addPatient(){
        System.out.println("Enter patient Name:");
        String name=scanner.next();
        System.out.println("Enter patient Age:");
        int age=scanner.nextInt();
        System.out.println("Enter patient Gender:");
        String gender=scanner.next();
        try{
          String Query="Insert into patients (name,age,gender) values (?,?,?) ";
            PreparedStatement preparedStatement=connection.prepareStatement(Query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedrow=preparedStatement.executeUpdate();
            if (affectedrow>0){
                System.out.println("Patient Added succcessfully");
            }
            else{
                System.out.println("Failed to add Patient");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatient(){
        String Query="Select * from Patients";

        try{
            PreparedStatement  preparedStatement=connection.prepareStatement(Query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients");
            System.out.println("+------------+--------------+----------+--------------+");
            System.out.println("| Patient Id | Name         | Age      | Gender       |");
            System.out.println("+------------+--------------+----------+--------------+");
            while(resultSet.next()){
                int id=resultSet.getInt("Id");
                String name=resultSet.getString("Name");
                int age=resultSet.getInt("Age");
                String gender=resultSet.getString("Gender");
                System.out.printf("|%-12s|%-20s|%-10s|%-12s\n",id,name,age,gender);
                System.out.println("+------------+--------------+----------+--------------+");
            }

        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getPatient(int id){
        String Query="Selec * from patients WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(Query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

}