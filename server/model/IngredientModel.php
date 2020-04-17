<?php
class IngredientModel{
  private $name;
  private $description;

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

}


 ?>
