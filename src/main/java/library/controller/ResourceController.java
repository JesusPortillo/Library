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
    public ResponseEntity<ResourceDTO> checkIfResourceIsAvailable(@PathVariable("title") String title){
        ResourceDTO resourceDTO = resourceService.findResource(title);
        if (resourceDTO != null){
            JSONObject res = new JSONObject();
            res.put("Message","Hay una disponibilidad de "+ resourceDTO.getQuantityAvailable() +" recursos");
            return new ResponseEntity(res, HttpStatus.OK);
        }
        JSONObject res = new JSONObject();
        res.put("Message","No hay recursos disponibles con ese titulo");
        return new ResponseEntity(res, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/lendResource/{title}")
    public ResponseEntity<ResourceDTO> lendResource(@PathVariable("title") String title){
        ResourceDTO resourceDTO = resourceService.lendResource(title);
        return new ResponseEntity(resourceDTO, HttpStatus.OK);
    }

}
