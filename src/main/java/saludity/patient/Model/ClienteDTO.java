package saludity.patient.Model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;




@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class ClienteDTO implements Serializable {

    private String firstname;
    private String lastname;
    private Integer age;
    private Integer ci;
    private Integer phone;
    private String gender;
    private String email;
    private LocalDate birthday;
}