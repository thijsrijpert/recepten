<?php
namespace model;
require_once(dirname(__FILE__,1) . '/Model.php');
require_once(dirname(__FILE__,1) . '/Update.php');
require_once(dirname(__FILE__,2) . '/exception/ModelNullException.php');
class Ingredient extends \model\Model{
  private $name;
  private $description;
  private $is_approved;
  private $username;

  public function __construct(string $name = null, String $description = null, int $is_approved = null, String $username = null){
    $this->name = $name;
    $this->description = $description;
    $this->is_approved = $is_approved;
    $this->username = $username;
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

  public function setIs_approved(int $is_approved){
    $this->is_approved = $is_approved;
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

  public function getVariables(){
      return [['name'], ['description'], ['is_approved'], ['username']];
  }

  public function getUpdateVariables(){
      return [['name'], ['description'], ['is_approved']];
  }

  public function jsonSerialize(){

    $json_name = '"name" : ' . '"' . $this->name . '"';
    $json_description = '"description" : ' . '"' . $this->description . '"';
    $json_is_approved = '"is_approved" : ' . '"' . $this->is_approved . '"';
    $json_username = '"username" : ' . '"' . $this->username . '"';
    $final_string = "{";

    if($this->name != null){
      $final_string .= $json_name;
    }

    if($this->description != null){
      if($this->name !== null){
        $final_string .= ",";
      }
      $final_string .= $json_description;
    }

    if($this->is_approved !== null){
      if($this->description !== null || $this->name !== null){
        $final_string .= ",";
      }
      $final_string .= $json_is_approved;
    }

    if($this->username !== null){
      if($this->is_approved !== null || $this->description !== null || $this->name !== null){
        $final_string .= ",";
      }
      $final_string .= $json_username;
    }

    $final_string .= "}";
    //\var_dump($json_username);
    return \json_decode($final_string);
  }

}
