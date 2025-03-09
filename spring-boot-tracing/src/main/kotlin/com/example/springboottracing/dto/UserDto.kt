package com.example.springboottracing.dto

data class UserDto(
    val id: Long? = null,
    val name: String,
    val cats: List<CatDto> = emptyList()
)

data class CreateUserRequest(
    val name: String
)

data class UpdateUserRequest(
    val name: String
)
