package dam.informesmusica.controllers;

import dam.informesmusica.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @GetMapping("/obtenerAlbums")
    public ResponseEntity<String> obtenerAlbums() {
        return ResponseEntity.ok("Obteniendo datos de la album");
    }

    @GetMapping("/obtenerArtistas")
    public ResponseEntity<byte[]> obtenerArtistas() {
        System.out.println("Obteniendo artistas");
        try{
            Map<String, Object> params = new HashMap<String, Object>();
            byte[] report = reportsService.generarReport("InformeArtistas",params);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition",
                    "inline; filename=InformeArtistas.pdf");
            return new ResponseEntity<>(report, headers, HttpStatus.OK);

        }catch(Exception e){
            System.out.println(e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    @GetMapping("/obtenerCancionesTitulo/{titulo}")
    public ResponseEntity<byte[]> obtenerCancionesTitulo(@PathVariable("titulo") String titulo) {
        System.out.println("Obteniendo artistas");
        try{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("titulo_cancion", titulo);
            byte[] report = reportsService.generarReport("InformeCanciones",params);
            if(report==null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition",
                    "inline; filename=InformeCanciones.pdf");
            return new ResponseEntity<>(report, headers, HttpStatus.OK);

        }catch(Exception e){
            System.out.println(e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
