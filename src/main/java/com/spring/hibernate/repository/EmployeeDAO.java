package com.spring.hibernate.repository;

import com.spring.hibernate.model.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements Dao<Employee> {

    private static SessionFactory factory;

     public EmployeeDAO() {
       try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public Optional<Employee> get(long id) {
        Session session = factory.openSession();
        Transaction tx = null;
        Employee employee = null;

        try {
            tx = session.beginTransaction();
            employee = (Employee)session.get(Employee.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return Optional.ofNullable(employee);
    }

    @Override
    public List<Employee> getAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        List employees = null;

        try {
            tx = session.beginTransaction();
            employees = session.createQuery("FROM Employee").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employees;
    }

    @Override
    public long save(Employee employee) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try {
            tx = session.beginTransaction();
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    @Override
    public void update(Employee employee) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
           // Employee employee = (Employee)session.get(Employee.class, EmployeeID);
           // employee.setSalary( salary );
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Employee employee) {
            Session session = factory.openSession();
            Transaction tx = null;

            try {
                tx = session.beginTransaction();
                //Employee employee = (Employee)session.get(Employee.class, employee.getId());
                session.delete(employee);
                tx.commit();
            } catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }

    }
}