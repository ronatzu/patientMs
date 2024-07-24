package saludity.patient.Service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saludity.patient.Data.PatientRepository;
import saludity.patient.Model.Pojo.PatientEntity;
import saludity.patient.Model.Request.CreatePatientRequest;
import saludity.patient.Service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    //@Transactional
    @Override
    public PatientEntity createPatient(CreatePatientRequest request) {

        if (request.getBirthday() == null) {
             throw new IllegalArgumentException("The birthday cannot be null");
        }
        LocalDate today = LocalDate.now();

        PatientEntity patientEntity = PatientEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .age(String.valueOf(Period.between(request.getBirthday(), today).getYears()))
                .ci(request.getCi())
                .phone(request.getPhone())
                .gender(request.getGender())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .creationDate(LocalDate.now())
                .build();

        System.out.println("Se crea el paciente");
        System.out.println(patientEntity);

        return patientRepository.save(patientEntity);
    }

   // @Transactional
    @Override
    public PatientEntity updatePatient(String patientId, String request) {

        Optional<PatientEntity> patient = patientRepository.findById(Long.valueOf(patientId));

        if (patient != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(String.valueOf(request)));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(patient)));
                PatientEntity patched = objectMapper.treeToValue(target, PatientEntity.class);
                patientRepository.save(patched);
                return patched;
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating patient {}", patientId, e);
                return null;
            }
        } else {
            return null;
        }

    }

    //@Transactional(readOnly = true)
    @Override
    public PatientEntity getPatient(String ci) {
        return patientRepository.findPatientByci(ci)
                .orElseThrow(() -> new NoSuchElementException("No patient found with CI: " + ci));
    }



}
