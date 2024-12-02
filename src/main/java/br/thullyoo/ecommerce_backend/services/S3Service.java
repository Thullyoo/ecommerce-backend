package br.thullyoo.ecommerce_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    public String registerImage(UUID product_id, MultipartFile file) throws IOException {
        try {
            String fileName = product_id.toString();

            PutObjectRequest putObject = PutObjectRequest.builder()
                    .bucket("my-bucket")
                    .key(fileName)
                    .build();

            s3Client.putObject(putObject, RequestBody.fromByteBuffer(ByteBuffer.wrap(file.getBytes())));

            GetUrlRequest request = GetUrlRequest.builder()
                    .bucket("my-bucket")
                    .key(fileName)
                    .build();

            return s3Client.utilities().getUrl(request).toString();

        } catch (IOException e){
            throw e;
        }

    }

}
