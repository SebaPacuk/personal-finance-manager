package com.pfm.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pfm.history.HistoryField;
import com.pfm.history.HistoryField.SpecialFieldType;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "CATEGORY id (generated by application)", required = true, example = "1")
  private Long id;

  @ApiModelProperty(value = "CATEGORY name", required = true, example = "Eating out")
  @HistoryField
  private String name;

  @ManyToOne
  @ApiModelProperty(value = "Parent category object", required = true)
  @HistoryField(fieldType = SpecialFieldType.PARENT_CATEGORY)
  //TODO try using parentCategoryID instead of CATEGORY object
  private Category parentCategory;

  @JsonIgnore
  private Long userId;

}
