package com.example.excelProj.Repository;

import com.example.excelProj.Model.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom,Long> {

    @Query(value = "select * from chatroom where (user1_id:=to AND user2_id=:from) " +
            "OR (user2_id:=to AND user1_id=:from)",nativeQuery = true)
    Chatroom findByUsers(@Param("from") Long from,@Param("to") Long to);
}
