<?php
namespace model;
class IngredientModel{
  private $name;
  private $description;

  public function __construct(string $name, String $description, int $is_approved, String $username = null){
    $this->name = $name;
    $this->description = $description;
    $this->is_approved = $is_approved;
    $this->username = $username;
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

  public function getIs_approved() : int{
    return $this->is_approved;
  }

  public function setIs_approved(int $is_approved) : void{
    return $this->is_approved = $is_approved;
  }

  public function getUsername() : String{
    return $this->username;
  }

  public function setUsername(String $username){
    $this->name = $username;
  }


}


 ?>
