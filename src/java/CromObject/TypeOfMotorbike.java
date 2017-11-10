/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CromObject;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tudt
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeOfMotorbike", namespace = "http://www.thientu.com/MotorbikeType", propOrder = {
    "id",
    "name",
    "length",
    "width",
    "height",
    "fuelTankCompacity",
    "engineType",
    "horsePower",
    "momentum",
    "weight",
    "brandName",
    "type",
    "fuelProvider",
    "xyloCapacity",
    "pitong",
    "compressor",
    "startEngine",
    "previewImage"
})

@XmlRootElement(name = "MotorbikeType", namespace = "http://www.thientu.com/MotorbikeType")
public class TypeOfMotorbike implements Serializable {

    private int id;
    private String name;
    private double length;
    private double width;
    private double height;
    private double fuelTankCompacity;
    private String engineType;
    private String horsePower;
    private String momentum;
    private double weight;
    private String brandName;
    private String type;
    private String fuelProvider;
    private String xyloCapacity;
    private String pitong;
    private String compressor;
    private String startEngine;
    private String previewImage;

    public TypeOfMotorbike() {
    }

    public TypeOfMotorbike(int id, String name, double length, double width, double height, double fuelTankCompacity, String engineType, String horsePower, String momentum, double weight, String brandName, String type, String fuelProvider, String xyloCapacity, String pitong, String compressor, String startEngine, String previewImage) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.fuelTankCompacity = fuelTankCompacity;
        this.engineType = engineType;
        this.horsePower = horsePower;
        this.momentum = momentum;
        this.weight = weight;
        this.brandName = brandName;
        this.type = type;
        this.fuelProvider = fuelProvider;
        this.xyloCapacity = xyloCapacity;
        this.pitong = pitong;
        this.compressor = compressor;
        this.startEngine = startEngine;
        this.previewImage = previewImage;
    }

    public TypeOfMotorbike(String name, double length, double width, double height, double fuelTankCompacity, String engineType, String horsePower, String momentum, double weight, String brandName, String type, String fuelProvider, String xyloCapacity, String pitong, String compressor, String startEngine, String previewImage) {
//        this.id=-1;
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.fuelTankCompacity = fuelTankCompacity;
        this.engineType = engineType;
        this.horsePower = horsePower;
        this.momentum = momentum;
        this.weight = weight;
        this.brandName = brandName;
        this.type = type;
        this.fuelProvider = fuelProvider;
        this.xyloCapacity = xyloCapacity;
        this.pitong = pitong;
        this.compressor = compressor;
        this.startEngine = startEngine;
        this.previewImage = previewImage;
    }

    @XmlElement(name = "Id", namespace = "http://www.thientu.com/MotorbikeType")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "Weight", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @XmlElement(name = "Name", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Length", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @XmlElement(name = "Width", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @XmlElement(name = "Height", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @XmlElement(name = "FuelTankCompacity", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public double getFuelTankCompacity() {
        return fuelTankCompacity;
    }

    public void setFuelTankCompacity(double fuelTankCompacity) {
        this.fuelTankCompacity = fuelTankCompacity;
    }

    @XmlElement(name = "EngineType", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @XmlElement(name = "HorsePower", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(String horsePower) {
        this.horsePower = horsePower;
    }

    @XmlElement(name = "Momentum", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getMomentum() {
        return momentum;
    }

    public void setMomentum(String momentum) {
        this.momentum = momentum;
    }

    @XmlElement(name = "BrandName", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @XmlElement(name = "Type", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "FuelProvider", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getFuelProvider() {
        return fuelProvider;
    }

    public void setFuelProvider(String fuelProvider) {
        this.fuelProvider = fuelProvider;
    }

    @XmlElement(name = "XyloCapacity", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getXyloCapacity() {
        return xyloCapacity;
    }

    public void setXyloCapacity(String xyloCapacity) {
        this.xyloCapacity = xyloCapacity;
    }

    @XmlElement(name = "Pitong", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getPitong() {
        return pitong;
    }

    public void setPitong(String pitong) {
        this.pitong = pitong;
    }

    @XmlElement(name = "Compressor", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getCompressor() {
        return compressor;
    }

    public void setCompressor(String compressor) {
        this.compressor = compressor;
    }

    @XmlElement(name = "StartEngine", required = true, namespace = "http://www.thientu.com/MotorbikeType")
    public String getStartEngine() {
        return startEngine;
    }

    public void setStartEngine(String startEngine) {
        this.startEngine = startEngine;
    }

    @XmlElement(name = "PreviewImage", namespace = "http://www.thientu.com/MotorbikeType")
    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

}
