<?php
namespace model;
require_once(dirname(__FILE__,1) . '/Model.php');
require_once(dirname(__FILE__,2) . '/exception/ModelNullException.php');

class Recipe_Ingredient extends  \model\Model{

  private $recipe_id;
  private $ingredient_name;

  public function __construct(Int $recipe_id = null, String $ingredient_name = null){
      $this->recipe_id = $recipe_id;
      $this->ingredient_name = $ingredient_name;
  }

  public function getRecipeId() : int{
      if($this->recipe_id !== null){
          return $this->recipe_id;
      }
      throw new \exception\ModelNullException("The value recipe_id is null");
  }

  public function setRecipeId(Int $recipe_id){
    $this->recipe_id = $recipe_id;
  }

  public function getIngredientName() : String{
      if($this->ingredient_name !== null){
          return $this->ingredient_name;
      }
      throw new \exception\ModelNullException("The value ingredient_name is null");
  }

  public function setIngredientName(String $ingredient_name){
    $this->ingredient_name = $ingredient_name;
  }

  public function getVariables(){
      return [['recipe_id'], ['ingredient_name']];
  }

  public function jsonSerialize() {
      if($this->recipe_id != null && $this->ingredient_name != null){
        return [
            'recipe_id' => $this->recipe_id,
            'ingredient_name' => $this->ingredient_name,
        ];
      }else if($this->recipe_id != null){
          return [
              'recipe_id' => $this->recipe_id,
          ];
      }else {
          return [
              'ingredient_name' => $this->ingredient_name,
          ];
      }
  }

}
