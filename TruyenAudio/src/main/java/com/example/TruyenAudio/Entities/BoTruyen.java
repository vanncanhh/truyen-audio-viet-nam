package com.example.TruyenAudio.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "BoTruyen")
@Table(name = "BoTruyen")
public class BoTruyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boTruyenId")
    private Integer boTruyenId;

    @NotNull(message = "Tên truyện không được để trống")
    @Column(name = "tenTruyen")
    private String tenTruyen;

    @NotNull(message = "MoTaNgan không được để trống")
    @Column(name = "moTaNgan")
    private String moTaNgan;

    @NotNull(message = "MoTaDai không được để trống")
    @Column(name = "MoTaDai")
    private String moTaDai;

    @Column(name = "thoiGianPhatHanh")
    private Date thoiGianPhatHanh;

    @Column(name = "views")
    private Integer views;

    @Column(name = "trangThai")
    private String trangThai;

    @Column(name = "image")
    private String image;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ChiTiet_TheLoai",
    joinColumns ={@JoinColumn(name = "BoTruyenId")},
    inverseJoinColumns = {@JoinColumn(name = "TheLoaiId")})
    private Set<TheLoai> theLoais = new HashSet<>();
}
