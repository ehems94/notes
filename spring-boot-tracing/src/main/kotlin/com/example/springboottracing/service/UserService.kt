package com.example.springboottracing.service

import com.example.springboottracing.dto.CatDto
import com.example.springboottracing.dto.CreateUserRequest
import com.example.springboottracing.dto.UpdateUserRequest
import com.example.springboottracing.dto.UserDto
import com.example.springboottracing.entity.User
import com.example.springboottracing.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    fun getAllUsers(): List<UserDto> {
        logger.debug("Fetching all users from database")
        val users = userRepository.findAll().map { it.toDto() }
        logger.debug("Found {} users", users.size)
        return users
    }

    fun getUserById(id: Long): UserDto {
        logger.debug("Fetching user with id: {}", id)
        return userRepository.findByIdOrNull(id)?.toDto()
            ?: throw IllegalArgumentException("User not found with id: $id").also {
                logger.error("User not found with id: {}", id)
            }
    }

    fun getUserCats(id: Long): List<CatDto> {
        logger.debug("Fetching cats for user with id: {}", id)
        val cats = userRepository.findByIdOrNull(id)?.cats?.map { 
            CatDto(id = it.id, name = it.name, age = it.age, userId = id)
        } ?: throw IllegalArgumentException("User not found with id: $id").also {
            logger.error("Failed to fetch cats: User not found with id: {}", id)
        }
        logger.debug("Found {} cats for user {}", cats.size, id)
        return cats
    }

    @Transactional
    fun createUser(request: CreateUserRequest): UserDto {
        logger.debug("Creating new user with name: {}", request.name)
        val user = User(name = request.name)
        val savedUser = userRepository.save(user).toDto()
        logger.info("Successfully created user with id: {}", savedUser.id)
        return savedUser
    }

    @Transactional
    fun updateUser(id: Long, request: UpdateUserRequest): UserDto {
        logger.debug("Updating user with id: {}", id)
        val user = userRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("User not found with id: $id").also {
                logger.error("Failed to update: User not found with id: {}", id)
            }
        
        logger.debug("Updating user name from '{}' to '{}'", user.name, request.name)
        user.name = request.name
        val updatedUser = userRepository.save(user).toDto()
        logger.info("Successfully updated user with id: {}", id)
        return updatedUser
    }

    fun deleteUser(id: Long) {
        logger.debug("Attempting to delete user with id: {}", id)
        if (!userRepository.existsById(id)) {
            throw IllegalArgumentException("User not found with id: $id").also {
                logger.error("Failed to delete: User not found with id: {}", id)
            }
        }
        userRepository.deleteById(id)
        logger.info("Successfully deleted user with id: {}", id)
    }

    private fun User.toDto() = UserDto(
        id = id,
        name = name,
        cats = cats.map { cat -> CatDto(id = cat.id, name = cat.name, age = cat.age, userId = id) }
    )
}
