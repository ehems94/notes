package com.example.springboottracing.service

import com.example.springboottracing.dto.CatDto
import com.example.springboottracing.dto.CreateCatRequest
import com.example.springboottracing.dto.UpdateCatRequest
import com.example.springboottracing.entity.Cat
import com.example.springboottracing.repository.CatRepository
import com.example.springboottracing.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CatService(
    private val catRepository: CatRepository,
    private val userRepository: UserRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    fun getAllCats(): List<CatDto> {
        logger.debug("Fetching all cats from database")
        val cats = catRepository.findAll().map { it.toDto() }
        logger.debug("Found {} cats", cats.size)
        return cats
    }

    fun getCatById(id: Long): CatDto {
        logger.debug("Fetching cat with id: {}", id)
        return catRepository.findByIdOrNull(id)?.toDto()
            ?: throw IllegalArgumentException("Cat not found with id: $id").also {
                logger.error("Cat not found with id: {}", id)
            }
    }

    @Transactional
    fun createCat(request: CreateCatRequest): CatDto {
        logger.debug("Creating new cat with name: {} and age: {}", request.name, request.age)
        val user = request.userId?.let { userId ->
            logger.debug("Fetching user with id: {} for new cat", userId)
            userRepository.findByIdOrNull(userId)
                ?: throw IllegalArgumentException("User not found with id: $userId").also {
                    logger.error("Failed to create cat: User not found with id: {}", userId)
                }
        }

        val cat = Cat(
            name = request.name,
            age = request.age,
            user = user
        )
        val savedCat = catRepository.save(cat).toDto()
        logger.info("Successfully created cat with id: {}", savedCat.id)
        return savedCat
    }

    @Transactional
    fun updateCat(id: Long, request: UpdateCatRequest): CatDto {
        logger.debug("Updating cat with id: {}", id)
        val cat = catRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Cat not found with id: $id").also {
                logger.error("Failed to update: Cat not found with id: {}", id)
            }

        request.name?.let {
            logger.debug("Updating cat name from '{}' to '{}'", cat.name, it)
            cat.name = it
        }
        request.age?.let {
            logger.debug("Updating cat age from {} to {}", cat.age, it)
            cat.age = it
        }
        
        if (request.userId != cat.user?.id) {
            logger.debug("Updating cat owner from id {} to {}", cat.user?.id, request.userId)
            cat.user = request.userId?.let { userId ->
                userRepository.findByIdOrNull(userId)
                    ?: throw IllegalArgumentException("User not found with id: $userId").also {
                        logger.error("Failed to update cat: User not found with id: {}", userId)
                    }
            }
        }

        val updatedCat = catRepository.save(cat).toDto()
        logger.info("Successfully updated cat with id: {}", id)
        return updatedCat
    }

    fun deleteCat(id: Long) {
        logger.debug("Attempting to delete cat with id: {}", id)
        if (!catRepository.existsById(id)) {
            throw IllegalArgumentException("Cat not found with id: $id").also {
                logger.error("Failed to delete: Cat not found with id: {}", id)
            }
        }
        catRepository.deleteById(id)
        logger.info("Successfully deleted cat with id: {}", id)
    }

    private fun Cat.toDto() = CatDto(
        id = id,
        name = name,
        age = age,
        userId = user?.id
    )
}
