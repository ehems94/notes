package com.example.springboottracing.controller

import com.example.springboottracing.dto.CatDto
import com.example.springboottracing.dto.CreateCatRequest
import com.example.springboottracing.dto.UpdateCatRequest
import com.example.springboottracing.service.CatService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cats")
class CatController(private val catService: CatService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getAllCats(): List<CatDto> {
        logger.info("Retrieving all cats")
        return catService.getAllCats()
    }

    @GetMapping("/{id}")
    fun getCatById(@PathVariable id: Long): CatDto {
        logger.info("Retrieving cat with id: {}", id)
        return catService.getCatById(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCat(@RequestBody request: CreateCatRequest): CatDto {
        logger.info("Creating new cat with name: {}", request.name)
        return catService.createCat(request)
    }

    @PutMapping("/{id}")
    fun updateCat(
        @PathVariable id: Long,
        @RequestBody request: UpdateCatRequest
    ): CatDto {
        logger.info("Updating cat with id: {}", id)
        return catService.updateCat(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCat(@PathVariable id: Long) {
        logger.info("Deleting cat with id: {}", id)
        catService.deleteCat(id)
    }
}
