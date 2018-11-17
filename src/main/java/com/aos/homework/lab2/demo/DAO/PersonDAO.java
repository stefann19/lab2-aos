package com.aos.homework.lab2.demo.DAO;

import com.aos.homework.lab2.demo.Models.PersonEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PersonDAO {
    private EntityManagerFactory entityManagerFactory;
    public PersonDAO(EntityManagerFactory emf) {
        entityManagerFactory = emf;
    }

    public String insertPerson(PersonEntity personEntity){
        String status;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try{
                entityManager.getTransaction().begin();
                entityManager.persist(personEntity);
                entityManager.getTransaction().commit();
                status = "Successfully inserted";
            }catch (Exception ex){
                status = ex.getMessage();
                ex.printStackTrace();
                entityManager.getTransaction().rollback();
            }finally {
                entityManager.close();
            }

        }catch (Exception ex){
            status = ex.getMessage();
            ex.printStackTrace();
        }
        return status;
    }

    public List<PersonEntity> getPersons(){
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try{
                 return entityManager.createQuery("select person from PersonEntity person", PersonEntity.class).getResultList();
            }catch (Exception ex){
                ex.printStackTrace();
                entityManager.getTransaction().rollback();
            }finally {
                entityManager.close();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String removePerson(PersonEntity personEntity){
        String status;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try{
                entityManager.getTransaction().begin();
                PersonEntity mergedPerson = entityManager.merge(personEntity);//the personEntity is managed by a different entityManager so we must change that...

                entityManager.remove(mergedPerson);
                entityManager.getTransaction().commit();
                status = "Successfully removed";
            }catch (Exception ex){
                status = ex.getMessage();
                ex.printStackTrace();
                entityManager.getTransaction().rollback();
            }finally {
                entityManager.close();
            }

        }catch (Exception ex){
            status = ex.getMessage();
            ex.printStackTrace();
        }
        return status;
    }

    public PersonEntity getPersonById(Integer id) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try{
                return entityManager.createQuery("select person from PersonEntity person where person.id=:id", PersonEntity.class).setParameter("id",id  ).getSingleResult();
            }catch (Exception ex){
                ex.printStackTrace();
                entityManager.getTransaction().rollback();
            }finally {
                entityManager.close();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public String updatePerson(PersonEntity personEntity){
        String status;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try{
                entityManager.getTransaction().begin();
                PersonEntity oldPerson = entityManager.find(PersonEntity.class,personEntity.getId());
                oldPerson.setName(personEntity.getName());
                oldPerson.setEmail(personEntity.getEmail());
                oldPerson.setAge(personEntity.getAge());
                entityManager.persist(oldPerson);
                entityManager.getTransaction().commit();
                status = "Successfully inserted";
            }catch (Exception ex){
                status = ex.getMessage();
                ex.printStackTrace();
                entityManager.getTransaction().rollback();
            }finally {
                entityManager.close();
            }

        }catch (Exception ex){
            status = ex.getMessage();
            ex.printStackTrace();
        }
        return status;
    }
}
