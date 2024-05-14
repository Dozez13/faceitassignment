package com.example.faceitassignment.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OrderPostDto {

    private Long mainCourseId;

    private Long dessertId;

    private Long drinkId;

    private List<Long> drinkAdditionsIds;
}
