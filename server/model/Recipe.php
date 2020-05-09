<?php
namespace model;

require_once(dirname(__FILE__,1) . '/Model.php');
class Recipe extends \model\Model{
  private $id;
  private $name;
  private $description;
  private $is_approved;
  private $countrycode;
  private $username;
  private $mealtype_name;
  private $religion_id;
  private $time_of_day;

  public function __construct(Int $id = null, String $name = null, String $description = null, int $is_approved = null, String $countrycode = null,
  String $username, String $mealtype_name = null, Int $religion_id = null, String $time_of_day = null){
    $this->id = $id;
    $this->name = $name;
    $this->description = $description;
    $this->is_approved = $is_approved;
    $this->countrycode = $countrycode;
    $this->username = $username;
    $this->mealtype_name = $mealtype_name;
    $this->religion_id = $religion_id;
    $this->time_of_day = $time_of_day;
  }

  public function getId() : int{
      if($this->name !== null){
          return $this->id;
      }
      throw new \exception\ModelNullException("The value id is null");
  }


  public function setId(Int $id){
    $this->id = $id;
  }

  public function getName() : String{
      if($this->name !== null){
          return $this->name;
      }
      throw new \exception\ModelNullException("The value name is null");
  }

  public function setName(String $name){
    $this->name = $name;
  }

  public function getDescription() : String{
      if($this->description !== null){
          return $this->description;
      }
      throw new \exception\ModelNullException("The value description is null");
  }

  public function setDescription(String $description){
    $this->description = $description;
  }

  public function getIs_approved() : int{
      if($this->is_approved !== null){
          return $this->is_approved;
      }
      throw new \exception\ModelNullException("The value is_approved is null");
  }

  public function setIs_approved(int $is_approved) : void{
    $this->is_approved = $is_approved;
  }


  public function getCountrycode() : String{
      if($this->countrycode !== null){
          return $this->countrycode;
      }
      throw new \exception\ModelNullException("The value countrycode is null");
  }

  public function setCountrycode(String $countrycode): void{
    $this->countrycode = $countrycode;
  }

  public function getUsername() : String{
      if($this->username !== null){
          return $this->username;
      }
      throw new \exception\ModelNullException("The value username is null");
  }

  public function setUsername(String $username){
    $this->username = $username;
  }

  public function getMealtype_name() : String{
      if($this->mealtype_name !== null){
          return $this->mealtype_name;
      }
      throw new \exception\ModelNullException("The value mealtype_name is null");
  }

  public function setMealtype_name(String $mealtype_name){
    $this->mealtype_name = $mealtype_name;
  }

  public function getReligion_id() : String{
      if($this->religion_id !== null){
          return $this->religion_id;
      }
      throw new \exception\ModelNullException("The value religion_id is null");
  }

  public function setReligion_id(Int $religion_id): void{
    $this->religion_id = $religion_id;
  }

  public function setTime_of_day() : ?String{
    return $this->time_of_day;
  }

  public function getTime_of_day() : String{
      if($this->time_of_day !== null){
          return $this->time_of_day;
      }
      throw new \exception\ModelNullException("The value time_of_day is null");
  }

  public function getVariables(){
      return [['id'], ['name'], ['description'], [' is_approved'], ['countrycode'], ['username'], ['mealtype_name'], ['religion_id'],
      ['time_of_day']];
  }

  public function jsonSerialize(){

    $json_name = "'name' => $this->name,";
    $json_description = "'description' => $this->description,";
    $json_is_aproved = "'is_approved' => $this->is_approved,";
    $json_countrycode = "'countrycode' => $this->countrycode,";
    $json_username= "'username' => $this->username,";
    $json_religion_id = "'religion_id' => $this->religion_id,";
    $json_time_of_day = "'time_of_day' => $this->time_of_day,";

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
    return \json_encode($final_string);
  }
}
