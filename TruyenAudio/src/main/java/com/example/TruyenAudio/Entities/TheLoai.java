package com.example.TruyenAudio.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "TheLoai")
@Table(name = "TheLoai")
public class TheLoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theLoaiId")
    private Integer theLoaiId;

    @NotNull(message = "Tên thể loại không thể để trống")
    @Column(name = "tenTheLoai")
    private String tenTheLoai;

    @Column(name = "moTa")
    private String moTa;

    @Column(name = "image")
    private String image;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ChiTiet_TheLoai",
            joinColumns ={@JoinColumn(name = "theLoaiId")},
            inverseJoinColumns = {@JoinColumn(name = "boTruyenId")})
    private Set<BoTruyen> boTruyens = new HashSet<>();

    public Integer getTheLoaiId() {
        return theLoaiId;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public Set<BoTruyen> getBoTruyens() {
        return boTruyens;
    }

    public void setTheLoaiId(Integer theLoaiId) {
        this.theLoaiId = theLoaiId;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setBoTruyens(Set<BoTruyen> boTruyens) {
        this.boTruyens = boTruyens;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
