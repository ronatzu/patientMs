package saludity.patient.Model.Request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientRequest {

    private String firstname;
    private String lastname;
    private String age;
    private String ci;
    private String phone;
    private String gender;
    private String email;
    private LocalDate birthday;
}
