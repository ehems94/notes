package com.example.springboottracing.controller

import com.example.springboottracing.dto.CatDto
import com.example.springboottracing.dto.CreateUserRequest
import com.example.springboottracing.dto.UpdateUserRequest
import com.example.springboottracing.dto.UserDto
import com.example.springboottracing.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getAllUsers(): List<UserDto> {
        logger.info("Retrieving all users")
        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserDto {
        logger.info("Retrieving user with id: {}", id)
        return userService.getUserById(id)
    }

    @GetMapping("/{id}/cats")
    fun getUserCats(@PathVariable id: Long): List<CatDto> {
        logger.info("Retrieving cats for user with id: {}", id)
        return userService.getUserCats(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody request: CreateUserRequest): UserDto {
        logger.info("Creating new user with name: {}", request.name)
        return userService.createUser(request)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserRequest
    ): UserDto {
        logger.info("Updating user with id: {}", id)
        return userService.updateUser(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Long) {
        logger.info("Deleting user with id: {}", id)
        userService.deleteUser(id)
    }
}
