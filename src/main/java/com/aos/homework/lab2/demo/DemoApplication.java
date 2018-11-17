package com.aos.homework.lab2.demo;

import com.aos.homework.lab2.demo.DAO.PersonDAO;
import com.aos.homework.lab2.demo.Models.PersonEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class DemoApplication {
    private EntityManagerFactory entityManagerFactory;
    private PersonDAO personDAO;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @RequestMapping("/")
    public String intialise() {
        entityManagerFactory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        personDAO = new PersonDAO(entityManagerFactory);
        return "Greetings from Gradle Spring Boot Application!";
    }
    @RequestMapping("/hello")
    public String sayHello(@RequestParam("name") String name) {
        return "HELLO "+name;
    }
    @RequestMapping(value="/insert", method= RequestMethod.GET)
    public String insert(@RequestParam("name") String name,@RequestParam("email")String email,@RequestParam("age")Integer age) {
        if(personDAO==null)intialise();
        return personDAO.insertPerson(new PersonEntity(email,name,age));
    }
    @RequestMapping(value="/read")
    public String read() {
        if(personDAO==null)intialise();
        List<PersonEntity> personEntities =personDAO.getPersons();
        List<String> personsString =personEntities.stream().map(PersonEntity::toString).collect(Collectors.toList());
        return String.join("\n",personsString) ;
    }

    @RequestMapping(value="/remove", method= RequestMethod.GET)
    public String remove(@RequestParam("id") Integer id) {
        if(personDAO==null)intialise();
        return personDAO.removePerson(personDAO.getPersonById(id));
    }
    @RequestMapping(value="/update", method= RequestMethod.GET)
    public String update(@RequestParam("id")Integer id,@RequestParam("name") String name,@RequestParam("email")String email,@RequestParam("age")Integer age) {
        if(personDAO==null)intialise();
        return personDAO.insertPerson(new PersonEntity(id,email,name,age));
    }
}
