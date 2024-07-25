package saludity.patient.Service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saludity.patient.Persistence.DTO.PatientProfileDTO;
import saludity.patient.Persistence.Repository.PatientRepository;
import saludity.patient.Model.Pojo.PatientEntity;
import saludity.patient.Persistence.DTO.PatientRequestDTO;
import saludity.patient.Service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PatientProfileDTO createPatient(PatientRequestDTO patientRequestDTO) {
        try {
            if (patientRequestDTO.getBirthday() == null) {
                throw new IllegalArgumentException("The birthday cannot be null");
            }

            ModelMapper modelMapper = new ModelMapper();
            PatientEntity patientEntity = modelMapper.map(patientRequestDTO, PatientEntity.class);
            System.out.println(patientEntity);

            patientEntity.setCreationDate(LocalDateTime.now());
            patientEntity.setAge((byte)((Period.between(patientEntity.getBirthday(), LocalDate.now()).getYears())));
            System.out.println(patientEntity);
            patientRepository.save(patientEntity);

            return modelMapper.map(patientEntity, PatientProfileDTO.class);

        }catch (Exception e){
            throw new UnsupportedOperationException("Error guardar usuario");
        }

    }

    @Override
    public PatientProfileDTO updatePatient(String patientci, PatientRequestDTO patientRequestDTO) {
        // Encuentra el paciente por su CI
        Optional<PatientEntity> optionalPatient = patientRepository.findPatientByci(patientci);

        // Verifica si el paciente existe
        if (optionalPatient.isPresent()) {
            PatientEntity existingPatient = optionalPatient.get();
            


            try {
                SimpleBeanPropertyFilter
                // Convierte patientDTO a JsonNode
                JsonNode patientDTONode = objectMapper.convertValue(patientRequestDTO, JsonNode.class);

                // Crea JsonMergePatch a partir del JsonNode del DTO
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(patientDTONode);

                // Convierte el paciente existente a JsonNode
                JsonNode existingPatientNode = objectMapper.convertValue(existingPatient, JsonNode.class);

                // Aplica el parche al JsonNode del paciente existente
                JsonNode targetNode = jsonMergePatch.apply(existingPatientNode);

                // Convierte el JsonNode resultante a PatientEntity
                PatientEntity patchedPatient = objectMapper.treeToValue(targetNode, PatientEntity.class);

                // Guarda el paciente parcheado
                patientRepository.save(patchedPatient);

                // Mapea el paciente parcheado a PatientDTO
                ModelMapper mapper = new ModelMapper();
                return mapper.map(patchedPatient, PatientProfileDTO.class);

            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating patient {}", patientci, e);
                return null;
            }
        } else {
            // Retorna null si el paciente no se encuentra
            return null;
        }
    }


    //@Transactional(readOnly = true)
    @Override
    public PatientProfileDTO getPatient(String ci) {
         Optional <PatientEntity> patient=patientRepository.findPatientByci(ci);
         if (patient.isPresent()) {
             ModelMapper mapper = new ModelMapper();
             PatientEntity patientactual=patient.get();
             return mapper.map(patientactual, PatientProfileDTO.class);
         } else {
             return null;
         }

    }



}
