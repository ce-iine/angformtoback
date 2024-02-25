package angulartoS3.vttp.backend.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import angulartoS3.vttp.backend.service.SaveService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@CrossOrigin
@RequestMapping("")
public class EntryController {

    @Autowired
    SaveService saveSvc;

    @PostMapping(path = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
    public ResponseEntity<String> process(@RequestPart MultipartFile imageFile, @RequestParam String date,
            @RequestParam Double weight, @RequestParam String routine) {

        // all received as string - even date, numbers

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // adjust the date format as per your Angular
                                                                          // date format
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            // Handle parse exception
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid date format");
        }

        System.out.println(">>>>>date" + date);
        System.out.println(">>>>>weight" + weight);
        System.out.println(">>>>>routine" + routine);

        System.out.printf(">>>> name: %s\n", imageFile.getName());
        System.out.printf(">>>> orignal file name: %s\n", imageFile.getOriginalFilename());
        System.out.printf(">>>> size: %d\n", imageFile.getSize());
        System.out.printf(">>>> content type: %s\n", imageFile.getContentType());

        try {
            JsonObject resp = saveSvc.savetoDB(imageFile.getInputStream(), imageFile.getContentType(), imageFile.getSize());

            // JsonObject resp = Json.createObjectBuilder().add("status", 200).build();

            // 200 -> then() in frontend form component processForm()
            return ResponseEntity.ok(resp.toString());
        } catch (IOException ex) {

            JsonObject err = Json.createObjectBuilder()
                    .add("message", "Cannot add friend")
                    .add("status", 400)
                    .build();

            // 400 -> catch() in frontend form component processForm()
            return ResponseEntity.status(400).body(err.toString());
        }



    }

}
