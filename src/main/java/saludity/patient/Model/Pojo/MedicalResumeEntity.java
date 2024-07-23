package saludity.patient.Model.Pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="medicalresume")
@Builder
public class MedicalResumeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "treatments", nullable = false)
    private String treatments;

    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @ManyToOne(targetEntity = PatientEntity.class)
    private PatientEntity patientEntity;
}
