package angulartoS3.vttp.backend.repo;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Repository
public class S3Repo {

    @Autowired
    AmazonS3 s3;

    public static final String BUCKET_NAME = "vttpce-iine";

    public void saveToS3(String imgId, InputStream is, String contentType, long length) {
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentType(contentType);
        metadata.setContentLength(length);

        PutObjectRequest putReq = new PutObjectRequest(BUCKET_NAME, "images/%s".formatted(imgId), is, metadata);
        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        // Upload to S3 bucket
        PutObjectResult result = s3.putObject(putReq);
        System.out.println("RESULT OF UPLOADING TO S3" + result);

    }

    public String getImageUrl(String key) {
        System.out.println("KEYYYYY>>" + key);
        return s3.getUrl(BUCKET_NAME, key).toExternalForm();
    }

}
