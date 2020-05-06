<?php
namespace model;
class RecepIngredientModel{

  private $recept_id;
  private $ingredient_name;

  public function __construct(Int $recept_id, String $ingredient_name = null){
      $this->recept_id = $recept_id;
      $this->ingredient_name = $ingredient_name;
  }


  public function getReceptId() : Int{
    return $this->recept_id;
  }

  public function setReceptId(Int $recept_id): void{
    $this->recept_id = $recept_id;
  }

  public function getIngredientName() : String{
    return $this->ingredient_name;
  }

  public function setIngredientName(String $ingredient_name){
    $this->ingredient_name = $ingredient_name;
  }

}

 ?>
