<?php
class RecepIngredientModel{

  private $recept_id;
  private $ingredient_name;


  public function getReceptId() : Int{
    return $this->recept_id;
  }

  public function setReceptId(Int $recept_id){
    return $this->recept_id;
  }

  public function getIngredientName() : String{
    return $this->ingredient_name;
  }

  public function setIngredientName(String $ingredient_name){
    return $this->ingredient_name;
  }

}

 ?>
