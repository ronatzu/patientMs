package saludity.patient.Model.Pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="patients")
@Builder
@ToString
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name= "lastname",nullable = false)
    private String lastname;

    @Column(name= "age",nullable = false)
    private String age;

    @Column(name= "ci",nullable = false, unique = true,length = 10)
    private String ci;

    @Column(name= "phone",nullable = false, unique = true,length = 10)
    private String phone;

    @Column(name= "gender",nullable = false)
    private String gender;

    @Column(name= "email",nullable = false, unique = true)
    private String email;

    @Column(name= "birthday",nullable = false)
    private LocalDate birthday;

    @Column(name="creation",nullable = false)
    private LocalDate creationDate;

    @OneToMany(targetEntity = MedicalResumeEntity.class,fetch = FetchType.LAZY,mappedBy = "patientEntity")
    private List<MedicalResumeEntity> medicalResume;


}
