package com.cbnuccc.cbnuccc.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.cbnuccc.cbnuccc.Util.SecurityUtil;
import com.cbnuccc.cbnuccc.Util.StatusCode;

@Service
public class ImageService {
    private final WebClient webClient = WebClient.builder().build();

    @Autowired
    private SecurityUtil securityUtil;

    public StatusCode uploadProfileImage(MultipartFile file) {
        final String bucketName = "profile";
        final String supabaseUrl = securityUtil.getSupbaseBaseUrl();
        final String supabaseKey = securityUtil.getSupabaseKey();

        try {
            // 확장자 추출
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // 안전한 파일명
            String safeFileName = UUID.randomUUID().toString() + extension;
            String path = bucketName + "/" + safeFileName;

            // Supabase Storage 업로드
            webClient.post()
                    .uri(supabaseUrl + "/storage/v1/object/" + path)
                    .header("Authorization", "Bearer " + supabaseKey)
                    .header("apikey", supabaseKey)
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .bodyValue(file.getBytes())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // 퍼블릭 URL 리턴
            String url = supabaseUrl + "/storage/v1/object/public/" + path;
            System.out.println(url);
            return StatusCode.NO_ERROR;

        } catch (Exception e) {
            return StatusCode.SOMETHING_WENT_WRONG;
        }
    }
}
