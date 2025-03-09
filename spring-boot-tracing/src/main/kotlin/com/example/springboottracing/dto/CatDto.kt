package com.example.springboottracing.dto

data class CatDto(
    val id: Long? = null,
    val name: String,
    val age: Int,
    val userId: Long? = null
)

data class CreateCatRequest(
    val name: String,
    val age: Int,
    val userId: Long? = null
)

data class UpdateCatRequest(
    val name: String? = null,
    val age: Int? = null,
    val userId: Long? = null
)
