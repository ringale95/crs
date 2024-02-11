package me.raveenaingale.crs.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String commit;
    private String author;
    private String prTitle;
    private boolean status;
    private Date createdAt;
    private String extentReportLocation;
}
