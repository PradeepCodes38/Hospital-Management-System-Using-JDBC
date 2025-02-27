package org.example;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor{
    private Connection connection;


    public Doctor(Connection connection, Scanner scanner){
        this.connection=connection;

    }


    public void viewDcotors(){
        String Query="Select * from Doctors";

        try{
            PreparedStatement  preparedStatement=connection.prepareStatement(Query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Doctors");
            System.out.println("+------------+--------------+-----------------+");
            System.out.println("| Doctor Id | Name         | Specialization     |");
            System.out.println("+------------+--------------+-----------------+");
            while(resultSet.next()){
                int id=resultSet.getInt("Id");
                String name=resultSet.getString("Name");
                String specialization=resultSet.getString("Specialization");
                System.out.printf("|%-12s|%-20s|%-18s\n",id,name,specialization);
                System.out.println("+------------+--------------+----------+--------------+");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id){
        String Query="Select * from doctors WHERE id=?";
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
