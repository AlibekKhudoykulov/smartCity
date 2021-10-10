package com.example.smartcity.payload.requestDTO;

import lombok.Data;

import java.util.UUID;

@Data
public class CityManagementRequestDTO {

      private UUID officerId;

      private boolean confirmed;

}
