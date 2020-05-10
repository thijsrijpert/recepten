<?php
namespace model;
class Recipe{
  private $id;
  private $name;
  private $description;
  private $landcode;
  private $username;
  private $mealtype_name;
  private $religion_id;
  private $time_of_day;
  private $is_approved;

  public function __construct(Int $id = null, String $name = null, String $description = null, Int $landcode = null,
  String $username = null, String $mealtype_name = null, Int $religion_id = null, String $time_of_day = null, int $is_approved   = null){
    $this->id = $id;
    $this->name = $name;
    $this->description = $description;
    $this->landcode = $landcode;
    $this->username = $username;
    $this->mealtype_name = $mealtype_name;
    $this->religion_id = $religion_id;
    $this->time_of_day = $time_of_day;
    $this->is_approved = $is_approved;
  }

  public function getId() : Int{
    return $this->id;
  }

  public function setId(Int $id){
    $this->id = $id;
  }

  public function getName() : String{
    return $this->name;
  }

  public function setName(String $name){
    $this->name = $name;
  }

  public function getDescription() : String{
    return $this->description;
  }

  public function setDescription(String $description){
    $this->description = $description;
  }

  public function getLandcode() : Int{
    return $this->landcode;
  }

  public function setLandcode(Int $landcode): void{
    $this->landcode = $landcode;
  }

  public function getUsername() : String{
    return $this->username;;
  }

  public function setUsername(String $username){
    $this->username = $username;
  }

  public function getMealtype_name() : String{
    return $this->mealtype_name;;
  }

  public function setMealtype_name(String $mealtype_name){
    $this->mealtype_name = $mealtype_name;
  }

  public function getReligion_id() : Int{
    return $this->religion_id;;
  }

  public function setReligion_id(Int $religion_id): void{
    $this->religion_id = $religion_id;
  }

  public function setTime_of_day() : String{
    return $this->time_of_day;
  }

  public function getTime_of_day(String $time_of_day){
    $this->time_of_day = $time_of_day;
  }

  public function getIs_approved() : int{
    return $this->is_approved;
  }

  public function setIs_approved(int $is_approved) : void{
    $this->is_approved = $is_approved;
  }

  public function jsonSerialize(){

    $json_name = "'id' => $this->id,";
    $json_description = "'description' => $this->description,";
    $json_countrycode = "'countrycode' => $this->countrycode,";
    $json_username= "'username' => $this->username,";
    $json_religion_id = "'religion_id' => $this->religion_id,";
    $json_time_of_day = "'time_of_day' => $this->time_of_day,";
    $json_is_aproved = "'is_approved' => $this->is_approved,";
    $final_string = "[";

    if($json_name != null){
      $final_string .= $json_name;
    }

    if($json_description != null){
      $final_string .= $json_description;
    }

    if($json_countrycode != null){
      $final_string .= $json_countrycode;
    }

    if($json_religion_id != null){
      $final_string .= $json_religion_id;
    }

    if($json_time_of_day != null){
      $final_string .= $json_time_of_day;
    }

    if($json_is_aproved != null){
      $final_string .= $json_is_aproved;
    }

    $final_string .= "]";
    return \html_entity_decode($final_string);
  }

}

 ?>
