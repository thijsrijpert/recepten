<?php
namespace model;

require_once(dirname(__FILE__,1) . '/Model.php');
require_once(dirname(__FILE__,2) . '/exception/ModelNullException.php');

class Recipe extends \model\Model{

  private $id;
  private $name;
  private $description;
  private $isApproved;
  private $countrycode;
  private $username;
  private $mealtype_name;
  private $religion_id;
  private $time_of_day;

  public function __construct(Int $id = null, String $name = null, String $description = null, int $isApproved = null, String $countrycode = null,
  String $username = null, String $mealtype_name = null, Int $religion_id = null, String $time_of_day = null){
    $this->id = $id;
    $this->name = $name;
    $this->description = $description;
    $this->isApproved = $isApproved;
    $this->countrycode = $countrycode;
    $this->username = $username;
    $this->mealtype_name = $mealtype_name;
    $this->religion_id = $religion_id;
    $this->time_of_day = $time_of_day;
  }

  public function getId() : int{
      if($this->id !== null){
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
      if($this->isApproved !== null){
          return $this->isApproved;
      }
      throw new \exception\ModelNullException("The value isApproved is null");
  }

  public function setIs_approved(int $isApproved){
    $this->isApproved = $isApproved;
  }


  public function getCountrycode() : String{
      if($this->countrycode !== null){
          return $this->countrycode;
      }
      throw new \exception\ModelNullException("The value countrycode is null");
  }

  public function setCountrycode(String $countrycode){
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

  public function setReligion_id(Int $religion_id){
    $this->religion_id = $religion_id;
  }

  public function setTime_of_day(String $time_of_day){
    $this->time_of_day = $time_of_day;
  }

  public function getTime_of_day() : String{
      if($this->time_of_day !== null){
          return $this->time_of_day;
      }
      throw new \exception\ModelNullException("The value time_of_day is null");
  }

  public function getVariables(){
      return [['id'], ['name'], ['description'], ['isApproved'], ['countrycode'], ['username'], ['mealtype_name'], ['religion_id'],
      ['time_of_day']];
  }

  public function jsonSerialize(){

    $json_id = '"id" : ' . '"' . $this->id . '"';
    $json_name = '"name" : ' . '"' . $this->name . '"';
    $json_description = '"description" : ' . '"' . $this->description . '"';
    $json_is_approved = '"isApproved" : ' . '"' . $this->isApproved . '"';
    $json_countrycode = '"countrycode" : ' . '"' . $this->countrycode . '"';
    $json_username = '"username" : ' . '"' . $this->username . '"';
    $json_mealtype_name= '"mealtype_name" : ' . '"' . $this->mealtype_name . '"';
    $json_religion_id = '"religion_id" : ' . '"' . $this->religion_id . '"';
    $json_time_of_day = '"time_of_day" : ' . '"' . $this->time_of_day . '"';

    $final_string = "{";

    if($this->id != null){
      $final_string .= $json_id;
    }

    if($this->name != null){
      if($this->id !== null){
        $final_string .= ",";
      }
      $final_string .= $json_name;
    }

    if($this->description != null){
      if($this->name !== null  || $this->id !== null){
        $final_string .= ",";
      }
      $final_string .= $json_description;
    }

    if($this->isApproved !== null){
      if($this->description !== null  || $this->name !== null  || $this->id !== null){
        $final_string .= ",";
      }
      $final_string .=  $json_is_approved;
    }

    if($this->countrycode != null){
      if($this->isApproved !== null  || $this->description !== null  || $this->name !== null  || $this->id !== null){
        $final_string .= ",";
      }
      $final_string .= $json_countrycode;
    }

    if($this->username != null){
      if($this->countrycode !== null  || $this->isApproved !== null  || $this->description !== null  || $this->name !== null  || $this->id !== null){
        $final_string .= ",";
      }
      $final_string .= $json_username;
    }

    if($this->mealtype_name != null){
      if($this->username !== null  || $this->countrycode !== null  || $this->isApproved !== null  || $this->description !== null  || $this->name !== null  || $this->id !== null){
        $final_string .= ",";
      }
      $final_string .= $json_mealtype_name;
    }

    if($this->religion_id !== null){
      if($this->mealtype_name !== null  || $this->username !== null  || $this->countrycode !== null  || $this->isApproved !== null  || $this->description !== null  || $this->name !== null  || $this->id !== null){
        $final_string .= ",";
      }
      $final_string .= $json_religion_id;
    }

    if($this->time_of_day != null){
      if($this->religion_id !== null  || $this->mealtype_name !== null  || $this->username !== null  || $this->countrycode !== null  || $this->isApproved !== null  || $this->description !== null  || $this->name !== null  || $this->id !== null){
        $final_string .= ",";
      }
      $final_string .= $json_time_of_day;
    }


    $final_string .= "}";
    //\var_dump($final_string);
    return \json_decode($final_string);
  }
}
