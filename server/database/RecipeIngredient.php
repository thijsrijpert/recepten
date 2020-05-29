<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
require_once(dirname(__FILE__,2) . '/model/RecipeIngredient.php');
class RecipeIngredient extends CRUD{

  function __construct(QueryBuilderParent ...$query){
    $sql = "INSERT INTO Recipe_Ingredient (recipe_id, ingredient_name) VALUES (:recipe_id , :ingredient_name)";
    $this->stmt = \database\Database::getConnection()->prepare($sql);

    parent::__construct($query);
  }

  function insert(\model\Model $model) : String{
    $recipe_id = $model->getRecipeId();
    $ingredient_name = $model->getIngredientName();
    $this->stmt->bindParam(':recipe_id', $recipe_id);
    $this->stmt->bindParam(':ingredient_name', $ingredient_name);

    $this->stmt->execute();

    return $this->stmt->errorcode();
  }

  function select(\model\Model $model) : array{
    try{
        $this->select[0]->bindParam(':recipe_id', $model->getRecipeId());
    }catch(\exception\ModelNullException $e){}

    try{
        $this->select[0]->bindParam(':ingredient_name', $model->getIngredientName());
    }catch(\exception\ModelNullException $e){}



    $this->select[0]->execute();

    $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Recipe_Ingredient');

    return array($this->select[0]->errorCode(), array($results));
  }

  function update(\model\Model $model, \model\Model $modelOld) : String{

    try{
        $this->select[0]->bindParam(':recipe_id', $model->getRecipeId());
    }catch(\exception\ModelNullException $e){
        throw new NullPointerException($e->getMessage());
    }

    try{
        $this->select[0]->bindParam(':recipe_id', $modelOld->getRecipeId());
    }catch(\exception\ModelNullException $e){
        throw new NullPointerException($e->getMessage());
    }

    try{
        $this->select[0]->bindParam(':ingredient_name', $model->getIngredientName());
    }catch(\exception\ModelNullException $e){
        throw new NullPointerException($e->getMessage());
    }

    try{
        $this->select[0]->bindParam(':ingredient_name', $modelold->getIngredientName());
    }catch(\exception\ModelNullException $e){
        throw new NullPointerException($e->getMessage());
    }

      $this->update[0]->execute();

      return $this->update[0]->errorCode();
  }



  function error_handler($errno, $errstr, $errfile, $errline){

  }

}



 ?>
