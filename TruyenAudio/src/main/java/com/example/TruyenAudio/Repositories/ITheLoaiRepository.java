package com.example.TruyenAudio.Repositories;

import com.example.TruyenAudio.Entities.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ITheLoaiRepository extends JpaRepository<TheLoai, Integer> {
    @Query("SELECT t.tenTheLoai FROM TheLoai t WHERE t.theLoaiId= :id")
    String getListTenTheLoais (@Param("id") Integer id);
}
