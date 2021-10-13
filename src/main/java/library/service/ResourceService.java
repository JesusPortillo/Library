package library.service;

import library.dto.ResourceDTO;
import library.dto.ResourceMapper;
import library.model.Resource;
import library.repository.ResourceRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ResourceService {

    private ResourceRepository repository;
    private ResourceMapper mapper;

    @Autowired
    public ResourceService(ResourceRepository repository, ResourceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ResourceDTO> getAllResources(){
        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        repository.findAll().forEach(resource -> resourceDTOS.add(mapper.convertToResourceDTO(resource)));
        return resourceDTOS;
    }

    public ResourceDTO findResource(String title){
        Optional<ResourceDTO> resourceDTOS = getAllResources().stream()
                .findFirst().filter(resourceDTO -> resourceDTO.getTitle()
                .equals(title) && isAvailable(resourceDTO));
        if (resourceDTOS.isPresent()){
            return resourceDTOS.get();
        }
        return null;
    }

    public ResourceDTO lendResource(String title){

        ResourceDTO resourceDTO = findResource(title);
        if ( resourceDTO != null && isAvailable(resourceDTO) ){
            repository.delete(mapper.convertToModel(resourceDTO));
            return mapper.convertToResourceDTO(repository.save(mapper.convertToModel(substractQuantityAndModifyDate(resourceDTO))));

        }
        throw new NoSuchElementException("No está disponible el titular "
                + title + "su ultimo ejemplar se prestó " + resourceDTO.getLastResourceLoanDate());
    }

    public ResourceDTO addResource(ResourceDTO resourceDTO){
       if (findResource(resourceDTO.getTitle()) != null){
            Resource resource = mapper.convertToModel(resourceDTO);
            resource.setQuantityAvailable(resource.getQuantityAvailable()+1);
            return mapper.convertToResourceDTO(resource);
       }
       repository.save(mapper.convertToModel(resourceDTO));
       return resourceDTO;
    }



    public ResourceDTO substractQuantityAndModifyDate(ResourceDTO resourceDTO){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        Resource resource = mapper.convertToModel(resourceDTO);
        resource.setQuantityAvailable(resource.getQuantityAvailable()-1);
        resource.setLastResourceLoanDate(formatter.format(date));
        return mapper.convertToResourceDTO(resource);}

    public boolean isAvailable(ResourceDTO resourceDTO){
       return resourceDTO.getQuantityAvailable() > 0;
    }

    public List<ResourceDTO> recommendedBykindOfResource(String kindOfResource){
        List<ResourceDTO> resourceDTOS = (List<ResourceDTO>) repository.findAll().stream().filter(resource -> resource.getKindOfResource().equals(kindOfResource));
        return resourceDTOS;
    }

    public List<ResourceDTO> recommendedBySubject(String subject){
        List<ResourceDTO> resourceDTOS = (List<ResourceDTO>) repository.findAll().stream().filter(resource -> resource.getSubject().equals(subject));
        return resourceDTOS;
    }

    public List<ResourceDTO> recommendedBykindOfResourceAndSubject(String kindOfResource, String subject){
        List<ResourceDTO> resourceDTOS = (List<ResourceDTO>) repository.findAll().stream().filter(resource -> resource.getKindOfResource().equals(kindOfResource)
                && resource.getSubject().equals(subject));
        return resourceDTOS;
    }


}
