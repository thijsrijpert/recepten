<?php
namespace model;
class RecepIngredientModel{

  private $recept_id;
  private $ingredient_name;

  public function __construct(Int $recept_id = null, String $ingredient_name = null){
      $this->recept_id = $recept_id;
      $this->ingredient_name = $ingredient_name;
  }

  public function getReceptId() : int{
      if($this->recept_id != null){
          return $this->recept_id;
      }
      throw new \exception\ModelNullException("The value recipe_id is null");
  }

  public function setReceptId(Int $recept_id): void{
    $this->recept_id = $recept_id;
  }

  public function getIngredientName() : String{
      if($this->ingredient_name != null){
          return $this->ingredient_name;
      }
      throw new \exception\ModelNullException("The value ingredient_name is null");
  }

  public function setIngredientName(String $ingredient_name){
    $this->ingredient_name = $ingredient_name;
  }

  public function getVariables(){
      return [['recept_id'], ['ingredient_name']];
  }

  public function jsonSerialize() {
      if($this->recept_id != null && $this->ingredient_name != null){
        return [
            'recept_id' => $this->recept_id,
            'ingredient_name' => $this->ingredient_name,
        ];
      }else if($this->recept_id == null){
          return [
              'id' => $this->recept_id,
          ];
      }else {
          return [
              'ingredient_name' => $this->ingredient_name,
          ];
      }
  }

}

 ?>
