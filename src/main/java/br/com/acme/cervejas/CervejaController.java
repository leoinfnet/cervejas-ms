package br.com.acme.cervejas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class CervejaController {
    @Value("${LOCATION}")
    private String location;
    @Autowired
    CervejaRepository cervejaRepository;
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(cervejaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable  Long id){
        Optional<Cerveja> byId = cervejaRepository.findById(id);
        if(byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId.get());
    }


    @GetMapping("/file")
    public ResponseEntity<?> getFromFile(){

        Path path = Paths.get(location);
        List<String> strings = new ArrayList<>();
        try {
            strings = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(strings);
    }

}
