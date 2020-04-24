<?php
class IngredientModel{
  private $name;
  private $description;

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

}


 ?>
