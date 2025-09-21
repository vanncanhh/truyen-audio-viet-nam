package com.example.TruyenAudio.Controllers;

import com.example.TruyenAudio.Entities.TheLoai;
import com.example.TruyenAudio.Services.TheLoaiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/theloai")
public class TheLoaiController {

    @Autowired
    private TheLoaiService theLoaiService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", theLoaiService.theLoaiList());
        return "theloai/index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("theLoai", new TheLoai());
        return "theloai/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("theLoai") TheLoai theLoai,
                         BindingResult br,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         RedirectAttributes ra) {
        if (br.hasErrors()) return "theloai/form";
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = theLoaiService.saveImage(imageFile);
                theLoai.setImage(fileName);
            }
            theLoaiService.createTheLoai(theLoai);
            ra.addFlashAttribute("msg", "Tạo thể loại thành công");
            return "redirect:/theloai";
        } catch (Exception e) {
            br.rejectValue("image", "uploadError", e.getMessage());
            return "theloai/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        TheLoai tl = theLoaiService.theLoai(id);
        if (tl == null) return "redirect:/theloai";
        model.addAttribute("theLoai", tl);
        return "theloai/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("theLoai") TheLoai theLoai,
                         BindingResult br,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         RedirectAttributes ra) {
        if (br.hasErrors()) return "theloai/form";
        TheLoai old = theLoaiService.theLoai(id);
        if (old == null) return "redirect:/theloai";

        try {
            // nếu upload ảnh mới -> lưu mới + xoá ảnh cũ
            if (imageFile != null && !imageFile.isEmpty()) {
                String newName = theLoaiService.saveImage(imageFile);
                theLoaiService.deleteImageIfExists(old.getImage());
                theLoai.setImage(newName);
            } else {
                theLoai.setImage(old.getImage());
            }
            theLoai.setTheLoaiId(id);
            theLoaiService.updateTheLoai(theLoai);
            ra.addFlashAttribute("msg", "Cập nhật thành công");
            return "redirect:/theloai";
        } catch (Exception e) {
            br.rejectValue("image", "uploadError", e.getMessage());
            return "theloai/form";
        }
    }
}