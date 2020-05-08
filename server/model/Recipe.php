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

  public function __construct(Int $id, String $name, String $description, Int $landcode,
  String $username, String $mealtype_name, Int $religion_id, String $time_of_day, int $is_approved   = null){
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

}

 ?>
