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

  function insert(array $array, int $recipe_id) : String{
    $sql = "INSERT INTO Recipe_Ingredient (recipe_id, ingredient_name) VALUES ";


    $i = 0;
    /*foreach ($array as $value) {
      if($i+1 != count($array)){
        $sql .= "(:recipe_id$i, :ingredient_name$i),";
        $i++;
      }else{
        $sql .= "(:recipe_id$i, :ingredient_name$i)";
        $i++;
      }
    }*/

    foreach ($array as $value) {
      if($i+1 != count($array)){
        $sql .= "(?,?),";
        $i++;
      }else{
        $sql .= "(?,?)";
        $i++;
      }
    }

    \var_dump($sql);
    \var_dump($array);

    $this->stmt = \database\Database::getConnection()->prepare($sql);

    /*$k = 0;
    foreach ($array as  $value) {
      $recipe_id = (int)$recipe_id;
      \var_dump($recipe_id);
      $this->stmt->bindParam(":recipe_id$k", $recipe_id);
      $this->stmt->bindParam(":ingredient_name$k", $value);
      var_dump(":ingredient_name$k");
      \var_dump($k);
      $k++;
    }*/
    $newArray = [];
    foreach ($array as $value) {
      $newArray[] = $recipe_id;
      $newArray[] = $value;
    }
    \var_dump($newArray);

    $this->stmt->execute($newArray);
    \var_dump($this->stmt);

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
        $this->update[0]->bindParam(':recipe_id', $model->getRecipeId());
    }catch(\exception\ModelNullException $e){}

    try{
        $this->update[0]->bindParam(':recipe_id', $modelOld->getRecipeId());
    }catch(\exception\ModelNullException $e){}

    try{
        $this->update[0]->bindParam(':ingredient_name', $model->getIngredientName());
    }catch(\exception\ModelNullException $e){}

    try{
        $this->update[0]->bindParam(':ingredient_name', $modelOld->getIngredientName());
    }catch(\exception\ModelNullException $e){}

      $this->update[0]->execute();

      return $this->update[0]->errorCode();
  }



  function error_handler($errno, $errstr, $errfile, $errline){

  }

}



 ?>
