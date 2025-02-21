package edu.eci.arsw.blueprints.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/app/blueprints") 
public class BlueprintController {

    @Autowired
    BlueprintsServices bps;

    @GetMapping("/all")
    public ResponseEntity<Collection<Blueprint>> getAllBlueprints() {
        return ResponseEntity.ok(bps.getAllBlueprints());
    }

    @GetMapping("/{author}/{name}")
    public ResponseEntity<Blueprint> getBlueprint(@PathVariable String author, @PathVariable String name) throws BlueprintNotFoundException {
        Blueprint blueprint = bps.getBlueprint(author, name);
        if (blueprint != null) {
            return ResponseEntity.ok(blueprint);
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @GetMapping("/{author}")
    public ResponseEntity<Set<Blueprint>> getBlueprint(@PathVariable String author) throws BlueprintNotFoundException {
        Set <Blueprint> blueprints = bps.getBlueprintsByAuthor(author);
        if (blueprints != null) {
            return ResponseEntity.ok(blueprints);
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> postMethodName(@RequestBody Blueprint blueprint) throws BlueprintPersistenceException {
        bps.addNewBlueprint(blueprint);
        return ResponseEntity.ok("Guardado");
    }
    
}
