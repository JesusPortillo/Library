package library.service;

import library.dto.ResourceDTO;
import library.dto.ResourceMapper;
import library.model.Resource;
import library.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
       try {
           Stream<Resource> resource = repository.findAll().stream().filter(resource1 -> resource1.getTitle().equals(title));
           return mapper.convertToResourceDTO(resource.findFirst().get());
       }catch (Exception ex){
           System.out.println(ex.getMessage());
       }
    return null;
    }

    public ResourceDTO lendResource(String title){

        ResourceDTO resourceDTO = findResource(title);
        if (resourceDTO != null && isAvailable(resourceDTO)){
            Resource resource = mapper.convertToModel(substractQuantityAndModifyDate(resourceDTO));
            return mapper.convertToResourceDTO(repository.save(resource));

        }
        return null;
    }

    public boolean isAvailable(ResourceDTO resourceDTO){
        return resourceDTO.getQuantityAvailable() > 0;
    }

    public String checkAvailability(String title){
        ResourceDTO resourceDTO = findResource(title);
        if (resourceDTO != null && isAvailable(resourceDTO)){
            return "Hay una disponibilidad de "+ resourceDTO.getQuantityAvailable() +" recursos";
        }
        return "No hay disponibilidad de este recurso";
    }

    public ResourceDTO substractQuantityAndModifyDate(ResourceDTO resourceDTO){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        Resource resource = mapper.convertToModel(resourceDTO);
        resource.setQuantityAvailable(resource.getQuantityAvailable()-1);
        resource.setLastResourceLoanDate(formatter.format(date));
        return mapper.convertToResourceDTO(resource);}

    public String addResource(String title){
        ResourceDTO resourceDTO = findResource(title);
       if (resourceDTO != null){
            Resource resource = mapper.convertToModel(resourceDTO);
            resource.setQuantityAvailable(resource.getQuantityAvailable()+1);
            repository.save(resource);
            return "El recurso " + resourceDTO.getTitle() + "ha sido devuelto";
       }
       return "El recurso no se prestó en ningun momento";
    }

    public List<ResourceDTO> recommendedBykindOfResource(String kindOfResource){
        try {
            Stream<Resource> resource = repository.findAll().stream().filter(resource1 -> resource1.getKindOfResource().equals(kindOfResource));
            List<Resource> resourceList = resource.collect(Collectors.toList());
            List<ResourceDTO> resourceDTOList = new ArrayList<>();
            resourceList.forEach(resource1 -> resourceDTOList.add(mapper.convertToResourceDTO(resource1)));
            return resourceDTOList;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<ResourceDTO> recommendedBySubject(String subject){
        try {
            Stream<Resource> resource = repository.findAll().stream().filter(resource1 -> resource1.getSubject().equals(subject));
            List<Resource> resourceList = resource.collect(Collectors.toList());
            List<ResourceDTO> resourceDTOList = new ArrayList<>();
            resourceList.forEach(resource1 -> resourceDTOList.add(mapper.convertToResourceDTO(resource1)));
            return resourceDTOList;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<ResourceDTO> recommendedBykindOfResourceAndSubject(String kindOfResource, String subject){
        try {
            Stream<Resource> resource = repository.findAll().stream()
                    .filter(resource1 -> resource1.getKindOfResource().equals(kindOfResource)
                            && resource1.getSubject().equals(subject));
            List<Resource> resourceList = resource.collect(Collectors.toList());
            List<ResourceDTO> resourceDTOList = new ArrayList<>();
            resourceList.forEach(resource1 -> resourceDTOList.add(mapper.convertToResourceDTO(resource1)));
            return resourceDTOList;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }


}
