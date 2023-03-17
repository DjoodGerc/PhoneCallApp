package com.example.phonecallapp.dataacces;

import com.example.phonecallapp.model.CallModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//хочу интефейс DAO
@Component //прочитвй
public class DataAccessObject {

    public static final String URL ="jdbc:postgresql://localhost:5432/AppDataBase";
    public static final String user="postgres";
    public static final String password="2709";
    public static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,user,password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean
    public static List<CallModel> getAllFields(){
        List<CallModel> calls= new ArrayList<CallModel>();

        try{
            Statement statement=connection.createStatement();

            ResultSet resultSet=statement.executeQuery("SELECT * FROM calls_info");
            while (resultSet.next()){

                CallModel newCall= new CallModel(resultSet.getString("caller_phone_number"),
                        resultSet.getString("called_phone_number"),
                        resultSet.getString("call_duration"),resultSet.getString("call_date"));

                calls.add(newCall);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return calls;
    }

    public static CallModel getFieldById(int ident){
        CallModel call;
        try {

            Statement statement=connection.createStatement();
            String SQL="SELECT * from calls_info where (id="+ident+")";
            ResultSet resultSet= statement.executeQuery(SQL);
            resultSet.next();

            call= new CallModel(resultSet.getString("caller_phone_number"),
                    resultSet.getString("called_phone_number"),
                    resultSet.getString("call_duration"),resultSet.getString("call_date"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return call;
    }

    public static void setField(CallModel call){
        try {
            PreparedStatement preparedStatement =connection.prepareStatement("insert into calls_info(caller_phone_number, called_phone_number, call_duration, call_date) values (?,?,?,?)");
            preparedStatement.setString(1,call.getCallerPhoneNumber());
            preparedStatement.setString(2,call.getCalledPhoneNumber());
            preparedStatement.setTime(3, Time.valueOf(call.getCallDuration()));
            preparedStatement.setDate(4, Date.valueOf(call.getCallDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
