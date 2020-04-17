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
    return $this->id;
  }

  public function getName() : String{
    return $this->name;
  }

  public function setName(String $name){
    return $this->name;
  }

  public function setDescription() : String{
    return $this->description;
  }

  public function getDescription(String $description){
    $this->description = $description;
  }

  public function setLandcode() : Int{
    return $this->landcode;
  }

  public function getLandcode(Int $landcode){
    $this->landcode = $landcode;
  }

  public function setUsername() : String{
    return $this->username;
  }

  public function getUsername(String $username){
    $this->username = $username;
  }

}

 ?>
