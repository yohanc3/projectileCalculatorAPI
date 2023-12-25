package com.projectilecalculator.projectilecalculatorapi.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Projectile {  
  private String problemType;
  private double angle;
  private double initialSpeed;
  private double height;
  private final ObjectMapper mapper = new ObjectMapper();
  private final ObjectNode jsonResponse = mapper.createObjectNode();

  //Constructor for problems of type 'golf ball' and 'cliff'
  public Projectile(String problemType, double angle, double initialSpeed, double height){
    this.problemType = problemType;
    this.angle = angle;
    this.initialSpeed = initialSpeed;
    this.height = height;
  }

  public String getProblemType() {
    return problemType;
  }
  public double getAngle() {
    return angle;
  }
  public double getInitialSpeed() {
    return initialSpeed;
  }
  public double getHeight() {
    return height;
  }

  public ObjectNode golfBall(){

    double theta = this.angle;
    double veloI = this.initialSpeed;

    //initial vertical and horizontal velocity
    double iVeloH = veloI * (Math.cos(Math.toRadians(theta)));
    double iVeloV = veloI * (Math.sin(Math.toRadians(theta)));
    
    //vertical (flight) time, (Vf = -Vo), (a = -9.81 m/s^2), (t = (Vf-Vo)/a)
    double flightTime = (((-iVeloV) - iVeloV)/-9.81);
    
    //horizontal displacement, a = 0 m/s^2, (d = Vot + (1/2)at^2)
    double horDisp = iVeloH * flightTime;
    
    //max height, t = 1/2(flightTime), (d = Vot + (1/2)at^2)
    double vertMax = ((iVeloV * (.5 * flightTime)) + (.5 * (-9.81) * ((.5 * flightTime) * (.5 * flightTime))));

    //jsonResponse
    jsonResponse.put("initialVerticalVelocity", iVeloV);
    jsonResponse.put("initialHorizontalVelocity", iVeloH);
    jsonResponse.put("flightTime", flightTime);
    jsonResponse.put("horizontalDisplacement", horDisp);
    jsonResponse.put("maxHeight", vertMax);

    return jsonResponse;

  } 

  public ObjectNode tableTop(){
    
    double iDisp = this.height;
    double VeloH = this.initialSpeed;

    //flight time, (d = Vot = (1/2)at^2), (t = Sqrt((2d)/a))
    double fallTime = Math.sqrt(Math.abs((2 * iDisp)/-9.81));
    
    //horizontal displacement, a = 0 m/s^2, (d = Vot + (1/2)at^2)
    double hDisp = VeloH * fallTime;
    
    //vertical final velocity, (Vf = Vo + at), (Vf = at)
    double fVeloV = ((-9.81) * fallTime);
    
    //true final velocity, (c = sqrt(a^2 + b^2)), (tan(theta) = V/H)
    double fVelo = Math.sqrt((VeloH * VeloH) + (fVeloV * fVeloV));
    
    //angle at final velocity, (tan(theta) = (Vf vert/Vf hor))
    double theta = Math.toDegrees(Math.atan(fVeloV/VeloH));

    jsonResponse.put("totalFallTime", fallTime);
    jsonResponse.put("totalHorizontalDisplacement", hDisp);
    jsonResponse.put("totalVerticalVelocity", fVeloV);
    jsonResponse.put("trueFinalVelocity", fVelo);
    jsonResponse.put("angleAtFinalVelocity", theta);

    return jsonResponse;


  }

  public ObjectNode cliff(){

    double theta = this.angle;
    double veloI = this.initialSpeed;

    //initial vertical and horizontal velo.
    double iVeloH = veloI * (Math.cos(Math.toRadians(theta)));
    double iVeloV = veloI * (Math.sin(Math.toRadians(theta)));
    
    //total vertical time, (Vf = -Vo), (a = -9.81 m/s^2), (t = (Vf-Vo)/a)
    double flightTime = (((-iVeloV) - iVeloV)/-9.81);
    
    //using time to determine horizontal displacement, a = 0 m/s^2, (d = Vot + (1/2)at^2)
    double horDisp = iVeloH * flightTime;
    
    //projectile's max height, t = 1/2(flightTime), (d = Vot + (1/2)at^2)
    double vertMax = ((iVeloV * (.5 * flightTime)) + (.5 * (-9.81) * ((.5 * flightTime) * (.5 * flightTime))));


    jsonResponse.put("initialVerticalVelocity", iVeloV);
    jsonResponse.put("initialHorizontalVelocity", iVeloH);
    jsonResponse.put("flightTime", flightTime);
    jsonResponse.put("horizontalDisplacement", horDisp);
    jsonResponse.put("maxHeight", vertMax);

    return jsonResponse;

  }

}
