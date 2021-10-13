package library.controller;

import library.dto.ResourceDTO;
import library.service.ResourceService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ResourceDTO>> getAll(){
        return new ResponseEntity(resourceService.getAllResources(), HttpStatus.OK);
    }

    @GetMapping("/checkAvailability/{title}")
    public ResponseEntity checkIfResourceIsAvailable(@PathVariable("title") String title){
            JSONObject res = new JSONObject();
            res.put("Message",resourceService.checkAvailability(title));
            return new ResponseEntity(res, HttpStatus.OK);

    }

    @GetMapping("/lendResource/{title}")
    public ResponseEntity<ResourceDTO> lendResource(@PathVariable("title") String title){
        ResourceDTO resourceDTO = resourceService.lendResource(title);
        if (resourceDTO != null){
            JSONObject res = new JSONObject();
            res.put("Message","se ha prestado un recurso de "+ resourceDTO.getTitle());
            return new ResponseEntity(res, HttpStatus.OK);
        }
        JSONObject res = new JSONObject();
        res.put("Message","No hay recursos disponibles con ese titulo");
        return new ResponseEntity(res, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/findResource/{title}")
    public ResponseEntity<ResourceDTO> findResource(@PathVariable("title") String title){
        ResourceDTO resourceDTO = resourceService.findResource(title);
        return new ResponseEntity(resourceDTO, HttpStatus.OK);
    }

    @GetMapping("/recommendedBykindOfResource/{kindOfResource}")
    public ResponseEntity<List<ResourceDTO>> recommendedByKindOfResource(@PathVariable("kindOfResource") String kindOfResource){
        List<ResourceDTO> resourceDTOList = resourceService.recommendedBykindOfResource(kindOfResource);
        if (resourceDTOList.isEmpty()){
            JSONObject res = new JSONObject();
            res.put("Message","No hay recursos de este tipo a recomendar");
            return new ResponseEntity(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(resourceDTOList,HttpStatus.OK);
    }

    @GetMapping("/recommendedBySubject/{subject}")
    public ResponseEntity<List<ResourceDTO>> recommendedBySubject(@PathVariable("subject") String subject){
        List<ResourceDTO> resourceDTOList = resourceService.recommendedBySubject(subject);
        if (resourceDTOList.isEmpty()){
            JSONObject res = new JSONObject();
            res.put("Message","No hay recursos de este tipo a recomendar");
            return new ResponseEntity(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(resourceDTOList,HttpStatus.OK);
    }

    @GetMapping("/recommendedBykindOfResourceAndSubject/{kindOfResource}/{subject}")
    public ResponseEntity<List<ResourceDTO>> recommendedBykindOfResourceAndSubject(@PathVariable("kindOfResource") String kindOfResource,
           @PathVariable("subject") String subject){
        List<ResourceDTO> resourceDTOList = resourceService.recommendedBykindOfResourceAndSubject(kindOfResource, subject);
        if (resourceDTOList.isEmpty()){
            JSONObject res = new JSONObject();
            res.put("Message","No hay recursos de este tipo a recomendar");
            return new ResponseEntity(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(resourceDTOList,HttpStatus.OK);
    }

    @GetMapping("/returnResource/{title}")
    public ResponseEntity returnResource(@PathVariable("title") String title){
        JSONObject res = new JSONObject();
        res.put("Message",resourceService.addResource(title));
        return new ResponseEntity(res,HttpStatus.OK);
    }



}
