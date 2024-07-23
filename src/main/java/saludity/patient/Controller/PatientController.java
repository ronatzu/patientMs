package saludity.patient.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saludity.patient.Model.Pojo.PatientEntity;
import saludity.patient.Model.Request.CreatePatientRequest;
import saludity.patient.Service.PatientService;

@RestController
@Slf4j
@RequestMapping("/api/v0")
public class PatientController {

    @Autowired
    private PatientService patientService;


    @GetMapping("/patient/{patientid}")
    public ResponseEntity<PatientEntity> getPatient(@PathVariable String patientci) {
        PatientEntity patient = patientService.getPatient(patientci);
        return ResponseEntity.ok(patient);
    }

    @PostMapping("/patient")
    public ResponseEntity<PatientEntity> updatePatient(@Valid @RequestBody CreatePatientRequest createPatientRequest) {

        PatientEntity patient =patientService.createPatient(createPatientRequest);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/patient/{patientid}")
    public ResponseEntity<PatientEntity> updatePatient(@PathVariable String patientid, @Valid @RequestBody String patchBody) {

        PatientEntity patched =patientService.updatePatient(patientid,patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
