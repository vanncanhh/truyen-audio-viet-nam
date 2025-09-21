package com.example.TruyenAudio.Services;

import com.example.TruyenAudio.Entities.TheLoai;
import com.example.TruyenAudio.Repositories.ITheLoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TheLoaiService {

    @Autowired
    private ITheLoaiRepository theLoaiRepository;

    @Value("${upload.path}")
    private String uploadPath;

    private static final Set<String> ALLOWED = Set.of("image/png", "image/jpeg", "image/webp", "image/gif");

    public List<TheLoai> theLoaiList(){
        return theLoaiRepository.findAll();
    }

    public TheLoai theLoai(Integer id){
        return theLoaiRepository.findById(id).orElse(null);
    }

    public void createTheLoai(TheLoai theLoai) {
        if(theLoai != null)
            theLoaiRepository.save(theLoai);
    }

    public void updateTheLoai(TheLoai theLoai){
        if(theLoai != null)
            theLoaiRepository.save(theLoai);
    }

    public void delete(TheLoai theLoai){
        if(theLoai != null)
            theLoaiRepository.delete(theLoai);
    }

    public String tenTheLoais(Integer id){
        return theLoaiRepository.getListTenTheLoais(id); //list ten the loai
    }

    public String saveImage(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) return null;

        String contentType = image.getContentType();
        if (contentType == null || !ALLOWED.contains(contentType)) {
            throw new IOException("Định dạng ảnh không hỗ trợ (chỉ: jpg/jpeg, png, webp, gif).");
        }

        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);

        String original = image.getOriginalFilename();
        String ext = getExtensionSafe(original);
        String uniqueFileName = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

        Path filePath = uploadDir.resolve(uniqueFileName);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName; // lưu tên file vào cột image
    }

    public void deleteImageIfExists(String fileName) {
        if (fileName == null || fileName.isBlank()) return;
        try {
            Path p = Paths.get(uploadPath).resolve(fileName);
            Files.deleteIfExists(p);
        } catch (Exception ignored) {}
    }

    private String getExtensionSafe(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        if (dot < 0 || dot == filename.length()-1) return "";
        return filename.substring(dot+1).toLowerCase();
    }
}
