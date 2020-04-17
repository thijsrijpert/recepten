<?php

class ReceptModel{
  private $id;
  private $name;
  private $description;
  private $landcode;
  private $username;

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

  public function setLandcode(Int $landcode){
    $this->landcode = $landcode;
  }

  public function getUsername() : String{
    return $this->username;;
  }

  public function setUsername(String $username){
    $this->username = $username;
  }


}

 ?>
