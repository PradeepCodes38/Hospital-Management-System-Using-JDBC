package org.example;

import java.awt.event.ContainerAdapter;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);

        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection, scanner);
            Doctor doctor=new Doctor(connection,scanner);
            while(true){
                System.out.println("Hospital Management System");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointments");
                System.out.println("5. Exit");
                System.out.println("Enter your Choice: ");
                int choice=scanner.nextInt();

                switch (choice){
                    case 1:
                        // Add Patients
                        patient.addPatient();
                        break;

                    case 2:
                        //View Patients
                        patient.viewPatient();
                        break;

                    case 3:
                        //View Doctors
                        doctor.viewDcotors();
                        break;

                    case 4:
                        bookAppointment(patient,doctor,connection,scanner);


                    case 5:
                        return;
                    default:
                        System.out.println("Enter Valid Choice");

                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection, Scanner scanner){
        System.out.println("Enter Patient Id");
        int patient_id=scanner.nextInt();
        System.out.println("Enter Doctor Id");
        int doctor_id=scanner.nextInt();
        System.out.println("Enter apointment date (YYYY-MM-DD):");
        String appointmentDate=scanner.next();

        if( patient.getPatient(patient_id) && doctor.getDoctorById(doctor_id)){
            if(checkdoctor(doctor_id,appointmentDate,connection)){
                String appointmentQuery="insert into appointments (patient_id,doctor_id,appointment_date) values (?,?,?)";

                try{
                    PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,appointmentDate);
                    int rowAffected=preparedStatement.executeUpdate();
                    if(rowAffected>0){
                        System.out.println("Data Inserted Successfully");
                    }
                    else{
                        System.out.println("not Inserted");
                    }

                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Doctor not available");
            }
        }
        else{
            System.out.println("does not exist");
        }

    }
    public static boolean checkdoctor(int doctor_id, String appointmentDate,Connection connection){
        String Query=" Select Count(*) from appointments Where doctor_id=? AND appointment_date=?";

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(Query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int count=resultSet.getInt(1);
                if (count==0){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
