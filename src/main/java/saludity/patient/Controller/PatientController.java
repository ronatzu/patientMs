package saludity.patient.Controller;

import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saludity.patient.Persistence.DTO.PatientRequestDTO;
import saludity.patient.Persistence.DTO.PatientProfileDTO;
import saludity.patient.Service.PatientService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequestMapping("/api/v0")
public class PatientController {

    @Autowired
    private PatientService patientService;


    //medicos
    @GetMapping("/patients/{ci}")
    public ResponseEntity<?> getPatient(
            @PathVariable @Pattern(regexp = "^[0-9]{10}$", message = "CI must be 10 digits") String ci) {

        System.out.println("empezo");
        try {
            PatientProfileDTO patient = patientService.getPatient(ci);
            if (patient == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(patient);
        } catch (DataAccessException exDt) {
            return ResponseEntity.internalServerError().body("An error occurred while accessing the patient data.");
        }catch (NoSuchElementException nse) {
            return ResponseEntity.notFound().build();
        }catch (Exception ex) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred. Please try again later.");
        }
    }


    @PostMapping("/patients")
    public ResponseEntity<PatientProfileDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) throws URISyntaxException {

        PatientProfileDTO patient =patientService.createPatient(patientRequestDTO);

        if (patient != null) {
            URI location = new URI("/patients/" + patient.getCi());
            return ResponseEntity.created(location).body(patient);


        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/patients/{ci}")
    public ResponseEntity<PatientProfileDTO> updatePatient(@PathVariable  String ci,@RequestBody JsonPatch patchBody) {


        System.out.println("cambio");
        System.out.println(patchBody);
        PatientProfileDTO patched =patientService.updatePatient(ci,patchBody);
        System.out.println(patched);

        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }



}
