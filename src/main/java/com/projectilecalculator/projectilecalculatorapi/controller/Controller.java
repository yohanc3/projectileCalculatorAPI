package com.projectilecalculator.projectilecalculatorapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.projectilecalculator.projectilecalculatorapi.model.Projectile;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping
public class Controller {

  private final ObjectMapper mapper = new ObjectMapper();
  
  @PostMapping("/calculate") 
  public ObjectNode calculate(@RequestBody Projectile projectile){
    
    String problemType = projectile.getProblemType();

    switch(problemType){
      case "golfBall": 
        return projectile.golfBall();
      case "tableTop":
        return projectile.tableTop();
      case "cliff": 
        return projectile.cliff();
      default: 
        return handleInputError(problemType);
    }
  }

  public ObjectNode handleInputError(String problemType){
    ObjectNode jsonResponse = mapper.createObjectNode();
    ArrayNode acceptableTypes = mapper.createArrayNode();

    String[] acceptableTypesArray = {"golfBall", "tableTop", "cliff"};

    for(String type : acceptableTypesArray){
      acceptableTypes.add(type);
    }
    
    jsonResponse.put("Error", "Not acceptable problem type");
    jsonResponse.put("receivedType", problemType);
    jsonResponse.putArray("acceptableTypes").addAll(acceptableTypes);

    return jsonResponse;

  }

}
