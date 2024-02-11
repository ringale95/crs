package me.raveenaingale.crs.dao;

import me.raveenaingale.crs.models.Report;
import me.raveenaingale.crs.models.User;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Repository
public class ReportDAO extends DAO {

    public Report create(Report report) throws Exception {
        try {

            // save user object in the database
            begin();
            getSession().save(report);
            commit();
            close();

            return report;
        } catch (HibernateException e) {
            rollback();
            // throw new AdException("Could not create user " + username, e);
            throw new Exception("Exception while creating report: " + e.getMessage());
        }
    }

    public List<Report> list() throws Exception {
        try {
            // Fetch all user objects from the database
            begin();
            Query query = getSession().createQuery("from Report");
            List<Report> reportList = query.list();
            commit();
            close();

            return reportList;
        } catch (HibernateException e) {
            rollback();
            // throw new AdException("Could not fetch user list", e);
            throw new Exception("Exception while getting user list: " + e.getMessage());
        }
    }

    public Report getReportByID(long id) throws Exception {
        try {
            // Fetch user object from the database based on id
            begin();
            Report report = getSession().get(Report.class, id);
            commit();
            close();

            return report;
        } catch (HibernateException e) {

            rollback();
            // throw new AdException("Could not fetch user with id: " + id, e);
            throw new Exception("Exception while fetching report with id: " + id + ", " + e.getMessage());
        }

    }

    @Override
    public List search(Map<String, String> criteria) throws Exception {
        return null;
    }
}
