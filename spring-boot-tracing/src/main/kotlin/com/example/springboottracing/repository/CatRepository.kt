package com.example.springboottracing.repository

import com.example.springboottracing.entity.Cat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CatRepository : JpaRepository<Cat, Long>
