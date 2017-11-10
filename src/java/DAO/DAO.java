/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import CromObject.ListType;
import CromObject.Motorbike;
import CromObject.Motorbikes;
import CromObject.News;
import CromObject.TypeOfMotorbike;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author tudt
 */
public class DAO {

    private static Connection con = DBO.openConnection();

    public static News getNewsById(int id) throws SQLException {
        if (con == null) {
            con = DBO.openConnection();
        }
        String sql = "SELECT * FROM tbl_News Where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int gId = rs.getInt("Id");
            String title = rs.getString("Title");
            String des = rs.getString("Description");
            String previewIMG = rs.getString("PreviewImage");
            String content = rs.getString("Content");
            String date = rs.getString("Date");
//            System.out.println(title);
            return new News(gId, title, des, previewIMG, content, date);
        }
        return null;
    }

    public static List<News> getAllTopNews() throws SQLException {
        List<News> newsList = new ArrayList<News>();
        if (con == null) {
            con = DBO.openConnection();
        }
        String sql = "SELECT TOP 20 * FROM tbl_News";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Id");
            String title = rs.getString("Title");
            String des = rs.getString("Description");
            String previewIMG = rs.getString("PreviewImage");
            String content = rs.getString("Content");
            String date = rs.getString("Date");
//            System.out.println(title);
            newsList.add(new News(id, title, des, previewIMG, content, date));
        }
        return newsList;
    }

    public static List<News> getAllNews() throws SQLException {
        List<News> newsList = new ArrayList<News>();
        if (con == null) {
            con = DBO.openConnection();
        }
        String sql = "SELECT * FROM tbl_News";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Id");
            String title = rs.getNString("Title");
            String des = rs.getNString("Description");
            String previewIMG = rs.getNString("PreviewImage");
            String content = rs.getNString("Content");
            String date = rs.getNString("Date");
            newsList.add(new News(id, title, des, previewIMG, content, date));
        }
        return newsList;
    }

    public static int insertNewṣ(News news) throws SQLException {
        int id = -1;
        if (con == null) {
            con = DBO.openConnection();
        }
        String querry = "insert into tbl_News values ("
                + "?," + "?," + "?," + "?," + "?)";
        PreparedStatement ps = con.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, news.getTitle());
        ps.setString(2, news.getDescription());
        ps.setString(3, news.getPreviewImage());
        ps.setString(4, news.getContent());
        ps.setString(5, news.getDate());
        try {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            long primaryKey = rs.getLong(1);
            id = (int) primaryKey;
        } finally {
            ps.close();
        }
        return id;
    }

    public static List<News> searchAllNewsContainTitle(String searchContent) throws SQLException {
        List<News> newsList = new ArrayList<News>();
        if (con == null) {
            con = DBO.openConnection();
        }
        System.out.println(searchContent);
        String sql = "SELECT * FROM tbl_News Where Title LIKE N'%" + searchContent + "%'";
        PreparedStatement ps = con.prepareStatement(sql);
//        ps.setString(1, "N'%" + searchContent + "%'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Id");
            String title = rs.getNString("Title");
            String des = rs.getNString("Description");
            String previewIMG = rs.getNString("PreviewImage");
            String content = rs.getNString("Content");
            String date = rs.getNString("Date");
            newsList.add(new News(id, title, des, previewIMG, content, date));
        }
        return newsList;
    }

    public static boolean checkLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM tbl_UserAccount Where Username=? AND Password=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return true;
        }
        return false;
    }

    public static List<TypeOfMotorbike> searchAllMotorContainName(String searchContent) throws SQLException {
        List<TypeOfMotorbike> list = new ArrayList<TypeOfMotorbike>();
        if (con == null) {
            con = DBO.openConnection();
        }
//        System.out.println(searchContent);
        String sql = "SELECT * FROM tbl_TypeInformation Where Name LIKE N'%" + searchContent + "%' Order by BrandName,Type";
        PreparedStatement ps = con.prepareStatement(sql);
//        ps.setString(1, "N'%" + searchContent + "%'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getNString("Name");
            double length = rs.getDouble("Length");
            double width = rs.getDouble("Width");
            double height = rs.getDouble("Height");
            double fuel = rs.getDouble("FuelTankCompacity");
            String engine = rs.getNString("EngineType");
            String horsePower = rs.getNString("Horse_Power");
            String moment = rs.getNString("Momentum");
            double weight = rs.getDouble("Weight");
            String brandName = rs.getNString("BrandName");
            String type = rs.getNString("Type");
            String fuelProvider = rs.getNString("FuelProvider");
            String xylo = rs.getNString("Xylo_Capacity");
            String pitong = rs.getNString("Pitong");
            String compressor = rs.getNString("Compressor");
            String startEngine = rs.getNString("StartEngine");
            String previewImage = rs.getNString("PreviewImage");
            list.add(new TypeOfMotorbike(id,
                    name, length, width, height, fuel, engine, horsePower,
                    moment, weight, brandName, type, fuelProvider, xylo,
                    pitong, compressor, startEngine, previewImage));
        }
        return list;
    }

    public static TypeOfMotorbike getMotorTypeById(int id) throws SQLException {
        if (con == null) {
            con = DBO.openConnection();
        }
//        System.out.println(searchContent);
        String sql = "SELECT * FROM tbl_TypeInformation Where ID = ? Order by BrandName,Type";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
//        ps.setString(1, "N'%" + searchContent + "%'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
//            int id = rs.getInt("ID");
            String name = rs.getNString("Name");
            double length = rs.getDouble("Length");
            double width = rs.getDouble("Width");
            double height = rs.getDouble("Height");
            double fuel = rs.getDouble("FuelTankCompacity");
            String engine = rs.getNString("EngineType");
            String horsePower = rs.getNString("Horse_Power");
            String moment = rs.getNString("Momentum");
            double weight = rs.getDouble("Weight");
            String brandName = rs.getNString("BrandName");
            String type = rs.getNString("Type");
            String fuelProvider = rs.getNString("FuelProvider");
            String xylo = rs.getNString("Xylo_Capacity");
            String pitong = rs.getNString("Pitong");
            String compressor = rs.getNString("Compressor");
            String startEngine = rs.getNString("StartEngine");
            String previewImage = rs.getNString("PreviewImage");
            return new TypeOfMotorbike(id,
                    name, length, width, height, fuel, engine, horsePower,
                    moment, weight, brandName, type, fuelProvider, xylo,
                    pitong, compressor, startEngine, previewImage);
        }
        return null;
    }

    public static List<Motorbike> searchMotorOfType(int id) throws SQLException {
        List<Motorbike> motorList = new ArrayList<Motorbike>();
        if (con == null) {
            con = DBO.openConnection();
        }
        TypeOfMotorbike type = getMotorTypeById(id);
        String sql = "SELECT DISTINCT * from tbl_MotorDetail Where TypeID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            motorList.add(new Motorbike(id, type, rs.getNString("Status"),
                    rs.getBigDecimal("Price"), rs.getNString("Color"),
                    rs.getNString("Picture")));

        }
        return motorList;
    }

    public DAO() {
//        con = DBO.openConnection();
    }

    public DAO(Connection con) {
        this.con = con;
    }

    public static int insertMotorbike(Motorbike motor) throws SQLException {
        int id = -1;
        if (con == null) {
            con = DBO.openConnection();
        }
        String querry = "insert into tbl_MotorDetail values ("
                + "?," + "?," + "?," + "?," + "?)";
        PreparedStatement ps = con.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, motor.getDetail().getId());
        ps.setString(2, motor.getColor());
        ps.setString(3, motor.getStatus());
        ps.setBigDecimal(4, motor.getPrice());
        ps.setString(5, motor.getPicture());
        try {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            long primaryKey = rs.getLong(1);
            id = (int) primaryKey;
        } finally {
            ps.close();
        }
        return id;
    }

    public static int insertMotorbikeType(TypeOfMotorbike type) throws SQLException {
        int id = -1;
        if (con == null) {
            con = DBO.openConnection();
        }
        String querry = "insert into tbl_TypeInformation values ("
                + "?," + "?," + "?," + "?," + "?," + "?," + "?,"
                + "?," + "?," + "?," + "?," + "?," + "?,"
                + "?," + "?," + "?," + "?" + ")";
        PreparedStatement ps = con.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, type.getName());
        ps.setDouble(2, type.getLength());
        ps.setDouble(3, type.getWidth());
        ps.setDouble(4, type.getHeight());
        ps.setDouble(5, type.getFuelTankCompacity());
        ps.setString(6, type.getEngineType());
        ps.setString(7, type.getHorsePower());
        ps.setString(8, type.getMomentum());
        ps.setDouble(9, type.getWeight());
        ps.setString(10, type.getBrandName());
        ps.setString(11, type.getType());
        ps.setString(12, type.getFuelProvider());
        ps.setString(13, type.getXyloCapacity());
        ps.setString(14, type.getPitong());
        ps.setString(15, type.getCompressor());
        ps.setString(16, type.getStartEngine());
        ps.setString(17, type.getPreviewImage());
        try {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            long primaryKey = rs.getLong(1);
            id = (int) primaryKey;
        } finally {
            ps.close();
        }
        return id;
    }

    public static Motorbikes getAllMotorbikes() throws JAXBException, SQLException {
        List<Motorbike> motorList = new ArrayList<Motorbike>();
        if (con == null) {
            con = DBO.openConnection();
        }
        String sql = "";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

        }
        Motorbikes motorbikes = new Motorbikes(motorList);
        return motorbikes;
    }

    public static ListType getAllMotorbikeType() throws JAXBException, SQLException {
        List<TypeOfMotorbike> typeList = new ArrayList<TypeOfMotorbike>();
        if (con == null) {
            con = DBO.openConnection();
        }
        String sql = "SELECT * \n"
                + "FROM tbl_TypeInformation\n"
                + "Order by BrandName,Type ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getNString("Name");
            double length = rs.getDouble("Length");
            double width = rs.getDouble("Width");
            double height = rs.getDouble("Height");
            double fuel = rs.getDouble("FuelTankCompacity");
            String engine = rs.getNString("EngineType");
            String horsePower = rs.getNString("Horse_Power");
            String moment = rs.getNString("Momentum");
            double weight = rs.getDouble("Weight");
            String brandName = rs.getNString("BrandName");
            String type = rs.getNString("Type");
            String fuelProvider = rs.getNString("FuelProvider");
            String xylo = rs.getNString("Xylo_Capacity");
            String pitong = rs.getNString("Pitong");
            String compressor = rs.getNString("Compressor");
            String startEngine = rs.getNString("StartEngine");
            String previewImage = rs.getNString("PreviewImage");
            typeList.add(new TypeOfMotorbike(id,
                    name, length, width, height, fuel, engine, horsePower,
                    moment, weight, brandName, type, fuelProvider, xylo,
                    pitong, compressor, startEngine, previewImage));
        }
        ListType list = new ListType(typeList);
        ps.close();
        return list;
    }
}
