Create Spring Boot Application
https://start.spring.io/

select spring boot : 2.0.8
Group : global.quest
Artifact : bike
Dependencies : Web

Create Project (It will download a new project)

Import this project to Spring tools.

Run BikeApplication.java

http://localhost:8080/

Create new package : global.quest.bike.models

In that create new class Bike

////////////////////////////////////////////////////////

package global.quest.bike.models;

import java.math.BigDecimal;
import java.sql.Date;

public class Bike {
    private String name;
    private String email;
    private String phone;
    private String model;
    private String serialNumber;
    private BigDecimal purchasePrice;
    
    private Date purchaseDate;
    private boolean contact;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public boolean isContact() {
        return contact;
    }
    public void setContact(boolean contact) {
        this.contact = contact;
    }
}

/////////////////////////////////////////////////////////////////////////

---------------------------------------------------------

Create new package : global.quest.bike.controllers
In that create new class : BikesController

///////////////////////////////////////////////////////////////////////////

package global.quest.bike.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import global.quest.bike.models.Bike;
import global.quest.bike.repositories.BikeRepository;

//@EnableJpaRepositories("global.quest.bike.repositories")
@RestController
@RequestMapping("/api/v1/bikes")
public class BikesController {
    
    
    @GetMapping
    public List<Bike> list(){
        System.out.println("------list-------");
        
          List<Bike> bikes = new ArrayList<>();
          
          Bike bike1 = new Bike(); bike1.setName("b1"); bike1.setEmail("aa@gmail.com");
          bikes.add(bike1); 
         return bikes;
         
        
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody Bike bike) {
        System.out.println("------create-------");
        
    }

    @GetMapping("/{id}")
    public Bike get(@PathVariable("id") long id) {
        System.out.println("------GET-------");
        return new Bike();
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////

------------------------------------------------------------

Go to Postman and test your service : http://localhost:8080/api/v1/bikes

/////////////////////////////////////////////////////////////

Create db and table in mysql : execute back up

Add more dependencies in pom.xml : Refer Second Folder

Do maven update project
////////////////////////////////////////////////////////////////

edit application.properties
//////////////////////////////////////////////////////////////
spring.datasource.url= jdbc:mysql://localhost:3306/alpha
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
//////////////////////////////////////////////////////////////////

Create new interface : global.quest.bike.repositories.BikeRepository

import org.springframework.data.jpa.repository.JpaRepository;

import global.quest.bike.models.Bike;

public interface BikeRepository extends JpaRepository<Bike, Long>{

}

////////////////////////////////////////////////////////////////////////

Update BikesController

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import global.quest.bike.models.Bike;
import global.quest.bike.repositories.BikeRepository;

//@EnableJpaRepositories("global.quest.bike.repositories")
@RestController
@RequestMapping("/api/v1/bikes")
public class BikesController {
    
    @Autowired
    private BikeRepository bikeRepository;
    
    @GetMapping
    public List<Bike> list(){
        System.out.println("------list-------");
        /*
         * List<Bike> bikes = new ArrayList<>();
         * 
         * Bike bike1 = new Bike(); bike1.setName("b1"); bike1.setEmail("aa@gmail.com");
         * bikes.add(bike1); return bikes;
         */
        return bikeRepository.findAll();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody Bike bike) {
        System.out.println("------create-------");
        bikeRepository.save(bike);
    }

    @GetMapping("/{id}")
    public Bike get(@PathVariable("id") long id) {
        System.out.println("------GET-------");
        return bikeRepository.getOne(id);
    }
}

/////////////////////////////////////////////////////////////////////////////////

Update Bike class


package global.quest.bike.models;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    private String name;
    private String email;
    private String phone;
    private String model;
    private String serialNumber;
    private BigDecimal purchasePrice;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
    private Date purchaseDate;
    private boolean contact;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public boolean isContact() {
        return contact;
    }
    public void setContact(boolean contact) {
        this.contact = contact;
    }
}

///////////////////////////////////////////////////////////////////////////////////

Go to Postman and test your service : 

http://localhost:8080/api/v1/bikes
http://localhost:8080/api/v1/bikes/3

change to POST method Body  --> raw --> change text to json

push some data

{"name" : "Ajith", "email" : "aj@gmail.com", "model" : "mr2"}

/////////////////////////////////////////////////////////////////////

Create Angular Application
ng new bike-ui -routing

Test your app : http://localhost:4200

create a new file : proxy.config.json 
//////////////////////////////////////////
{
    "/server": {
        "target": "http://localhost:8080",
        "secure": false,
    "changeOrigin": true,
    "logLevel": "debug",
    "pathRewrite": {
      "^/server" : ""
    }
    }
}
/////////////////////////////////////////////////

Open package.json

Edit : "start": "ng serve --proxy-config proxy.config.json",


http://localhost:4200/serve/api/v1/bikes