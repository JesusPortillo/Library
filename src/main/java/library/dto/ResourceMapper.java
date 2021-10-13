package library.dto;

import library.model.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ResourceMapper {

    @Autowired
    private ModelMapper mapper;

    public ResourceDTO convertToResourceDTO(Resource resource){
        ResourceDTO resourceDTO = mapper.map(resource, ResourceDTO.class);
        return resourceDTO;
    }

    public Resource convertToModel(ResourceDTO resourceDTO){
        Resource resource = mapper.map(resourceDTO, Resource.class);
        return resource;
    }


}
