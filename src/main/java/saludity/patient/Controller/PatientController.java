package saludity.patient.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saludity.patient.Model.Pojo.PatientEntity;
import saludity.patient.Data.DTO.PatientDTO;
import saludity.patient.Service.PatientService;

@RestController
@Slf4j
@RequestMapping("/api/v0")
public class PatientController {

    @Autowired
    private PatientService patientService;


    //medicos
    @GetMapping("/patient")
    public ResponseEntity<?> getPatient(
            @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = "CI must be 10 digits") String ci) {
        try {
            PatientEntity patient = patientService.getPatient(ci);
            if (patient == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(PatientDTO.builder()
                    .firstname(patient.getFirstname())
                    .lastname(patient.getLastname())
                    .ci(patient.getCi())
                    .phone(patient.getPhone())
                    .gender(patient.getGender())
                    .email(patient.getEmail())
                    .birthday(patient.getBirthday())
                    .build());
        } catch (DataAccessException exDt) {
            return ResponseEntity.internalServerError().body("An error occurred while accessing the patient data.");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred. Please try again later.");
        }
        }


    @PostMapping("/patient")
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {

        PatientEntity patient =patientService.createPatient(patientDTO);

        if (patient != null) {
            return ResponseEntity.ok( PatientDTO.builder()
                    .firstname(patient.getFirstname())
                    .lastname(patient.getLastname())
                    .ci(patient.getCi())
                    .phone(patient.getPhone())
                    .gender(patient.getGender())
                    .email(patient.getEmail())
                    .birthday(patient.getBirthday())
                    .build());


        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/patient/{patientid}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable String patientid, @Valid @RequestBody String patchBody) {

        PatientEntity patched =patientService.updatePatient(patientid,patchBody);
        if (patched != null) {
            return ResponseEntity.ok( PatientDTO.builder()
                    .firstname(patched.getFirstname())
                    .lastname(patched.getLastname())
                    .ci(patched.getCi())
                    .phone(patched.getPhone())
                    .gender(patched.getGender())
                    .email(patched.getEmail())
                    .birthday(patched.getBirthday())
                    .build());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
