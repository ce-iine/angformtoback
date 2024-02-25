package angulartoS3.vttp.backend.service;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import angulartoS3.vttp.backend.repo.S3Repo;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class SaveService {

    @Autowired
    S3Repo s3Repo;

    public JsonObject savetoDB(InputStream is, String contentType, long length){
        String id = UUID.randomUUID().toString().substring(0, 8);
        s3Repo.saveToS3(id, is, contentType, length);

        String url = s3Repo.getImageUrl("images/%s".formatted(id));

        JsonObject resp = Json.createObjectBuilder().add("url", url).build();
        return resp;
    }
    
}
