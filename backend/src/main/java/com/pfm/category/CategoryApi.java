package com.pfm.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("categories")
@CrossOrigin
@Api(value = "CATEGORY", description = "Controller used to list / add / update / delete categories.")
public interface CategoryApi {

  @ApiOperation(value = "Find category by id", response = Category.class, authorizations = {@Authorization(value = "Bearer")})
  @GetMapping(value = "/{categoryId}")
  ResponseEntity<Category> getCategoryById(@PathVariable long categoryId);

  @ApiOperation(value = "Get list of categories", response = Category.class, responseContainer = "List",
      authorizations = {@Authorization(value = "Bearer")})
  @GetMapping
  ResponseEntity<List<Category>> getCategories();

  @ApiOperation(value = "Create a new category", response = Long.class, authorizations = {@Authorization(value = "Bearer")})
  @PostMapping
  ResponseEntity<?> addCategory(CategoryRequest categoryRequest);

  @ApiOperation(value = "Update an existing category", response = Void.class, authorizations = {@Authorization(value = "Bearer")})
  @PutMapping(value = "/{categoryId}")
  ResponseEntity<?> updateCategory(@PathVariable long categoryId, CategoryRequest categoryRequest);

  @ApiOperation(value = "Delete an existing category", response = Void.class, authorizations = {@Authorization(value = "Bearer")})
  @DeleteMapping(value = "/{categoryId}")
  ResponseEntity<?> deleteCategory(@PathVariable long categoryId);

}
